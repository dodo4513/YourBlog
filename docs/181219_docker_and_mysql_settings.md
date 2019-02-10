### docker mysql 
docker pull mysql
docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=password --name mysql mysql
docker exec -i -t mysql bash

mysql -u root -p
