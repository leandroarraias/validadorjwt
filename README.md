# Descrição Geral da Aplicação

### Objetivo

Esta aplicação WEB destina-se à validação de tokens JWT.

A aplicação terá um único serviço exposto ao cliente, que consiste na validação de tokens JWT.

O token é informado à aplicação via requisição de uma API REST, que irá retornar um valor booleano: ``true`` se token 
válido, ``false`` se inválido. Adicionalmente, os status HTTP do response serão ``200 (OK)`` para os tokens válidos 
e ``403 (FORBIDDEN)`` para inválidos.

Caso ocorra algum erro sistêmico durante a requisição, será retornado false no response e status ``500 (INTERNAL_SERVER_ERROR)``.

### Regras de validação do token

- Deve ser um JWT válido
- Deve conter apenas 3 claims (Name, Role e Seed)
- A claim Name não pode ter carácter de números
- A claim Role deve conter apenas 1 dos três valores (Admin, Member e External)
- A claim Seed deve ser um número primo
- O tamanho máximo da claim Name é de 256 caracteres

#### Considerações adicionais sobre as regras de validação
 - Para garantir uma boa performance, uma vez que não especificado o tamanho do seed, a aplicação irá limitá-lo à
Integer.MAX_VALUE (2.147.483.647). Essa limitação também responde à requisitos de segurança, conforme descrito em seção
seguinte
 - Para padronização, o seed informato deverá ser o formato String, e não número
 - O Name foi limitado a caracteres de nomes em Português
 - As claims e roles podem ser case insensitive.

# Arquitetura

### Diagrama de Integração

<img alt="Arquitetura" src="./img/arquitetura.png">

