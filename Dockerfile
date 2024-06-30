FROM openjdk:8
EXPOSE 8089
ADD /target/eventsProject-1.0.0-SNAPSHOT.jar  events.jar
ENTRYPOINT ["java", "-jar", "events.jar"]
