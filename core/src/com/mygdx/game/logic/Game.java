package com.mygdx.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.logic.sprites.Bomb;
import com.mygdx.game.logic.sprites.Bullet;
import com.mygdx.game.logic.sprites.Enemy;
import com.mygdx.game.logic.sprites.GameObject;
import com.mygdx.game.logic.sprites.Player;
import com.mygdx.game.logic.sprites.Weapon;

import java.util.ArrayList;
import java.util.Random;

/**
 * Implements all game logic and all objects relations
 */
public class Game {
    //game properties
    private int level;
    private long score;
    private boolean pause;
    private boolean endGame;
    //gameObjects
    private ArrayList<Enemy> enemies;
    private ArrayList<Bullet> bullets;
    private ArrayList<GameObject> ammoBoxs;
    private ArrayList<Bomb> bombs;
    private com.mygdx.game.logic.sprites.Player player;
    //factory
    private GameObjectFactory objectFactory;
    //game range
    private Rectangle map;


    /**
     * Game constructor take as parameters game map dimensions
     * @param map_width  map width
     * @param map_height map height
     */
    public Game(int map_width, int map_height) {
        pause = false;
        level = 0;
        score = 0;
        map = new Rectangle(0, 0, map_width, map_height);

        enemies = new ArrayList<Enemy>();
        bullets = new ArrayList<Bullet>();
        ammoBoxs = new ArrayList<GameObject>();
        bombs = new ArrayList<Bomb>();

        player = new Player();

        objectFactory = new GameObjectFactory((int)map.getWidth(),(int)map.getHeight());
        endGame = false;
    }

    /**
     * Set On/Off Pause state in game
     * @param p boolean Pause state
     */
    public void setPause(boolean p){
        pause = p;
    }

    /**
     * Return current Pause state
     * @return Pause state
     */
    public boolean isPause(){
        return pause;
    }

    /**
     * Return Player present in the game
     * @return Player
     */
    public final Player getPlayer() {
        return player;
    }

    /**
     * Move player in a direction.
     * X and Y values should be between 0 and 1.
     * @param x percent of movement in X axis
     * @param y percent of movement in Y axis
     * @return Vector2 with player movement
     */
    public Vector2 movePlayer(float x, float y) {

        float x_init = (float)(player.getX()  + x * player.getVelocity());
        float y_init = (float)(player.getY()  + y * player.getVelocity());
        float x_fin = (float)(player.getX() + player.getSprite().getWidth() + x * player.getVelocity());
        float y_fin = (float)(player.getY() + player.getSprite().getHeight() + y * player.getVelocity());

        if(x != 0 || y != 0)
            player.moveAnimation();

        //if new x coord is between the map limits
        Vector2 res = new Vector2(0,0);
        if(x_init >= 0 && x_fin <= map.getWidth()){
            player.addPosition((float)(x*player.getVelocity()), 0);
            res.x = (float)(x*player.getVelocity());
            player.setDirection(new Vector2(x + player.getDirection().x,player.getDirection().y).nor());
        }
        //if new y coord is between the map limits
        if(y_init >= 0 && y_fin <= map.getHeight()){
            player.addPosition(0, (float)(y*player.getVelocity()));
            res.y = (float)(y*player.getVelocity());
            player.setDirection(new Vector2(player.getDirection().x, player.getDirection().y + y).nor());
        }

            return res;
    }

    /**
     * Test collision between player  and enemies
     * If collision exist kills player
     */
    public void playerEnemiesColision(){
        for(int i = 0; i < enemies.size();i++) {
            boolean collidingPlayer = enemies.get(i).getSprite().getBoundingRectangle().overlaps(player.getSprite().getBoundingRectangle());
            if (collidingPlayer) {
                player.die();
                gameOver();
            }
        }
    }

