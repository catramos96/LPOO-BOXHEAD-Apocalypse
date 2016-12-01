package com.mygdx.game.logic.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.logic.Animation;

import java.util.Vector;

/**
 * Design a skeleton for Weapons in game
 */
public abstract class Weapon{
    protected int durability; /* bullet durability - number of zombies capable of kill with just one shot*/
    protected int ammo;
    protected Animation idle;
    protected Animation reload;
    protected Animation current_anim;

    /**
     * Weapon Constructor
     * @param a amount of ammo loaded
     * @param d bullet durability
     */
    public Weapon(int a,int d){
        ammo = a;
        durability = d;
    }
   /* public void decDurability(int d){
        durability--;
    }
*/

    /**
     *
     * @return Return bullet durability
     */
    public int getDurability(){
        return durability;
    }

    /**
     * Set new bullet durability
     * @param d bullet durability
     */
    public void setDurability(int d){
        durability = d;
    }


    /**
     * If weapon have ammo above 0  will decrease ammo by 1
     * @return true if ammo decreased otherwisae will return false
     */
    public boolean use(){
        if(ammo == 0)
            return false;
        ammo--;
        return true;
    }

    /**
     * Recharges Weapon with ammo.
     * The amount of ammo depends on level.
     * @param level Game level
     */
    public abstract void recharge(int level);

    /**
     * Set  weapon animation.
     * char 'r' - reload animation
     * char 'i' - idle animation
     * @param anim char animation selector
     */
    public void setAnimation(char anim){
        switch (anim){
            case 'r':{ //reload
                current_anim = reload;
                break;
            }
            case 'i':{ //idle
                current_anim = idle;
                break;
            }
            default: {
                break;
            }
        }
    }

    /**
     *
     * @return Return current animation frame.
     */
    public TextureRegion getFrame()
    {
        return current_anim.getFrame();
    }



}
