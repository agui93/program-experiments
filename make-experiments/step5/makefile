CC = gcc          
CFLAGS = -Isub -Iadd -O2 
RM = rm -f        

VPATH = add:sub:.
TARGET = cacu  
OBJS = add_int.o add_float.o sub_int.o sub_float.o main.o
OBJSDIR = objs
                  
$(TARGET):$(OBJSDIR) $(OBJS)
	${CC} -o $(TARGET) $(OBJSDIR)/*.o $(CFLAGS)
                  
$(OBJSDIR):
	mkdir -p ./$@

$(OBJS):%.o:%.c                                                                                       
	$(CC) -c $< -o $(OBJSDIR)/$@ $(CFLAGS)


clean:
	-$(RM) $(TARGET) 
	-$(RM) $(OBJSDIR)/*.o
