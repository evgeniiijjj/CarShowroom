public class Customer implements Runnable {
    private final CarShowroom showroom;
    private final long decisionMakingTime;

    public Customer(CarShowroom showroom) {
        this.showroom = showroom;
        this.decisionMakingTime = showroom.getDecisionMakingTime();
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        try {
            System.out.println("Посетитель " + name + " вошел в салон");
            Thread.sleep(decisionMakingTime);
            System.out.println("Посетитель " + name + " принял решение о покупке автомобиля");
        } catch (InterruptedException ignored) { }
        showroom.buyCar();
    }
}
