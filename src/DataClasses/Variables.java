package DataClasses;

import java.awt.*;

public class Variables {
    // constants
    public static final int CELL_SIZE = 40;
    public static final int WALLS_COUNT = 40;
    public static final Dimension[] DIMENSIONS = {new Dimension(800,600),
                                                  new Dimension(1024,768),
                                                  new Dimension(1200,600),
                                                  new Dimension(1280,1024),
                                                  new Dimension(1680,1050),
                                                  new Dimension(1920,1080)};
    //variables
    public static int CELLS_W;
    public static int CELLS_H;
    public static int FIELD_WIDTH;
    public static int FIELD_HEIGHT;

    public static void setDimension(Dimension d){
        CELLS_W = (int)d.getWidth()  / CELL_SIZE;
        CELLS_H = (int)d.getHeight()  / CELL_SIZE;
        FIELD_WIDTH  = CELLS_W * CELL_SIZE;
        FIELD_HEIGHT = CELLS_H * CELL_SIZE;
    }
}
