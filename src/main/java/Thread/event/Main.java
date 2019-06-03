package Thread.event;

public class Main {

    public static void main(String[] args) {

        final EventQueue eventQueue = new EventQueue();

        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {

                for (; ; ) {
                    eventQueue.offer(new EventQueue.Event());
                }

            }
        }, "producer");

        Thread conumser = new Thread(new Runnable() {
            @Override
            public void run() {

                for (; ; ) {
                    EventQueue.Event take = eventQueue.take();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, "conumser");
        producer.start();
        conumser.start();
    }
}