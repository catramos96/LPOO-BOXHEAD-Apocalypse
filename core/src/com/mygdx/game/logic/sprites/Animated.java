package com.mygdx.game.logic.sprites;

import com.badlogic.gdx.utils.Array;
import com.mygdx.game.logic.Animation;

/**
 * Class that make a skeleton for animated GameObjects
 */
public abstract class Animated extends GameObject{
    protected Array<Animation> animations;
    protected int currentAnimation;
    protected int frame;
    protected float timer;
    protected double animationcicle;


    /**
     * Animated constructor
     * @param size size of GameObject
     * @param x  Coordinate X in the map
     * @param y  Coordinate Y in the map
     */
    public Animated(int size, int x, int y){
        super(size,x,y);
        currentAnimation = 0;
        frame = 0;
        timer = 0;
        animationcicle = 0;
        animations = new Array<Animation>();
    }

    /**
     * Load objects animations
     */
    public abstract void loadAnimations();

    /**
     * Update object
     * @param dt time between updates
     */
    public abstract void update(float dt);

    /**
     * @return Return number of animation loops
     */
    public double getAnimationcicle()
    {
        return animationcicle;
    }


}
