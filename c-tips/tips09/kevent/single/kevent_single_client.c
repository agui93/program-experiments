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
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/select.h>
#include <sys/un.h>
#include <netinet/in.h>
#include <netinet/tcp.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <netdb.h>
#include <errno.h>
#include <stdarg.h>
#include <stdio.h>
#include <poll.h>
#include <limits.h>

#define SERVER_PORT 9588

#define SERVER_IP_ADDR  "127.0.0.1"

#define BUFFER_SIZE 1024

#define BLOCKING_FLAG 0x1

static int setBlo(int fd, int blocking) {
    int flags;

    /* Set the socket nonblocking.
     * Note that fcntl(2) for F_GETFL and F_SETFL can't be
     * interrupted by a signal. */
    if ((flags = fcntl(fd, F_GETFL)) == -1) {
        close(fd);
        return -1;
    }

    if (blocking)
        flags &= ~O_NONBLOCK;
    else
        flags |= O_NONBLOCK;

    if (fcntl(fd, F_SETFL, flags) == -1) {
        close(fd);
        return -1;
    }
    return 1;
}

static int setTcpNoDelay(int fd) {
    int yes = 1;
    if (setsockopt(fd, IPPROTO_TCP, TCP_NODELAY, &yes, sizeof(yes)) == -1) {
        close(fd);
        return -1;
    }
    return 1;
}

static int connectTcp(int *fd, int blocking) {
    int sfd, rv;
    char _port[6];
    struct addrinfo hints, *servinfo, *p;


    snprintf(_port, 6, "%d", SERVER_PORT);
    memset(&hints, 0, sizeof(hints));
    hints.ai_family = AF_INET;
    hints.ai_socktype = SOCK_STREAM;
    if ((rv = getaddrinfo(SERVER_IP_ADDR, _port, &hints, &servinfo)) != 0) {
        return -1;
    }


    for (p = servinfo; p != NULL; p = p->ai_next) {
        if ((sfd = socket(p->ai_family, p->ai_socktype, p->ai_protocol)) == -1)
            continue;

        if (setBlo(sfd, 0) != 1)goto error;


        if (connect(sfd, p->ai_addr, p->ai_addrlen) != -1) {
            close(sfd);
            goto error;
        }

        if (blocking && setBlo(sfd, 1) != 1)goto error;


        if (setTcpNoDelay(sfd) != 1)goto error;

        goto end;
    }

    if (p == NULL) {
        printf("Can't create socket: %s", strerror(errno));
    }

    error:
    rv = -1;
    end:
    freeaddrinfo(servinfo);
    *fd = sfd;

    return rv;
}


int main(void) {

    int blocking = 0x1;
    int fd;
    if (connectTcp(&fd, blocking) == -1) {
        printf("connectTcp fail");
        return -1;
    }
    printf("connectTcp success\n");

    char *cli_response = "hello world";
    size_t len = strlen(cli_response);
    char buf[1024 * 16];
    int nread, nwritten;
    for (int i = 0; i < 10; ++i) {
        int wdone = -1;
        do {
            nwritten = write(fd, cli_response, len);
            if (nwritten == -1) {
                if (errno == EAGAIN && !(blocking & BLOCKING_FLAG)) {
                    /* Try again later */
                } else {
                    printf("write error %d %d\n", nwritten, errno);
                    break;
                }
            }
            wdone = 1;
            printf("write %d bytes to server\n", nwritten);
        } while (!wdone);

        nread = read(fd, buf, sizeof(buf));
        printf("read from server %d bytes: %s\n", nread, buf);
    }

    close(fd);
    return 0;
}

#else
int main(void){
    return 0;
}
#endif