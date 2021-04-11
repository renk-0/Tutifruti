package mx.cotapro.dev.tortuga;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

class PlayerShip extends Ship {

    int lives;


    public PlayerShip(float movementSpeed, int shield,
                      float width, float height, float xcenter,
                      float ycenter, float laserwidth, float laserheight,
                      float lasermovementspeed, float tiemebetween,
                      TextureRegion shipTextureRegion, TextureRegion shieldtextureRegion,
                      TextureRegion laserTextureRegion) {
        super(movementSpeed, shield, width, height, xcenter, ycenter, laserwidth, laserheight,
                lasermovementspeed, tiemebetween, shipTextureRegion, shieldtextureRegion,
                laserTextureRegion);
        lives = 3;
    }

    @Override
     public Laser[] fireLasers() {
        Laser[] laser = new Laser[2];
        laser[0] = new Laser(boundingbox.x+boundingbox.width*0.09f,boundingbox.y+boundingbox.height*0.45f,
                laserwidth,laserheight,
                lasermovementspeed,laserTextureRegion);
        laser[1] = new Laser(boundingbox.x+boundingbox.width*0.93f,boundingbox.y+boundingbox.height*0.45f,
                laserwidth,laserheight,
                lasermovementspeed,laserTextureRegion);
        timesincelastshot = 0;

         return laser;
     }
 }
