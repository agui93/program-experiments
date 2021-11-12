cmake_minimum_required(VERSION 3.10)

# set the project name and version
project(Linux-Network-Source VERSION 1.0)


# specify the C standard
set(CMAKE_C_STANDARD 99)
set(CMAKE_C_STANDARD_REQUIRED True)


# 验证项目构建
add_executable(s001-hello s001-hello/hello.c)


