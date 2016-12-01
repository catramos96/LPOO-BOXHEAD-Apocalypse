package com.mygdx.game.logic.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Class that represents bullet in the game
 * It is used to kill the enemies
 */
public class Bullet extends GameObject {
    private int durability; /*Number of enemies that can kill */
    private final int velocity = 40;
    private Vector2 direction;

    /**
     * Bullet constructor
     * @param direction Movement direction
     * @param dur Hit  durability
     * @param text bullet texture
     */
    public Bullet(Vector2 direction , int dur, Texture text){
        super(24,0,0);
        this.direction = direction;
        durability =dur;
        getSprite().setTexture(text);
        getSprite().setBounds((float)getX(), (float)getY(), getSize(), getSize());
    }

    /**
     * Move bullet in movement direction
     */
    public void incPosition(){
        setPosition(direction.x*velocity + getX(),direction.y*velocity + getY());
    }

    /**
     * Decreases bullet durability
     */
    public void decDurability(){
        durability--;
    }

    /**
     *
     * @return Return Bullet durability
     */
    public int getDurability(){
        return durability;
    }
}
