# create and run DB container:
docker run --name reload_db -e MYSQL_RANDOM_ROOT_PASSWORD=yes -e MYSQL_DATABASE=reload_db -e MYSQL_USER=rld -e MYSQL_PASSWORD=rld -d reload_db