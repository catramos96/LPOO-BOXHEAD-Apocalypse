package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.gui.states.GameStateManager;
import com.mygdx.game.gui.states.Menu;
import com.mygdx.game.audio.SoundManager;

public class MyGdxGame extends ApplicationAdapter {

	public static final String GameTitle = "Apocalypse";
	private GameStateManager stateManager;
	private SpriteBatch batch; //1 per game
	private SoundManager soundManager;
	Texture img;

	@Override
	public void create () {
		batch = new SpriteBatch();
		stateManager = new GameStateManager();
		soundManager = new SoundManager();
		Gdx.gl.glClearColor(0, 0, 0, 1); //black
		stateManager.push(new Menu(stateManager,soundManager));
	}

	@Override
	public void render () {	//method that is executed in a loop
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		//clears the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stateManager.update(Gdx.graphics.getDeltaTime());
		stateManager.render(batch);
	}

	/*SpriteBatch batch;
	Texture img;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("MenuBackground.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1); //black
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}*/
}
