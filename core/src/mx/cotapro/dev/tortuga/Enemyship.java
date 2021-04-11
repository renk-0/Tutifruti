package mx.cotapro.dev.tortuga;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

class Enemyship extends Ship {

    Vector2 direccion;
    float cambiodedireccion = 0;
    float frecuenciadecambio = 0.75f;





    public Enemyship(float movementSpeed, int shield,
                      float width, float height, float xcenter,
                      float ycenter, float laserwidth, float laserheight,
                      float lasermovementspeed, float tiemebetween,
                      TextureRegion shipTextureRegion, TextureRegion shieldtextureRegion,
                      TextureRegion laserTextureRegion) {
        super(movementSpeed, shield, width, height, xcenter, ycenter, laserwidth, laserheight, lasermovementspeed, tiemebetween, shipTextureRegion, shieldtextureRegion, laserTextureRegion);

       direccion = new Vector2(0,-1);
    }

    public Vector2 getDireccion() {
        return direccion;
    }

    private void randomizedireccionvector(){
        double bearing = SpaceShoterGame.random.nextDouble()*6.283185;
        direccion.x =(float)Math.sin(bearing);
        direccion.y =(float)Math.cos(bearing);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        cambiodedireccion +=deltaTime;
        if(cambiodedireccion > frecuenciadecambio){
            randomizedireccionvector();
            cambiodedireccion -=frecuenciadecambio;

        }
    }

    @Override
    public Laser[] fireLasers() {
        Laser[] laser = new Laser[2];
        laser[0] = new Laser(boundingbox.x+boundingbox.width*0.31f, boundingbox.y,
                laserwidth,laserheight,
                lasermovementspeed,laserTextureRegion);
        laser[1] = new Laser(boundingbox.x+boundingbox.width*0.7f,boundingbox.y ,
                laserwidth,laserheight,
                lasermovementspeed,laserTextureRegion);
        timesincelastshot = 0;

        return laser;
    }
    @Override
    public void draw(Batch batch){
        batch.draw(shipTextureRegion,boundingbox.x,boundingbox.y,boundingbox.width,boundingbox.height);
        if(shield > 0){
            batch.draw(shieldtextureRegion,boundingbox.x,boundingbox.y-boundingbox.height*0.2f,boundingbox.width,boundingbox.height);
        }
    }
}
