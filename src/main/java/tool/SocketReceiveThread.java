package tool;

import javax.swing.*;
import java.io.IOException;

public class SocketReceiveThread extends Thread{
    SimpleSocketClient  client;
    JTextArea textArea;
    private int id;

    public SocketReceiveThread(SimpleSocketClient client, JTextArea textArea, int id) {
        this.client = client;
        this.textArea = textArea;
        this.id = id;
    }

    public void run(){
        try {
            while (true){
                textArea.append("\nserver: "+ client.readMsg());
                System.out.println("id: " + id);
            }
        } catch (IOException e1) {
            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
