package geek.week3;

import java.util.concurrent.*;

public class TestThread{
//    public static void main(String[] args) {
//        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
//        SubRunnable runnable = new SubRunnable();
//        String mainThreadID = "main-thread-id-"+Thread.currentThread().getId();
//        TraceRunnable traceRunnable = new TraceRunnable(mainThreadID,runnable);
//        executorService.execute(traceRunnable);
//
//    }

    public static void main(String[] args) {
        Runnable task = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread t = Thread.currentThread();
            System.out.println("当前线程:" + t.getName()+"-"+t.getId());
        }
    };
        Thread t = Thread.currentThread();
        System.out.println("当前主线程:" + t.getName()+"-"+t.getId());
        Thread thread = new Thread(task);
        thread.setName("test-thread");
        thread.setDaemon(true);
        thread.start();
//        全是守护线程Daemon，无其他线程，当前线程执行结束则自动关闭，即主线程结束，start不能输出参数
        thread.run();
    }
}

// 测试线程池的排队和max线程数执行顺序
//    Runnable task = new Runnable() {
//        @Override
//        public void run() {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            Thread t = Thread.currentThread();
//            System.out.println("当前线程:" + t.getName() + "-" + t.getId());
//        }
//    };
//        try {
//                ThreadPoolExecutor executor = new ThreadPoolExecutor(1,3,50, TimeUnit.SECONDS,new LinkedBlockingDeque<>(2),new ThreadPoolExecutor.CallerRunsPolicy());
//        for (int i=0;i<10;i++) {
//        executor.execute(task);
//        executor.getActiveCount();
//        }
//        }catch (Exception e) {
//        System.out.println(e);
//        }