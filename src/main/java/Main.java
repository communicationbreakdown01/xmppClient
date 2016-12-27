import com.pi4j.io.i2c.I2CFactory;

import java.io.IOException;

/**
 * Created by Jessica Laxa on 05.12.2016.
 */
public class Main {

    private static XMPP xmppSession = new XMPP();
    //private static SurveillanceDevice patientDevice = new SurveillanceDevice();
    //private static ConcreteObserver customer = new ConcreteObserver();

    public static void main(String[] args) throws IOException, I2CFactory.UnsupportedBusNumberException {
        //patientDevice.registerObserver(customer);
        //patientDevice.set();
        xmppSession.createConfig("test","test");
        xmppSession.connectMe();
        xmppSession.login();
        xmppSession.setStatus(true, "I am available!");

        while(true){
            xmppSession.sendMessage("hey");
            xmppSession.listenChat();

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }





}
