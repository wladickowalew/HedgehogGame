package DataClasses;

public class Variables {
    // constants
    public static final int CELL_SIZE = 40;

    public static final int WALLS_COUNT = 40;

    //variables
    public static int CELLS_W;
    public static int CELLS_H;
    public static int FIELD_WIDTH;
    public static int FIELD_HEIGHT;

    public static void computeValues(){
        CELLS_W = FIELD_WIDTH  / CELL_SIZE;
        CELLS_H = FIELD_HEIGHT / CELL_SIZE;
    }
}
