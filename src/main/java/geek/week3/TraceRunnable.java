package geek.week3;

public class TraceRunnable implements Runnable{
    private String parentTraceID = "";
    private Runnable delegate;
    private ThreadLocal<String> threadLocal = new ThreadLocal();

    public TraceRunnable(String parentTraceID,Runnable delegate) {
        this.delegate = delegate;
        this.parentTraceID = parentTraceID;
    }

    @Override
    public void run() {
        System.out.println("trace runnable id is = "+Thread.currentThread().getId());
        System.out.println("parentTraceID runnable id is = "+parentTraceID);
        threadLocal.set(parentTraceID);
        delegate.run();
        System.out.println("trace runnable id is = "+Thread.currentThread().getId() + " end");
        //        System.gc();
//        try {
//            //休眠一下，在运行的时候加上虚拟机参数-XX:+PrintGCDetails，输出gc信息，确定gc发生了。
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println("main thread local id = "+getThreadLocalParam());
    }

    public String getThreadLocalParam() {
        return threadLocal.get();
    }
}
