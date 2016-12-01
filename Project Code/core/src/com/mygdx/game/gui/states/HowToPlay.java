package com.mygdx.game.gui.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.audio.*;

public class HowToPlay extends State {
    private Texture tutorial_1;
    private Texture tutorial_2;
    private Texture tutorial_3;
    private int n_tut;
    private com.mygdx.game.audio.SoundManager soundManager;

    public HowToPlay(GameStateManager manager, com.mygdx.game.audio.SoundManager soundManager) {
        super(manager);
        tutorial_1 = new Texture("Tutorial_1.png");
        tutorial_2 = new Texture("Tutorial_2.png");
        tutorial_3 = new Texture("Tutorial_3.png");
        n_tut = 1;
        this.soundManager = soundManager;
        this.soundManager.PlayMusic();


        super.width = tutorial_1.getWidth();
        super.height = tutorial_1.getHeight();
        super.cam.setToOrtho(false, width, height);

        Button b = new Button();
        b.setBounds(0,0,width,height);
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            soundManager.PlayClick();
            n_tut++;
            Gdx.app.log("just touched","");
            if(n_tut>3){
                manager.set(new Menu(manager,soundManager));
                dispose();
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        if(n_tut == 1)
            batch.draw(tutorial_1, 0, 0);
        else if(n_tut == 2)
            batch.draw(tutorial_2,0,0);
        else if(n_tut == 3)
            batch.draw(tutorial_3,0,0);
        batch.end();
    }

    @Override
    public void dispose() {
        tutorial_1.dispose();
        tutorial_2.dispose();
    }
}
