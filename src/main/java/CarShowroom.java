import java.util.Deque;
import java.util.LinkedList;

public class CarShowroom {
    private final Deque<Car> cars = new LinkedList<>();
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

    public synchronized void putCar(Car car) {
        cars.addLast(car);
        notify();
    }

    public synchronized void buyCar() {
        String name = Thread.currentThread().getName();
        try {
            while (cars.isEmpty()) {
                System.out.println("Посетилель " + name + " ожидает в очереди поставки автомобиля");
                wait();
            }
        } catch (InterruptedException ignored) {
        }
        Car car = cars.pollFirst();
        System.out.println("Посетилель " + name + " купил авто марки " + car.getBrand() + " и покинул салон");
    }
}
