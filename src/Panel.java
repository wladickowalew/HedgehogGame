import DataClasses.Images;
import DataClasses.Variables;
import Objects.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Panel extends JPanel {

    private Player player;
    private Exit exit;
    private ArrayList<Wall> walls;
    private ArrayList<Coin> coins;
    private ArrayList<Fire> fires;
    private ArrayList<Enemy> enemies;
    private boolean isEnd;
    private int coin_count;
    private Timer updateTimer;

    public Panel(){
        setPreferredSize(new Dimension(Variables.FIELD_WIDTH, Variables.FIELD_HEIGHT));
        startGame();
    }

    private void startGame(){
        if (updateTimer != null)
            updateTimer.stop();
        isEnd = false;
        player = new Player();
        exit = new Exit();
        coin_count = 0;
        addWalls();
        addCoins();
        addFires();
        addEnemies();
        updateTimer = new Timer(Variables.ENEMY_STEP_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                field_update();
            }
        });
        updateTimer.start();
    }

    private void field_update(){
        for (Enemy enemy: enemies){
            int[] xy = enemy.getNewCoords();
            if (collisionWith(walls, xy[0], xy[1]))
                continue;
            enemy.setCoordinates(xy[0], xy[1]);
            if (enemy.collisionWith(player))
                endLoseGame();
        }
        repaint();
    }

    public void stepArrow(int dx, int dy){
        if (isEnd) return;
        int newX = player.getX() + dx * Variables.CELL_SIZE;
        int newY = player.getY() + dy * Variables.CELL_SIZE;
        if (collisionWith(walls, newX, newY)) return;
        player.setCoordinates(newX, newY);
        if (player.collisionWith(exit))
            endWinLevel();
        if (collisionWith(fires, player)||collisionWith(enemies, player))
            endLoseGame();
        if (collisionWith(coins, player)){
            coin_count++;
            for(int i = 0; i < coins.size(); i++){
                if (player.collisionWith(coins.get(i))){
                    coins.remove(i);
                    break;
                }
            }
        }

        repaint();
    }

    private void endWinLevel(){
        isEnd = true;
        updateTimer.stop();
        System.out.println("You Win! Coins: " + coin_count);
    }

    private void endLoseGame(){
        isEnd = true;
        updateTimer.stop();
        System.out.println("You Lose! Coins: " + coin_count);
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
        if (coins != null && collisionWith(coins, x, y)) return true;
        if (fires != null && collisionWith(fires, x, y)) return true;
        if (enemies != null && collisionWith(enemies, x, y)) return true;
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

    public void addCoins(){
        coins = new ArrayList<Coin>();
        while (coins.size() < Variables.COINS_COUNT){
            Coin coin = new Coin();
            if (collisionWithAllObjects(coin))
                continue;
            coins.add(coin);
        }
    }

    public void addFires(){
        fires = new ArrayList<Fire>();
        while (fires.size() < Variables.FIRE_COUNT){
            Fire fire = new Fire();
            if (collisionWithAllObjects(fire))
                continue;
            fires.add(fire);
        }
    }

    public void addEnemies(){
        enemies = new ArrayList<Enemy>();
        while (enemies.size() < Variables.ENEMY_COUNT){
            Enemy enemy = new Enemy();
            if (collisionWithAllObjects(enemy))
                continue;
            enemies.add(enemy);
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
        for (Coin coin: coins)
            coin.draw(gr);
        for (Fire fire: fires)
            fire.draw(gr);
        for (Enemy enemy: enemies)
            enemy.draw(gr);
        exit.draw(gr);
        player.draw(gr);
    }

    public void paintComponent(Graphics gr){
        super.paintComponent(gr);
        drawGrass(gr);
        drawObjects(gr);
    }

}
