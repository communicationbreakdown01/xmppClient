/**
 * Created by JLx_F on 05.12.2016.
 */
public class Main {

    private static XMPP xmppSession = new XMPP();

    public static void main(String[] args) {

        xmppSession.createConfig("test","test");
        xmppSession.connectMe();
        xmppSession.login();
        xmppSession.sendMessage();


        while(true){
            xmppSession.sendMessage();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }





}
