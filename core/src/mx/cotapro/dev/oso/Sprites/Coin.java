package mx.cotapro.dev.oso.Sprites;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import mx.cotapro.dev.oso.Oso;

import mx.cotapro.dev.oso.Scenes.Hud;

public class Coin extends InteractiveTileObject{
    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN =3987;
    public Coin(World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);
        tileSet = map.getTileSets().getTileSet("tilemap_mapping");
        fixture.setUserData(this);
        setCategoryFilter(Oso.COIN_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Coin", "Collision");
        getCell().setTile(tileSet.getTile(BLANK_COIN));
        Hud.addscore(100);
    }
}
