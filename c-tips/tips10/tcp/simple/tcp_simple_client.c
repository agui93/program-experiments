#include <sys/socket.h>
#include <arpa/inet.h>
#include <string.h>
#include <stdio.h>
#include <unistd.h>

#define PORT 8080
#define ADDR "127.0.0.1"

void processClient(int socket_fd) {

    char buf[1024];
    ssize_t size;
    for (;;) {
        size = read(STDIN_FILENO, buf, 1024);
        if (size > 0) {
            write(socket_fd, buf, size);
            size = read(socket_fd, buf, 1024);
            if (size == 0) {
                break;
            }
            write(STDOUT_FILENO, buf, size);
        }
    }
    close(socket_fd);
    printf("disconnect");
}


int main(void) {
    /*s为socket描述符*/
    int socket_fd;
    /*服务器地址结构*/
    struct sockaddr_in server_addr;
    bzero(&server_addr, sizeof(server_addr));

    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(8080);
    server_addr.sin_addr.s_addr = INADDR_ANY;

    inet_pton(AF_INET, ADDR, &server_addr.sin_addr);

    if ((socket_fd = socket(AF_INET, SOCK_STREAM, 0)) == -1) {
        printf("socket failed\n");
        return -1;
    }
    printf("socket success\n");

    if (connect(socket_fd, (struct sockaddr *) &server_addr, sizeof(struct sockaddr)) < 0) {
        close(socket_fd);
        return -1;
    }
    printf("connect success; port=%d\n", server_addr.sin_port);

    processClient(socket_fd);

    close(socket_fd);
    return 0;
}