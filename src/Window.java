import DataClasses.Variables;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Window extends JFrame {

    private Panel myPanel;

    class myKey implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            switch (code) {
                case 37: //left
                    myPanel.stepArrow(-1, 0);
                    break;
                case 38: //up
                    myPanel.stepArrow(0, -1);
                    break;
                case 39: //right
                    myPanel.stepArrow(1, 0);
                    break;
                case 40: //down
                    myPanel.stepArrow(0, 1);
                    break;
                default:
                    System.out.println("Pressed: " + code);
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    public Window() {
        addKeyListener(new myKey());
        setFocusable(true);
        setTitle("My First Game");
        Container сontentPane = getContentPane();
        сontentPane.setLayout(new BorderLayout(0,0));
        myPanel = new Panel();
        getContentPane().add(myPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
