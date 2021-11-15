############################################################
##  构建镜像的命令:
##   docker build -t clion-env/remote-cpp-env:0.1 -f Dockerfile  .
##  启动容器的命令:
##   docker run -d --cap-add sys_ptrace -p127.0.0.1:2222:22 --name clion_remote_env --hostname clion-machine clion-env/remote-cpp-env:0.1
##  ssh登录容器, 用户名:clion  密码:clion 端口:2222
##   ssh clion@127.0.0.1 -p 2222
##
############################################################


FROM ubuntu:20.04

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update \
  && apt-get install --no-install-recommends -y\
    ssh \
    gdb \
    make \
    clang \
    cmake \
    rsync \
  && apt-get clean autoclean \
  && apt-get autoremove --yes \
  && rm -rf /var/lib/{apt,dpkg,cache,log}

RUN ( \
    echo 'PermitRootLogin yes'; \
    echo 'PasswordAuthentication yes'; \
    echo 'Subsystem sftp /usr/lib/openssh/sftp-server'; \
  ) > /etc/ssh/sshd_config_test_clion \
  && mkdir /run/sshd \
  && useradd -m clion \
  && yes clion | passwd clion

CMD ["/usr/sbin/sshd", "-D", "-e", "-f", "/etc/ssh/sshd_config_test_clion"]

