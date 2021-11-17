CC = gcc          
CFLAGS = -Isub -Iadd -O2 
RM = rm -f        

VPATH = add:sub:.
TARGET = cacu  
OBJS = add_int.o add_float.o sub_int.o sub_float.o main.o
OBJSDIR = objs
                  
$(TARGET):$(OBJS)
	${CC} -o $(TARGET) $(OBJS) $(CFLAGS)
                  
clean:
	-$(RM) $(TARGET) 
	-$(RM) $(OBJS) 
