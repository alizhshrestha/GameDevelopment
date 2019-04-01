package com.mygdx.game.Sprites.Enemies;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Jumper;
import com.mygdx.game.ZickZackJump;

public class Goomba extends Enemy{

    public Goomba(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        //setBounds(getX(), getY(), 16/ZickZackJump.PPM, 16/ZickZackJump.PPM);
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/ ZickZackJump.PPM);
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void hitOnHead(Jumper jumper) {

    }

    @Override
    public void hitByEnemy(Enemy enemy) {

    }
}
