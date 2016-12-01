package com.mygdx.game.gui.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Catarina Ramos on 12/05/2016.
 * NOTES: Para ser usado como stack
 */
public abstract class State {
    protected OrthographicCamera cam;   //camera
    protected GameStateManager manager;
    protected int width;
    protected int height;

    public State(GameStateManager manager){
        this.manager = manager;

        width = Gdx.app.getGraphics().getWidth();
        height = Gdx.app.getGraphics().getHeight();

        cam = new OrthographicCamera();
    }

    public abstract void handleInput();

    public void update(float delta_time){
        handleInput();
    };

    public abstract void render(SpriteBatch batch);

    public abstract void dispose();
}
