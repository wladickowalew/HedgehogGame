package DataClasses;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Images {

    public static Image GRASS, PLAYER, EXIT;
    public static Image WALL, COIN, FIRE;
    public static Image SIMPLE_ENEMY;

    public static void  loadImages(){
        try {
            GRASS  = ImageIO.read(new File("images/grass.jpg"));
            WALL   = ImageIO.read(new File("images/wall.png"));
            PLAYER = ImageIO.read(new File("images/pers.png"));
            EXIT   = ImageIO.read(new File("images/exit.png"));
            COIN   = ImageIO.read(new File("images/coin.png"));
            FIRE   = ImageIO.read(new File("images/fire.png"));
            SIMPLE_ENEMY = ImageIO.read(new File("images/enemy.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}