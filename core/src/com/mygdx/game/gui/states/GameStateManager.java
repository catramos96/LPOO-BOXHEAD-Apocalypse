package com.mygdx.game.gui.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Created by Catarina Ramos on 12/05/2016.
 */
public class GameStateManager {
    private Stack<State> States;

    public GameStateManager(){
        States = new Stack<State>();
    }

    public void push(State st){
        States.push(st);
    }

    public void pop(){
        States.pop();
    }

    public void set(State st){
        States.pop();
        States.push(st);
    }

    public void update(float delta_time){   //delta_time between renders
        States.peek().update(delta_time);
    }

    public void render(SpriteBatch s_batch){
        States.peek().render(s_batch);
    }
}
