#include <iostream>
#include <unistd.h>
#include <cstring>

int main() {
    char hostname[255];
    memset(hostname, 0, sizeof(hostname));
    gethostname(hostname, sizeof(hostname));
    char *buffer = hostname;
    std::cout << "hostname:" << buffer << std::endl;
    return 0;
}
