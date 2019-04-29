package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.ZickZackJump;

public class MainMenuScreen implements Screen {

    private ZickZackJump game;
    private Stage stage;
    private Table table;
    private Skin skin;
    private Texture back_texture;
    private TextureRegion background;
    private TextButton playGame, help, exit;

    public MainMenuScreen(ZickZackJump game){
        this.game = game;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
    }
    @Override
    public void show() {
        stage.clear();

        back_texture = new Texture(Gdx.files.internal("background.png"));
        background = new TextureRegion(back_texture);

        // Create a table that fills the screen
        table = new Table();
        table.setFillParent(true);
        table.setBackground(new TiledDrawable(background));
        table.setDebug(false);

        //create buttons
        playGame = new TextButton("Play Game", skin);
        exit = new TextButton("Exit", skin);
        help = new TextButton("How to play", skin);

        //add buttons to table
        table.add(playGame).fillX().uniformX();
        table.row().pad(10,0,10,0);
        table.add(help).fillX().uniformX();
        table.row().pad(10,0,10,0);
        table.add(exit).fillX().uniformX();

        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        playGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stage.clear();
                game.setScreen(new PlayScreen(game));
            }
        });

        help.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stage.clear();
                game.setScreen(new HelpScreen(game));
            }
        });

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
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
        stage.dispose();
    }
}
