package com.mygdx.game.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.ZickZackJump;


public class Hud implements Disposable{
    //Scene2D.ui Stage and its own Viewport for HUD
    public Stage stage;
    private Viewport viewport;

    //Jumper score/time Tracking Variables
    private static Integer score, level, worldTimer;
    private float timeCount;

    private boolean timeUp; //true when the world timer reaches 0

    private static Label scoreLabel;
    private Label countdownLabel, scoreName, levelName, levelLabel, timeLabel;

    public Hud(SpriteBatch sb){
        score = 0;
        level = 1;
        timeCount = 0;
        worldTimer = 200;

        viewport = new FitViewport(ZickZackJump.V_WIDTH, ZickZackJump.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);


        Table table = new Table();
        table.top();
        table.setFillParent(true); //table is size of the stage


        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreName = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelName = new Label("LEVEL", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label(String.format("%03d", level), new Label.LabelStyle(new BitmapFont(), Color.WHITE));


        table.add(scoreName).expandX().padTop(10);
        table.add(levelName).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);
    }


    public void update(float dt){
        timeCount += dt;
        if (timeCount >= 1){
            if (worldTimer > 0){
                worldTimer--;
            }else{
                timeUp = true;
            }
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public boolean isTimeUp(){
        return timeUp;
    }



}
