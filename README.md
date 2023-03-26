Docker setup


Run this command from root of the project to build image:

```console
docker build --build-arg VERSION=0.0.1-SNAPSHOT -t ecommerce-back-office-service-image .
```


run image on container:

```console
docker run --name ecommerce-back-office-service -p 8087:8087 ecommerce-back-office-service-image
```
