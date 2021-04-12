package geek.week3;

/**
 * 接口
 */
public class SubRunnable implements Runnable{
    @Override
    public void run() {
        System.out.println("subRunnable id = "+Thread.currentThread().getId());
    }
}
