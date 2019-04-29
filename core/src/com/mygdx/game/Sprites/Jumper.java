package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Enemies.Enemy;
import com.mygdx.game.ZickZackJump;

public class Jumper extends Sprite {
    public enum State{FALLING, JUMPING, STANDING, RUNNING, DEAD};
    public State currentState;
    public State previousState;


    public World world;
    public Body b2body;

    private PlayScreen screen;

    private TextureRegion marioStand;

    private Animation marioRun;
    private TextureRegion marioJump;

    private TextureRegion marioDead;


    private float stateTimer;
    private boolean runningRight;


    private boolean marioIsDead;


    public Jumper (PlayScreen screen){
        //Initialize default values
        this.screen = screen;
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        //get run animation frames and add them to marioRun Animation
        for (int i = 1; i < 4; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("little_mario"), i*16, 0, 16, 16));
        marioRun = new Animation(0.1f, frames);

        frames.clear();

        marioJump = new TextureRegion(screen.getAtlas().findRegion("little_mario"), 80, 0, 16,16);

        //create texture region for mario standing
        marioStand = new TextureRegion(screen.getAtlas().findRegion("little_mario"), 0,0,16,16);


        //create dead mario texture region
        marioDead = new TextureRegion(screen.getAtlas().findRegion("little_mario"), 96, 0, 16, 16);

        //define mario in Box2d
        defineMario();

        //set initial values for marios location, width and height. And initial frame as marioStand
        setBounds(0,0, 16 / ZickZackJump.PPM, 16/ZickZackJump.PPM);
        setRegion(marioStand);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
    }

    public void update(float dt){
        if (screen.getHud().isTimeUp() && !isDead()){
            die();
        }

        if((b2body.getPosition().x + getWidth()/2) > ZickZackJump.V_WIDTH / ZickZackJump.PPM){

        }

        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);

        setRegion(getFrame(dt));
    }

    public void die(){
        if (!isDead()){
            marioIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = ZickZackJump.NOTHING_BIT;

            for (Fixture fixture: b2body.getFixtureList()){
                fixture.setFilterData(filter);
            }

            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
        }
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;

        //depending on the state, get corresponding animation keyframe
        switch(currentState){
            case DEAD:
                region = marioDead;
                break;
            case JUMPING:
                region = marioJump;
                break;
            case RUNNING:
                region = (TextureRegion) marioRun.getKeyFrame(stateTimer,true);
                break;
            case FALLING:
            case STANDING:
                default:
                    region = marioStand;
                    break;
        }

        //if mario is running left and the texture isnt facing left... flip it.
        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }


        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        stateTimer = currentState == previousState ? stateTimer + dt : 0;

        //update previous state
        previousState = currentState;

        //return our final adjusted frame
        return region;
    }

    public State getState(){
        //Test to Box2D for velocity on the X and Y-Axis
        //if mario is going positive in Y-Axis he is jumping... or if he just jumped and is falling remain in jump state
        if (marioIsDead)
            return State.DEAD;
        if ((b2body.getLinearVelocity().y > 0 && currentState == State.JUMPING) || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
            //if negative in Y-Axis mario is falling
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
            //if mario is positive or negative in the X axis he is running
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
            //if none of these return then he must be standing
        else
            return State.STANDING;
    }

    public boolean isDead(){
        return marioIsDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

    public void jump(){
        if(currentState != State.JUMPING){
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
            currentState = State.JUMPING;
        }

    }

    public void hit(Enemy enemy){
        die();
    }

    public void defineMario(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/ZickZackJump.PPM, 32/ZickZackJump.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/ ZickZackJump.PPM);
        fdef.filter.categoryBits = ZickZackJump.JUMPER_BIT;
        fdef.filter.maskBits = ZickZackJump.GROUND_BIT |
                ZickZackJump.COIN_BIT |
                ZickZackJump.BRICK_BIT |
                ZickZackJump. ENEMY_BIT |
                ZickZackJump.OBJECT_BIT |
                ZickZackJump.ENEMY_HEAD_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / ZickZackJump.PPM, 6 / ZickZackJump.PPM), new Vector2(2 / ZickZackJump.PPM, 6 / ZickZackJump.PPM));
        fdef.filter.categoryBits = ZickZackJump.JUMPER_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData(this);
    }



}
