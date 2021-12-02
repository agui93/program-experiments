docker pull nginx:latest

docker run --name nginxweb -p 8080:80 -d nginx

docker exec -it nginxweb /bin/bash

docker stop nginxweb
docker rm nginxweb
