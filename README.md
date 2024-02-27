# validadorjwt
API para validação de tokens JWT

ClaimsValidator
Explicar limitação seed e name
Explicar seed como string
Explicar claims case insensitive

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