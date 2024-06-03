# API Gateway service

## Microservice Features
### Request routing - Single entry point for the application
- As a client you will only need to send your requests here, this microservice will handle the internal routing to request the required resource (authentication is required for most endpoints, except register and login/autenticate).

#### AUTH (See features here --> https://github.com/JosepeDevs/PcRepairAuthService)
- Append /auth/ before your request to send requests to this service (e.g. localhost:7777/authmanager/api/v1/noauth/register), you can find the body requirements in my OAS file and Swagger ReDoc .

#### PERSONS
- Append /persons/ before your request to send requests to this service (e.g. localhost:7777/person/persons), you can find the body requirements in my OAS file and Swagger ReDoc .

## Microservice from project:
### https://github.com/JosepeDevs/PcTallerProject
