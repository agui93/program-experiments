FROM centos:7

MAINTAINER Nginx Dockerfile Write by agui


RUN yum -y install epel-release 

RUN yum -y install wget gcc make pcre-devel \
zlib-devel openssl-devel libxml2-devel libxslt-devel luajit GeoIP-devel \
gd-devel libatomic_ops-devel luajit-devel perl-devel perl-ExtUtils-Embed


RUN cd /tmp && \ 
    wget https://openresty.org/download/openresty-1.15.8.2.tar.gz && \
    tar zxmf openresty-1.15.8.2.tar.gz && \
    cd openresty-1.15.8.2 && \
    ./configure \
            --with-threads \
            --with-file-aio \
            --with-http_ssl_module \
            --with-http_v2_module \
            --with-http_realip_module \
            --with-http_addition_module \
            --with-http_xslt_module=dynamic \
            --with-http_image_filter_module=dynamic \
            --with-http_geoip_module=dynamic \
            --with-http_sub_module \
            --with-http_dav_module \
            --with-http_flv_module \
            --with-http_mp4_module \
            --with-http_gunzip_module \
            --with-http_gzip_static_module \
            --with-http_auth_request_module \
            --with-http_random_index_module \
            --with-http_secure_link_module \
            --with-http_degradation_module \
            --with-http_slice_module \
            --with-http_stub_status_module \
            --with-stream=dynamic \
            --with-stream_ssl_module \
            --with-stream_realip_module \
            --with-stream_geoip_module=dynamic \
            --with-libatomic \
            --with-pcre-jit \
            --with-stream_ssl_preread_module && \
    gmake && gmake install 

ENV PATH $PATH:/usr/local/nginx/sbin
RUN ln -s /usr/local/openresty/nginx /usr/local/nginx
RUN ln -sf /dev/stdout /usr/local/nginx/logs/access.log && \
    ln -sf /dev/stderr /usr/local/nginx/logs/error.log

EXPOSE 80
ENTRYPOINT ["nginx","-g","daemon off;"]








             
