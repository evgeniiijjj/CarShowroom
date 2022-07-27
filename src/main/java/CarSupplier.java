public class CarSupplier implements Runnable {
    private final CarShowroom showroom;
    private final String brand;

    public CarSupplier(CarShowroom showroom, String brand) {
        this.showroom = showroom;
        this.brand = brand;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println("Поставщик " + name + " заключил контракт на поставку " + CarShowroom.NUMBER_OF_CARS + " автомобилей марки " + brand);
        System.out.println("Поставщик " + name + " приступил к выполнению контракта");
        try {
            for (int i = 0; i < CarShowroom.NUMBER_OF_CARS; i++) {
                Thread.sleep(CarShowroom.DELIVERY_TIME);
                showroom.putCar(new Car(brand));
                System.out.println("Поставщик " + name + " поставил автомобиль");
            }
        } catch (InterruptedException ignored) { }
        System.out.println("Поставщик " + name + " выполнил условие контракта");
    }
}
