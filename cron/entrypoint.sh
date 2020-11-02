#!/bin/sh

touch /logs/cron.log
touch /var/log/cron.log
crontab /etc/crontabs/hello-cron
crond &
tail -f /var/log/cron.log
tail -f /logs/cron.log