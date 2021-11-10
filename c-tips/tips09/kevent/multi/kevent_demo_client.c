#ifdef __APPLE__

#include <AvailabilityMacros.h>

#endif


#if (defined(__APPLE__) && defined(MAC_OS_X_VERSION_10_6)) || defined(__FreeBSD__) || defined(__OpenBSD__) || defined (__NetBSD__)
#define HAVE_KQUEUE 1
#endif


#ifdef HAVE_KQUEUE

#include <stdio.h>
#include <netinet/in.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>

#define BUFFER_SIZE 1024
#define ADDR "127.0.0.1"
#define PORT 9588


static int connectServer() {
    struct sockaddr_in server_addr;
    server_addr.sin_len = sizeof(struct sockaddr_in);
    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(PORT);
    server_addr.sin_addr.s_addr = inet_addr(ADDR);
    bzero(&(server_addr.sin_zero), 8);

    int server_sock_fd;

    if ((server_sock_fd = socket(AF_INET, SOCK_STREAM, 0)) == -1) {
        perror("socket error");
        return -1;
    }

    if (connect(server_sock_fd, (struct sockaddr *) &server_addr, sizeof(struct sockaddr_in)) == 0) {
        printf("connect to server");
        return server_sock_fd;
    }

    close(server_sock_fd);
    return -1;
}

int main(void) {

    char recv_msg[BUFFER_SIZE];
    char input_msg[BUFFER_SIZE];

    fd_set client_fd_set;
    struct timeval tv = {20, 0};

    int server_sock_fd = connectServer();

    while (1) {
        FD_ZERO(&client_fd_set);
        FD_SET(STDIN_FILENO, &client_fd_set);
        FD_SET(server_sock_fd, &client_fd_set);

        int ret = select(server_sock_fd + 1, &client_fd_set, NULL, NULL, &tv);
        if (ret < 0) {
            printf("select error!\n");
            continue;
        } else if (ret == 0) {
            printf("select timeout!\n");
            continue;
        } else {
            if (FD_ISSET(STDIN_FILENO, &client_fd_set)) {
                bzero(input_msg, BUFFER_SIZE);
                fgets(input_msg, BUFFER_SIZE, stdin);
                if (send(server_sock_fd, input_msg, BUFFER_SIZE, 0) == -1) {
                    perror("send to server error!\n");
                }
            }
            if (FD_ISSET(server_sock_fd, &client_fd_set)) {
                bzero(recv_msg, BUFFER_SIZE);
                long byte_num = recv(server_sock_fd, recv_msg, BUFFER_SIZE, 0);
                if (byte_num > 0) {
                    if (byte_num > BUFFER_SIZE) {
                        byte_num = BUFFER_SIZE;
                    }
                    recv_msg[byte_num] = '\0';
                    printf("recv form server:%s\n", recv_msg);
                } else if (byte_num < 0) {
                    printf("recv error!\n");
                } else {
                    printf("server quit!\n");
                    exit(0);
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