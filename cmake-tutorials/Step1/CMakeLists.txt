# 设置cmake的最低版本
cmake_minimum_required(VERSION 3.10)

# 设置工程名称 和版本
# set the project name and version
project(Tutorial VERSION 1.0)

# specify the C++ standard
# 指定为C++11 版本
set(CMAKE_CXX_STANDARD 11)
# 设置指定的C++编译器版本是必须的
set(CMAKE_CXX_STANDARD_REQUIRED True)


## 直接指定CMAKE_CXX_FLAGS标志位进行设置C++编译器版本
## 设置指定C++编译器版本。
#include(CheckCXXCompilerFlag)
#CHECK_CXX_COMPILER_FLAG("-std=c++11" COMPILER_SUPPORTS_CXX11)
#CHECK_CXX_COMPILER_FLAG("-std=c++0x" COMPILER_SUPPORTS_CXX0X)
#if(COMPILER_SUPPORTS_CXX11)
#set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++14") # set C++ 11
## set(CMAKE_C_FLAGS  "${CMAKE_C_FLAGS} -std=c99")
#elseif(COMPILER_SUPPORTS_CXX0X)
#set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++0x")
#message( STATUS "The comipler ${CMAKE_CXX_COMIPLER} has no C++ 11 suport. Please use a different C++ comipler.")
#endif()


# configure a header file to pass some of the CMake settings
# to the source code
configure_file(TutorialConfig.h.in TutorialConfig.h)


# 增加生成可执行文件
# add the executable
add_executable(Tutorial tutorial.cxx)


# 为指定项目添加 include 路径
# add the binary tree to the search path for include files
# so that we will find TutorialConfig.h
target_include_directories(Tutorial PUBLIC "${PROJECT_BINARY_DIR}")
