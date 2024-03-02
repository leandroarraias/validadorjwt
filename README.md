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

# validadorjwt
API para validação de tokens JWT

ClaimsValidator
Explicar limitação seed e name
Explicar seed como string
Explicar claims case insensitive

ajustar dados impressos nos logs

dashboards necessários:
start time
jvm metrics

quantidade requisições:
- Http Server requests Rates
   - sum by (method,uri,status) (rate(http_server_requests_seconds_count{uri!="/actuator/prometheus"}[1m]))


tempo requisições
media tempo requisições
status requisições
top 10 lentas
top 10 executadas
estastistica logs

alertas de falha

increase(http_server_requests_seconds_count{}[1m])