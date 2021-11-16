############################################################
##  构建镜像的命令:
##      docker build -t c-vim-env/c_vim_env:0.1 -f Dockerfile  .
##  启动容器的命令:
##      docker run -it -d --cap-add sys_ptrace -p127.0.0.1:2223:22 --name c-vim --hostname c-vim-machine c-vim-env/c_vim_env:0.1
##  本地进入容器:
##      docker exec -it c-vim /bin/bash
############################################################


FROM ubuntu:20.04

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update

RUN apt-get install -y  tzdata
RUN ln -fs /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN dpkg-reconfigure --frontend noninteractive tzdata

RUN apt-get install -y  ssh
RUN apt-get install -y  build-essential
RUN apt-get install -y  gcc
RUN apt-get install -y  g++
RUN apt-get install -y  gdb
RUN apt-get install -y  clang
RUN apt-get install -y  cmake
RUN apt-get install -y  automake
RUN apt-get install -y  pkg-config
RUN apt-get install -y  rsync
RUN apt-get install -y  tar
RUN apt-get install -y  python
RUN apt-get install -y  vim
RUN apt-get install -y  sudo
RUN apt-get install -y  ctags 
RUN apt-get install -y  git 
RUN apt-get install -y  python3-dev 

RUN apt-get clean autoclean

RUN yes root | passwd root
RUN mkdir /run/sshd

RUN mkdir -p /root/.vim/bundle/Vundle.vim
ADD ./Vundle.vim /root/.vim/bundle/Vundle.vim
ADD ./vim.rc /root/.vimrc
ADD ./vim_plugins_install.sh /root/vim_plugins_install.sh
RUN ["chmod", "+x", "/root/vim_plugins_install.sh"]
ADD ./ycm_extra_conf.py /root/ycm_extra_conf.py

RUN ( \
    echo 'PermitRootLogin yes'; \
    echo 'PasswordAuthentication yes'; \
    echo 'Subsystem sftp /usr/lib/openssh/sftp-server'; \
  ) > /etc/ssh/sshd_config_test_clion

RUN /root/vim_plugins_install.sh
CMD ["/usr/sbin/sshd", "-D", "-e", "-f", "/etc/ssh/sshd_config_test_clion"]

