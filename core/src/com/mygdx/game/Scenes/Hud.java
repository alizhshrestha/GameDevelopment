package com.mygdx.game.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.ZickZackJump;


public class Hud implements Disposable{
    //Scene2D.ui Stage and its own Viewport for HUD
    public Stage stage;
    private Viewport viewport;

    //Jumper score/time Tracking Variables
    private static Integer score, level, worldTimer, highScore;
    private float timeCount;

    private boolean timeUp; //true when the world timer reaches 0

    private static Label scoreLabel, highScoreLabel;
    private Label countdownLabel, scoreName, levelName, levelLabel, timeLabel, highScoreName;

    public Hud(SpriteBatch sb){
        score = 0;
        level = 1;
        timeCount = 0;
        highScore = ZickZackJump.getHighScore();
        worldTimer = 100;

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
//        highScoreName = new Label("HIGH SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
//        highScoreLabel = new Label(String.format("%06d", highScore), new Label.LabelStyle(new BitmapFont(), Color.WHITE));


//        table.add(highScoreName).align(Align.center);
//        table.add(highScoreLabel).expandX();
//        table.row();
        table.add(scoreName).expandX().padTop(10);
        table.add(levelName).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);
    }

    public Hud(SpriteBatch sb, Integer score){
        this.score = score;
        level = 1;
        timeCount = 0;
        highScore = ZickZackJump.getHighScore();
        worldTimer = 100;

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
//        highScoreName = new Label("HIGH SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
//        highScoreLabel = new Label(String.format("%06d", highScore), new Label.LabelStyle(new BitmapFont(), Color.WHITE));


//        table.add(highScoreName).align(Align.center);
//        table.add(highScoreLabel).expandX();
//        table.row();
        table.add(scoreName).expandX().padTop(10);
        table.add(levelName).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);
    }

    public static Integer getScore(){
        return score;
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
        if (highScore < score){
            ZickZackJump.setHighScore(score);
        }

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public boolean isTimeUp(){
        return timeUp;
    }



}
