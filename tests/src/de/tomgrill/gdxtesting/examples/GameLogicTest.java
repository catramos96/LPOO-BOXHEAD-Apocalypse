package de.tomgrill.gdxtesting.examples;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.logic.Animation;
import com.mygdx.game.logic.Game;
import com.mygdx.game.logic.GameData;
import com.mygdx.game.logic.GameObjectFactory;

import com.mygdx.game.logic.sprites.Bomb;
import com.mygdx.game.logic.sprites.Bullet;
import com.mygdx.game.logic.sprites.Enemy;
import com.mygdx.game.logic.sprites.GameObject;
import com.mygdx.game.logic.sprites.Player;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import de.tomgrill.gdxtesting.GdxTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Catarina Ramos on 07/06/2016.
 */
@RunWith(GdxTestRunner.class)
public class GameLogicTest {

    @Test
    public void testGame()
    {
        Game test = new Game(500,500);

        //Chech pause state
        test.setPause(true);

        assertTrue(test.isPause());

        //Check Player
        Player gamePlayer = test.getPlayer();

        assertEquals(0,gamePlayer.getInUse());

        //change weapon
        gamePlayer.setInUse(1);

        assertEquals(1,gamePlayer.getInUse());

        //moveplayer
        double moveX = gamePlayer.getVelocity()*0.5f + gamePlayer.getX();
        double moveY = gamePlayer.getVelocity()*0.5f + gamePlayer.getY();
        test.movePlayer(0.5f,0.5f);

        gamePlayer = test.getPlayer();

        assertEquals(moveX,gamePlayer.getX(),1);
        assertEquals(moveY,gamePlayer.getY(),1);

        //test player shoot
        int ammo = gamePlayer.getAmmo();

        test.shoot();
        gamePlayer= test.getPlayer();

        assertEquals(ammo -1 , gamePlayer.getAmmo());

        //test weapon change
        test.nextWeapon();
        gamePlayer = test.getPlayer();

        assertEquals(0,gamePlayer.getInUse());

        test.nextWeapon();
        gamePlayer = test.getPlayer();

        assertEquals(1,gamePlayer.getInUse());

        // test spawn tim
        test.update(0.5f);

        assertEquals(1,test.getLevel());
    }

    @Test
    public void testBomb()
    {
        Animation bomb = new Animation(new TextureRegion(new Texture("explosion.png")),13,0.05f);
        Bomb test = new Bomb(10,10,bomb);

        assertEquals(10,test.getX(),0);
        assertEquals(10,test.getY(),0);
    }
    @Test
    public void testFactory(){
        GameObjectFactory factory = new GameObjectFactory(500,500);

        ArrayList<Enemy> zombies =  factory.createEnemies(10);
        assertEquals(10, zombies.size());

        Bullet test = factory.createBullet(new Vector2(0, 1), 1, new Vector2(0, 1));
        assertEquals(1,test.getDurability());

        ArrayList<GameObject> temp  = factory.createAmmoBoxes(12);
        assertEquals(12,temp.size());

        Bomb b = factory.createBomb(new Vector2(2,3));
        assertEquals(2,(int)b.getX());
        assertEquals(3,(int)b.getY());
    }

    @Test
    public void testSave()
    {
        Gdx.files.local("highscore.txt").delete();

        GameData.init();
        GameData.load();

        long scores [] = GameData.getHighScores();
        String names[] = GameData.getNames();

        for(int i = 0; i < 5 ; i++)
        {
            assertEquals(0,scores[i]);
            assertEquals("----",names[i]);
        }

        GameData.addHighScore(10, "20/20/20");
        GameData.sortHighScores();

        GameData.save();
        GameData.load();

        scores = GameData.getHighScores();
        names  = GameData.getNames();

        assertEquals(10,scores[0]);
        assertEquals("20/20/20", names[0]);

        GameData.addHighScore(10, "20/20/20");
        GameData.addHighScore(10, "20/20/20");
        GameData.addHighScore(10, "20/20/20");
        GameData.addHighScore(10, "20/20/20");

        GameData.save();

        GameData.load();

        scores = GameData.getHighScores();
        names  = GameData.getNames();
        for(int i = 0; i < 5 ; i++) {
            assertEquals(10, scores[i]);
            assertEquals("20/20/20", names[i]);
        }
    }

    @Test
    public void testGameData()
    {
        com.mygdx.game.logic.GameData test = new com.mygdx.game.logic.GameData();

        test.init();

        long scores [] = test.getHighScores();
        String names[] = test.getNames();

        for(int i = 0; i < 5 ; i++)
        {
            assertEquals(0,scores[i]);
            assertEquals("----",names[i]);
        }

        test.addHighScore(10,"20/10/10");
        test.sortHighScores();

        scores=test.getHighScores();
        names =test.getNames();

        assertEquals(10, scores[0]);
        assertEquals("20/10/10", names[0]);

        test.addHighScore(20,"20/10/10");
        test.addHighScore(30,"20/10/10");
        test.addHighScore(40, "20/10/10");
        test.addHighScore(50, "20/10/10");

        assertEquals(test.isHighScore(5),false);
        assertEquals(test.isHighScore(100),true);
    }
}
