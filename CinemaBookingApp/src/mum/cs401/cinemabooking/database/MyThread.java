package mum.cs401.cinemabooking.database;

/**
 *
 * @author Enkh Amgalan Erdenebat
 */
public class MyThread implements Runnable {

    @Override
    public void run() {
        System.out.println(getClass());
    }

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        Thread t = new Thread(myThread);
        t.start();
        Thread t1 = new Thread(() -> System.out.println("Running a thread."));
        t1.start();
    }

}
