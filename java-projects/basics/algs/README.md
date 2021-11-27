

https://leetcode-cn.com
https://leetcode.com




算法ceilingNextPowerOfTwo的java实现
public static int ceilingNextPowerOfTwo(final int x){
return 1 << (32 - Integer.numberOfLeadingZeros(x - 1));
}

