# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.15

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /Applications/CLion.app/Contents/bin/cmake/mac/bin/cmake

# The command to remove a file.
RM = /Applications/CLion.app/Contents/bin/cmake/mac/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /Users/agui93/Workspace/aguiSpaces/agui93-moonlight/c-tips

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /Users/agui93/Workspace/aguiSpaces/agui93-moonlight/c-tips/cmake-build-debug

# Include any dependencies generated for this target.
include CMakeFiles/Tips09_kqueue_single_server.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/Tips09_kqueue_single_server.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/Tips09_kqueue_single_server.dir/flags.make

CMakeFiles/Tips09_kqueue_single_server.dir/tips09/kevent/single/kevent_single_server.c.o: CMakeFiles/Tips09_kqueue_single_server.dir/flags.make
CMakeFiles/Tips09_kqueue_single_server.dir/tips09/kevent/single/kevent_single_server.c.o: ../tips09/kevent/single/kevent_single_server.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/agui93/Workspace/aguiSpaces/agui93-moonlight/c-tips/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building C object CMakeFiles/Tips09_kqueue_single_server.dir/tips09/kevent/single/kevent_single_server.c.o"
	/Library/Developer/CommandLineTools/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/Tips09_kqueue_single_server.dir/tips09/kevent/single/kevent_single_server.c.o   -c /Users/agui93/Workspace/aguiSpaces/agui93-moonlight/c-tips/tips09/kevent/single/kevent_single_server.c

CMakeFiles/Tips09_kqueue_single_server.dir/tips09/kevent/single/kevent_single_server.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/Tips09_kqueue_single_server.dir/tips09/kevent/single/kevent_single_server.c.i"
	/Library/Developer/CommandLineTools/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /Users/agui93/Workspace/aguiSpaces/agui93-moonlight/c-tips/tips09/kevent/single/kevent_single_server.c > CMakeFiles/Tips09_kqueue_single_server.dir/tips09/kevent/single/kevent_single_server.c.i

CMakeFiles/Tips09_kqueue_single_server.dir/tips09/kevent/single/kevent_single_server.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/Tips09_kqueue_single_server.dir/tips09/kevent/single/kevent_single_server.c.s"
	/Library/Developer/CommandLineTools/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /Users/agui93/Workspace/aguiSpaces/agui93-moonlight/c-tips/tips09/kevent/single/kevent_single_server.c -o CMakeFiles/Tips09_kqueue_single_server.dir/tips09/kevent/single/kevent_single_server.c.s

# Object files for target Tips09_kqueue_single_server
Tips09_kqueue_single_server_OBJECTS = \
"CMakeFiles/Tips09_kqueue_single_server.dir/tips09/kevent/single/kevent_single_server.c.o"

# External object files for target Tips09_kqueue_single_server
Tips09_kqueue_single_server_EXTERNAL_OBJECTS =

Tips09_kqueue_single_server: CMakeFiles/Tips09_kqueue_single_server.dir/tips09/kevent/single/kevent_single_server.c.o
Tips09_kqueue_single_server: CMakeFiles/Tips09_kqueue_single_server.dir/build.make
Tips09_kqueue_single_server: CMakeFiles/Tips09_kqueue_single_server.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/Users/agui93/Workspace/aguiSpaces/agui93-moonlight/c-tips/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking C executable Tips09_kqueue_single_server"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/Tips09_kqueue_single_server.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/Tips09_kqueue_single_server.dir/build: Tips09_kqueue_single_server

.PHONY : CMakeFiles/Tips09_kqueue_single_server.dir/build

CMakeFiles/Tips09_kqueue_single_server.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/Tips09_kqueue_single_server.dir/cmake_clean.cmake
.PHONY : CMakeFiles/Tips09_kqueue_single_server.dir/clean

CMakeFiles/Tips09_kqueue_single_server.dir/depend:
	cd /Users/agui93/Workspace/aguiSpaces/agui93-moonlight/c-tips/cmake-build-debug && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /Users/agui93/Workspace/aguiSpaces/agui93-moonlight/c-tips /Users/agui93/Workspace/aguiSpaces/agui93-moonlight/c-tips /Users/agui93/Workspace/aguiSpaces/agui93-moonlight/c-tips/cmake-build-debug /Users/agui93/Workspace/aguiSpaces/agui93-moonlight/c-tips/cmake-build-debug /Users/agui93/Workspace/aguiSpaces/agui93-moonlight/c-tips/cmake-build-debug/CMakeFiles/Tips09_kqueue_single_server.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/Tips09_kqueue_single_server.dir/depend

