from alpine:latest

COPY hello-cron /etc/crontabs/
COPY script.sh /scripts/
COPY entrypoint.sh /scripts/
ENTRYPOINT ["/scripts/entrypoint.sh"]