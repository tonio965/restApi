FROM alpine:3.11
RUN apk add vim
RUN apk add jq
COPY ./scripts myApp/scripts
COPY ./conf /myApp/conf
COPY ./cert /myApp/cert
RUN mkdir /myApp/myLogs
WORKDIR /myApp/scripts
CMD ["./uptimeCtr.sh"]