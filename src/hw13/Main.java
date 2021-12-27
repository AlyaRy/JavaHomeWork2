package hw13;
import java.util.*;

public class Main {
    static final Queue<Integer> buffer = new LinkedList<>();
    static int maxSize = 5;
    public static void main (String[] args) {
        Thread producer = new Thread(() -> {
            while (true)
                synchronized (buffer) {
                    if (buffer.size() == maxSize) {
                        System.out.println("Очередь производства заполнена, ожидаю потребления");
                        try {
                            buffer.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Random random = new Random();
                        int i = random.nextInt(101);
                        System.out.println("Произведено даннных: " + i + "Мб");
                        buffer.add(i);
                        buffer.notifyAll();
                    }
                }
        });
        Thread consumer = new Thread(() -> {
            while (true) {
                synchronized (buffer) {
                    while (buffer.isEmpty()) {
                        System.out.println("Очередь потребления пуста, ожидаю данные");
                        try {
                            buffer.wait();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    System.out.println("Потреблено данных: " + buffer.remove() + "Мб");
                    buffer.notifyAll();
                }
            }
        });
        producer.start();
        consumer.start();
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}