import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CarShowroom {
    private final Deque<Car> cars = new LinkedList<>();
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private final static int numCustomers = 10;
    private final static int pause = 500;
    final int numCars = 10; // переменная не приватная для доступа к ней из класса CarSupplier
    final int deliveryTime = 1000; // переменная не приватная для доступа к ней из класса CarSupplier
    final int decisionMakingTime = 1000; // переменная не приватная для доступа к ней из класса Customer

    public static void main(String[] args) throws InterruptedException {
        CarShowroom showroom = new CarShowroom();
        System.out.println("Автосалон открылся");
        Thread supplier = new Thread(new CarSupplier(showroom, "Toyota"));
        supplier.start();
        Thread.sleep(pause);
        for (int i = 0; i < numCustomers; i++) {
            Thread customer = new Thread(new Customer(showroom));
            customer.setDaemon(true);
            customer.start();
            Thread.sleep(pause);
        }
        supplier.join();
        Thread.sleep(pause);
        System.out.println("Автосалон закрылся");
    }

    public void putCar(Car car) {
        lock.lock();
        try {
            cars.addLast(car);
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public void buyCar() {
        lock.lock();
        try {
            String name = Thread.currentThread().getName();
            try {
                while (cars.isEmpty()) {
                    System.out.println("Посетитель " + name + " ожидает в очереди");
                    condition.await();
                }
            } catch (InterruptedException ignored) {
            }
            Car car = cars.pollFirst();
            System.out.println("Посетитель " + name + " купил авто марки " + car.getBrand() + " и покинул салон");
        } finally {
            lock.unlock();
        }
    }
}
