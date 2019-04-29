package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.ZickZackJump;

public class HelpScreen implements Screen {

    private ZickZackJump game;
    private Stage stage;
    private Table table;
    private Skin skin;
    private Texture back_texture;
    private TextureRegion background;

    private Label rightLabel, freeSpace, leftLabel, upLabel;
    private TextButton back_button;

    public HelpScreen(ZickZackJump game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
    }

    @Override
    public void show() {
        stage.clear();

        back_texture = new Texture(Gdx.files.internal("background.png"));
        background = new TextureRegion(back_texture);

        table = new Table();
        table.setFillParent(true);
        table.setBackground(new TiledDrawable(background));
        table.setDebug(false);

        upLabel = new Label("Press up key to jump", skin);
        upLabel.setColor(Color.BLACK);
        rightLabel = new Label("Press right key to go right", skin);
        rightLabel.setColor(Color.BLACK);
        freeSpace = new Label("                            ", skin);
        leftLabel = new Label("Press left key to go left", skin);
        leftLabel.setColor(Color.BLACK);

        back_button = new TextButton("Back", skin);

        back_button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        table.add(upLabel).fillX().uniformX();
        table.row().pad(40,0,40,0);
        table.add(leftLabel).align(Align.left);
        table.add(freeSpace);
        table.add(rightLabel).align(Align.right);
        table.row().pad(40,0,40,0);
        table.add(back_button).colspan(3);


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
        stage.getViewport().update(width, height, true);
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
