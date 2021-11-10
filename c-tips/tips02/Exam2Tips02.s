# gcc -S Tips02.c -o ***.s
#
#gcc -v
#Configured with: --prefix=/Library/Developer/CommandLineTools/usr --with-gxx-include-dir=/Library/Developer/CommandLineTools/SDKs/MacOSX10.14.sdk/usr/include/c++/4.2.1
#Apple LLVM version 10.0.1 (clang-1001.0.46.4)
#Target: x86_64-apple-darwin18.7.0
#Thread model: posix
#InstalledDir: /Library/Developer/CommandLineTools/usr/bin

	.file	"Tips02.c"
	.text
	.globl	f
	.type	f, @function
f:
.LFB0:
	.cfi_startproc
	movl	$1, a_very_long_name_to_see_how_long_they_can_be(%rip)
	ret
	.cfi_endproc
.LFE0:
	.size	f, .-f
	.globl	func_ret_int
	.type	func_ret_int, @function
func_ret_int:
.LFB1:
	.cfi_startproc
	leal	(%rsi,%rdi), %eax
	addl	%edx, %eax
	ret
	.cfi_endproc
.LFE1:
	.size	func_ret_int, .-func_ret_int
	.globl	func_ret_double
	.type	func_ret_double, @function
func_ret_double:
.LFB2:
	.cfi_startproc
	movsd	.LC0(%rip), %xmm0
	ret
	.cfi_endproc
.LFE2:
	.size	func_ret_double, .-func_ret_double
	.globl	func_ret_char_ptr
	.type	func_ret_char_ptr, @function
func_ret_char_ptr:
.LFB3:
	.cfi_startproc
	leaq	1(%rdi), %rax
	ret
	.cfi_endproc
.LFE3:
	.size	func_ret_char_ptr, .-func_ret_char_ptr
	.globl	static_variable
	.data
	.align 4
	.type	static_variable, @object
	.size	static_variable, 4
static_variable:
	.long	5
	.section	.rodata.cst8,"aM",@progbits,8
	.align 8
.LC0:
	.long	1374389535
	.long	1074339512
	.ident	"GCC: (Ubuntu 5.4.0-6ubuntu1~16.04.11) 5.4.0 20160609"
	.section	.note.GNU-stack,"",@progbits
