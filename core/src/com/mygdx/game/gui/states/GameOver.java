package com.mygdx.game.gui.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.game.audio.SoundManager;
import com.mygdx.game.logic.GameData;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Madnar on 05/06/2016.
 */
public class GameOver extends State {


    Texture background;
    SoundManager soundManager;
    long score;
    private BitmapFont font;
    private GameData scores;
    private boolean drawMsg;
    private SimpleDateFormat sdf;


    public GameOver(GameStateManager manager, long score, SoundManager soundManager) {
        super(manager);
        this.soundManager = soundManager;
        this.score = score;


        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(
                Gdx.files.internal("chicagoexpress.ttf")
        );

        FreeTypeFontGenerator.FreeTypeFontParameter config = new FreeTypeFontGenerator.FreeTypeFontParameter();
        config.size = 30;
        config.magFilter = Texture.TextureFilter.Nearest;
        config.minFilter = Texture.TextureFilter.Nearest;
        config.color = Color.GRAY;
        font = gen.generateFont(config);

        background = new Texture("MenuBackgroundTitle.png");
        super.cam.setToOrtho(false, background.getWidth() , background.getHeight() );
        super.cam.position.set(background.getWidth()/2, background.getHeight()/2, 0);



        sdf = new SimpleDateFormat("dd/MM/yyyy");
        GameData.init();
        GameData.load();

        if(GameData.isHighScore(score)) {
            String currentDateandTime = sdf.format(new Date());
            GameData.addHighScore(score, currentDateandTime);
            GameData.sortHighScores();
            GameData.save();
            drawMsg = true;
        }

    }

    @Override
    public void handleInput() {
        if(Gdx.input.isTouched())
        {
            soundManager.PlayClick();
            manager.set(new HighScores(manager,soundManager));
            dispose();
        }
    }

    @Override
    public void render(SpriteBatch batch) {

            cam.update();
            batch.setProjectionMatrix(cam.combined);

            batch.begin();
            batch.draw(background, 0, 0);

            float GameWidth = background.getWidth();

            GlyphLayout glyphLayout = new GlyphLayout();

            String s;
            float w;


            s = "Score";

            glyphLayout.setText(font, s);

            w = glyphLayout.width;
            font.draw(batch, s, (GameWidth - w / 2) / 2, 240);

            s = String.format("%7d", score);

            font.draw(batch, s, (GameWidth - w / 2) / 2, 200);


        if(drawMsg)
            {
                 s = "NEW HIGHSCORE";
                font.draw(batch, s, (GameWidth - w) / 2, 180);
            }


            batch.end();


    }

    @Override
    public void dispose() {
        background.dispose();
        font.dispose();
    }
}
