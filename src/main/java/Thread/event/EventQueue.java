package Thread.event;

import java.util.LinkedList;

public class EventQueue {

    private final int max;
    private final LinkedList<Event> eventQueue = new LinkedList<Event>();

    static class Event {


    }

    private final static int DEFAULT_MAX_EVENTS = 10;

    public EventQueue() {
        this(DEFAULT_MAX_EVENTS);
    }

    public EventQueue(int max) {
        this.max = max;
    }

    public void offer(Event event) {

        synchronized (eventQueue) {

            if (eventQueue.size() >= max) {

                console(" the queue is full.");

                try {
                    eventQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            console(" the new event is submitted.");
            this.eventQueue.addLast(event);
            this.eventQueue.notify();
        }
    }


    public Event take() {
        synchronized (eventQueue) {

            if (eventQueue.isEmpty()) {

                console(" the queue is empty.");

                try {
                    eventQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Event event = eventQueue.removeFirst();
            this.eventQueue.notify();
            console(" the  event "+event+" is handled.");
            return  event;
        }
    }



    private void console(String message) {

        System.out.println(Thread.currentThread().getName()+":"+message);

    }


}