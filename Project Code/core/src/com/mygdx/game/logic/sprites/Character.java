package com.mygdx.game.logic.sprites;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.logic.Animation;

/**
 * Class that represents the games "living" characters
 */
public abstract class Character extends Animated {
    protected boolean alive;
    protected Vector2 direction;
    protected float velocity;

    /**
     * Character constructor
     * @param x  Map coordinate x
     * @param y Map coordinate y
     * @param velocity Character velocity
     */
    public Character(int x,int y, float velocity){
        super(48,x,y);
        this.alive = true;
        this.velocity = velocity;
        direction = new Vector2(0,0);
        getSprite().rotate(direction.angle());
        getSprite().setBounds(getX(), getY(), getSize(), getSize());
    }

    /**
     * @return Return current object velocity
     */
    public double getVelocity(){
        return velocity;
    }

    /**
     *
     * @return Return current object direction
     */
    public Vector2 getDirection(){
        return direction;
    }

    /**
     * Set new character direction
     * @param d Vector2 direction
     */
    public void setDirection(Vector2 d){
        direction = d;
    }

    /**
     * Kills character
     */
    public void die(){
        alive = false;
    }

}
