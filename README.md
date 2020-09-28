## jhipster-demo

Demo project to observe inter-operation between a jhipster-uaa and jhipster-generated microservice applications

#### What I want to achieve

- NO jhipster registry (Eureka & Config server)
- Assume that Kubernetes service and kube-dns take the role of service discovery

![](jhipster-demo.png)
- #0. `demo1` and `demo2` periodically sync [token_key](docker/cluster-compose.yml#L43) with `uaa`
- #1. A client logs in to `uaa` with "password" grant and gets an "access_token"
- #2. The client try to access protected resource in `demo1` by providing the "access_token" acquired in #1
- #3. Upon receiving the request(#2), `demo1` logs in to `uaa` with "client_credentials" grand and get an "access_token"
- #4. `demo1` try to access protected resource in `demo2` by providing the "access_token" acquired in #3

Finally, `demo1` gets the resource from `demo2` and respond to the client.

### Build & Run

```bash
~/jhipster-demo $ bash build.sh
~/jhipster-demo $ bash run.sh
~/jhipster-demo $ bash test.sh
#
# Login to the UAA with a "client_credentials" grant
#
# REQUEST:
# curl -s -X POST --data "grant_type=client_credentials" http://internal:internal@localhost:9999/oauth/token
#
# RESPONSE:
# {
#   "access_token": "ey..Ww",
#   "token_type": "bearer",
#   "expires_in": 299,
#   "scope": "web-app",
#   "iat": 1601302590,
#   "jti": "eDUSpAuRoYk1wE_2nKarlmIrj3s"
# }
#
# => SUCCESS
#
# ---
# Login to the UAA with a "password" grant
#
# REQUEST:
# curl -s -X POST --data "username=user&password=user&grant_type=password&scope=openid" http://web_app:changeit@localhost:9999/oauth/token
#
# RESPONSE:
# {
#   "access_token": "ey..pQ",
#   "token_type": "bearer",
#   "refresh_token": "ey..3A",
#   "expires_in": 299,
#   "scope": "openid",
#   "iat": 1601302590,
#   "jti": "LOn6oXubU2siTKQgjnSI01lgjJE"
# }
#
# => SUCCESS
#
# ---
#
# Accessing a micro service's resource with the access_token
#
# REQUEST:
# curl -sSf -H "Authorization: bearer ey..pQ" http://localhost:8081/api/demo1
#
# This is demo1
```

### Notable UAA api endpoints
endpoint|description
---|---
`GET /oauth/token_key`|JWT public token key
`POST /oauth/token`|login


### Commands that I used to build up this project

Creating an UAA service

```bash
~/jhipster-demo $ mkdir uaa && cd uaa
~/jhipster-demo/uaa $ jhipster
# ? Which *type* of application would you like to create? JHipster UAA server
# ? What is the base name of your application? uaa
# ? As you are running in a microservice architecture, on which port would like your server to run? It should be unique to avoid port conflicts
# . 9999
# ? What is your default Java package name? dev.appkr.uaa
# ? Which service discovery server do you want to use? No service discovery
# ? Which *type* of database would you like to use? SQL (H2, MySQL, MariaDB, PostgreSQL, Oracle, MSSQL)
# ? Which *production* database would you like to use? MySQL
# ? Which *development* database would you like to use? MySQL
# ? Do you want to use the Spring cache abstraction? Yes, with the Caffeine implementation (local cache, for a single node)
# ? Do you want to use Hibernate 2nd level cache? No
# ? Would you like to use Maven or Gradle for building the backend? Gradle
# ? Which other technologies would you like to use? API first development using OpenAPI-generator
# ? Would you like to enable internationalization support? No
# ? Besides JUnit and Jest, which testing frameworks would you like to use?
# ? Would you like to install other generators from the JHipster Marketplace? No
```

Creating a micro service

```bash
~/jhipster-demo $ mkdir demo1 && cd demo1
~/jhipster-demo/demo1 $ jhipster
# ? Which *type* of application would you like to create? Microservice application
# ? [Beta] Do you want to make it reactive with Spring WebFlux? No
# ? What is the base name of your application? demo1
# ? As you are running in a microservice architecture, on which port would like your server to run? It should be unique to avoid port conflicts. 8081
# ? What is your default Java package name? dev.appkr.demo1
# ? Which service discovery server do you want to use? No service discovery
# ? Which *type* of authentication would you like to use? Authentication with JHipster UAA server (the server must be generated separately)
# ? What is the folder path of your UAA application? ../uaa
# ? Which *type* of database would you like to use? SQL (H2, MySQL, MariaDB, PostgreSQL, Oracle, MSSQL)
# ? Which *production* database would you like to use? MySQL
# ? Which *development* database would you like to use? MySQL
# ? Do you want to use the Spring cache abstraction? Yes, with the Caffeine implementation (local cache, for a single node)
# ? Do you want to use Hibernate 2nd level cache? No
# ? Would you like to use Maven or Gradle for building the backend? Gradle
# ? Which other technologies would you like to use? API first development using OpenAPI-generator
# ? Would you like to enable internationalization support? No
# ? Besides JUnit and Jest, which testing frameworks would you like to use?
# ? Would you like to install other generators from the JHipster Marketplace? No
```
