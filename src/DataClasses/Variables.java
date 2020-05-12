package DataClasses;

import java.awt.*;
import java.util.logging.Level;

public class Variables {
    // constants
    public static final int CELL_SIZE = 40;
    public static final Dimension[] DIMENSIONS = {new Dimension(800,600),
                                                  new Dimension(1024,768),
                                                  new Dimension(1200,600),
                                                  new Dimension(1280,1024),
                                                  new Dimension(1680,1050),
                                                  new Dimension(1920,1080)};
    //variables
    public static int ENEMY_STEP_DELAY = 1000;
    public static double WALLS_PERCENT = 0.3;
    public static double COINS_PERCENT = 0.1;
    public static double FIRE_PERCENT  = 0.07;
    public static double ENEMY_PERCENT = 0.03;
    public static int CELLS_W;
    public static int CELLS_H;
    public static int FIELD_WIDTH;
    public static int FIELD_HEIGHT;
    public static int WALLS_COUNT;
    public static int COINS_COUNT;
    public static int FIRE_COUNT;
    public static int ENEMY_COUNT;
    public static int LEVEL = 1;

    public static void nextLevel(){
        LEVEL++;
        ENEMY_STEP_DELAY *= 0.7;
        WALLS_PERCENT += 0.01;
        COINS_PERCENT -= 0.005;
        FIRE_PERCENT  += 0.005;
        ENEMY_PERCENT += 0.005;
        calculateCounts();
    }

    public static void calculateCounts(){
        int cells = CELLS_H * CELLS_W;
        WALLS_COUNT  = (int)(cells * WALLS_PERCENT);
        COINS_COUNT  = (int)(cells * COINS_PERCENT);
        FIRE_COUNT   = (int)(cells * FIRE_PERCENT);
        ENEMY_COUNT  = (int)(cells * ENEMY_PERCENT);
    }

    public static void setDimension(Dimension d){
        CELLS_W = (int)d.getWidth()  / CELL_SIZE;
        CELLS_H = (int)d.getHeight()  / CELL_SIZE;
        FIELD_WIDTH  = CELLS_W * CELL_SIZE;
        FIELD_HEIGHT = CELLS_H * CELL_SIZE;
        calculateCounts();
    }
}
