#include <stdio.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <string.h>


#define PORT 8080
#define BACKLOG 100


void processClient(int client_fd) {

    int pid = getpid();
    printf("accept one connect(fd=%d pid=%d)", client_fd, pid);

    ssize_t size = 0;
    char buffer[1024];

    for (;;) {
        size = read(client_fd, buffer, 1024);
        if (size == 0) {
            break;
        }

        buffer[size] = '\0';
        printf("read from client(pid=%d):%s\n", pid, buffer);

        sprintf(buffer, "%ld bytes altogether,pid=%d\n", size, pid);
        write(client_fd, buffer, strlen(buffer) + 1);
    }


    close(client_fd);
    printf("disconnect(pid=%d)\n", pid);
}

int main(void) {

    int server_socket_fd, client_fd;
    struct sockaddr_in sock_addr;
    struct sockaddr_in client_addr;

    if ((server_socket_fd = socket(AF_INET, SOCK_STREAM, 0)) == -1) {
        printf("socket fail\n");
        return -1;
    }
    printf("socket success\n");


    bzero(&sock_addr, sizeof(sock_addr));
    sock_addr.sin_family = AF_INET;
    sock_addr.sin_port = htons(PORT);
    sock_addr.sin_addr.s_addr = htonl(INADDR_ANY);
    if (bind(server_socket_fd, (struct sockaddr *) &sock_addr, sizeof(struct sockaddr)) == -1) {
        close(server_socket_fd);
        printf("bind failed\n");
        return -1;
    }
    printf("bind success\n");

    if ((listen(server_socket_fd, BACKLOG)) < 0) {
        close(server_socket_fd);
        return -1;
    }
    printf("listen success\n");


    for (;;) {
        socklen_t addrlen = sizeof(struct sockaddr);
        client_fd = accept(server_socket_fd, (struct sockaddr *) &client_addr, &addrlen);
        if (client_fd < 0) {
            continue;
        }


        printf("accept client_fd=%d;port=%d\n", client_fd, ntohs(client_addr.sin_port));
        if (fork() == 0) {
            close(server_socket_fd);
            processClient(client_fd);
        } else {
            close(client_fd);
        }
    }

}

