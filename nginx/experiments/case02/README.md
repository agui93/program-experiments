获取软件
wget http://nginx.org/download/nginx-1.15.12.tar.gz


构建镜像
docker build -t agui-centos-nginx:v1 -f Dockerfile .

运行容器
docker run -it -d -p8080:80 --name nginx-v1 agui-centos-nginx:v1

查看容器情况
docker ps

访问 curl http://localhost:8080
浏览器  http://localhost:8080


进入容器
docker exec -it nginx-v1 /bin/bash


