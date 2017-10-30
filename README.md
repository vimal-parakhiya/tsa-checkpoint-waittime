# tsa-checkpoint-waittime

## Build 

```mvn clean install -f tsa-waittime-api/pom.xml```

## Run

```java -jar tsa-waittime-api/target/tsa-waittime-api-1.0-SNAPSHOT.jar```

## API

### Fetch wait time of all security checkpoints

```curl http://localhost:8090/airports/ATL```

### Fetch wait time of a security checkpoint

```curl http://localhost:8090/airports/ATL/security-checkpoints/South```
