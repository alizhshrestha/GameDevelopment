package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.LoadingScreen;
import com.mygdx.game.Screens.PlayScreen;

public class ZickZackJump extends Game {
	//Virtual screen size and Box2D scale(pixels per meter)
	public static final int V_WIDTH = 150;
	public static final int V_HEIGHT = 300;
	public static final float PPM = 100;


	//Box2D collision bits
	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short JUMPER_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short OBJECT_BIT =32;
	public static final short ENEMY_BIT = 64;
	public static final short ENEMY_HEAD_BIT = 128;
	public static final short JUMPER_HEAD_BIT = 512;


	public SpriteBatch batch;

	public static Preferences prefs;

	public static AssetManager manager;

	@Override
	public void create () {
		batch = new SpriteBatch();

		prefs = Gdx.app.getPreferences("ZickZackJump");

		if (!prefs.contains("highScore")){
			prefs.putInteger("highScore", 0);
		}

		manager = new AssetManager();

		manager.load("audio/music/mario_music.ogg", Music.class);
		manager.load("audio/sounds/coin.wav", Sound.class);
		manager.load("audio/sounds/bump.wav", Sound.class);
		manager.load("audio/sounds/breakblock.wav", Sound.class);
		manager.load("audio/sounds/stomp.wav", Sound.class);
		manager.load("audio/sounds/mariodie.wav", Sound.class);

		manager.finishLoading();

		setScreen(new LoadingScreen(this));
	}

	public static void setHighScore(int val){
		prefs.putInteger("highScore", val);
		prefs.flush();
	}

	public static int getHighScore(){
		return prefs.getInteger("highScore");
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
	}
}
