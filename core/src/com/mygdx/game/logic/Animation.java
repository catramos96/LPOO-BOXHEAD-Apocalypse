package com.mygdx.game.logic;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by syram on 01-06-2016.
 */

/**
 * Responsible for controlling a sprite
 */
public class Animation {
    private Array<TextureRegion> frames;
    private float maxFramesTime;
    private float currentFrameTime;
    private int frameCount;
    private int frame;
    public double animationcicle;

    /**
     * Animation constructor
     * @param region Sequence of images to produce animation
     * @param frameCount number of frames present in the animation
     * @param cycletime time between frames
     */
    public Animation(TextureRegion region, int frameCount, float cycletime)
    {
        frames = new Array<TextureRegion>();
        int frameWidth = region.getRegionWidth() / frameCount;

        for(int i = 0 ; i < frameCount; i++)
        {
            frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth,region.getRegionHeight()));

        }
        this.frameCount = frameCount;

        maxFramesTime = cycletime;

        frame = 0;
    }

    /**
     * Update animation frame
     * @param dt time between updates
     */
    public void update(float dt) {
        currentFrameTime += dt;
        if (currentFrameTime > maxFramesTime) {
            frame++;
            currentFrameTime = 0;

        }
        if(frame >= frameCount)
        {
            frame = 0;
            animationcicle ++;
        }
    }

    /**
     *
     * @return Current animation frame
     */
    public TextureRegion getFrame(){
        return frames.get(frame);
    }


    /**
     *
     * @return number of animation loops
     */
    public double getAnimationCount () {return animationcicle;}
    public void resetAnimation(){
        frame = 0;
        animationcicle = 0;
        currentFrameTime = 0;
    }

    /**
     * Return correspondent frame to a given time
     * @param frame current time
     * @return TextureRegion  frame
     */
    public TextureRegion getFrame(int frame)
    {
        return frames.get(frame);
    }

    /**
     *
     * @return number of frames in animation
     */
    public int getFramesCount()
    {
        return frames.size;
    }

    /**
     *
     * @return time between frames
     */
    public  float getFrameTime()
    {
        return maxFramesTime;
    }
}
