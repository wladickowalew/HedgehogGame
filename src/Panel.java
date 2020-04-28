import DataClasses.Images;
import DataClasses.Variables;
import Objects.GameObject;
import Objects.Player;
import Objects.Wall;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Panel extends JPanel {

    private Player player;
    private ArrayList<Wall> walls;

    public Panel(){
        Variables.computeValues();
        Images.loadImages();
        setPreferredSize(new Dimension(Variables.FIELD_WIDTH, Variables.FIELD_HEIGHT));
        startGame();
    }

    private void startGame(){
        player = new Player();
        addWalls();
    }

    public void stepArrow(int dx, int dy){
        newCoordinates(dx * Variables.CELL_SIZE, dy * Variables.CELL_SIZE);
        repaint();
    }

    private void newCoordinates(int dx, int dy){
        int newX = player.getX() + dx;
        int newY = player.getY() + dy;
        if (collisionWithAllObjects(newX, newY)) return;
        player.setCoordinates(newX, newY);
    }

    //collisions

    private boolean collisionWith(ArrayList list, GameObject object){
        return collisionWith(list, object.getX(), object.getY());
    }

    private boolean collisionWith(ArrayList list, int x, int y){
        for (GameObject object: (ArrayList<GameObject>)list)
            if (object.collisianWith(x, y))
                return true;
        return  false;
    }

    private boolean collisionWithAllObjects(GameObject object){
        return collisionWithAllObjects(object.getX(), object.getY());
    }

    private boolean collisionWithAllObjects(int x, int y){
        if (walls != null && collisionWith(walls, x, y)) return true;
        return false;
    }

    public void addWalls(){
        walls = new ArrayList<Wall>();
        while (walls.size() < Variables.WALLS_COUNT){
            Wall wall = new Wall();
            if (wall.collisianWith(player) || collisionWithAllObjects(wall))
                continue;
            walls.add(wall);
        }
    }

    //PAINT

    public void drawGrass(Graphics gr){
        for (int i = 0; i < Variables.CELLS_H; i++){
            for (int j = 0; j < Variables.CELLS_W; j++){
                gr.drawImage(Images.GRASS, j * Variables.CELL_SIZE,
                                           i * Variables.CELL_SIZE, null);
            }
        }
    }

    public void drawObjects(Graphics gr){
        for (Wall wall: walls)
            wall.draw(gr);
        player.draw(gr);
    }

    public void paintComponent(Graphics gr){
        super.paintComponent(gr);
        drawGrass(gr);
        drawObjects(gr);
    }

}
