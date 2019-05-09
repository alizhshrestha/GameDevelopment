package com.mygdx.game.Sprites.TileObjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Jumper;
import com.mygdx.game.ZickZackJump;

public class Brick extends InteractiveTileObject{

    public Brick(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(ZickZackJump.BRICK_BIT);
    }

    @Override
    public void onHeadHit(Jumper jumper) {
        setCategoryFilter(ZickZackJump.DESTROYED_BIT);
        getCell().setTile(null);
        Hud.addScore(200);
        ZickZackJump.manager.get("audio/sounds/breakblock.wav", Sound.class).play();
    }
}
