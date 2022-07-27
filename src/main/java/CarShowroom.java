import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CarShowroom {
    private final Deque<Car> cars = new LinkedList<>();
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    public static final int NUMBER_OF_CUSTOMERS = 10;
    public static final int PAUSE = 500;
    public static final int NUMBER_OF_CARS = 10;
    public static final int DELIVERY_TIME = 1000;
    public static final int DECISION_MAKING_TIME = 1000;

    public static void main(String[] args) throws InterruptedException {
        CarShowroom showroom = new CarShowroom();
        System.out.println("Автосалон открылся");
        Thread supplier = new Thread(new CarSupplier(showroom, "Toyota"));
        supplier.start();
        Thread.sleep(PAUSE);
        for (int i = 0; i < NUMBER_OF_CUSTOMERS; i++) {
            Thread customer = new Thread(new Customer(showroom));
            customer.setDaemon(true);
            customer.start();
            Thread.sleep(PAUSE);
        }
        supplier.join();
        Thread.sleep(PAUSE);
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
                    System.out.println("Посетитель " + name + " ожидает в очереди поставки автомобиля");
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
