public class CarSupplier implements Runnable {
    private final CarShowroom showroom;
    private final String brand;
    private final int numCars;
    private final int deliveryTime;

    public CarSupplier(CarShowroom showroom, String brand) {
        this.showroom = showroom;
        this.brand = brand;
        this.numCars = showroom.getNumCars();
        this.deliveryTime = showroom.getDeliveryTime();
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println("Поставщик " + name + " заключил контракт на поставку " + numCars + " автомобилей марки " + brand);
        System.out.println("Поставщик " + name + " приступил к выполнению контракта");
        try {
            for (int i = 0; i < numCars; i++) {
                Thread.sleep(deliveryTime);
                showroom.putCar(new Car(brand));
                System.out.println("Поставщик " + name + " поставил автомобиль");
            }
        } catch (InterruptedException ignored) { }
        System.out.println("Поставщик " + name + " выполнил условие контракта");
    }
}
