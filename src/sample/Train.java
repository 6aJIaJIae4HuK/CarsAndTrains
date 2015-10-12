package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by BALALAIKA on 18.09.2015.
 */
public class Train implements Runnable{
    private Storage storage = null;
    private IntegerProperty liquidCounter = new SimpleIntegerProperty(0);
    private IntegerProperty looseCounter = new SimpleIntegerProperty(0);
    private IntegerProperty perishableCounter = new SimpleIntegerProperty(0);

    public Train (int wagonCapacity,
                  int wagonLiquidCounter,
                  int wagonLooseCounter,
                  int wagonPerishableCounter,
                  Storage store) {
        liquidCounter.setValue(wagonCapacity * wagonLiquidCounter);
        looseCounter.setValue(wagonCapacity * wagonLooseCounter);
        perishableCounter.setValue(wagonCapacity * wagonPerishableCounter);

        storage = store;
    }

    public IntegerProperty getLiquidCounter() {
        return liquidCounter;
    }

    public IntegerProperty getLooseCounter() {
        return looseCounter;
    }

    public IntegerProperty getPerishableCounter() {
        return perishableCounter;
    }

    public boolean isEmpty() {
        return getLiquidCounter().getValue() == 0 &&
               getLooseCounter().getValue() == 0 &&
               getPerishableCounter().getValue() == 0;
    }

    synchronized public void giveGood(Good.GoodTypes type) {
        switch (type) {
            case LIQUIDGOOD:
                giveLiquidGood();
                break;
            case LOOSEGOOD:
                giveLooseGood();
                break;
            case PERISHABLEGOOD:
                givePerishableGood();
                break;
        }
    }

    synchronized public boolean canGive(Good.GoodTypes type) {
        boolean res = false;
        switch (type) {
            case LIQUIDGOOD:
                res = liquidCounter.getValue() > 0 && storage.canReceiveGoodFromTrain(Good.GoodTypes.LIQUIDGOOD, this);
                break;
            case LOOSEGOOD:
                res = looseCounter.getValue() > 0 && storage.canReceiveGoodFromTrain(Good.GoodTypes.LOOSEGOOD, this);
                break;
            case PERISHABLEGOOD:
                res = perishableCounter.getValue() > 0 && storage.canReceiveGoodFromTrain(Good.GoodTypes.PERISHABLEGOOD, this);
                break;
        }
        return res;
    }

    public void giveLiquidGood() {
        if (getLiquidCounter().getValue() > 0)
            liquidCounter.setValue(getLiquidCounter().getValue() - 1);
    }

    public void giveLooseGood() {
        if (getLooseCounter().getValue() > 0)
            looseCounter.setValue(getLooseCounter().getValue() - 1);
    }

    public void givePerishableGood() {
        if (getPerishableCounter().getValue() > 0)
            perishableCounter.setValue(getPerishableCounter().getValue() - 1);
    }

    @Override
    public void run() {
        try {
            while (!isEmpty()) {
                Thread.sleep(500);
                if (canGive(Good.GoodTypes.LIQUIDGOOD)) {
                    storage.receiveGoodFromTrain(Good.GoodTypes.LIQUIDGOOD, this);
                } else if (canGive(Good.GoodTypes.LOOSEGOOD)) {
                    storage.receiveGoodFromTrain(Good.GoodTypes.LOOSEGOOD, this);
                } else if (canGive(Good.GoodTypes.PERISHABLEGOOD)) {
                    storage.receiveGoodFromTrain(Good.GoodTypes.PERISHABLEGOOD, this);
                }
            }
        }
        catch (InterruptedException e) {
            return;
        }
    }
}
