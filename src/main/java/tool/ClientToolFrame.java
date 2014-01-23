package tool;

import javax.swing.*;
import java.awt.*;

public class ClientToolFrame extends JFrame{
    public static final int PLAYER_COUNT = 6;
    public ClientToolFrame() {
        setLayout(new GridLayout());
        for (int i = 0; i < PLAYER_COUNT; ++i){
            add(new SingleClientFrame(i));
        }
    }
}