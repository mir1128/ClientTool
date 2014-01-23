package tool;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class SingleClientFrame extends JPanel {
    private int id;
    private JButton sendButton;
    private JButton connectButton;
    private JButton disconnectButton;
    private JTextArea messageAreas;
    private JTextArea sendArea;
    private JComboBox selectSendMessage;

    private SimpleSocketClient clients;

    private Map<String, String> messageTemplateMap = new LinkedHashMap<String, String>();

    private void initMessageTemplateMap(){
        messageTemplateMap.put("001 : playerID", "{\"MsgID\" : \"001\",\"Msg\": \"1001\"}");
        messageTemplateMap.put("201 : T Move", "{\"MsgID\" :201,\"Msg\" : \"(2,3)\"}");
        messageTemplateMap.put("301 : P Type", "{ \"MsgID\" :301,\"Msg\": \"walk\"}");
        messageTemplateMap.put("302 : P Deduce","{\"MsgID\": 302,\"Msg\": \"(1,1)100 (2,2)80 (1,2)70 (12,3)0\"}");
        messageTemplateMap.put("303 : P Proposal","{\"MsgID\": 303,\"Msg\" :{\"playerid1\" : \"(10,10)\",\"playerid2\" : \"(10,10)\",\"playerid3\" : \"(10,10)\",\"playerid4\" : \"(10,10)\",\"playerid5\" : \"(10,10)\",}}");
        messageTemplateMap.put("304 : P Vote","{\"MsgID\": 304,\"Msg\": [\"playerid1\",\"playerid2\",\"playerid3\",\"playerid4\",\"playerid5\"]}");
        messageTemplateMap.put("305 : P Move","{\"MsgID\":305,\"Msg\":(2,3)}");
    }


    SingleClientFrame(int id){
        this.id = id;
        initMessageTemplateMap();
        setLayout(new FlowLayout());

        sendButton = new JButton("p"+id+" send");
        connectButton = new JButton("p"+id+" connect");
        disconnectButton = new JButton("p"+id+" disconnect");
        messageAreas = new JTextArea(30, 20);
        sendArea = new JTextArea(8, 20);
        selectSendMessage = new JComboBox();

        sendButton.addActionListener(new SendMessageButtonListener());
        connectButton.addActionListener(new ConnectButtonListener());
        disconnectButton.addActionListener(new DisconnectButtonListener());
        selectSendMessage.addActionListener(new ComboBoxSelectActionListener());

        for (String key : messageTemplateMap.keySet()){
            selectSendMessage.addItem(key);
        }

        add( new JScrollPane(messageAreas));
        add( new JScrollPane(sendArea));
        add( connectButton);
        add( disconnectButton);
        add( sendButton);
        add(selectSendMessage);
    }

    class ConnectButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                clients = new SimpleSocketClient("127.0.0.1", 4321);
                messageAreas.append(clients.readMsg());
            } catch (IOException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    class DisconnectButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                clients.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    class SendMessageButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String message = null;
            messageAreas.append(sendArea.getText());
            clients.sendMsg(sendArea.getText());
            try {
                messageAreas.append(clients.readMsg());
            } catch (IOException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            sendArea.setText("");
        }
    }

    class ComboBoxSelectActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String selectItem = (String)((JComboBox)e.getSource()).getSelectedItem();

            sendArea.setText(messageTemplateMap.get(selectItem));
        }
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("sdfsdf");
        jFrame.add(new SingleClientFrame(1));
        SwingConsole.run(jFrame, 1440, 960);
    }
}
