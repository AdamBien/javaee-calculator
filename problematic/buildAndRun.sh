#!/bin/sh
mvn clean package && docker build -t airhacks/problematic .
docker rm -f problematic || true && docker run --net prometheus -d -p 8010:8080 --name problematic airhacks/problematic 
