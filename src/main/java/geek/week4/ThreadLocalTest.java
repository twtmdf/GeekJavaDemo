package geek.week4;

/**
 * 中的 HASH_INCREMENT 也不是随便取的，它转化为十进制是 1640531527，2654435769 转换成 int 类型就是 -1640531527，
 * 2654435769 等于 (√5-1)/2 乘以 2 的 32 次方。(√5-1)/2 就是黄金分割数，近似为 0.618，
 * 也就是说 0x61c88647 理解为一个黄金分割数乘以 2 的 32 次方，它可以保证 nextHashCode 生成的哈希值，
 * 均匀的分布在 2 的幂次方上，且小于 2 的 32 次方。
 *
 * 下面是 javaspecialists 中一篇文章对它的介绍：
 *
 * This number represents the golden ratio (sqrt(5)-1) times two to the power of 31 ((sqrt(5)-1) * (2^31)). The result is then a golden number, either 2654435769 or -1640531527.
 * print result is
 * 0 7 14 21 28 3 10 17 24 31 6 13 20 27 2 9 16 23 30 5 12 19 26 1 8 15 22 29 4 11 18 25
 */
public class ThreadLocalTest {
//    可以看成是context模式
//    不用显示传参
//    用作操作员id，traceid等
//    使用完需要释放
    private static final int HASH_INCREMENT = 0x61c88647;

    public static void main(String[] args) throws Exception {
        int n = 5;
        int max = 2 << (n - 1);
        for (int i = 0; i < max; i++) {
            System.out.print(i * HASH_INCREMENT & (max - 1));
            System.out.print(" ");
        }
        ThreadLocal<Integer> local = new ThreadLocal();

    }
}
