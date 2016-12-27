/**
 * Created by JLx_F on 19.12.2016.
 */
public class ConcreteObserver implements Observer{

    @Override
    public void update(String pulseData) {
        System.out.println("Hier der Sensorwert:"+pulseData);
    }
}
