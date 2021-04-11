package mx.cotapro.dev.tortuga;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Laser {

    //posicion y dimensiones
    Rectangle boundingbox;

    //carateristicas del laser
    float movementSpeed;

    //graficos
    TextureRegion textureRegion;

    public Laser(float xPosition, float yPosition, float width, float height, float movementSpeed, TextureRegion textureRegion) {
        this.boundingbox = new Rectangle(xPosition - width/2 ,yPosition ,width,height);

        this.movementSpeed = movementSpeed;
        this.textureRegion = textureRegion;
    }

    public void draw(Batch batch){
        batch.draw(textureRegion,boundingbox.x,boundingbox.y,boundingbox.width,boundingbox.height);
    }

    public Rectangle getBoundingBox(){
        return boundingbox;
    }

}
