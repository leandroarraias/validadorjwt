# Descrição

Aplicação WEB para validação de tokens JWT.

## Funcionalidade básica
A aplicação terá um único serviço exposto ao cliente, que consiste na validação de tokens JWT.

O token é informado à aplcação via requisição de uma API REST, que irá retornar um valor booleano: true se token válido,
false se inválido. Adicionalmente, os status HTTP do response serão 200 (OK) para os tokens válidos e 403 (FORBIDDEN)
para inválidos.

Caso ocorra algum erro sistêmico durante a requisição, será retornado false no response e status 500
(INTERNAL_SERVER_ERROR).

### Regras de validação do token

- Deve ser um JWT válido
- Deve conter apenas 3 claims (Name, Role e Seed)
- A claim Name não pode ter carácter de números
- A claim Role deve conter apenas 1 dos três valores (Admin, Member e External)
- A claim Seed deve ser um número primo
- O tamanho máximo da claim Name é de 256 caracteres

## Pré requisitos
### Java
Certifique-se de que a versão corrente do Java instalado seja a 17. Para verificar, execute o comando ``java -version``. O resultado, deverá ser semelhante ao exibido abaixo:

```
D:\workspace\validadorjwt>java -version
openjdk version "17.0.2" 2022-01-18
OpenJDK Runtime Environment (build 17.0.2+8-86)
OpenJDK 64-Bit Server VM (build 17.0.2+8-86, mixed mode, sharing)
```

Caso não possua, realizar o download e instalação conforme [site oficial](https://docs.oracle.com/en/java/javase/17/install/overview-jdk-installation.html#GUID-8677A77F-231A-40F7-98B9-1FD0B48C346A)

### Maven
Certifique-se de que a versão corrente do Maven instalado seja a 3.X.X. Para verificar, execute o comando ``mvn -version``. O resultado, deverá ser semelhante ao exibido abaixo:

```
D:\workspace\validadorjwt>mvn -version
Apache Maven 3.8.1 (05c21c65bdfed0f71a2f2ada8b84da59348c4c5d)
Maven home: D:\Tecnologia\apache-maven-3.8.1\bin\..
Java version: 17.0.2, vendor: Oracle Corporation, runtime: D:\Tecnologia\java\openjdk-17.0.2
Default locale: pt_BR, platform encoding: Cp1252
OS name: "windows 11", version: "10.0", arch: "amd64", family: "windows"
```

Caso não possua, realizar o download e instalação conforme [site oficial](https://maven.apache.org/users/index.html)

### Docker
Certifique-se de que o Docker esteja instalado, executando o comando ``docker --version``. O resultado, deverá ser semelhante ao exibido abaixo:

```
D:\workspace\validadorjwt>docker --version
Docker version 20.10.17, build 100c701
```

Caso não possua, realizar o download e instalação conforme [site oficial](https://docs.docker.com/engine/install/)

## Startup da aplicação
1. Realizar o download da aplicação em sua pasta de preferência.
2. Na pasta ``infra`` executar o comando ``docker compose -p validadorjwt up`` para inicializar os containers de observability.
3. Na pasta ``app`` executar o comando ``mvnw spring-boot:run`` para inicializar a aplicação.

## Contrato da API

O contrato da API no formato OpenAPI 3 atualiado para geração de clients está disponível para download [aqui](http://localhost:8080/api-docs.yaml), ou ser visualizado no browser [aqui](http://localhost:8080/api-docs).

A página de documentação da API no formato Swagger pode ser acessada [aqui](http://localhost:8080/api-docs/swagger.html).

## Execução da API

A execução da validação de um token JWT é relativamente simples. Conforme pode ser observado em mais detalhes na definição do contrato informado na seção Contrato da API, para realizar a validação de um token JWT deve-se executar um ``POST`` na URI ``/jwt/validar``, informando o token JWT no body no formato ``text/plain``.

Abaixo segue um exemplo da requisição HTTP para a validação de um dado token:

```
POST /jwt/validar HTTP/1.1
Content-Type: text/plain
Host: localhost:8080
Content-Length: 137

eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJTZWVkIjoiNzg0MSIsIk5hbWUiOiJUb25pbmhvIEFyYXVqbyJ9.QY05sIjtrcJnP533kQNk8QXcaleJ1Q01jWY_ZzIZuAg
```

## Arquitetura geral da solução

<img alt="Arquitetura" src="./img/arquitetura.png">

## Observability

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

<img alt="Arquitetura" src="./img/prometheus_home.png">

### Zipkin

O Zipkin é um sistema de tracing distribuído. Ele auxilia na coleta de dados temporais necessários para solucionar 
problemas de latência em arquiteturas de serviços. Na arquitetura de solução proposta da aplicação, ele é alimentado
pelo Opentelemetry collector.

Aqui, ele foi selecionado em paralelo ao ``Tempo`` devido sua interface mais amigável, enquanto o ``Tempo`` oferece
uma integração mais natural com o Grafana.

Para acessar o Zipkin: [http://localhost:9411/zipkin/](http://localhost:9411/zipkin/)

Home:

<img alt="Arquitetura" src="./img/zipkin_home.png">

Consulta geral realizada:

<img alt="Arquitetura" src="./img/zipkin_run_query.png">

Tracing selecionado:

<img alt="Arquitetura" src="./img/zipkin_show.png">
