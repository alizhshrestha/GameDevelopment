package com.mygdx.game.Sprites.TileObjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Jumper;
import com.mygdx.game.ZickZackJump;

public class Coin extends InteractiveTileObject{
    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28;

    public Coin(PlayScreen screen, MapObject object) {
        super(screen, object);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(ZickZackJump.COIN_BIT);
    }

    @Override
    public void onHeadHit(Jumper jumper) {
        if (getCell().getTile().getId() == BLANK_COIN){
            ZickZackJump.manager.get("audio/sounds/bump.wav", Sound.class).play();
        }else{
            ZickZackJump.manager.get("audio/sounds/coin.wav", Sound.class).play();
            getCell().setTile(tileSet.getTile(BLANK_COIN));
        }
        Hud.addScore(100);
    }

}
