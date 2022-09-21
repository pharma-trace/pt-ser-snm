# Introduction

Code Repository for the Serial Number Profile Services

# Getting Started
## Prerequisites
- Java v11.0 - programming language
- Maven v3.8.5 - build tool
- Docker - containerization tool
- PostgresDB v13.0 - persistence technology 

# Build and Test

1. Build project
``` Shell
mvn clean install -DskipTests=true
```

2. Generate Docker file using maven plugin if DockerFile is not present otherwise skip this step
``` 
mvn mn:dockerfile -Dpackaging=docker
```

3. Run ddl script present at below location
``` 
pt-ser-product-service/src/main/resources/schema.sql
```

4. Run service locally
``` shell
java -jar target/pt-ser-snm-0.0.1-SNAPSHOT.jar -Dspring.profiles.active=local  
```

# Test
1. Download and [Install Postman Client](https://www.postman.com/downloads/) of use in online
2. Import the collection from below path in Postman client:
```
pt-serial-number-profile-apis.postman_collection.json
```
3. Test the APIs