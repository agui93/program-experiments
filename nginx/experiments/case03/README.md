构建镜像
docker build -t agui-centos-nginx:v2 -f Dockerfile .

运行容器
docker run -it -d -p8081:80 --hostname docker-nginx --name nginx-v2 agui-centos-nginx:v2

查看容器情况
docker ps

访问 curl http://localhost:8081
浏览器  http://localhost:8081


进入容器
docker exec -it nginx-v2 /bin/bash


