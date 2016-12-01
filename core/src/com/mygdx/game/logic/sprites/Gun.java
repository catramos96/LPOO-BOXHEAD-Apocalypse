package com.mygdx.game.logic.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.logic.Animation;

/**
 * Represents gun in players bag
 */
public class Gun extends Weapon {

    /**
     * Gun constructor
     * @param a amount ammo loaded in weapon
     */
    public Gun(int a) {
        super(a,1); //always kill just one zombie
        idle = new Animation(new TextureRegion(new Texture("idle_gun.png")),1,0.30f);
        reload = new Animation(new TextureRegion(new Texture("reload_gun.png")),15,0.10f);
        current_anim = idle;
    }

    /**
     * Increase ammo loaded in gun
     * It depends on level
     * @param level Game level
     */
    @Override
    public void recharge(int level) {
        ammo += 10 + level;
    }
}
