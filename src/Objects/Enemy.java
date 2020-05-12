package Objects;

import DataClasses.Images;
import DataClasses.Variables;

public class Enemy extends GameObject{

    public Enemy() {
        super(Images.SIMPLE_ENEMY);
    }

    public int[] getNewCoords(){
        int n = (int)(Math.random() * 4);
        if (n == 0) return new int[]{getX(), getY() + Variables.CELL_SIZE};
        if (n == 1) return new int[]{getX(), getY() - Variables.CELL_SIZE};
        if (n == 2) return new int[]{getX() + Variables.CELL_SIZE, getY()};
        if (n == 3) return new int[]{getX() - Variables.CELL_SIZE, getY()};
        return null;
    }

}