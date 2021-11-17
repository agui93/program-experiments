#Static Pattern Rules and Filter

obj_files = foo.result bar.o lose.o
src_files = foo.raw bar.c lose.c

all: $(obj_files)
	#这步防止隐式规则自动编译
	echo $@

$(filter %.o,$(obj_files)): %.o: %.c
	echo "target: $@ prereq: $<"
$(filter %.result,$(obj_files)): %.result: %.raw
	echo "target: $@ prereq: $<" 

%.c %.raw:
	touch $@

clean:
	rm -f $(src_files)
	rm -f all.c
