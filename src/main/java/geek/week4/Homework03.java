package geek.week4;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 一个简单的代码参考：
 */

/**
 * Thread创建单线程,start调用
 * 使用AtomicInteger接收返回结果
 */
public class Homework03 {
    
    public static void main(String[] args) {
        try {
            long start = System.currentTimeMillis();

            // 在这里创建一个线程或线程池，
            // 异步执行 下面方法
            AtomicInteger result = new AtomicInteger();
            Runnable task = () ->{
                //这是得到的返回值
                result.set(sum());
            };
            Thread thread = new Thread(task);
            thread.start();
//            run不创建新线程
//            thread.run();
            // 确保  拿到result 并输出
            System.out.println("异步计算结果为：" + result);

            System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 然后退出main线程
    }
    
    private static int sum() {
        return fibo(2);
    }
    
    private static int fibo(int a) {
        if ( a < 2) 
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}
