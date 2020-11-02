#!/bin/sh
echo "$(tail -n 1 /logs/mavenUptimePercentages.txt)" >> /logs/cron.log
echo "$(tail -n 1 /logs/mavenMemoryUsage.txt)" >> /logs/cron.log
echo "$(tail -n 1 /logs/springMemoryUsage.txt)" >> /logs/cron.log
echo "$(tail -n 1 /logs/springUptimePercentages.txt)" >> /logs/cron.log