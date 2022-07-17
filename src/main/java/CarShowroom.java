import java.util.Deque;
import java.util.LinkedList;

public class CarShowroom {
    private final Deque<Car> cars = new LinkedList<>();
    private final int numCustomers = 10;
    private final int pause = 500;
    private final int numCars = 10;
    private final int deliveryTime = 1000;
    private final int decisionMakingTime = 1000;

    public static void main(String[] args) throws InterruptedException {
        CarShowroom showroom = new CarShowroom();
        System.out.println("Автосалон открылся");
        Thread supplier = new Thread(new CarSupplier(showroom, "Toyota"));
        supplier.start();
        Thread.sleep(showroom.pause);
        for (int i = 0; i < showroom.numCustomers; i++) {
            Thread customer = new Thread(new Customer(showroom));
            customer.setDaemon(true);
            customer.start();
            Thread.sleep(showroom.pause);
        }
        supplier.join();
        Thread.sleep(showroom.pause);
        System.out.println("Автосалон закрылся");
    }

    public synchronized void putCar(Car car) {
        cars.addLast(car);
        notify();
    }

    public int getNumCars() {
        return numCars;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public int getDecisionMakingTime() {
        return decisionMakingTime;
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
