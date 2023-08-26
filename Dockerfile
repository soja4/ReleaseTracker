FROM openjdk:17
ADD /target/docker-release-tracker.jar docker-release-tracker.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "docker-release-tracker.jar"]