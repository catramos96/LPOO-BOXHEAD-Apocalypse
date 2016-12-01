package de.tomgrill.gdxtesting.examples;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.logic.Animation;
import com.mygdx.game.logic.sprites.Bullet;
import com.mygdx.game.logic.sprites.Enemy;
import com.mygdx.game.logic.sprites.GameObject;
import com.mygdx.game.logic.sprites.Gun;
import com.mygdx.game.logic.sprites.Player;
import com.mygdx.game.logic.sprites.Rifle;

import org.junit.Test;
import org.junit.runner.RunWith;

import de.tomgrill.gdxtesting.GdxTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class GameObjectsTest {

    @Test
    public void testAnimation()
    {
        Animation test = new Animation(new TextureRegion(new Texture("idle_player.png")),1,0.5f);

        assertEquals(0.5f,test.getFrameTime(),0);
        assertEquals(1,test.getFramesCount(),0);

        test.update(0.5f);
        test.update(0.5f);

        assertEquals(1,test.getAnimationCount(),0);
    }

    @Test
    public void testBullet()
    {
        Texture bullet_text = new Texture("bullet.png");
        Bullet test = new Bullet(new Vector2(0,1),1,bullet_text);

        // test movement
        test.incPosition();

        assertEquals(0,test.getX(),0);
        assertEquals(40,test.getY(),0);
        //test durability
        assertEquals(1, test.getDurability());

        test.decDurability();

        assertEquals(0,test.getDurability());
    }

    @Test
    public void testEnemy()
    {
        Enemy test = new Enemy(1,2);

        test.setTracking(true);

        assertEquals(true, test.isTracking());
        //AssertNumber of animatiom cicles
        assertEquals(0,test.getAnimationcicle(),0);
    }

    @Test
    public  void GameObjectTest()
    {
        GameObject test = new GameObject(10,1,2);

        assertEquals(1, test.getX(), 0);
        assertEquals(2,test.getY(),0);
        assertEquals(10,test.getSize(),0);
        assertEquals(0,test.getWidth(),0);
        assertEquals(0, test.getHeight(), 0);
        assertEquals(1, test.getCenterX(), 0);
        assertEquals(2,test.getCenterY(),0);


        assertTrue(test.isVisible());
        test.setVisible(false);
        assertFalse(test.isVisible());

        test.setPosition(0, 0);

        assertEquals(0, test.getX(), 0);
        assertEquals(0, test.getY(), 0);

        test.addPosition(1, 2);

        assertEquals(1,test.getX(),0);
        assertEquals(2,test.getY(),0);
    }

    @Test
    public void testGun()
    {
        Gun test = new Gun(1);

        assertEquals(1,test.getDurability());
        assertEquals(true,test.use());
        assertEquals(false,test.use());

        test.recharge(1);

        assertEquals(true,test.use());
    }

    @Test
    public void testPlayer()
    {
        Player test = new Player();

        assertEquals(0,test.getInUse());

        //change weapon
        test.setInUse(1);

        assertEquals(1,test.getInUse());

        //ammo in weapon 0;
        test.setInUse(0);

        assertEquals(15, test.getAmmo());

        //2 weapons on bag;
        assertEquals(2,test.getBag().size());
    }

    @Test
    public void testRifle()
    {
        Rifle test = new Rifle(1);

        assertEquals(2,test.getDurability());
        assertEquals(true,test.use());
        assertEquals(false,test.use());

        test.recharge(1);

        assertEquals(true,test.use());
        assertEquals(2,test.getDurability());

        test.setDurability(3);

        assertEquals(3,test.getDurability());
        assertNotNull(test.getFrame());

        test.setAnimation('r');

        assertNotNull(test.getFrame());

        test.setAnimation('i');

        assertNotNull(test.getFrame());

        test.setAnimation('x');

        assertNotNull(test.getFrame());
    }
}
