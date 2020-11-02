# Estimation with explanation:
Estimated time per task in the file received:

        1a - 0.25h
        1b - 0,3h
        1c - 0,75h
        1b - 2h
        2a - 3.5h
        2b - 1h
        3a - 2h
        3b - 3h
        3c - 4h
        3d - 2.5h
   #

# Total time : 19.3h

# Explanation:

 1. prepare a basic docker image including openJdk, libs and vim
	and pass program and vm arguments as env entires
	process: First of all i started by trying to execute my jar file onto openjdk image.
	I created jar using **mvn package** - it created an executable jar file.
	Then I created a Dockerfile (available in repo) additionally needed to copy cert and conf directories
	execution of jar : **CMD ["java", "-jar", "-DMODULE=reloadapi.log", "-Dlogging.config=/myApp/conf/example.log4j2.xml", "reloadapi-0.0.1.jar", "--spring.config.location=/myApp/conf/example.properties"]**
	first argument java -jar then i provided vm params separated by commas, then i provided jar name followed by application params - in this case example.properties file.
	To run this i used docker run with tty and interactive flags linked to db container
	Problems:
		- jar file name can't have capital letters
		- never ever put a server property outside app.properties - app returned an error, without any description:
			"run failed: Communications link failure. The last packet sent successfully to the server was 0 milliseconds ago. The driver has not received any packets from the server"
			I used to get this error when i tried to connect to db when its container was down - so I thought the issue was connected with communitation between containers. The issue was an ip address inserted in main class of app.
			
			
 2. I read about how to create images using maven - I will just compare one to the other
	To create a docker image using spring boot i used a command: **mvn spring-boot:build-image**. Also in the spring boot maven plugin in configuration I added layers TRUE 
	Result: **docker.io/library/reloadapi:0.0.1'** weird thing is that the image is 40 years old 
	compared 2 images using  docker history --format "{{.ID}} {{.CreatedBy}} {{.Size}}" alpinejava2:latest and reloadapi:0.0.1
	image created by spring is much lighter than created in standard way.
	I wanted to run spring witout any issues on this image: so minor adjustments had to be done.
	I ran the docker container using ** docker run --tty --interactive --name fromspring2img2 -v C:/Users/tomasz.koltun/exampleapi/log:/log -v C:/Users/tomasz.koltun/exampleapi/conf:/conf -v C:/Users/tomasz.koltun/exampleapi/cert:/cert reloadapi:0.0.1 /bin/sh**
	I created volumes for configuration, certificates and logs  and opened a bash shell.
	When jar is extracted it creates 2 folders and 1 file. By creating volumes like above i needed to change the certificate path in the app to find it - just needed to add 2 dots before path to start from the root folder.
	To start the app from bash i used command similar to this used in excercise 1: **java -DMODULE=reloadapi -DLOG_BASE/log -Dlogging.config=/conf/example.log4j2.xml org.springframework.boot.loader.JarLauncher --spring.config.location=/conf/example.properties**
	It looks almost identical to the opening jar command: vm arguments then command to start and program parameters
	PROBLEMS: no major problems find, just had to look for the solution how to start the app from bash with arguments
 3. add and configure actuator, test if endpoints are exposed. My plan is 
	to create a scheduled task to write down stats into file to read the last 
	line of it every 15 minutes in monitor container.
	PROBLEMS: Error path used to redirect my /actuator requests - had to change the port in the app.props
	Using actuator i will be reading some metrics:
		https://localhost:8083/actuator/metrics/http.server.requests <- it will count amount of requets
		https://localhost:8083/actuator/metrics/jvm.memory.used <- read memory usage
		https://localhost:8083/actuator/metrics/system.cpu.usage
		
		Networks:
		docker network create --driver=bridge --subnet=192.168.0.0/16 databasenetwork
		docker network create --driver=bridge --subnet=192.169.0.0/16 appsandmonitornetwork
	
		Java alpine image from dockerfile from previous excercise:
		#
		STEPS TO CREATE WORKING CONTAINER USING MAVEN MANUALLY:
		-build maven jar
		docker build -t javaimage5 .
		docker create --network=databasenetwork --hostname mavenJava2 --name mavenJava2 --tty --interactive -p 8082:8082 -p 8083:8083 javaimage5:latest
		for testing purposes I mapped ports to be able to see if everything wroks
		
		**build from maven**
		prepare dockerfile and
		docker network connect appsandmonitornetwork mavenJava2 - there is no possibility to connect to 2 networks at once using eg. docker run --network, you have to connect manually to both networks
		docker start --interactive mavenJava2 <- it starts fully working spring server
		
		** build from springboot**
		mvn spring-boot:build-image
		docker create --tty --interactive --network=databasenetwork --name springJava2 -p 8084:8082 -p 8085:8083 -v C:/Users/tomasz.koltun/exampleapi/log:/log -v C:/Users/tomasz.koltun/exampleapi/conf:/conf -v C:/Users/tomasz.koltun/exampleapi/cert:/cert -v C:/Users/tomasz.koltun/exampleapi/scripts:/scripts reloadapi:0.0.1 ../scripts/parametersScript.sh
		docker network connect appsandmonitornetwork springJava2
		docker start --interactive springJava2
		
		**build monitor for maven**
		simple dockerfile:
		FROM alpine:3.11
		RUN apk add vim
		RUN apk add jq
		COPY ./scripts /scripts
		WORKDIR /scripts
		CMD ["./uptimeCtr.sh"]
		docker build -t fullyworkingimagemavenmonitor6 . <- image for monitoring maven server from its dockerfile
		docker run --network appsandmonitornetwork --interactive --tty --name fullyworkingimagemavenv4 -v C:/stage2/monitors/mavenMonitors/myLogs:/myLogs fullyworkingimagemavenmonitor6:latest
		**build monitor for springboot created image**
		docker build -t fullyworkingimagespringmonitor3 .
		just create similar script with another hostname and run image
		docker run --network appsandmonitornetwork --interactive --tty --name workingspringmonitor3 -v C:/stage2/monitors/mavenMonitors/myLogs:/myLogs fullyworkingimagespringmonitor3:latest
		
		#
		
		
	reading resources - i used spring metrics. I am reading in the loop every 5 seconds the amount of memory used. I add the results and subtract every 15 minutes to have the mean value
	Additionally im reading the uptime percentage of the server in the similar fashion
	Reading happens in the monitor and the data is being saved to the file in shared volume with reporter conatiner
	In reporter container im using cron to read the last line of the shared files every 15 minutes 
	
	##
	**dockerfiles**
	
	**maven java app dockerfile**
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
	CMD ["javaw", "-jar", "-DMODULE=reloadapi.log", "-Dlogging.config=/myApp/conf/example.log4j2.xml", "reloadapi-0.0.1.jar", "--spring.config.location=/myApp/conf/example.properties"]
	**spring java app dockerfile**
	
	doesn't exist - i just open the file with a flags like below:
	docker create --tty --interactive --network=databasenetwork --name springJava2 -p 8084:8082 -p 8085:8083 -v C:/Users/tomasz.koltun/exampleapi/log:/log -v C:/Users/tomasz.koltun/exampleapi/conf:/conf -v C:/Users/tomasz.koltun/exampleapi/cert:/cert -v C:/Users/tomasz.koltun/exampleapi/scripts:/scripts reloadapi:0.0.1 ../scripts/parametersScript.sh
	docker start --it springJava2
	
	**spring app monitor dockerfile**
	
	FROM alpine:3.11
	RUN apk add vim
	RUN apk add jq
	COPY ./scripts /scripts
	WORKDIR /scripts
	CMD ["./uptimeCtrforSpring.sh"]
	
	script is available in the project /scripts directory
	
	**maven app monitor dockerfile**
	
	FROM alpine:3.11
	RUN apk add vim
	RUN apk add jq
	COPY ./scripts myApp/scripts
	COPY ./conf /myApp/conf
	COPY ./cert /myApp/cert
	RUN mkdir /myApp/myLogs
	WORKDIR /myApp/scripts
	CMD ["./uptimeCtr.sh"]
	
	script is available in the project /scripts directory
	
	**report container dockerfile**
	
	from alpine:latest
	COPY hello-cron /etc/crontabs/
	COPY script.sh /scripts/
	COPY entrypoint.sh /scripts/
	ENTRYPOINT ["/scripts/entrypoint.sh"]
	
	**report container cron**
	
	#not an empty line
	*/15	*	*	*	*	/bin/sh /scripts/script.sh
	#not an empty line
	
	**report container script**
	
	#!/bin/sh
	echo "$(tail -n 1 /logs/mavenUptimePercentages.txt)" >> /logs/cron.log
	echo "$(tail -n 1 /logs/mavenMemoryUsage.txt)" >> /logs/cron.log
	echo "$(tail -n 1 /logs/springMemoryUsage.txt)" >> /logs/cron.log
	echo "$(tail -n 1 /logs/springUptimePercentages.txt)" >> /logs/cron.log
	
	
	
	
	
	