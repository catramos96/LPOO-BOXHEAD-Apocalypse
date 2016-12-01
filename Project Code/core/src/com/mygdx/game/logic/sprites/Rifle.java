package com.mygdx.game.logic.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.logic.Animation;

/**
 * Created by Catarina Ramos on 01/06/2016.
 */

/**
 * Represents player Rifle
 */
public class Rifle extends Weapon {

    /**
     * Rifle constructor
     * @param a ammount of ammo
     */
    public Rifle(int a) {
        super(a,2);//damage - mata 2 zombies
        idle = new Animation(new TextureRegion(new Texture("idle_rifle.png")),1,0.20f);
        reload = new Animation(new TextureRegion(new Texture("reload_rifle.png")),20,0.10f);
        current_anim = idle;
    }

    /**
     * Increases weapon ammo.
     * The increase depends on level.
     * @param level Game level
     */
    @Override
    public void recharge(int level) {
        ammo += 5 + level/2;
    }
}
