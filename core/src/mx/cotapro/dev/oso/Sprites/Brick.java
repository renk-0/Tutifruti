package mx.cotapro.dev.oso.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import mx.cotapro.dev.oso.Oso;
import mx.cotapro.dev.oso.Scenes.Hud;


public class Brick extends InteractiveTileObject{
    public Brick(World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(Oso.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick", "Collision");
        setCategoryFilter(Oso.DESTROYED_BIT);
        getCell().setTile(null);
        Hud.addscore(200);
    }
}
