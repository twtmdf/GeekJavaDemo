package geek.week1;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * 官网：http://openjdk.java.net/projects/code-tools/jol/
 * 参考链接：https://www.cnblogs.com/flydean/p/java-object-layout-jol.html
 *
 * 对象大小包括:
 * 1.对象头：标记字（一个机器字），Class指针（1机器字），数组长度（int，数组独有）
 * 2.对象体：内部空白（padding），实例数据（字段），外部对齐（alignment），排列：parent整体先排列，
 * gap…，8|4|2|1大字段优先排列，gap…，原生类型优先排列
 *
 * 对象头和对象引用：
 * 在64位 JVM 中，对象头占据的空间是 12- byte(=96bit=64+32)，但是以8字节对齐，所以一 个空类的实例至少占用16字节。
 * 在32位 JVM 中，对象头占8个字节，以4的倍数对 齐(32=4*8)。
 * 所以 new 出来很多简单对象，甚至是 new Object()，都会占用不少内容。
 * 包装类型
 * 比原生数据类型消耗的内存要多:
 * Integer:占用16字节(头部8+4=12，数据4字节)，因 为 int 部分占4个字节。 所以使用 Integer 比原生类型 int 要多消耗 300% 的内存。
 * Long:一般占用24个字节(头部8+4+数据8=20字节， 再对齐)，当然，对象的实际大小由底层平台的内存对 齐确定，具体由特定 CPU平台的 JVM 实现决定。 看起 来一个 Long 类型的对象，比起原生类型 long 多占用 了8个字节(也多消耗200%)。
 * 通常在32位 JVM，以及内存小于 -Xmx32G 的64位 JVM 上(默认开启指针压缩)，一个引用占的内存默 认是4个字节。
 * 因此，64位 JVM 一般需要多消耗堆内存。
 *
 * 多维数组:在二维数组 int[dim1][dim2] 中，每个嵌套的 数组 int[dim2] 都是一个单独的 Object，会额外占用16字 节的空间。当数组维度更大时，这种开销特别明显。
 * int[128][2] 实例占用3600字节。 而 int[256] 实例则只占用 1040字节。里面的有效存储空间是一样的，3600 比起 1040多了246%的额外开销。在极端情况下，byte[256][1]， 额外开销的比例是19倍!
 *
 *  String: String 对象的空间随着内部字符数组的增长而增长。 当然，String 类的对象有24个字节的额外开销。
 * 对于10字符以内的非空 String，增加的开销比起有效载荷 (每个字符2字节 + 4 个字节的 length)，多占用了100% 到 400% 的内存。
 *
 * 对齐是绕不过去的问题
 * 我们可能会认为，一个 X 类的实例占用17字节的空间。 但是 由于需要对齐(padding)，JVM 分配的内存是8字节的整数倍， 所以占用的空间不是17字节，而是24字节
 */
public class JolDemo {
    public static void main(String[] args) {
        System.out.println( VM.current().details());
        A a = new A();
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
        B b = new B();
        System.out.println(ClassLayout.parseInstance(b).toPrintable());
        C c = new C();
        System.out.println(ClassLayout.parseInstance(c).toPrintable());
        int[] aa = new int[0];
        System.out.println(ClassLayout.parseInstance(aa).toPrintable());
    }
}

class A {}

class B {
    private long s;
}

class C {
    private int a;
    private long s;
    private Long sl=1L;
    private String ss = "3rrrttt";
}