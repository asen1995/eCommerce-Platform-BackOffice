FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 8087
ADD target/eCommercePlatform-BackOffice-0.0.1-SNAPSHOT.war app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
