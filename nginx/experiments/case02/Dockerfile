FROM centos:7
 
RUN yum -y install gcc make pcre-devel zlib-devel tar zlib
RUN yum -y install wget


RUN cd /tmp \                                                                                  
    && mkdir /usr/local/nginx \
    && wget http://nginx.org/download/nginx-1.15.8.tar.gz \
    && tar zxmf nginx-1.15.8.tar.gz \ 
    && cd /tmp/nginx-1.15.8 \
    && ./configure --prefix=/usr/local/nginx && make && make install \
    && ln -s /usr/local/nginx/sbin/nginx /usr/local/sbin/ 
 
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]

