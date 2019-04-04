package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Sprites.Enemies.Enemy;
import com.mygdx.game.Sprites.Enemies.Goomba;
import com.mygdx.game.Sprites.Jumper;
import com.mygdx.game.Tools.B2WorldCreator;
import com.mygdx.game.Tools.WorldContactListener;
import com.mygdx.game.ZickZackJump;

public class PlayScreen implements Screen {
    //Reference to our game, used to set Screens
    private ZickZackJump game;
    private TextureAtlas atlas;

    //Tiled map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    private Jumper player;

    private Hud hud;

    //basic playscreen variables
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    //private Sprite sprite;

    public PlayScreen(ZickZackJump game){
        atlas = new TextureAtlas("Mario_and_Enemies.pack");

        //sprite = new Sprite(new Texture("badlogic.jpg"));
        this.game = game;

//
//        //create cam used to follow jumper through cam world
        gamecam = new OrthographicCamera();
//
//        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(ZickZackJump.V_WIDTH / ZickZackJump.PPM,
                ZickZackJump.V_HEIGHT/ ZickZackJump.PPM,
                gamecam);


        //create our game HUD for scores/timers/level info
        hud = new Hud(game.batch);


        //Load our map and setup our map renderer
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("ZickZack.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / ZickZackJump.PPM);
//
//        //initially set our gamecam to be centered correctly at the start of of map
        gamecam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);

        //create our Box2D world, setting no gravity in X, -10 gravity in Y, and allow bodies to sleep
        world = new World(new Vector2(0, -10), true);

        //allows for debug lines of our box2d world.
        b2dr = new Box2DDebugRenderer();

        creator = new B2WorldCreator(this);


        //create jumper in our game world
        player = new Jumper(this);

        world.setContactListener(new WorldContactListener());

        Goomba goomba = new Goomba(this, 50, 32);

    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    public void handleInput(float dt){
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.jump();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <=2)
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
    }

    public void update(float dt){
        handleInput(dt);


        //takes 1 step in the physics simulation(60 times per second)
        world.step(1 / 60f, 6, 2);

        for (Enemy enemy: creator.getGoombas()){
            enemy.update(dt);
            if (enemy.getY() < player.getY() + 20 / ZickZackJump.PPM){
                enemy.b2body.setActive(true);
            }
        }

        player.update(dt);

        hud.update(dt);

        gamecam.position.y = player.b2body.getPosition().y;

        //update our gamecam with correct coordinates after changes
        gamecam.update();

        //tell our renderer to draw only what our camera can see in our game world
        renderer.setView(gamecam);
    }


    @Override
    public void render(float delta) {

        //separate our update logic from render
        update(delta);

        //Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        //render our game map
        renderer.render();

        //renderer our Box2DDebugLines
        b2dr.render(world, gamecam.combined);


        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        for (Enemy enemy: creator.getGoombas())
            enemy.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

    }

    public TiledMap getMap(){
        return map;
    }

    public World getWorld(){
        return world;
    }



    @Override
    public void show() {

    }


    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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
        //dispose of all our opened resources
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }

    public Hud getHud(){
        return hud;
    }
}
