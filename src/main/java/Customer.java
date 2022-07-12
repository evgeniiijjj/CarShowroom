public class Customer implements Runnable {
    private final CarShowroom showroom;
    private final int decisionMakingTime;

    public Customer(CarShowroom showroom) {
        this.showroom = showroom;
        this.decisionMakingTime = showroom.decisionMakingTime;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        try {
            System.out.println("Посетитель " + name + " вошел в салон");
            Thread.sleep(decisionMakingTime);
        } catch (InterruptedException ignored) { }
        showroom.buyCar();
    }
}
