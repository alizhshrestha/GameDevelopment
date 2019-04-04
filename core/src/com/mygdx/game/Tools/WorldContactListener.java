package com.mygdx.game.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Sprites.Jumper;
import com.mygdx.game.Sprites.TileObjects.InteractiveTileObject;
import com.mygdx.game.ZickZackJump;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case ZickZackJump.JUMPER_HEAD_BIT | ZickZackJump.BRICK_BIT:
            case ZickZackJump.JUMPER_HEAD_BIT | ZickZackJump.COIN_BIT:
                if (fixA.getFilterData().categoryBits == ZickZackJump.JUMPER_HEAD_BIT)
                    ((InteractiveTileObject)fixB.getUserData()).onHeadHit((Jumper)fixA.getUserData());
                else
                    ((InteractiveTileObject)fixA.getUserData()).onHeadHit((Jumper)fixB.getUserData());
                break;
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
