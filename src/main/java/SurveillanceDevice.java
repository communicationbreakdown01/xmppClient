import com.pi4j.io.i2c.I2CFactory;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by JLx_F on 18.12.2016.
 */
public class SurveillanceDevice implements ISubject{

    private SensorRead ekgSensor = new SensorRead();

    private ArrayList<Observer> observerList;

    private String ekgData;

    public SurveillanceDevice(){
        observerList = new ArrayList<Observer>();
    }

    public void registerObserver(Observer o){
        observerList.add(o);
    }

    public void removeObserver(Observer o){
        int i = observerList.indexOf(o);
        if (i >= 0){
            observerList.remove(i);
        }
    }

    public void notifyObservers() {
        for (int i = 0; i < observerList.size(); i++) {
            Observer o = observerList.get(i);
            o.update(ekgData);
        }
    }

    public void set() throws IOException, I2CFactory.UnsupportedBusNumberException {
        ekgData = ekgSensor.getValues();
        notifyObservers();
    }

}
