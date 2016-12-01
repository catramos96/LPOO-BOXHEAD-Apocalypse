package com.mygdx.game.logic.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Class that represents all objects of the game
 */
public class GameObject {
    private boolean visible; /*attribute to know if the object is visible to the player*/
    private int spriteSize;
    private Sprite sprite;

    /**
     * GameObject constructor
     * @param size width of the square object
     * @param x  coordinate x on map
     * @param y coordinate y on map
     */
    public GameObject(int size,int x,int y){
        sprite = new Sprite();
        sprite.setPosition(x,y);
        visible = true;
        spriteSize = size;
    }

    /**
     *
     * @return Return object sprite
     */
    public Sprite getSprite(){return sprite;}

    /**
     *
     * @return Return object coordinate x on the map
     */
    public float getX(){
        return sprite.getX();
    }

    /**
     *
     * @return Return object coordinate y on the map
     */
    public float getY(){
        return sprite.getY();
    }

    /**
     *
     * @return Return object center coordinate x on the map
     */
    public float getCenterX(){return sprite.getX()+sprite.getWidth()/2;}


    /**
     *
     * @return Return object center coordinate y on the map
     */
    public float getCenterY(){return sprite.getY()+sprite.getWidth()/2;}


    /**
     * Translate object in a direction
     * @param x Translate in X axis
     * @param y Translate in Y axis
     */
    public void addPosition(float x,float y){
        sprite.setPosition(x+getX(), y+getY());
    }

    /**
     *
     * @return Return current object position
     */
    public Vector2 getPosition(){
        return new Vector2(getX(),getY());
    }

    /**
     * Set object in a position
     * @param x Coordinate X of new position
     * @param y Coordinate Y of new position
     */
    public void setPosition(float x, float y){
        sprite.setPosition(x,y);
    }

    /**
     *
     * @return Return object size
     */
    public float getSize(){
        return spriteSize;
    }

    /**
     *
     * @return Return object width
     */
    public float getWidth(){
        return sprite.getWidth();
    }

    /**
     * @return Return object height
     */
    public float getHeight(){
        return sprite.getHeight();
    }

    /**
     *
     * @return Return  true if is a visible object otherwise return false
     */
    public final boolean isVisible(){
        return visible;
    }

    /**
     * Set visible state
     * @param v boolean state
     */
    public void setVisible(boolean v){
        visible = v;
    }

    /**
     * Draw object in a SpriteBatch
     * @param batch SpriteBatch
     */
    public void draw(SpriteBatch batch){
        sprite.setBounds(sprite.getX(),sprite.getY(),spriteSize,spriteSize);
        batch.draw(sprite.getTexture(),sprite.getX(),sprite.getY(),spriteSize,spriteSize);
    }
}
