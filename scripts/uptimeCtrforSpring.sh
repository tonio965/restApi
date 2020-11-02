#!/bin/sh
while true; do
        touch uptime.txt
        ups=0;
        downs=0;
        memory=0;
        x=1
        while [ $x -le 180 ]; do
                wget --no-check-certificate --spider https://172.18.0.12:8082/v1/test --timeout=1 &> /dev/null;
                if [ $? -ne 0 ]; then
                        downs=`expr $downs + 1`
						echo "down"
                        echo "down" >> ../myLogs/springUptime.txt
                else
                        ups=`expr $ups + 1`
                        wget --no-check-certificate  https://172.18.0.12:8083/actuator/metrics/jvm.memory.used --timeout=1 &> /dev/null;
						currentMemory=$(jq '.measurements[0].value' jvm.memory.used)
                        memory=$(($memory + $currentMemory))
                        rm jvm.memory.used
						echo "up"
                        echo "up" >> ../myLogs/springUptime.txt
                fi
                sleep 5;
        x=$(( $x + 1 ))
        done;
        rm uptime.txt
        echo "$ups: ups"
        echo "$downs: downs"
        echo "$((memory/180)):mean memory usage"
        echo "last 15 mins springJava3 mean mem usage $((memory/180)) KB" >> ../myLogs/springMemoryUsage.txt
        echo "up percentage: $((100*($ups)/($ups+$downs)))"
        echo "last 15 mins springJava3 uptime $((100*($ups)/($ups+$downs))) %" >> ../myLogs/springUptimePercentages.txt
done;