# Segurança
### OWASP Top 10
A aplicação responde (ou contribui para) os seguintes ataques descritos no 
[OWASP API Security Top 10 (2013)](https://owasp.org/API-Security/editions/2023/en/0x11-t10/)
1. Broken Object Level Authorization: A validação do token JWT é primordial para mitigar esse tipo de ataque,
pois antes de verificar a autorização à um determinado recurso, é necessário verificar a validade do token.
2. Broken Object Property Level Authorization: Os dados retornados, inclusive os status, são limitados, dificultando
inclusive um ataque de discovery.
3. Unrestricted Resource Consumption: A aplicação realiza uma validação prévia do tamanho do token enviado antes mesmo
de tentar realizar o parse ou qualquer outra validação que consumirá mais recursos. Adicionalmente, como não houve 
requisito específico, o tamanho do seed foi limitado de maneira a garantir a performance no cálculo de um número primo.
4. Broken Function Level Authorization: A validação do token JWT é primordial para mitigar esse tipo de ataque,
pois antes de verificar a autorização à uma determinada API, é necessário verificar a validade do token.
5. Security Misconfiguration: Tratamento central das exceptions evitando que detalhes do erro, como stacktrace, sejam
retornados na requisição expondo detalhes da implementação da aplicação. Adicionalmente, payloads críticos e impressões
necessárias em logs de informações dadas pelo cliente, são tratadas realizando scaping de caracteres utilizados em
ataques.

#### Observação:
Essa aplicação expõe um endpoint para testes de observability que estaria suscetível à ataques classificados como
``Improper Inventory Management``. Esse endpoint deverá ser exposto "apenas" em ambiente local e de testes, jamais
deva ser exposto em produção. Na classe, foi adicionada a limitação por código por meio da annotation
``@Profile({"!homol", "!prod"})``.

# Pré requisitos
### Java
Certifique-se de que a versão corrente do Java instalado seja a 17. Para verificar, execute o comando ``java -version``. 
O resultado, deverá ser semelhante ao exibido abaixo:

```
D:\workspace\validadorjwt>java -version
openjdk version "17.0.2" 2022-01-18
OpenJDK Runtime Environment (build 17.0.2+8-86)
OpenJDK 64-Bit Server VM (build 17.0.2+8-86, mixed mode, sharing)
```

Caso não possua, realizar o download e instalação conforme [site oficial](https://docs.oracle.com/en/java/javase/17/install/overview-jdk-installation.html#GUID-8677A77F-231A-40F7-98B9-1FD0B48C346A)

### Maven

A aplicação possui o maven wrapper não sendo obrigatória sua instalação na máquina local. Mas caso já o tenha instalado,
certifique-se de que a versão corrente seja a 3.X.X. Para verificar, execute o comando ``mvn -version``. O resultado, 
deverá ser semelhante ao exibido abaixo:

```
D:\workspace\validadorjwt>mvn -version
Apache Maven 3.8.1 (05c21c65bdfed0f71a2f2ada8b84da59348c4c5d)
Maven home: D:\Tecnologia\apache-maven-3.8.1\bin\..
Java version: 17.0.2, vendor: Oracle Corporation, runtime: D:\Tecnologia\java\openjdk-17.0.2
Default locale: pt_BR, platform encoding: Cp1252
OS name: "windows 11", version: "10.0", arch: "amd64", family: "windows"
```

Caso seja necessário nova instalação, realizar o download e configuração conforme [site oficial](https://maven.apache.org/users/index.html)

### Docker

Os sistemas de observability estão configurados para serem utilizados como containers Docker. Portanto, certifique-se de 
que o Docker esteja instalado, executando o comando ``docker --version``. O resultado, deverá ser semelhante ao exibido 
abaixo:

```
D:\workspace\validadorjwt>docker --version
Docker version 20.10.17, build 100c701
```

Caso não possua, realizar o download e instalação conforme [site oficial](https://docs.docker.com/engine/install/)

# Startup da aplicação
1. Realizar o download da aplicação em sua pasta de preferência.
2. Na pasta ``infra`` executar o comando ``docker compose -p validadorjwt up`` para inicializar os containers de observability.
3. Na pasta ``app`` executar o comando ``mvnw spring-boot:run`` para inicializar a aplicação.

# Execução

### Contrato da API

O contrato da API no formato OpenAPI 3 atualiado para geração de clients está disponível para download [aqui](http://localhost:8080/api-docs.yaml), ou 
ser visualizado no browser [aqui](http://localhost:8080/api-docs).

A página de documentação da API no formato Swagger pode ser acessada [aqui](http://localhost:8080/api-docs/swagger.html).

### Execução da API

A execução da validação de um token JWT é relativamente simples. Conforme pode ser observado em mais detalhes na definição 
do contrato informado na seção Contrato da API, para realizar a validação de um token JWT deve-se executar um ``POST`` na URI ``/jwt/validar``, informando o token JWT no body no formato ``text/plain``.

Abaixo segue um exemplo da requisição HTTP para a validação de um dado token:

```
POST /jwt/validar HTTP/1.1
Content-Type: text/plain
Host: localhost:8080
Content-Length: 137

eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJTZWVkIjoiNzg0MSIsIk5hbWUiOiJUb25pbmhvIEFyYXVqbyJ9.QY05sIjtrcJnP533kQNk8QXcaleJ1Q01jWY_ZzIZuAg
```

# Observability

As seguintes soluções e recursos foram utilizados para prover observabilidade à aplicação:

### Spring Boot Actuator

O Spring Boot Actuator é um conjunto de recursos adicionais oferecidos pelo Spring Boot para facilitar a monitorização e
gestão de uma aplicação Spring Boot em execução. Ele fornece endpoints que expõem informações sobre a aplicação,
como métricas, informações de saúde, informações sobre propriedades de configuração, entre outros.

### Micrometer

O Micrometer fornece um facade para os sistemas de observability mais populares, permitindo a instrumentação do
código da aplicação baseada em JVM.

Assim como o Spring Boot Actuator, trata-se de um conjunto de dependências adicionadas ao pom.xml da aplicação.

### Opentelemetry collector

O OpenTelemetry Collector (otel collector) oferece uma implementação independente de fornecedor sobre como receber,
processar e exportar dados de telemetria. Ele reduz a necessidade de executar, operar e manter vários agentes/coletores.
Sendo assim, a aplicação é configurada para enviar dados de telemtria apenas para o otel collector, que por sua vez,
às envia para os sistemas de telemetria selecionados.

A configuração dos receivers e exporters estão definidos no arquivo ./infra/cfg/otel-collector.yml. Nele, as seguintes
soluções de telemetria foram configuradas:
- Métricas: Prometheus
- Tracing: Zipkin e Tempo
- Logs: Loki

### Prometheus

O Prometheus é um conjunto de ferramentas de monitoramento de sistemas e alerta de código aberto. Ele coleta e armazena
métricas como dados de séries temporais, ou seja, as informações de métricas são armazenadas com o carimbo de data e
hora no qual foram registradas, juntamente com pares chave-valor opcionais chamados rótulos.

Devido algumas métricas fornecidas pelo Opentelemetry collector divergirem das oferecidas pela biblioteca do Micrometer,
optou-se nessa solução pelo uso de ambas na alimentação de métricas ao Prometheus.

Para acessar o Prometheus: [http://localhost:9090/](http://localhost:9090/)

Home:

<img alt="Prometheus Home" src="./img/prometheus_home.png">

### Zipkin

O Zipkin é um sistema de tracing distribuído. Ele auxilia na coleta de dados temporais necessários para solucionar
problemas de latência em arquiteturas de serviços. Na arquitetura de solução proposta da aplicação, ele é alimentado
pelo Opentelemetry collector.

Aqui, ele foi selecionado em paralelo ao ``Tempo`` devido sua interface mais amigável, enquanto o ``Tempo`` oferece
uma integração mais natural com o Grafana.

Para acessar o Zipkin: [http://localhost:9411/zipkin/](http://localhost:9411/zipkin/)

Home:

<img alt="Zipkin Home" src="./img/zipkin_home.png">

Consulta geral realizada:

<img alt="Zipkin Run Query" src="./img/zipkin_run_query.png">

Tracing selecionado:

<img alt="Zipkin Show" src="./img/zipkin_show.png">

### Loki

Loki é um sistema de agregação de logs escalável, altamente disponível e multi-tenant.

Na solução adotada nessa aplicação, ele é alimentado pelo Opentelemetry collector e os logs propriamente dito podem
ser acessados no Grafana:

Para consultar os logs no Loki:

1. Acessar a opção ``Explore`` no Grafana:

<img alt="Grafana Menu Explore" src="./img/grafana_menu_explore.png">

2. Na página de ``Explore``, selecionar o Loki como datasource e a aplicação no job:

<img alt="Loki Datasource" src="./img/grafana_loki_filter.png">

3. Exemplo de resultado:

<img alt="Loki Result" src="./img/grafana_loki_result.png">

### Tempo

O Grafana Tempo é um sistema de tracing distribuído.

Aqui, ele é utilizado em paralelo ao Zipkin devido sua fácil integraçaõ com o Grafana e Loki, permitindo em um único
sistema a integração entre dashboards, traces e logs. É alimentado pelo Opentelemetry collector e os traces podem
ser acessados no Grafana:

Para consultar os traces no Tempo:

1. Acessar a opção ``Explore`` no Grafana:

<img alt="Grafana Menu Explore" src="./img/grafana_menu_explore.png">

2. (A) Na página de ``Explore``, na aba ``Search``, selecionar o Tempo como datasource e a aplicação no Service Name:

<img alt="Tempo Datasource" src="./img/grafana_tempo_filter.png">

2. (B) Na página de ``Explore``, na aba ``TraceQL``, selecionar o Tempo como datasource e informar um traceId:

<img alt="Tempo Datasource 2" src="./img/grafana_tempo_filter_2.png">

3. Exemplo de resultado:

<img alt="Tempo Result" src="./img/grafana_tempo_result.png">

4. No resultado, é possível identificar os logs associados ao tracing clicando na opção ``Logs for this span``:

<img alt="Tempo Loki Integration" src="./img/grafana_tempo_loki_integration.png">

### Grafana

Grafana é uma plataforma de análise e monitoramento de código aberto que integra dados de diversas fontes em uma
interface visual personalizável.

Nessa solução, utilizamos o Grafana para centralizar as soluções de observability: Loki para logs, Tempo para tracing e
Prometheus para métricas.

Adicionalmente, foram criados 2 dashboards: JVM Micrometer com os dados básicos da saúde da JVM e Requisições HTTP para
métricas de processamento da API de validação de tokens JWT.

Para acessar o Grafana: [http://localhost:3000](http://localhost:3000).

1. Dashboard JVM Micrometer:

<img alt="JVM Micrometer" src="./img/grafana_dash_jvm_micrometer.png">

2. Dashboard Requisições HTTP:

<img alt="Requisições HTTP" src="./img/grafana_dash_http.png">




# validadorjwt
ClaimsValidator
Explicar limitação seed e name
Explicar seed como string
Explicar claims case insensitive

top 10 lentas
top 10 executadas
estastistica logs

alertas de falha
rate limiting