package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.ZickZackJump;

public class Jumper extends Sprite {
    public World world;
    public Body b2body;

    private PlayScreen screen;

    public Jumper (PlayScreen screen){
        //Initialize default values
        this.screen = screen;
        this.world = screen.getWorld();

        //define mario in Box2d
        defineMario();

        //set initial values for marios location, width and height. And initial frame as marioStand
        setBounds(0,0, 16 / ZickZackJump.PPM, 16/ZickZackJump.PPM);
    }

    public void defineMario(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / ZickZackJump.PPM, 32 / ZickZackJump.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/ ZickZackJump.PPM);
        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }
}
