FROM adoptopenjdk/openjdk11:alpine-jre
ARG VERSION=0.0.1-SNAPSHOT

EXPOSE 8087

ADD target/eCommercePlatform-BackOffice-${VERSION}.war app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
