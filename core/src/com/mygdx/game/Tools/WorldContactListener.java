package com.mygdx.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Sprites.Enemies.Enemy;
import com.mygdx.game.Sprites.Jumper;
import com.mygdx.game.Sprites.TileObjects.Ground;
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
                    ((InteractiveTileObject)fixB.getUserData())
                            .onHeadHit((Jumper)fixA.getUserData());
                else
                    ((InteractiveTileObject)fixA.getUserData())
                            .onHeadHit((Jumper)fixB.getUserData());
                break;
            case ZickZackJump.ENEMY_HEAD_BIT | ZickZackJump.JUMPER_BIT:
                if (fixA.getFilterData().categoryBits == ZickZackJump.ENEMY_HEAD_BIT)
                    ((Enemy)fixA.getUserData()).hitOnHead((Jumper)fixB.getUserData());
                else
                    ((Enemy)fixB.getUserData()).hitOnHead((Jumper) fixA.getUserData());
                break;
            case ZickZackJump.ENEMY_BIT | ZickZackJump.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == ZickZackJump.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
//            case ZickZackJump.JUMPER_BIT | ZickZackJump.ENEMY_BIT:
//                if(fixA.getFilterData().categoryBits == ZickZackJump.JUMPER_BIT)
//                    ((Jumper) fixA.getUserData()).hit((Enemy)fixB.getUserData());
//                else
//                    ((Jumper) fixB.getUserData()).hit((Enemy)fixA.getUserData());
//                break;
            case ZickZackJump.JUMPER_BIT | ZickZackJump.GROUND_BIT:
                if (fixA.getFilterData().categoryBits == ZickZackJump.GROUND_BIT)
                    ((Ground)fixA.getUserData()).onCloudGround((Jumper)fixB.getUserData());
                else
                    ((Ground)fixB.getUserData()).onCloudGround((Jumper)fixA.getUserData());

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
