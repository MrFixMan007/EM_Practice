package org.example.z2_worker_thread;

import java.util.ArrayList;

public class WorkerThread {
    private static Runnable getTask() {

        return new Runnable() {
            @Override
            public void run() {
                System.out.println("Task started: " + this);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Task finished: " + this);
            }
        };

    }

    static class BlockingQueue {
        private final ArrayList<Runnable> tasks = new ArrayList<>();

        public synchronized Runnable get() throws InterruptedException {
            while (tasks.isEmpty()) {
                wait();
            }
            Runnable task = tasks.get(0);
            tasks.remove(task);
            return task;
        }

        public synchronized void put(Runnable task) {
            tasks.add(task);
            notify();
        }
    }

    public static void main(String[] args) {

        BlockingQueue queue = new BlockingQueue();

        Thread worker = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                Runnable task;
                try {
                    task = queue.get();
                    task.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        worker.start();

        for (int i = 0; i < 10; i++) {
            queue.put(getTask());
        }

        queue.put(worker::interrupt);
    }

}
