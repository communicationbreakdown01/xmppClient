/**
 * Created by Jessica Laxa on 16.12.2016.
 */

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogFile {


    private static LogFile instance;
    public static final Logger myLogger = Logger.getLogger("Logfile");


    private LogFile(){

    }

    public static synchronized LogFile getInstance()
    {
        if (instance == null)
            //doSomething();
            System.out.println("Loggerinstanz gestartet");
        instance = new LogFile();

        return instance;
    }

    public static void doSomething(){
        try {
            System.out.println("Der Logger führt etwas durch.");
            FileHandler myFileHandler = new FileHandler("logfile.txt", true);
            myFileHandler.setFormatter(new SimpleFormatter());
            myLogger.addHandler(myFileHandler);
            myLogger.setUseParentHandlers(false);
            myLogger.setLevel(Level.ALL);


        } catch (SecurityException e) {
            System.out.println("Loggerinstanz nicht gestartet");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Loggerinstanz nicht gestartet");
            e.printStackTrace();
        }
    }
}
