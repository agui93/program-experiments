#生成cacu, ":"右边是目标
cacu:add_int.o add_float.o sub_int.o sub_float.o main.o
	gcc -o cacu add/add_int.o add/add_float.o \
	sub/sub_int.o sub/sub_float.o                 \
    main.o


#生成add_int.o的规则,将add_int.c编译成目标文件add_int.o
add_int.o:add/add_int.c add/add.h
	gcc -c -o add/add_int.o add/add_int.c

add_float.o:add/add_float.c add/add.h
	gcc -c -o add/add_float.o add/add_float.c
	

sub_int.o:sub/sub_int.c sub/sub.h
	gcc -c -o sub/sub_int.o sub/sub_int.c


sub_float.o:sub/sub_float.c sub/sub.h
	gcc -c -o sub/sub_float.o sub/sub_float.c

main.o:main.c add/add.h sub/sub.h
	gcc -c -o main.o main.c -Iadd -Isub

#清理规则
clean:
	rm -f  cacu add/add_int.o add/add_float.o \
	   	   sub/sub_int.o sub/sub_float.o \
		   main.o



