package com.mygdx.game.logic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.logic.Animation;
import com.mygdx.game.logic.sprites.Bomb;
import com.mygdx.game.logic.sprites.Bullet;
import com.mygdx.game.logic.sprites.Enemy;
import com.mygdx.game.logic.sprites.GameObject;

import java.util.ArrayList;
import java.util.Random;


public class GameObjectFactory {
    private int width;
    private int height;
    //enemies
    private Animation idle;
    private Animation move;
    private Animation attack;
    //bullet
    private Texture bullet_text;
    //ammo boxes
    private Texture box_text;
    //bomb
    private Animation bomb;

    public GameObjectFactory(int w, int h){
        width = w;
        height = h;

        idle = new Animation(new TextureRegion(new Texture("idle_zombie.png")),17,0.1f);
        move = new Animation(new TextureRegion(new Texture("move_zombie.png")),17,0.10f);
        attack = new Animation (new TextureRegion(new Texture("attack_zombie.png")),9,0.1f);;
        bullet_text = new Texture("bullet.png");
        box_text = new Texture("ammo.png");
        bomb  = new Animation(new TextureRegion(new Texture("explosion.png")),13,0.05f);
    };


    public ArrayList<Enemy> createEnemies(int number){
        Random rnd = new Random();
        Vector2 entrance = new Vector2(0,0);
        ArrayList<Enemy> wave = new ArrayList<Enemy>();
        for(int i = 0; i < number;i++){
            switch (rnd.nextInt(4)){
                case 0:{//N
                    entrance.set(rnd.nextInt(width-60),height - 60);
                    break;
                }
                case 1:{//E
                    entrance.set(width - 60,rnd.nextInt(height-60));
                    break;
                }
                case 2:{//S
                    entrance.set(rnd.nextInt(width-60) ,60);
                    break;
                }
                case 3:{//W
                    entrance.set(60,rnd.nextInt(height-60));
                    break;
                }
            }
            Enemy e = new Enemy((int)entrance.x,(int)entrance.y);
            e.loadAnimation(idle,move,attack);
            wave.add(e);
        }
        return wave;
    }

    public Bullet createBullet(Vector2 direction, int durability,Vector2 position){
        Bullet bullet = new Bullet(direction,durability,bullet_text);
        bullet.setPosition(position.x, position.y);
        return bullet;
    }

    public ArrayList<GameObject> createAmmoBoxes(int number) {
        Random rnd = new Random();
        ArrayList<GameObject> boxs = new ArrayList<GameObject>();
        for (int i = 0; i < number; i++) {
            GameObject box = new GameObject(48,rnd.nextInt(width - 50),rnd.nextInt(height - 50));
            box.getSprite().setTexture(box_text);
            boxs.add(box);
        }
        return boxs;
    }

    public Bomb createBomb(Vector2 position){
        return new Bomb((int)(position.x),(int)(position.y),bomb);
    }

    public void dispose(){
        bullet_text.dispose();
        box_text.dispose();
    }
}
