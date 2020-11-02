#!/bin/sh
mvn package
docker build -t javaimage5 .
docker create --network=databasenetwork --hostname mavenJava2 --name mavenJava2 --tty --interactive -p 8082:8082 -p 8083:8083 javaimage5:latest
docker network connect appsandmonitornetwork mavenJava2
docker start --interactive mavenJava2