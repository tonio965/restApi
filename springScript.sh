#!/bin/sh
mvn spring-boot:build-image
docker create --tty --interactive --network=databasenetwork --name springJava2 -p 8084:8082 -p 8085:8083 -v C:/Users/tomasz.koltun/exampleapi/log:/log -v C:/Users/tomasz.koltun/exampleapi/conf:/conf -v C:/Users/tomasz.koltun/exampleapi/cert:/cert -v C:/Users/tomasz.koltun/exampleapi/scripts:/scripts reloadapi:0.0.1 ../scripts/parametersScript.sh
docker network connect appsandmonitornetwork springJava2
docker start --interactive springJava2