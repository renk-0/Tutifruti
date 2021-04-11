package mx.cotapro.dev.tortuga;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.awt.Rectangle;

public class Explosion {

    private Animation<TextureRegion> explosionAnimation;
    private float explosionTimer;

    private Rectangle boundingBox;

   Explosion(Texture tecture , Rectangle boundingBox, float totalanimationtime){
        this.boundingBox = boundingBox;


        TextureRegion[][] textureregion2d = TextureRegion.split(tecture,64,64);

        TextureRegion[] unadimension = new TextureRegion[16];
        int index = 0;
        for(int i = 0 ; i<4;i++){
            for(int j = 0; j <4;j++){
                unadimension[index] = textureregion2d[i][j];
                index++;
            }
        }

        explosionAnimation = new Animation<TextureRegion>(totalanimationtime/16,unadimension);
        explosionTimer =0;

    }


    public void update(float deltaTime){
        explosionTimer += deltaTime;
    }
    public void draw (SpriteBatch batch){
        batch.draw (explosionAnimation.getKeyFrame(explosionTimer),
                boundingBox.x, boundingBox.y,
                boundingBox.width, boundingBox.height);
    }

    public boolean isFinised(){
        return explosionAnimation.isAnimationFinished(explosionTimer);
    }


}
