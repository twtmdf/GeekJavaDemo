package geek.week4;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 一个简单的代码参考：
 */

/**
 * ThreadPoolExecutor创建线程池
 * 通过submit方法提交,回传返回值
 *
 */
public class Homework05 {

    public static void main(String[] args) {
        try {
            long start = System.currentTimeMillis();

            // 在这里创建一个线程或线程池，
            // 异步执行 下面方法

            ThreadPoolExecutor executor = new ThreadPoolExecutor(1,2,1,TimeUnit.MINUTES,new ArrayBlockingQueue<>(100));
            Result result = new Result();
            Future<Result> resultFuture =executor.submit(new CallbackThread(result),result);
            //这是得到的返回值
            result = resultFuture.get();
            executor.shutdown();

            // 确保  拿到result 并输出
            System.out.println("异步计算结果为：" + result.getResult());

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
    static class CallbackThread implements Runnable{
        Result result;
        CallbackThread(Result result) {
           this.result = result;
        }
        @Override
        public void run() {
            result.setResult(sum());
        }
    }
    @Getter
    @Setter
    @NoArgsConstructor
    static class Result {
        Integer result;
    }
}


