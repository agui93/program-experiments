CC = gcc          
CFLAGS = -Isub -Iadd -O2    #编译选项,设置头文件搜索路径,添加优化
RM = rm -f        
                  
TARGET = cacu  
OBJS = add/add_int.o add/add_float.o sub/sub_int.o sub/sub_float.o main.o
                  
$(TARGET):$(OBJS)
	${CC} -o $(TARGET) $(OBJS) $(CFLAGS)
                  
$(OBJS):%.o:%.c                                                                                       
	$(CC) -c $< -o $@ $(CFLAGS)


clean:
	$(RM) $(TARGET) $(OBJS)
