#!/usr/bin/env bash

java -Djava.security.egd=file:/dev/urandom \
     -jar \
     -XX:InitialRAMPercentage=25.0 \
     -XX:MaxRAMPercentage=80.0 \
     /app/app.jar \
     --server.port=8080 \