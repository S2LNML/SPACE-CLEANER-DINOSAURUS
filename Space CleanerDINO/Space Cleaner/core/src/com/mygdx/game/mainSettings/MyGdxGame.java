package com.mygdx.game.mainSettings;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.mainSettings.GameSettings;
import com.mygdx.game.managers.AudioManager;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.SettingsScreen;

public class MyGdxGame extends Game {
	public SettingsScreen settingsScreen;
	public AudioManager audioManager;
	public MenuScreen menuScreen;
	public BitmapFont commonWhiteFont, largeWhiteFont, commonBlackFont;
	float accumulator = 0;
	public World world;
	public Vector3 touch;
	public GameScreen gameScreen;
	public OrthographicCamera camera;
	public SpriteBatch batch;
	
	@Override
	public void create () {
		Box2D.init();
		world = new World(new Vector2(0,0), true);
		batch = new SpriteBatch();
		commonWhiteFont = FontBuilder.generate(24, Color.WHITE, GameResources.FONT_PATH);
		largeWhiteFont = FontBuilder.generate(48, Color.WHITE, GameResources.FONT_PATH);
		commonBlackFont = FontBuilder.generate(24, Color.BLACK, GameResources.FONT_PATH);
		audioManager = new AudioManager();
		camera = new OrthographicCamera(GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
		camera.setToOrtho(false, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
		gameScreen = new GameScreen(this);
		menuScreen = new MenuScreen(this);
		settingsScreen = new SettingsScreen(this);
		setScreen(menuScreen);
	}
	public void stepWorld(){
		float delta = Gdx.graphics.getDeltaTime();
		accumulator += Math.min(delta, 0.25f);
		if (accumulator >= GameSettings.STEP_TIME) {
			accumulator -= GameSettings.STEP_TIME;
			world.step(GameSettings.STEP_TIME, GameSettings.VELOCITY_ITERATIONS, GameSettings.POSITION_ITERATIONS);
		}
	}
	@Override
	public void dispose () {
		batch.dispose();
	}
}
