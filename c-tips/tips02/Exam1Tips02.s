# gcc -S Tips02.c -o ***.s
#
#gcc -v
#Using built-in specs.
#COLLECT_GCC=gcc
#COLLECT_LTO_WRAPPER=/usr/lib/gcc/x86_64-linux-gnu/5/lto-wrapper
#Target: x86_64-linux-gnu
#Configured with: ../src/configure -v --with-pkgversion='Ubuntu 5.4.0-6ubuntu1~16.04.11' --with-bugurl=file:///usr/share/doc/gcc-5/README.Bugs --enable-languages=c,ada,c++,java,go,d,fortran,objc,obj-c++ --prefix=/usr --program-suffix=-5 --enable-shared --enable-linker-build-id --libexecdir=/usr/lib --without-included-gettext --enable-threads=posix --libdir=/usr/lib --enable-nls --with-sysroot=/ --enable-clocale=gnu --enable-libstdcxx-debug --enable-libstdcxx-time=yes --with-default-libstdcxx-abi=new --enable-gnu-unique-object --disable-vtable-verify --enable-libmpx --enable-plugin --with-system-zlib --disable-browser-plugin --enable-java-awt=gtk --enable-gtk-cairo --with-java-home=/usr/lib/jvm/java-1.5.0-gcj-5-amd64/jre --enable-java-home --with-jvm-root-dir=/usr/lib/jvm/java-1.5.0-gcj-5-amd64 --with-jvm-jar-dir=/usr/lib/jvm-exports/java-1.5.0-gcj-5-amd64 --with-arch-directory=amd64 --with-ecj-jar=/usr/share/java/eclipse-ecj.jar --enable-objc-gc --enable-multiarch --disable-werror --with-arch-32=i686 --with-abi=m64 --with-multilib-list=m32,m64,mx32 --enable-multilib --with-tune=generic --enable-checking=release --build=x86_64-linux-gnu --host=x86_64-linux-gnu --target=x86_64-linux-gnu
#Thread model: posix
#gcc version 5.4.0 20160609 (Ubuntu 5.4.0-6ubuntu1~16.04.11)

	.section	__TEXT,__text,regular,pure_instructions
	.build_version macos, 10, 14	sdk_version 10, 14
	.globl	_f                      ## -- Begin function f
	.p2align	4, 0x90
_f:                                     ## @f
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	pushq	%r15
	pushq	%r14
	pushq	%rbx
	subq	$136, %rsp
	.cfi_offset %rbx, -40
	.cfi_offset %r14, -32
	.cfi_offset %r15, -24
	movq	_a_very_long_name_to_see_how_long_they_can_be@GOTPCREL(%rip), %rax
	movl	$200, %ecx
	movl	%ecx, %edx
	movl	$190, %ecx
	movl	%ecx, %esi
	movl	$180, %ecx
	movl	%ecx, %edi
	movl	$170, %ecx
	movl	%ecx, %r8d
	movl	$160, %ecx
	movl	%ecx, %r9d
	movl	$150, %ecx
	movl	%ecx, %r10d
	movl	$140, %ecx
	movl	%ecx, %r11d
	movl	$130, %ecx
	movl	%ecx, %ebx
	movl	$120, %ecx
	movl	%ecx, %r14d
	movl	$110, %ecx
	movl	%ecx, %r15d
	movl	$1, -28(%rbp)
	movl	$2, -32(%rbp)
	movl	$3, -36(%rbp)
	movl	$4, -40(%rbp)
	movl	$5, -44(%rbp)
	movl	$6, -48(%rbp)
	movl	$7, -52(%rbp)
	movl	$8, -56(%rbp)
	movl	$9, -60(%rbp)
	movl	$10, -64(%rbp)
	movq	%r15, -72(%rbp)
	movq	%r14, -80(%rbp)
	movq	%rbx, -88(%rbp)
	movq	%r11, -96(%rbp)
	movq	%r10, -104(%rbp)
	movq	%r9, -112(%rbp)
	movq	%r8, -120(%rbp)
	movq	%rdi, -128(%rbp)
	movq	%rsi, -136(%rbp)
	movq	%rdx, -144(%rbp)
	movl	$1, (%rax)
	movl	-28(%rbp), %esi
	movl	-64(%rbp), %edx
	movl	$10, %edi
	callq	_func_ret_int
	movl	%eax, -32(%rbp)
	callq	_func_ret_double
	movsd	%xmm0, -152(%rbp)
	movq	-72(%rbp), %rdi
	callq	_func_ret_char_ptr
	movq	%rax, -72(%rbp)
	addq	$136, %rsp
	popq	%rbx
	popq	%r14
	popq	%r15
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.globl	_func_ret_int           ## -- Begin function func_ret_int
	.p2align	4, 0x90
_func_ret_int:                          ## @func_ret_int
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	movl	%edi, -4(%rbp)
	movl	%esi, -8(%rbp)
	movl	%edx, -12(%rbp)
	movl	-8(%rbp), %edx
	subl	$6, %edx
	movl	%edx, -16(%rbp)
	movl	-4(%rbp), %edx
	addl	-8(%rbp), %edx
	addl	-12(%rbp), %edx
	movl	%edx, %eax
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.section	__TEXT,__literal8,8byte_literals
	.p2align	3               ## -- Begin function func_ret_double
LCPI2_0:
	.quad	4614253070214989087     ## double 3.1400000000000001
	.section	__TEXT,__text,regular,pure_instructions
	.globl	_func_ret_double
	.p2align	4, 0x90
_func_ret_double:                       ## @func_ret_double
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	movsd	LCPI2_0(%rip), %xmm0    ## xmm0 = mem[0],zero
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.globl	_func_ret_char_ptr      ## -- Begin function func_ret_char_ptr
	.p2align	4, 0x90
_func_ret_char_ptr:                     ## @func_ret_char_ptr
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	movq	%rdi, -8(%rbp)
	movq	-8(%rbp), %rdi
	addq	$1, %rdi
	movq	%rdi, %rax
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.section	__DATA,__data
	.globl	_static_variable        ## @static_variable
	.p2align	2
_static_variable:
	.long	5                       ## 0x5


.subsections_via_symbols
