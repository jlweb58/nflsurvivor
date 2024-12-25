# nflsurvivor

Start Mariadb in a docker container:

docker run -d \
--name mariadb-container \
-e MARIADB_ROOT_PASSWORD=Oneringtorulethemall \
-v ~/mariadb/nfl:/var/lib/mysql \
-v ~/code/nflsurvivor/src/main/resources/static/images:/var/lib/mysql-files \
-p 3306:3306 \
mariadb:latest \
--secure-file-priv=/var/lib/mysql-files
