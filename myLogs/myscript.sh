#!/bin/sh
echo $(tail -n 1 /logs/mavenMemoryUsage.txt)
echo $(tail -n 1 /logs/mavenUptimePercentages.txt)
echo $(tail -n 1 /logs/springMemoryUsage.txt)
echo $(tail -n 1 /logs/springUptimePercentages.txt)
