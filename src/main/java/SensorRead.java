/**
 * Created by Jessica Laxa on 12.12.2016.
 */
import java.io.IOException;
import java.text.DecimalFormat;

import com.pi4j.gpio.extension.ads.ADS1115GpioProvider;
import com.pi4j.gpio.extension.ads.ADS1115Pin;
import com.pi4j.gpio.extension.ads.ADS1x15GpioProvider.ProgrammableGainAmplifierValue;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinAnalogInput;
import com.pi4j.io.gpio.event.GpioPinAnalogValueChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerAnalog;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;
public class SensorRead{

    private  DecimalFormat df;
    private  DecimalFormat pdf;
    private GpioController gpio;
    private ADS1115GpioProvider gpioProvider;
    String values;

    public void startLogging(){

        //Singleton get logger instance to write in logfile
        LogFile mylogger = LogFile.getInstance();
        mylogger.doSomething();

    }

    public void setFormat(){
        //format of sensor values.
        df = new DecimalFormat("#.##");
        pdf = new DecimalFormat("###.#");

    }

    public String getGPIOValues() throws IOException, UnsupportedBusNumberException {
        // creating a GPIO controller to access the GPIO pins
        gpio = GpioFactory.getInstance();

        // GPIO Provider for ADS1115 (Especially for Adafruit)
        gpioProvider = new ADS1115GpioProvider(I2CBus.BUS_1, ADS1115GpioProvider.ADS1115_ADDRESS_0x48);

        //  gpio analog input pins from ADS1115
        GpioPinAnalogInput myInputs[] = {
                gpio.provisionAnalogInputPin(gpioProvider, ADS1115Pin.INPUT_A0, "MyAnalogInput-A0"),
                gpio.provisionAnalogInputPin(gpioProvider, ADS1115Pin.INPUT_A1, "MyAnalogInput-A1"),
                gpio.provisionAnalogInputPin(gpioProvider, ADS1115Pin.INPUT_A2, "MyAnalogInput-A2"),
                gpio.provisionAnalogInputPin(gpioProvider, ADS1115Pin.INPUT_A3, "MyAnalogInput-A3"),
        };

        //Set Amplifier to get the true Value at 3.3V (norm)
        gpioProvider.setProgrammableGainAmplifier(ProgrammableGainAmplifierValue.PGA_4_096V, ADS1115Pin.ALL);


        // Define a threshold value for each pin for analog value change events to be raised.
        gpioProvider.setEventThreshold(500, ADS1115Pin.ALL);

        // Define the monitoring thread refresh interval (in milliseconds).
        gpioProvider.setMonitorInterval(100);

        // create analog pin value change listener
        GpioPinListenerAnalog listener = new GpioPinListenerAnalog()
        {
            @Override
            public void handleGpioPinAnalogValueChangeEvent(GpioPinAnalogValueChangeEvent event)
            {
                // RAW value
                double value = event.getValue();

                // percentage
                double percent =  ((value * 100) / ADS1115GpioProvider.ADS1115_RANGE_MAX_VALUE);

                // approximate voltage ( *scaled based on PGA setting )
                double voltage = gpioProvider.getProgrammableGainAmplifier(event.getPin()).getVoltage() * (percent/100);

                // display output
                values = (" (" + event.getPin().getName() +") : VOLTS=" + df.format(voltage) + "  | PERCENT=" + pdf.format(percent) + "% | RAW=" + value + "       ");

            }
        };

        myInputs[0].addListener(listener);
        myInputs[1].addListener(listener);
        myInputs[2].addListener(listener);
        myInputs[3].addListener(listener);

        return values;
    }

    public void setSurveillanceTime(int time) throws InterruptedException {

        // keep program running for 10 minutes
        Thread.sleep(time);
    }

    public void stopGPIO(){

        // stop all GPIO activity/threads by shutting down the GPIO controller
        gpio.shutdown();
    }

    public void getValues() throws IOException, UnsupportedBusNumberException {
        getGPIOValues();
    }


        public void main(String args[]) throws InterruptedException, UnsupportedBusNumberException, IOException {

           startLogging();
           setFormat();
           getValues();
           setSurveillanceTime(600000);
           stopGPIO();


            //Only for developing purposes, can be deleted when finished
            System.out.println("ADS1115 GPIO ... started.");


            System.out.println("Exiting ADS1115GpioExample");
        }
    }