# nflsurvivor

Start Mariadb in a docker container (replace version as needed):

docker run -p3306:3306 -d --rm -e MYSQL_ROOT_PASSWORD=Oneringtorulethemall -e MYSQL_DATABASE=nfl jlweb58/jlweb58-repo:nfl-database-dump-0.9.2
