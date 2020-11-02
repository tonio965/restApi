FROM alpine:3.11
RUN apk add vim
RUN apk add jq
COPY ./scripts /scripts
WORKDIR /scripts
CMD ["./uptimeCtrforSpring.sh"]