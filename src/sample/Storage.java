package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by BALALAIKA on 18.09.2015.
 */
public class Storage {

    private int looseGoodsCapacity = 0;
    private int liquidGoodsCapacity = 0;
    private int perishableGoodsCapacity = 0;

    private final IntegerProperty currentLooseCounter = new SimpleIntegerProperty(0);
    private final IntegerProperty currentLiquidCounter = new SimpleIntegerProperty(0);
    private final IntegerProperty currentPerishableCounter = new SimpleIntegerProperty(0);

    public Storage(int looseCapacity, int liquidCapacity, int perishableCapacity) {
        looseGoodsCapacity = looseCapacity;
        liquidGoodsCapacity = liquidCapacity;
        perishableGoodsCapacity = perishableCapacity;
    }

    synchronized boolean canReceiveGoodFromTrain(Good.GoodTypes goodType, Train train) {
        boolean res = true;
        switch (goodType) {
            case LIQUIDGOOD:
                res = res && currentLiquidCounter.getValue() < liquidGoodsCapacity;
                res = res && train.getLiquidCounter().getValue() > 0;
                break;
            case LOOSEGOOD:
                res = res && currentLooseCounter.getValue() < looseGoodsCapacity;
                res = res && train.getLooseCounter().getValue() > 0;
                break;
            case PERISHABLEGOOD:
                res = res && currentPerishableCounter.getValue() < perishableGoodsCapacity;
                res = res && train.getPerishableCounter().getValue() > 0;
                break;
        }
        return res;
    }

    synchronized void receiveGood(Good.GoodTypes goodType) {
        switch (goodType) {
            case LIQUIDGOOD:
                if (currentLiquidCounter.getValue() < liquidGoodsCapacity)
                    currentLiquidCounter.setValue(currentLiquidCounter.getValue() + 1);
                break;
            case LOOSEGOOD:
                if (currentLooseCounter.getValue() < looseGoodsCapacity)
                    currentLooseCounter.setValue(currentLooseCounter.getValue() + 1);
                break;
            case PERISHABLEGOOD:
                if (currentPerishableCounter.getValue() < perishableGoodsCapacity)
                    currentPerishableCounter.setValue(currentPerishableCounter.getValue() + 1);
                break;
        }
    }

    synchronized void receiveGoodFromTrain(Good.GoodTypes goodType, Train train) {
        if (canReceiveGoodFromTrain(goodType, train)) {
            train.giveGood(goodType);
            receiveGood(goodType);
        }
    }

    synchronized boolean canGiveFoodToCar(Car car) {
        boolean res = true;
        if (car.isFull())
            res =  false;
        switch (car.getGoodType()) {
            case LIQUIDGOOD:
                res = res && currentLiquidCounter.getValue() > 0;
                break;
            case LOOSEGOOD:
                res = res && currentLooseCounter.getValue() > 0;
                break;
            case PERISHABLEGOOD:
                res = res && currentPerishableCounter.getValue() > 0;
                break;
        }
        return res;
    }

    synchronized void giveGood(Good.GoodTypes goodType) {
        switch (goodType) {
            case LIQUIDGOOD:
                if (currentLiquidCounter.getValue() > 0)
                    currentLiquidCounter.setValue(currentLiquidCounter.getValue() - 1);
                break;
            case LOOSEGOOD:
                if (currentLooseCounter.getValue() > 0)
                    currentLooseCounter.setValue(currentLooseCounter.getValue() - 1);
                break;
            case PERISHABLEGOOD:
                if (currentPerishableCounter.getValue() > 0)
                    currentPerishableCounter.setValue(currentPerishableCounter.getValue() - 1);
                break;
        }
    }

    synchronized void giveGoodToCar(Car car) {
        if (canGiveFoodToCar(car)) {
            giveGood(car.getGoodType());
            car.receiveGood();
        }
    }

    public IntegerProperty currentLiquidProperty() {
        return currentLiquidCounter;
    }

    public IntegerProperty currentLooseProperty() {
        return currentLooseCounter;
    }

    public IntegerProperty currentPerishableProperty() {
        return currentPerishableCounter;
    }
}
