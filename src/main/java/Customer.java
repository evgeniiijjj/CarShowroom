public class Customer implements Runnable {
    private final CarShowroom showroom;

    public Customer(CarShowroom showroom) {
        this.showroom = showroom;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        try {
            System.out.println("Посетитель " + name + " вошел в салон");
            Thread.sleep(CarShowroom.DECISION_MAKING_TIME);
            System.out.println("Посетитель " + name + " принял решение о покупке автомобиля");
        } catch (InterruptedException ignored) { }
        showroom.buyCar();
    }
}
