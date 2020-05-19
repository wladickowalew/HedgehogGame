package GUI;

import javax.swing.*;
import java.awt.*;

public class GameLabel extends JLabel {

    private String text;

    public GameLabel(String text){
        super(text);
        setForeground(Color.WHITE);
        Font f = new Font("Comic Sans MS", 0, 24);
        setFont(f);
        setSize(200,30);
    }

    public GameLabel(String text, int number){
        this(text + ": " + number);
        this.text = text;
    }

    public void change(int number){
        setText(text + ": " + number);
    }

}
