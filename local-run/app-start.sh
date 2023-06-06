#!/bin/bash

./mvnw package verify
if [ $? -eq 0 ]
then
    java -Xmx512m -Dserver.port=8082 \
    -Dapi.message.url=http://localhost:8081 \
    -jar target/cloudnative-api-1.0.0-SNAPSHOT.jar
fi
