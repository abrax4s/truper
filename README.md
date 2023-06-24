# Examen Práctico Truper

Examen de conocimientos técnicos de acceso a Truper.
Se utilizan las siguientes librerías:
-Spring JPA
-Spring H2
-Spring Web
-Spring Security

## Operaciones

**Login**

A continuación se especifican las operaciones que incluye el proyecto:

###Login

```
curl --request POST \
  --url 'http://localhost:8080/login?username=admin&pwd=Truper123' \
```
### Crear nueva lista

```
curl --request POST \
  --url http://localhost:8080/purchases \
  --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJ0cnVwZXJKV1QiLCJzdWIiOiJhZG1pbiIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2ODc1NjgwMTUsImV4cCI6MTY4NzY1NDQxNX0.7uTmenv7-P50l0cEqh6Lpf7q-mKiuvhHBomAR-_gJto' \
  --header 'Content-Type: application/json' \
  --data '{
	"clientId":720010,
	"purchaseList":{
		"Lista Mensual":[
			{
				"productId":18156,
				"quantity":5
			}
		]
	}
}'
```
### Actualizar lista:
```
curl --request PUT \
  --url http://localhost:8080/purchases \
  --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJ0cnVwZXJKV1QiLCJzdWIiOiJhZG1pbiIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2ODc1NjgwMTUsImV4cCI6MTY4NzY1NDQxNX0.7uTmenv7-P50l0cEqh6Lpf7q-mKiuvhHBomAR-_gJto' \
  --header 'Content-Type: application/json' \
  --data '{
	"clientId":720010,
	"purchaseList":{
		"Lista Actualizada":[
			{
				"productId":25020,
				"quantity":2
			},
			{
				"productId":10170,
				"quantity":9
			}
		]
	}
}'
```
### Consultar listas por cliente
```
curl --request GET \
  --url http://localhost:8080/purchases/720010 \
  --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJ0cnVwZXJKV1QiLCJzdWIiOiJhZG1pbiIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2ODc1NjgwMTUsImV4cCI6MTY4NzY1NDQxNX0.7uTmenv7-P50l0cEqh6Lpf7q-mKiuvhHBomAR-_gJto' \
```
### Eliminar una lista
```
curl --request DELETE \
  --url http://localhost:8080/purchases/1 \
  --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJ0cnVwZXJKV1QiLCJzdWIiOiJhZG1pbiIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2ODc1NjgwMTUsImV4cCI6MTY4NzY1NDQxNX0.7uTmenv7-P50l0cEqh6Lpf7q-mKiuvhHBomAR-_gJto' \
```
