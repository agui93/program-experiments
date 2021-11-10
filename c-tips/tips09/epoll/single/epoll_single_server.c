#ifdef __linux__
#define HAVE_EPOLL 1
#endif

#ifdef HAVE_EPOLL
#include <sys/epoll.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <err.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <netinet/in.h>
#include <arpa/inet.h>



#define SERVER_PORT 9588

#define SERVER_IP_ADDR  "127.0.0.1"

#define BUFFER_SIZE 1024

#define BACKLOG 200

#define EVENT_SIZE 10

#define RESPONSE "SERVER RESPONSE"

//socket bind listen
static int tcpBind() {
    int sfd;
    struct sockaddr_in addr4;
    memset(&addr4, 0, sizeof(addr4));
    addr4.sin_family = AF_INET;
    addr4.sin_addr.s_addr = inet_addr(SERVER_IP_ADDR);
    addr4.sin_port = htons(SERVER_PORT);

    if ((sfd = socket(AF_INET, SOCK_STREAM, 0)) == -1) {
        printf("socket failed");
        return -1;
    }
    if ((bind(sfd, (struct sockaddr *) &addr4, sizeof(struct sockaddr))) == -1) {
        close(sfd);
        printf("bind failed");
        return -1;
    }
    if ((listen(sfd, BACKLOG)) == -1) {
        close(sfd);
        printf("listen failed");
        return -1;
    }

    return sfd;
}

static void interactive(int cli_fd,int epfd) {
    char recv_msg[BUFFER_SIZE];

    bzero(recv_msg, BUFFER_SIZE);
    long byte_num = recv(cli_fd, recv_msg, BUFFER_SIZE, 0);
    if (byte_num > 0) {
        if (byte_num > BUFFER_SIZE) {
            byte_num = BUFFER_SIZE;
        }
        recv_msg[byte_num] = '\0';
        printf("recv from client(fd = %d):%s\n", cli_fd, recv_msg);

        send(cli_fd, RESPONSE, strlen(RESPONSE), 0);
        printf("write to client(fd=%d): %s\n", cli_fd, RESPONSE);
    } else if (byte_num < 0) {
        printf("recv client(fd = %d) error.\n", cli_fd);
    } else {

        struct epoll_event ee;
        ee.events = EPOLLIN|EPOLLOUT;
        ee.data.u64 = 0; 
        ee.data.fd = cli_fd;
        epoll_ctl(epfd,EPOLL_CTL_MOD,cli_fd,&ee);
        close(cli_fd);
        printf("client (fd = %d) disconnect\n", cli_fd);
    }
}




int main(void){
    int sfd;
    if ((sfd = tcpBind()) == -1) {
        return -1;
    }
    
    int epfd = epoll_create(1024); /* 1024 is just a hint for the kernel */
    if(epfd==-1){
        printf("epoll_create failed");
        return -1;
    }

    struct epoll_event ev;
    ev.events = EPOLLIN;
    ev.data.u64 = 0; /* avoid valgrind warning */
    ev.data.fd = sfd;
    if(-1 == epoll_ctl(epfd,EPOLL_CTL_ADD,sfd,&ev)){
        printf("epoll_ctl failed");
        close(sfd);
        return -1;
    }

    struct epoll_event *events = malloc(sizeof(struct epoll_event) * EVENT_SIZE);
    int n,nfds;
    for(;;){
        nfds = epoll_wait(epfd,events,EVENT_SIZE,-1);        
        if(nfds== -1){
            close(sfd);
            printf("epoll wait");
            exit(-1);
        }
        for(n=0;n<nfds;n++){
            struct epoll_event ee =  events[n];
            if(ee.data.fd == sfd){
                struct sockaddr_in client_addr;
                socklen_t addr_len;
                int client_fd = accept(sfd, (struct sockaddr *) &client_addr, &addr_len);
                if(client_fd > 0){
                    printf("accpet one client client_fd=%d\n",client_fd);
                    ev.events = EPOLLIN | EPOLLET;
                    ev.data.fd = client_fd;
                    if(-1 == epoll_ctl(epfd, EPOLL_CTL_ADD, client_fd,&ev)){
                       printf("epoll_ctl failed client_fd=%d\n",client_fd); 
                    }
                }                  
            }else{
                interactive(ee.data.fd,epfd);                 
            }
        }
    } 

    return 0;
}
#else

#include <stdint.h>
#include <stdio.h>

#define __LITTLE_ENDIAN  1234    /* LSB first: i386, vax */
#define __BIG_ENDIAN     4321    /* MSB first: 68000, ibm, net */
#define __PDP_ENDIAN     3412    /* LSB first in word, MSW first in long */

#define __BYTE_ORDER     __LITTLE_ENDIAN

#define AGUI_LITTLE_ENDIAN   __LITTLE_ENDIAN
#define AGUI_BIG_ENDIAN      __BIG_ENDIAN
#define AGUI_PDP_ENDIAN      __PDP_ENDIAN


#define AGUI_BYTE_ORDER      __BYTE_ORDER

int main(void) {
#if (BYTE_ORDER == LITTLE_ENDIAN)
    printf("little endian\n");
#else
    printf("big endian\n");
#endif

    printf("1234=0x%x  size:%ld\n", 1234, sizeof(__LITTLE_ENDIAN));
    printf("4321=0x%x\n", 4321);
    printf("3412=0x%x\n", 3412);
    printf("AGUI_LITTLE_ENDIAN %d\n", AGUI_LITTLE_ENDIAN);
    printf("AGUI_BIG_ENDIAN %d\n", AGUI_BIG_ENDIAN);
    printf("AGUI_PDP_ENDIAN %d\n", AGUI_PDP_ENDIAN);
    printf("AGUI_BYTE_ORDER %d\n", AGUI_BYTE_ORDER);

    return 0;
}

#endif