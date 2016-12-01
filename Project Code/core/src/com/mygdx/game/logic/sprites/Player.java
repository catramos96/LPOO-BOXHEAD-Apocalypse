package com.mygdx.game.logic.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.logic.Animation;
import com.sun.javafx.binding.StringFormatter;

import java.util.ArrayList;

/**
 * Class responsible for user character
 */
public class Player extends Character {

    private static final int IDLE = 0;/*index of idle animation*/
    private static final int WALK = 1; /*index of walk animation*/
    private ArrayList<Weapon> bag; /*bag with weapons*/
    private int inUseIndex; /*current weapon of bag in use*/
    private boolean move = false;

    /**
     * Player constructor
     */
    public Player(){
        super(0,0,4);
        bag = new ArrayList<Weapon>();
        inUseIndex = 0;
        bag.add(new Gun(15));
        bag.add(new Rifle(5));
        direction = new Vector2(1,0);
        loadAnimations();
    }

    /**
     * Load legs animation
     */
    @Override
    public void loadAnimations() {
        super.animations.add( new Animation(new TextureRegion(new Texture("idle_player.png")),1,0.5f));
        super.animations.add( new Animation(new TextureRegion(new Texture("walk.png")),20,0.10f));
    }

    /**
     * Draw player on a SpriteBatch
     * @param batch Spritebatch
     */
    @Override
    public void draw(SpriteBatch batch) {
        float scale = 0.5f;


        TextureRegion legs = getFrame();

        float Rotation = MathUtils.atan2(direction.y,direction.x)* MathUtils.radiansToDegrees;;

        float legsOffsetX = 0;
        float legsOffsetY = 0;

        TextureRegion temp = bag.get(inUseIndex).getFrame();

        if (inUseIndex  == 0) {

            if(move) //legs are moving (animation walk)
            batch.draw(legs,(float)(getWidth()/2+ legsOffsetX + getX() - legs.getRegionWidth()*0.5f),(float)(getHeight()/2 +legsOffsetY + getY() - legs.getRegionHeight()*0.5f),
                    legs.getRegionWidth()*0.5f, legs.getRegionHeight()*0.5f,
                    legs.getRegionWidth(), legs.getRegionHeight(),scale,scale,Rotation);
            else  //legs are quiet (idle animation)
                batch.draw(animations.get(IDLE).getFrame(),(float)(getWidth()/2+ legsOffsetX + getX() - legs.getRegionWidth()*0.5f),(float)(getHeight()/2 +legsOffsetY + getY() - legs.getRegionHeight()*0.5f),
                        legs.getRegionWidth()*0.5f, legs.getRegionHeight()*0.5f,
                        legs.getRegionWidth(), legs.getRegionHeight(),scale,scale,Rotation);
            batch.draw(temp,(float)( getWidth()/2+ getX() - temp.getRegionWidth() * 0.5f),(float)(getHeight()/2 + getY() - temp.getRegionHeight() * 0.5f),
                    temp.getRegionWidth() * 0.5f, temp.getRegionHeight() * 0.5f,
                    temp.getRegionWidth(), temp.getRegionHeight(), scale, scale, Rotation);
        }
        else if (inUseIndex == 1) {
            float cos = MathUtils.cosDeg(Rotation);
            float sin = MathUtils.sinDeg(Rotation);
            float offsetX = 10* cos;
            float offsetY = 10* sin;

            legsOffsetX = 0;//10 * cos;
            legsOffsetY = 0;//10* sin;

            if(move)
            batch.draw(legs,(float)(legsOffsetX +getWidth()/2+ getX() - legs.getRegionWidth()*0.5f),(float)(getHeight()/2 + getY() - legs.getRegionHeight()*0.5f),
                    legs.getRegionWidth()*0.5f, legs.getRegionHeight()*0.5f,
                    legs.getRegionWidth(), legs.getRegionHeight(),scale,scale,Rotation);
            else
                batch.draw(animations.get(IDLE).getFrame(),(float)(legsOffsetX +getWidth()/2+  getX() - legs.getRegionWidth()*0.5f),(float)(getHeight()/2 +legsOffsetY + getY() - legs.getRegionHeight()*0.5f),
                        legs.getRegionWidth()*0.5f, legs.getRegionHeight()*0.5f,
                        legs.getRegionWidth(), legs.getRegionHeight(),scale,scale,Rotation);
            batch.draw(temp,(float)(offsetX +getWidth()/2+  getX() - temp.getRegionWidth() *0.5f), (float)(getHeight()/2 +offsetY + getY() - temp.getRegionHeight()  *0.5f),
                    temp.getRegionWidth()*0.5f,temp.getRegionHeight()*0.5f,
                    temp.getRegionWidth(), temp.getRegionHeight(),scale,scale,Rotation);
        }
    }


    /**
     * Updates player animation
     * @param dt time between updates
     */
    public void update(float dt)
    {
        if(move)
            animations.get(WALK).update(dt);

        bag.get(inUseIndex).current_anim.update(dt);

        if(bag.get(inUseIndex).current_anim.getAnimationCount() >= 1)
        {
            bag.get(inUseIndex).current_anim.resetAnimation();
            idleAnimation();

        }
        if(animations.get(WALK).getAnimationCount() >= 1)
        {
            animations.get(WALK).resetAnimation();
            move = false;
        }
    }


    /**
     *
     * @return Return current animation frame
     */
    public TextureRegion getFrame()
    {
        return animations.get(WALK).getFrame();
    }

    /**
     * Change in use weapon to the wepon in "u" position in the bag
     * @param u  bag weapon index
     */
    public void setInUse(int u){

        bag.get(inUseIndex).current_anim.resetAnimation();
        bag.get(inUseIndex).setAnimation('i');
        inUseIndex = u;
    }


    /**
     *
     * @return Return current weapon index in the bag
     */
    public int getInUse(){
        return inUseIndex;
    }

    /**
     *
     * @return Return bag content
     */
    public final ArrayList<Weapon> getBag(){
        return bag;
    }

    /**
     * Set current weapon animation.
     * char 'r' - reload animation
     * char 'i' - idle animation
     * @param b weapon behaviour
     */
    public void setWeaponBehavior(char b){
        bag.get(inUseIndex).setAnimation(b);
    }

    /**
     * Reload in use weapon.
     * Ammont of ammo added depends on level.
     * @param level Game level
     */
    public void rechargeWeapons(int level){
        for(int i = 0; i < bag.size(); i++){
            bag.get(i).recharge(level);
        }
    }

    /**
     * Set player attack animmation
     */
    public void attackAnimation()
    {

    }

    /**
     * Set weapon reload animation
     */
    public void reloadAnimation()
    {
        bag.get(inUseIndex).setAnimation('r');
    }

    /**
     * Set weapon  idle animation
     */
    public void idleAnimation()
    {
        bag.get(inUseIndex).setAnimation('i');
    }

    /**
     *Set at true  moveflag animation
     */
    public void moveAnimation()
    {
        move = true;
    }

    /**
     *
     * @return Return the amount of ammo in equippped weapon
     */
    public int getAmmo()
    {
        return bag.get(inUseIndex).ammo;
    }

}
