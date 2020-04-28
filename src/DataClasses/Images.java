package DataClasses;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Images {

    public static Image GRASS, PLAYER, EXIT;
    public static Image WALL;

    public static void  loadImages(){
        try {
            GRASS  = ImageIO.read(new File("images/grass.jpg"));
            WALL   = ImageIO.read(new File("images/wall.png"));
            PLAYER = ImageIO.read(new File("images/pers.png"));
            EXIT   = ImageIO.read(new File("images/exit.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}