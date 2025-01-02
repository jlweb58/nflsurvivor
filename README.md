# nflsurvivor

Start Mariadb in a docker container:

docker run -d \
--name mariadb-container \
-e MARIADB_ROOT_PASSWORD=Oneringtorulethemall \
-v ~/mariadb/nfl:/var/lib/mysql \
-p 3306:3306 \
mariadb:latest \
