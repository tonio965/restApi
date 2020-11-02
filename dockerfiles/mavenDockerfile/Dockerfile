FROM alpine:3.11 AS libs
RUN apk add openjdk14 --repository=http://dl-3.alpinelinux.org/alpine/edge/testing/
RUN apk add vim
FROM libs AS app
COPY ./target/reloadapi-0.0.1.jar /myApp/reloadapi-0.0.1.jar
EXPOSE 8082
COPY ./conf /myApp/conf
COPY ./cert2 /myApp/cert
RUN mkdir /myApp/log
WORKDIR /myApp
CMD ["java", "-jar", "-DMODULE=reloadapi.log", "-Dlogging.config=/myApp/conf/example.log4j2.xml", "reloadapi-0.0.1.jar", "--spring.config.location=/myApp/conf/example.properties"]