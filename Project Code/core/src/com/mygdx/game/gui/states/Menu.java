package com.mygdx.game.gui.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.audio.SoundManager;

/**
 * Created by Catarina Ramos on 12/05/2016.
 */
public class Menu extends State {
    private ImageButton playButton;
    private ImageButton highScoresButton;
    private ImageButton settingsButton;
    private ImageButton exitButton;
    private TextureAtlas ButtonsPack;
    private Skin skin;
    private ImageButton.ImageButtonStyle style;
    private Texture background;
    private Viewport viewp;
    private Stage stage;
    private SoundManager soundManager;


    public Menu(GameStateManager manager,SoundManager soundManager) {
        super(manager);

        this.soundManager = soundManager;
        this.soundManager.PlayMusic();

        background = new Texture("MenuBackgroundTitle.png");
        super.width = background.getWidth();
        super.height = background.getHeight();
        super.cam.setToOrtho(false, width, height);

        viewp = new FillViewport(width,height);
        stage = new Stage(viewp);
        stage.clear();

        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);

        //buttons
        ButtonsPack = new TextureAtlas("MenuButtons.atlas");        //pack de botões
        skin = new Skin();                                      //skin de botão
        skin.addRegions(ButtonsPack);
        style = new ImageButton.ImageButtonStyle();

        style = new ImageButton.ImageButtonStyle();
        style.up = skin.getDrawable("Play");       //estilo quando carregado
        style.down = skin.getDrawable("Play");
        playButton = new ImageButton(style);
        playButton.setPosition(width/5 - playButton.getWidth()/2, height/2 - playButton.getHeight()/2);

        style = new ImageButton.ImageButtonStyle();
        style.up = skin.getDrawable("Scores");       //estilo quando carregado
        style.down = skin.getDrawable("Scores");
        highScoresButton = new ImageButton(style);
        highScoresButton.setPosition(2*width/5 - playButton.getWidth()/2, height/2 - playButton.getHeight()/2);

        style = new ImageButton.ImageButtonStyle();
        style.up = skin.getDrawable("Settings");       //estilo quando carregado
        style.down = skin.getDrawable("Settings");
        settingsButton = new ImageButton(style);
        settingsButton.setPosition(3*width/5 - playButton.getWidth()/2,height/2 - playButton.getHeight()/2);

        style = new ImageButton.ImageButtonStyle();
        style.up = skin.getDrawable("Exit");       //estilo quando carregado
        style.down = skin.getDrawable("Exit");
        exitButton = new ImageButton(style);
        exitButton.setPosition(4*width/5 - playButton.getWidth()/2, height/2 - playButton.getHeight()/2);


        stage.addActor(playButton);
        stage.addActor(highScoresButton);
        stage.addActor(settingsButton);
        stage.addActor(exitButton);
    }

    @Override
    public void handleInput() {
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.set(new Play(manager,soundManager));
                soundManager.PlayClick();
                dispose();  //free memory
            }
        });

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.set(new HowToPlay(manager,soundManager));
                soundManager.PlayClick();
                dispose();  //free memory
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundManager.PlayClick();
                dispose();  //free memory
                Gdx.app.exit();
            }
        });

        highScoresButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
               manager.set(new HighScores(manager,soundManager));
                soundManager.PlayClick();
                dispose();
            }
        });
    }

    @Override
    public void render(SpriteBatch batch){
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.end();
        stage.draw();
    }

    @Override
    public void dispose() {
        background.dispose();
        stage.dispose();
        ButtonsPack.dispose();
        skin.dispose();
    }
}
