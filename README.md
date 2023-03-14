
Docker containers setup:

1.Oracle database example

download open source oracle database from oracle container registry


https://container-registry.oracle.com/ords/f?p=113:4:18029637061400:::4:P4_REPOSITORY,AI_REPOSITORY,AI_REPOSITORY_NAME,P4_REPOSITORY_NAME,P4_EULA_ID,P4_BUSINESS_AREA_ID:803,803,Oracle%20Database%20Express%20Edition,Oracle%20Database%20Express%20Edition,1,0&cs=36mOR4cSDCi0CsSmQrfyV7xyjCjdlmQhdbv5zMrnB2XUHTWNqpQ6GiUpxE1znrw0z2o8jXUUeK-lqwr4r3BWl4A


```console
docker pull container-registry.oracle.com/database/express:latest
```

Start the oracle database container

docker run --name <container name> \
-p <host port>:1521 -p <host port>:5500 \
-e ORACLE_PWD=<your database passwords> \
-e ORACLE_CHARACTERSET=<your character set> \
-v [<host mount point>:]/opt/oracle/oradata \
container-registry.oracle.com/database/express:latest


```console
docker run --name back-office-service-oracle-database-container -p 1522:1521 -p 5502:5500 -e ORACLE_PWD=asen311 container-registry.oracle.com/database/express:latest
```
