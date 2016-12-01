package com.mygdx.game.logic.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.logic.Animation;

/**
 * Class that draw small explosion with animation
 * It is used when a bullet collides with an enemy
 */
public class Bomb extends Animated {
    /**
     * Bomb Constructor
     * @param x Coordinate X of the map
     * @param y Coordinate Y of the map
     * @param bomb Animation that will be displayed
     */
    public Bomb(int x, int y,Animation bomb) {
        super(48,x,y);
        frame = 0;
        timer = 0;
        animationcicle = 0;
        loadAnimations(bomb);
    }

    /**
     * Load the animation automatic
     */
    @Override
    public void loadAnimations() {
        super.animations.add(new Animation(new TextureRegion(new Texture("explosion.png")),13,0.05f));
    }

    /**
     * Add the animation to  animations array
     * @param bomb  bomb animation
     */
    public void loadAnimations(Animation bomb) {
        super.animations.add(bomb);
    }

    /**
     * Update animation
     * @param dt time between frames
     */
    public void update(float dt) {
        timer += dt;
        if (timer > animations.get(0).getFrameTime()) {
            frame++;
            timer = 0;

        }
        if (frame >= animations.get(0).getFramesCount()) {
            frame = 0;
            animationcicle = 1;
        }
    }

    /**
     * Draw bomb current frame in a Spritebatch
     * @param batch SpriteBatch
     */
    public void draw(SpriteBatch batch)
    {
        float scale  = 0.5f;
        TextureRegion temp = animations.get(0).getFrame(frame);
        //float Rotation = MathUtils.atan2(direction.y,direction.x)* MathUtils.radiansToDegrees;

        batch.draw(temp,getWidth()/2  + getX() - temp.getRegionWidth()*0.5f,getHeight()/2  + getY() - temp.getRegionHeight()*0.5f,
                temp.getRegionWidth()*0.5f, temp.getRegionHeight()*0.5f,
                temp.getRegionWidth(), temp.getRegionHeight(),scale,scale,0);

    }
}
