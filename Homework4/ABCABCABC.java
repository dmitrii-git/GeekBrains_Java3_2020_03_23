import java.util.concurrent.atomic.AtomicInteger;

public class ABCABCABC {

    private static final Object mon = new Object();

    public static void main(String[] args) {
    AtomicInteger turn = new AtomicInteger(1);
        new Thread(() -> {
            synchronized (mon) {

                for (int i = 0; i <5; i++) {
                       if(turn.get() == 1) {
                           System.out.print("A");
                           turn.set(2);
                           mon.notifyAll();
                       }
                       else {
                           i = i - 1;
                          try {
                              mon.wait();
                          } catch (InterruptedException e) {
                              e.printStackTrace();
                          }
                       }
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (mon) {
                for (int i = 0; i < 5; i++) {
                if(turn.get() == 2) {
                    System.out.print("B");
                    turn.set(3);
                    mon.notifyAll();
                }
                    else {
                        i = i - 1;
                        try {
                            mon.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (mon) {
                for (int i = 0; i < 5; i++) {
                    if(turn.get() == 3) {
                        System.out.print("C");
                        turn.set(1);
                        mon.notifyAll();
                    }
                    else {
                        i = i - 1;
                        try {
                            mon.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                 }
            }
        }).start();
    }


}
