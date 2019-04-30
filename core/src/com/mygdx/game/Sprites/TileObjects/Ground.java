package com.mygdx.game.Sprites.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.mygdx.game.Screens.GameOverScreen;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Jumper;
import com.mygdx.game.ZickZackJump;

public class Ground extends InteractiveTileObject{


    private PlayScreen screen;
    private ZickZackJump game;

    public Ground(PlayScreen screen, MapObject object) {
        super(screen, object);
        this.screen = screen;
        fixture.setUserData(this);
        setCategoryFilter(ZickZackJump.GROUND_BIT);
        this.game = screen.getGame();

    }

    @Override
    public void onHeadHit(Jumper jumper) {

    }

    public void onCloudGround(Jumper jumper){
        if (object.getProperties().containsKey("cloud")){
            System.out.println("finish level");
            game.setScreen(new PlayScreen(game, "level2.tmx"));
        }else if (object.getProperties().containsKey("last_cloud")){
            game.setScreen(new GameOverScreen(game));
        }else
            System.out.println("end game");
    }
}
