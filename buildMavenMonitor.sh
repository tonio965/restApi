#!/bin/sh
docker run --network appsandmonitornetwork --interactive --name myMavenMonitor35 -v c:/stage2/monitors/mavenMonitors/:/scripts alpine:latest /scripts/openMonitoring.sh
apk add jq
cd /scripts
mkdir loopLogs
./openMonitoring.sh