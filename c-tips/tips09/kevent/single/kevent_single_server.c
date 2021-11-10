#ifdef __APPLE__

#include <AvailabilityMacros.h>

#endif


#if (defined(__APPLE__) && defined(MAC_OS_X_VERSION_10_6)) || defined(__FreeBSD__) || defined(__OpenBSD__) || defined (__NetBSD__)
#define HAVE_KQUEUE 1
#endif


#ifdef HAVE_KQUEUE

#include <sys/event.h>
#include <sys/socket.h>
#include <err.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <arpa/inet.h>
#include <netinet/in.h>

#include <unistd.h>


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
    addr4.sin_len = sizeof(struct sockaddr_in);
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

//accpet client connect
static void acceptClient(int sfd, int kq) {
    struct kevent change_event;
    struct sockaddr_in client_addr;
    socklen_t addr_len;

    int client_fd = accept(sfd, (struct sockaddr *) &client_addr, &addr_len);

    if (client_fd > 0) {
        //监听该客户端输入
        EV_SET(&change_event, client_fd, EVFILT_READ, EV_ADD, 0, 0, NULL);
        //放入事件队列
        kevent(kq, &change_event, 1, NULL, 0, NULL);

        printf("新客户端（fd=%d）加入成功 %s:%d \n", client_fd, inet_ntoa(client_addr.sin_addr),
               ntohs(client_addr.sin_port));
    }
}


static void interactive(int cli_fd, int kq) {
    struct kevent change_event;
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
        EV_SET(&change_event, cli_fd, EVFILT_READ, EV_DELETE, 0, 0, NULL);
        kevent(kq, &change_event, 1, NULL, 0, NULL);
        close(cli_fd);
        printf("client (fd = %d) disconnect\n", cli_fd);
    }
}


int main(void) {
    int kq, sfd, retval;


    struct kevent events[EVENT_SIZE + 2];
    struct kevent change_event;

    struct timespec timeout = {10, 0};
    if ((sfd = tcpBind()) == -1) {
        return -1;
    }


    if ((kq = kqueue()) == -1) {
        printf("kqueue failed");
        close(sfd);
        return -1;
    }


    EV_SET(&change_event, sfd, EVFILT_READ, EV_ADD, 0, 0, NULL);
    if (kevent(kq, &change_event, 1, NULL, 0, NULL) == -1) {
        close(sfd);
        close(kq);
        printf("kevent ev_set failed");
        return -1;
    }


    while (1) {
        retval = kevent(kq, NULL, 0, events, EVENT_SIZE, &timeout);
        if (retval < 0) {
            printf("kevent error");
        } else if (0 == retval) {
            printf("kevent timeout");
        } else {
            for (int i = 0; i < retval; ++i) {
                struct kevent current_event = events[i];
                if (current_event.ident == sfd) {
                    acceptClient(sfd, kq);
                } else {
                    interactive(current_event.ident, kq);
                }
            }
        }
    }
}

#else
int main(void){
    return 0;
}

#endif