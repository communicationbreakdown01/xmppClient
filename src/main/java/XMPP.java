import org.jivesoftware.smack.*;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.IOException;

/**
 * Created by JLx_F on 05.12.2016.
 */
public class XMPP {
    public static final String host = "192.168.119.102";
    public static final int port = 5222;
    private String userName="";
    private String passWord="";
    AbstractXMPPConnection connection;
    ChatManager chatManager;
    Chat newChat;



    public void createConfig(String usrName, String pwd){
        this.userName=usrName;
        this.passWord=pwd;

        XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();
                configBuilder.setUsernameAndPassword(userName, passWord);
                configBuilder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
                configBuilder.setResource("ClientJava");
                configBuilder.setServiceName(host);
                configBuilder.setHost(host);
                configBuilder.setPort(port);

                connection = new XMPPTCPConnection(configBuilder.build());
    }

    public void disconnectConnection(){
        connection.disconnect();
    }

    public void connectMe(){
        try {
            connection.connect();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        }

    }



    public void sendMessage(){
        chatManager=ChatManager.getInstanceFor(connection);
        newChat = chatManager.createChat("admin@mms-virtualbox");


        try {
            newChat.sendMessage("Hello you");
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }

    }

    public void listenChat(){
        ChatManager manager = ChatManager.getInstanceFor(this.connection);
        manager.addChatListener(new ChatManagerListener() {

            @Override
            public void chatCreated(Chat chat, boolean createdLocally) {
                System.out.println("Created chat");
                chat.addMessageListener(new ChatMessageListener() {

                    @Override
                    public void processMessage(Chat chat, Message message) {
                        System.out.println(message.getBody());

                    }
                });

            }
        });
    }



    public void login(){
        try {
            connection.login(userName,passWord);
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
