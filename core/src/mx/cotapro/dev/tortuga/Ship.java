package mx.cotapro.dev.tortuga;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

abstract class Ship {

    //caracteristicas
    float movementSpeed;
    int shield;
    //posicion y dimesion
    Rectangle boundingbox;

    //laser
    float laserwidth,laserheight;
    float lasermovementspeed;
    float tiemebetween;
    float timesincelastshot= 0;

    //graficos
    TextureRegion shipTextureRegion, shieldtextureRegion,laserTextureRegion;

    public Ship(float movementSpeed, int shield,
                float width, float height,
                float xcenter , float ycenter ,
                float laserwidth, float laserheight, float lasermovementspeed,
                float tiemebetween,
                TextureRegion shipTextureRegion,
                TextureRegion shieldtextureRegion, TextureRegion laserTextureRegion) {
        this.movementSpeed = movementSpeed;
        this.shield = shield;
        this.boundingbox = new Rectangle(xcenter - width/2 ,ycenter - height/2,width,height);
        this.laserwidth = laserwidth;
        this.laserheight = laserheight;
        this.lasermovementspeed = lasermovementspeed;
        this.tiemebetween = tiemebetween;
        this.shipTextureRegion = shipTextureRegion;
        this.shieldtextureRegion = shieldtextureRegion;
        this.laserTextureRegion = laserTextureRegion;
    }

    public  void update(float deltaTime){
        timesincelastshot += deltaTime;
    }

    public  boolean carfirelaser(){
        return (timesincelastshot - tiemebetween >= 0);
    }

    public  abstract  Laser[] fireLasers();

    public boolean intersects(Rectangle otherRectangle){
       return boundingbox.overlaps(otherRectangle);
    }

    public boolean hitandcheckdestroyed(Laser laser){
        if(shield >0){
            shield--;
            return false;
        }
        return true;

    }

    public void translate (float xchange, float ychange){
        boundingbox.setPosition(boundingbox.x+xchange,boundingbox.y+ychange);
    }

    public void draw(Batch batch){
        batch.draw(shipTextureRegion,boundingbox.x,boundingbox.y,boundingbox.width,boundingbox.height);
        if(shield > 0){
            batch.draw(shieldtextureRegion,boundingbox.x,boundingbox.y,boundingbox.width,boundingbox.height);
        }
    }

}
