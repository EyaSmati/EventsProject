FROM openjdk:8
EXPOSE 8089
ADD /target/events.jar  events.jar
ENTRYPOINT ["java", "-jar", "events.jar"]