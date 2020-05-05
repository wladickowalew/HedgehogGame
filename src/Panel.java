import DataClasses.Images;
import DataClasses.Variables;
import Objects.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Panel extends JPanel {

    private Player player;
    private Exit exit;
    private ArrayList<Wall> walls;
    private boolean isEnd;

    public Panel(){
        setPreferredSize(new Dimension(Variables.FIELD_WIDTH, Variables.FIELD_HEIGHT));
        startGame();
    }

    private void startGame(){
        isEnd = false;
        player = new Player();
        exit = new Exit();
        addWalls();
    }

    public void stepArrow(int dx, int dy){
        if (isEnd) return;
        int newX = player.getX() + dx * Variables.CELL_SIZE;
        int newY = player.getY() + dy * Variables.CELL_SIZE;
        if (collisionWith(walls, newX, newY)) return;
        player.setCoordinates(newX, newY);
        if (player.collisionWith(exit))
            endWinLevel();

        repaint();
    }

    private void endWinLevel(){
        isEnd = true;
        System.out.println("You Win!");
    }

    //collisions
    //-------------------------------------------------------------------------------------------
    private boolean collisionWith(ArrayList list, GameObject object){
        return collisionWith(list, object.getX(), object.getY());
    }

    private boolean collisionWith(ArrayList list, int x, int y){
        for (GameObject object: (ArrayList<GameObject>)list)
            if (object.collisionWith(x, y))
                return true;
        return false;
    }

    private boolean collisionWithAllObjects(GameObject object){
        return collisionWithAllObjects(object.getX(), object.getY());
    }

    private boolean collisionWithAllObjects(int x, int y){
        if (player.collisionWith(x, y) || exit.collisionWith(x, y)) return true;
        if (walls != null && collisionWith(walls, x, y)) return true;
        return false;
    }
    //---------------------------------------------------------------------------------------------------

    public void addWalls(){
        walls = new ArrayList<Wall>();
        while (walls.size() < Variables.WALLS_COUNT){
            Wall wall = new Wall();
            if (collisionWithAllObjects(wall))
                continue;
            walls.add(wall);
        }
    }

    //PAINT
    //---------------------------------------------------------------------------------------------------
    public void drawGrass(Graphics gr){
        for (int i = 0; i < Variables.CELLS_H; i++){
            for (int j = 0; j < Variables.CELLS_W; j++){
                gr.drawImage(Images.GRASS, j * Variables.CELL_SIZE, i * Variables.CELL_SIZE,
                                 Variables.CELL_SIZE, Variables.CELL_SIZE,null);
            }
        }
    }

    public void drawObjects(Graphics gr){
        for (Wall wall: walls)
            wall.draw(gr);
        exit.draw(gr);
        player.draw(gr);
    }

    public void paintComponent(Graphics gr){
        super.paintComponent(gr);
        drawGrass(gr);
        drawObjects(gr);
    }

}
