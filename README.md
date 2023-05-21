Docker setup


Run this command from root of the project to build image:

```console
docker build --build-arg VERSION=0.0.1-SNAPSHOT -t ecommerce-back-office-service-image .
```


run image on container:

```console
docker run --name ecommerce-back-office-service -p 8087:8087 ecommerce-back-office-service-image
```

Sonaqube analysis

```console


mvn clean verify sonar:sonar \
-Dsonar.projectKey=ecommerce-back-office \
-Dsonar.projectName='ecommerce-back-office' \
-Dsonar.host.url=http://localhost:9000 \
-Dsonar.token=sqp_90efce895dc11a158b804392b88e98668c07200c

```
