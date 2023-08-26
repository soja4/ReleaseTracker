# ReleaseTracker

ReleaseTracker is Java/Spring Boot application that allows you to manipulate with releases.

Main features of application are:
- find release by id
- search releases by parameters
- create new release
- update release
- delete relese

Technologies that are used in application:
- Java
- Spring Boot
- Maven
- H2 database
- Docker

## How to run application

Considering that application will be running in Docker, application jar file has to be created first.
```
mvn clean install
```
Application jar file docker-release-tracker.jar can be found in targer folder. After creating jar file you need to create image.
```
docker build -f Dockerfile -t docker-release-ticket .
```
After creating image we need to run docker image in docker container
```
docker run -p 8080:8080 docker-release-tracker
```
Application will be up and running on port 8080

Accessing h2 database --> http://localhost:8080/h2-console

APIs documentation --> http://localhost:8080/swagger-ui/index.html
