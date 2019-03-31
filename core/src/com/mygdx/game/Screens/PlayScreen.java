package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.ZickZackJump;

public class PlayScreen implements Screen {
    //Reference to our game, used to set Screens
    private ZickZackJump game;

    //basic playscreen variables
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Sprite sprite;

    public PlayScreen(ZickZackJump game){
        sprite = new Sprite(new Texture("badlogic.jpg"));
        this.game = game;
//
//        //create cam used to follow jumper through cam world
        gamecam = new OrthographicCamera();
//
//        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(ZickZackJump.V_WIDTH / ZickZackJump.PPM,
                ZickZackJump.V_HEIGHT/ ZickZackJump.PPM,
                gamecam);
//
//        //initially set our gamecam to be centered correctly at the start of of map
        gamecam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);
    }

    public void update(float dt){
        gamecam.update();
    }


    @Override
    public void render(float delta) {

        //separate our update logic from render
        update(delta);

        //Clear the game screen with Black
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        game.batch.begin();
        sprite.draw(game.batch);
        game.batch.end();

    }



    @Override
    public void show() {

    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
