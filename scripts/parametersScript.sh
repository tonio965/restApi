!#/bin/sh
java -DMODULE=reloadapi -DLOG_BASE/log -Dlogging.config=/conf/example.log4j2.xml org.springframework.boot.loader.JarLauncher --spring.config.location=/conf/example.properties