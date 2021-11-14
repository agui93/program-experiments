gcc -c add/add_float.c  -o add/add_float.o
gcc -c add/add_int.c -o add/add_int.o
gcc -c sub/sub_float.c -o sub/sub_float.o
gcc -c sub/sub_int.c -o sub/sub_int.o
gcc -c main.c -o main.o -Iadd -Isub
gcc add/add_float.o add/add_int.o sub/sub_float.o sub/sub_int.o  main.o  -o cau 
