package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by BALALAIKA on 18.09.2015.
 */
public class Car implements Runnable{
    private Storage storage = null;
    private Good.GoodTypes goodType;
    private int carCapacity = 0;
    private IntegerProperty currentCounter = new SimpleIntegerProperty(0);

    public Car(Good.GoodTypes type, int capacity, Storage store) {
        carCapacity = capacity;
        goodType = type;
        storage = store;
    }

    public Good.GoodTypes getGoodType() {
        return goodType;
    }

    public IntegerProperty getCurrentCounter() {
        return currentCounter;
    }

    synchronized public void receiveGood() {
        currentCounter.setValue(currentCounter.getValue() + 1);
    }

    synchronized public boolean isFull() {
        return currentCounter.getValue() >= carCapacity;
    }

    @Override
    public void run() {
        try {
            while (!isFull()) {
                Thread.sleep(200);
                storage.giveGoodToCar(this);
            }
        }
        catch (InterruptedException e) {
            return;
        }
    }
}