    /**
     * Test collision between bullets  and enemies
     * If collision exists the zombie is killed
     * If bullet durability decreases to 0, the bullet is destroyed
     */
    public void bulletsEnemiesColision() {
        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < enemies.size(); j++) {
                //colision ?
                if (bullets.get(i).getSprite().getBoundingRectangle().overlaps(enemies.get(j).getSprite().getBoundingRectangle())) {
                    //explosion and zombie death
                    bombs.add(objectFactory.createBomb(enemies.get(j).getPosition()));
                    enemies.get(j).die();
                    enemies.remove(j);
                    score += level;
                    j--;
                    bullets.get(i).decDurability();
                    //destroy bullet ?
                    if(bullets.get(i).getDurability() == 0){
                        bullets.remove(i);
                        i--;
                        break;
                    }
                }
            }
        }
    }

    /**
     * Test collision between player and ammoBoxs
     * If a collision occurs delete ammoBoxs and increase ammo in weapons
     */
    public void playerBoxColision(){
        for(int i = 0; i < ammoBoxs.size();i++){
            if(ammoBoxs.get(i).getSprite().getBoundingRectangle().overlaps(player.getSprite().getBoundingRectangle())){
                player.setWeaponBehavior('r');
                player.rechargeWeapons(level);
                ammoBoxs.remove(i);
                i--;
            }
        }
    }

    //Only draws the visible objects

    /**
     * Draw visible objects to SpriteBatch
     * @param batch Spritebatch
     */
    public void draw(SpriteBatch batch) {
        batch.begin();
        for (int b = 0; b < ammoBoxs.size();b++){
            if(ammoBoxs.get(b).isVisible())
                ammoBoxs.get(b).draw(batch);
        }
        for (int i = 0; i < bullets.size(); i++) {
            if(bullets.get(i).isVisible())
                bullets.get(i).draw(batch);
        }
        for (int j = 0; j < enemies.size(); j++) {
            if(enemies.get(j).isVisible()) {
               enemies.get(j).draw(batch);
            }
        }
        for(Bomb bomb : bombs)
        {
            bomb.draw(batch);
        }

       player.draw(batch);
        batch.end();
    }

    /**
     * Set Player attack animation
     * Decrease ammo in current weapon
     * Create bullet with current player direction
     */
    public void shoot() {
        player.attackAnimation();
        Weapon w = player.getBag().get(player.getInUse());
        if(w.use()) {

            bullets.add(objectFactory.createBullet(player.getDirection(), w.getDurability(), player.getPosition()));
        }
    }

    /**
     * Game cicle
     * Create botwaves, move gamme object, check collisions, updates animation frames
     * @param dt time between updates
     */
    public void update(float dt) {
        //new level ?
        if(enemies.size() == 0){
            enemies = objectFactory.createEnemies(6 + level);
            ammoBoxs = objectFactory.createAmmoBoxes(2 + level / 3);
            player.getBag().get(1).setDurability(2+level/6);
            level++;
        }

        //moves
        moveEnemies();
        moveBullets();

        //collisions
        bulletsEnemiesColision();
        playerEnemiesColision();
        playerBoxColision();

        //animation updates
        player.update(dt);
        for(Enemy enemy: enemies)
        {
            enemy.update(dt);
        }
        for(int i = 0; i < bombs.size(); i ++){
            if(bombs.get(i).getAnimationcicle() == 1)
            {
                bombs.remove(i);
                i--;
            }
            else
            bombs.get(i).update(dt);
        }
    }

    /**
     * Set EndGame Flag
     */
    public void gameOver() {
        endGame = true;
    }

    /**
     * Release Game resources
     */
    public void dispose() {
       objectFactory.dispose();
    }

    /**
     * Switch to nextWeapon in  player bag
     */
    public void nextWeapon(){
        int inUse = player.getInUse();
        int size = player.getBag().size();

        if(inUse == size-1)
            player.setInUse(0);
        else
            player.setInUse(inUse+1);
    }

    /**
     * Set reload player animation
     */
    public void reloadWeapon(){
        player.reloadAnimation();
    }

    /**
     * Receives the games camera viewport and calculate the game objects that are visible in that area to be drawn later
     * Once visible an enemy tracks the player until one of them is dead
     * @param x camera X coordinate
     * @param y camera Y coordinate
     * @param width displayed width
     * @param height displayed height
     */
    public void loadVisibleObjects(float x, float y, float width, float height){
        Rectangle visibleRegion = new Rectangle(x,y,width,height);
        for (int i = 0;i < enemies.size();i++){
            if(visibleRegion.overlaps(enemies.get(i).getSprite().getBoundingRectangle())){
                enemies.get(i).setVisible(true);
                enemies.get(i).setTracking(true);

            }
            else
                enemies.get(i).setVisible(false);
        }
        for(int j = 0; j < bullets.size();j++){
            if(visibleRegion.overlaps(bullets.get(j).getSprite().getBoundingRectangle())){
                bullets.get(j).setVisible(true);
            }
            else
                bullets.get(j).setVisible(false);
        }
        for(int b = 0; b < ammoBoxs.size();b++){
            if(visibleRegion.overlaps(ammoBoxs.get(b).getSprite().getBoundingRectangle())){
                ammoBoxs.get(b).setVisible(true);
            }
            else
                ammoBoxs.get(b).setVisible(false);
        }
    }

    /**
     * Move all enemies
     * If the enemy is visible or is tracking the player it moves goes after the plyer (direction)
     * Otherwise moves in a random way
     */
    public void moveEnemies(){
        for(int i = 0; i < enemies.size(); i++) {

            if (enemies.get(i).isVisible() || enemies.get(i).isTracking()) {    //Tracks Player
                Vector2 playerDirection = new Vector2((int) (player.getCenterX() - enemies.get(i).getCenterX()), (int) (player.getCenterY() - enemies.get(i).getCenterY())).nor();
                if (playerDirection.x != 0 || playerDirection.y != 0)
                    enemies.get(i).setDirection(playerDirection);
                enemies.get(i).addPosition((float)(playerDirection.x*enemies.get(i).getVelocity()), (float)(playerDirection.y*enemies.get(i).getVelocity()));

                if(Math.sqrt(Math.pow(player.getCenterX() - enemies.get(i).getX(),2) + Math.pow(player.getCenterY() - enemies.get(i).getCenterY(),2)) <= player.getWidth()*4){
                    enemies.get(i).attackAnimation();
                }
            }

             else{  //Random movement
                Random rnd = new Random();
                Vector2 newDirection = new Vector2((float)(rnd.nextDouble()/8 - 0.5/8 + enemies.get(i).getDirection().x),(float)(rnd.nextDouble() / 8 - 0.5/8 + enemies.get(i).getDirection().y)).nor();
                enemies.get(i).setDirection(newDirection);
                enemies.get(i).addPosition(newDirection.x, newDirection.y);
                if(!map.contains(enemies.get(i).getSprite().getBoundingRectangle())) {
                    newDirection.set(-newDirection.x,-newDirection.y);
                    enemies.get(i).setDirection(newDirection);
                    enemies.get(i).addPosition((float)(newDirection.x*enemies.get(i).getVelocity()),(float)(newDirection.y*enemies.get(i).getVelocity()));
                }
           }
            }
        }

    /**
     * Move all game bullets in their on direction at a certain speed
     */
    public void moveBullets(){
        for (int i = 0; i < bullets.size(); i++) {
            if (!map.overlaps(bullets.get(i).getSprite().getBoundingRectangle())) {
                bullets.remove(i);
                i--;
            }
            else
                bullets.get(i).incPosition();
        }
    }

    /**
     *
     * @return Return EndGame flag
     */
    public boolean getEndGame(){
        return  endGame;
    }

    /**
     *
     * @return Return current pplayer score
     */
    public long getScore()
    {
        return score;
    }

    /**
     *
     * @return Return current difficult game level
     */
    public int getLevel() {return level; }

}
