
启动容器
docker run -it -d -p8081:80 --hostname docker-nginx --name nginx-v2 agui-centos-nginx:v2


复制配置
例如:customDir /opt/data/nginx-experiment/conf
mkdir -p  customDir
docker cp nginx-v2:/usr/local/nginx/conf  customDir 
docker stop nginx-v2
docker rm nginx-v2

重新启动容器,把本地目录挂载到容器中,customDir
docker run -it -d -p8081:80 --hostname docker-nginx  -v/customDir:/usr/local/nginx/conf --name nginx-v2 agui-centos-nginx:v2



