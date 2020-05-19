import DataClasses.Images;
import DataClasses.Variables;
import GUI.GameLabel;
import Objects.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private GameLabel levelLBL, coinsLBL, restartLBL;

    public Panel(){
        setPreferredSize(new Dimension(Variables.FIELD_WIDTH, Variables.FIELD_HEIGHT + 30));
        coin_count = 0;
        addLabels();
        startGame();
    }

    private void startGame(){
        if (updateTimer != null)
            updateTimer.stop();
        isEnd = false;
        player = new Player();
        exit = new Exit();
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

    private void restartGame(){
        coin_count = 0;
        Variables.LEVEL = 1;
        startGame();
    }

    private void addLabels(){
        setLayout(null);

        levelLBL = new GameLabel("Level", Variables.LEVEL);
        levelLBL.setLocation(10, Variables.FIELD_HEIGHT);
        add(levelLBL);

        coinsLBL = new GameLabel("Coins", coin_count);
        coinsLBL.setLocation(220, Variables.FIELD_HEIGHT);
        add(coinsLBL);

        restartLBL = new GameLabel("Restart");
        restartLBL.setLocation(430, Variables.FIELD_HEIGHT);
        restartLBL.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                restartGame();
            }
        });
        add(restartLBL);

    }

    private void updateLabels(){
        levelLBL.change(Variables.LEVEL);
        coinsLBL.change(coin_count);
    }

    private void field_update(){
        for (Enemy enemy: enemies){
            int[] xy = enemy.getNewCoords();
            int i;
            if ((i = collisionWith(walls, xy[0], xy[1])) != -1)
                continue;
            enemy.setCoordinates(xy[0], xy[1]);
            if (enemy.collisionWith(player)) {
                if (coin_count >= enemy.getPrice()) {
                    coin_count -= enemy.getPrice();
                    enemies.remove(enemies.indexOf(enemy));
                } else
                    endLoseGame();
            }
        }
        repaint();
    }

    public void stepArrow(int dx, int dy){
        if (isEnd) return;
        int newX = player.getX() + dx * Variables.CELL_SIZE;
        int newY = player.getY() + dy * Variables.CELL_SIZE;
        if (collisionWith(walls, newX, newY) != -1) return;
        player.setCoordinates(newX, newY);
        int i;
        if (player.collisionWith(exit))
            endWinLevel();
        if ((i = collisionWith(fires, player)) != -1)
            if(coin_count >= fires.get(i).getPrice()){
                coin_count-=fires.get(i).getPrice();
                fires.remove(i);
            }else
                endLoseGame();
        if ((i = collisionWith(enemies, player)) != -1)
            if(coin_count >= enemies.get(i).getPrice()){
                coin_count -= enemies.get(i).getPrice();
                enemies.remove(i);
            }else
                endLoseGame();
        if ((i = collisionWith(coins, player)) != -1){
            coin_count++;
            coins.remove(i);
        }
        repaint();
    }

    private void endWinLevel(){
        isEnd = true;
        updateTimer.stop();
        System.out.println("You Win! Coins: " + coin_count);
        Variables.nextLevel();
        startGame();
    }

    private void endLoseGame(){
        isEnd = true;
        updateTimer.stop();
        System.out.println("You Lose! Coins: " + coin_count);
        repaint();
    }

    //collisions
    //-------------------------------------------------------------------------------------------
    private int collisionWith(ArrayList list, GameObject object){
        return collisionWith(list, object.getX(), object.getY());
    }

    private int collisionWith(ArrayList list, int x, int y){
        for (GameObject object: (ArrayList<GameObject>)list)
            if (object.collisionWith(x, y))
                return list.indexOf(object);
        return -1;
    }

    private boolean collisionWithAllObjects(GameObject object){
        return collisionWithAllObjects(object.getX(), object.getY());
    }

    private boolean collisionWithAllObjects(int x, int y){
        if (player.collisionWith(x, y) || exit.collisionWith(x, y)) return true;
        if (walls != null && collisionWith(walls, x, y) != -1) return true;
        if (coins != null && collisionWith(coins, x, y) != -1) return true;
        if (fires != null && collisionWith(fires, x, y) != -1) return true;
        if (enemies != null && collisionWith(enemies, x, y) != -1) return true;
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
        updateLabels();
        if (isEnd){
            int x = (Variables.FIELD_WIDTH - Images.END.getWidth(null)) / 2;
            int y = (Variables.FIELD_HEIGHT - Images.END.getHeight(null)) / 2;
            gr.drawImage(Images.END, x, y, null);
        }
    }

}
