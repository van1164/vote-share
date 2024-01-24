FROM openjdk:18
ARG JAR_FILE_PATH=voteshare-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE_PATH} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]