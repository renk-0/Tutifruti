package mx.cotapro.dev.tortuga;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.cotapro.dev.Tutifruti;

import java.util.LinkedList;
import java.util.ListIterator;

public class GameScreen  implements Screen{

   //screem
    private Camera camera;
    private Viewport viewport;
    //grapish
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private Texture explisionwe ;

    // private Texture background;
    private TextureRegion[] backgrounds;
    private TextureRegion jugador,escudo,enemigo,escudodelenemigo
            ,disparoamigo,disparoenemigo;



//tiempos

    private float[] backgroundOFFsets = {0,0,0};
    private float backgroudMaxScrollingSpeed;
    private float timeBetweenEnemySpawn = 1f;
    private float enemySpawnTimer = 0;
//parametros del mundo
    private  final float WORLD_WIDTH = 72;
    private  final float WORLD_HEIGHT = 128;
    private final float TOUCH_MOVEMENT = 0.5f;
    private LinkedList<Explosion> explosionlist;
//objetos del juego
    private PlayerShip playerShip;
    private LinkedList<Enemyship> enemyShipsList;
    private LinkedList<Laser> playerlaserlist;
    private LinkedList<Laser> enemylaserlist;

    private int score = 0;

    //head-uo dispalay
    BitmapFont font;
    float hudVerticalMARGIN,hudleftx,hudrigthx,hudcenterx,hudrow1y,hudrow2y,hudsectionwidth;

    public GameScreen(final Tutifruti game) {
        camera =new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT,camera);
		camera.translate(WORLD_WIDTH/2, WORLD_HEIGHT/2, 0);
        textureAtlas = new TextureAtlas("tortuga/imagenes.atlas");
        //background = new Texture("fondo1.png");
        //backgroundOFFset= 0;
        backgrounds = new  TextureRegion[3];

        backgrounds[0] = textureAtlas.findRegion("Starscape00");
        backgrounds[1] = textureAtlas.findRegion("Starscape01");
        backgrounds[2] = textureAtlas.findRegion("Starscape02");


        backgroudMaxScrollingSpeed =(float)WORLD_HEIGHT / 4;

        //texturas
        jugador = textureAtlas.findRegion("SpaceTurtle1");
        escudo = textureAtlas.findRegion("shield2");
        enemigo = textureAtlas.findRegion("enemyRed2");
        escudodelenemigo = textureAtlas.findRegion("shield1");
        escudodelenemigo.flip(false,true);
        disparoamigo = textureAtlas.findRegion("laserBlue04");
        disparoenemigo = textureAtlas.findRegion("laserRed03");

        explisionwe = new Texture("tortuga/exp2_0.png");


        //set oup
        playerShip = new PlayerShip(50,1,10,
                10,WORLD_WIDTH/2,
                WORLD_HEIGHT/4,0.4f,4,45,
                0.5f,jugador,escudo,disparoamigo);

        enemyShipsList = new LinkedList<>();


        playerlaserlist = new LinkedList<>();
        enemylaserlist = new LinkedList<>();

        explosionlist = new LinkedList<>();
		
		batch = game.batch;
		
        //prepareHUD();
    }

    /*private void prepareHUD(){
        //crear un bitmap


        //scalar el font
        font.getData().setScale(0.08f);

        //moriri de tristeza  a y calcular el hud
        hudVerticalMARGIN = font.getCapHeight()/2;
        hudleftx = hudVerticalMARGIN;
        hudrigthx = WORLD_WIDTH * 2 /3 - hudleftx;
        hudcenterx = WORLD_WIDTH / 3;
        hudrow1y = WORLD_HEIGHT - hudVerticalMARGIN;
        hudrow2y = hudrow1y - hudVerticalMARGIN -font.getCapHeight();
        hudsectionwidth = WORLD_WIDTH / 3;

    }*/




    @Override
    public void render(float deltaTime) {
        ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();

        //scroll
        renderBackground(deltaTime);

        detectInput(deltaTime);
        playerShip.update(deltaTime);



        ListIterator<Enemyship> enemyshiplistiterator = enemyShipsList.listIterator();
        while (enemyshiplistiterator.hasNext()){
            Enemyship enemyShip = enemyshiplistiterator.next();
            movimintoenemigo(enemyShip,deltaTime);
            enemyShip.update(deltaTime);
            enemyShip.draw(batch);
        }

        //juegador
        playerShip.draw(batch);
        //laser
        renderLasers(deltaTime);

        Spawnenemigos(deltaTime);


        //ver si le pegaron o no
        detectCollisions();
        //exploson
        renderExplosions(deltaTime);

        //renderizado del hud (guapo el  que lo lea)






        batch.end();

    }

    /*private void updateAndrenderHud(){
        font.draw(batch,"Puntos",hudleftx,hudrow1y,hudsectionwidth, Align.left ,false);
        font.draw(batch,"shield",hudcenterx,hudrow1y,hudsectionwidth,Align.center,false);
        font.draw(batch,"vidas",hudrigthx,hudrow1y,hudsectionwidth,Align.right,false);
        //nose que es esto pero se ocupa :D
        font.draw(batch,String.format(Locale.getDefault(),"%06d",score),
                hudleftx,hudrow2y,hudsectionwidth,Align.left,false);
        font.draw(batch,String.format(Locale.getDefault(),"%02d",playerShip.shield),
                hudcenterx,hudrow2y,hudsectionwidth,Align.center,false);
        font.draw(batch,String.format(Locale.getDefault(),"%02d",playerShip.lives),
                hudrigthx,hudrow2y,hudsectionwidth,Align.right,false);


    }*/

    private void Spawnenemigos(Float deltaTime)
    {
        enemySpawnTimer +=deltaTime;
        if (enemySpawnTimer > timeBetweenEnemySpawn) {
            enemyShipsList.add(new Enemyship(48, 1, 10, 10,
                    SpaceShoterGame.random.nextFloat() * (WORLD_WIDTH - 10) + 5, WORLD_HEIGHT - 5,
                    0.3f, 5, 50,
                    0.8f, enemigo, escudodelenemigo, disparoenemigo));
            enemySpawnTimer -= timeBetweenEnemySpawn;
        }

    }

    private void detectInput(float deltatime){
        //si le da el click

        //determinar el movimiento de la nave

        //check each key that matters and move accordingly

        float leftLimit, rigthLimit,upLimit,downLimit;
        leftLimit = -playerShip.boundingbox.x;
        downLimit = -playerShip.boundingbox.y;
        rigthLimit = WORLD_WIDTH - playerShip.boundingbox.x-playerShip.boundingbox.width;
        upLimit = (float)WORLD_HEIGHT/2 - playerShip.boundingbox.y - playerShip.boundingbox.height;

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)&& rigthLimit>0){
            playerShip.translate(Math.min(playerShip.movementSpeed*deltatime,rigthLimit),0f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)&& upLimit>0){
            playerShip.translate(0f,Math.min(playerShip.movementSpeed*deltatime,upLimit));
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)&& leftLimit < 0){
            playerShip.translate(Math.max(-playerShip.movementSpeed*deltatime,leftLimit),0f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)&& downLimit < 0){
            playerShip.translate(0f,Math.max(-playerShip.movementSpeed*deltatime,downLimit));
        }

        //los mismo pero con el mouse
        if (Gdx.input.isTouched()){
            //posicion de la camara
            float xtouchpixel = Gdx.input.getX();
            float ytouchpixel = Gdx.input.getY();

            //posicion de l mundo
            Vector2 touchPoint = new Vector2(xtouchpixel,ytouchpixel);
            touchPoint = viewport.unproject(touchPoint);

            //x y Y diferencias
            Vector2 playershipcenter = new Vector2(
                    playerShip.boundingbox.x + playerShip.boundingbox.width/2,
                    playerShip.boundingbox.y+playerShip.boundingbox.height/2);

            float touchDistance = touchPoint.dst(playershipcenter);

             if(touchDistance > TOUCH_MOVEMENT){
                 float xtouchdiferente = touchPoint.x - playershipcenter.x;
                 float ytouchdiferente = touchPoint.y - playershipcenter.y;


                 //escala de velocidad maxima
                 float xmove = xtouchdiferente / touchDistance * playerShip.movementSpeed * deltatime;
                 float ymove = ytouchdiferente / touchDistance * playerShip.movementSpeed * deltatime;

                 if(xmove > 0) xmove=Math.min(xmove,rigthLimit);
                 else xmove = Math.max(xmove,leftLimit);

                 if(ymove > 0) ymove=Math.min(ymove,upLimit);
                 else ymove = Math.max(ymove,downLimit);

                 playerShip.translate(xmove,ymove);


             }





        }
    }

    private  void movimintoenemigo (Enemyship enemyShip ,float deltaTime){
        //determinar el movimiento de la nave

        float leftLimit, rigthLimit,upLimit,downLimit;
        leftLimit = -enemyShip.boundingbox.x;
        downLimit = (float) WORLD_HEIGHT/2-enemyShip.boundingbox.y;
        rigthLimit = WORLD_WIDTH - enemyShip.boundingbox.x-enemyShip.boundingbox.width;
        upLimit = WORLD_HEIGHT - enemyShip.boundingbox.y - enemyShip.boundingbox.height;


        float xmove = enemyShip.getDireccion().x * enemyShip.movementSpeed * deltaTime;
        float ymove = enemyShip.getDireccion().y* enemyShip.movementSpeed * deltaTime;

        if(xmove > 0) xmove=Math.min(xmove,rigthLimit);
        else xmove = Math.max(xmove,leftLimit);

        if(ymove > 0) ymove=Math.min(ymove,upLimit);
        else ymove = Math.max(ymove,downLimit);

        enemyShip.translate(xmove,ymove);

    }

    private void detectCollisions(){
        //si le pego [*0*]
        ListIterator<Laser> laserListiterator = playerlaserlist.listIterator();
        while(laserListiterator.hasNext()) {
            Laser laser = laserListiterator.next();
            ListIterator<Enemyship> enemyshipListIterator = enemyShipsList.listIterator();
            while (enemyshipListIterator.hasNext()) {
                Enemyship enemyShip = enemyshipListIterator.next();

                if (enemyShip.intersects(laser.boundingbox)) {
                   if( enemyShip.hitandcheckdestroyed(laser))
                   {
                       enemyshipListIterator.remove();
                   }
                    laserListiterator.remove();

                }
            }
        }
        //si me pegan :(
        laserListiterator = enemylaserlist.listIterator();
        while(laserListiterator.hasNext()) {
            Laser laser = laserListiterator.next();
            if(playerShip.intersects(laser.boundingbox)){
               if(playerShip.hitandcheckdestroyed(laser)) {
                 playerShip.shield= 10;
               }
                laserListiterator.remove();
            }
        }

    }

    private void renderExplosions(float deltaTime){
        ListIterator<Explosion> explosionListIterator = explosionlist.listIterator();
        while (explosionListIterator.hasNext()){
            Explosion explosion = explosionListIterator.next();
            explosion.update(deltaTime);
            if(explosion.isFinised()){
                explosionListIterator.remove();
            }
            else {
                explosion.draw(batch);
            }
        }

    }


    private void renderLasers(float deltaTime){
        if(playerShip.carfirelaser()){
            Laser[] lasers = playerShip.fireLasers();
            for(Laser laser : lasers){
                playerlaserlist.add(laser);
            }
        }
        // laser enemigo
        ListIterator<Enemyship> enemyshipListIterator = enemyShipsList.listIterator();
        while (enemyshipListIterator.hasNext()) {
            Enemyship enemyShip = enemyshipListIterator.next();
            if (enemyShip.carfirelaser()) {
                Laser[] lasers = enemyShip.fireLasers();
                for (Laser laser : lasers) {
                    enemylaserlist.add(laser);
                }
            }
        }
        //dibujar laser
        //eliminar los viejos laser
        ListIterator<Laser> iterator = playerlaserlist.listIterator();
        while(iterator.hasNext()){
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.boundingbox.y += laser.movementSpeed*deltaTime;
            if(laser.boundingbox.y > WORLD_HEIGHT){
                iterator.remove();
            }
        }
        iterator = enemylaserlist.listIterator();
        while(iterator.hasNext()){
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.boundingbox.y -= laser.movementSpeed*deltaTime;
            if(laser.boundingbox.y + laser.boundingbox.height < 0){
                playerShip.hitandcheckdestroyed(laser);
                iterator.remove();
            }
        }
    }


    private void renderBackground (float deltaTime)
    {
        backgroundOFFsets[0] += deltaTime * backgroudMaxScrollingSpeed / 8;
        backgroundOFFsets[1] += deltaTime * backgroudMaxScrollingSpeed / 4;
        backgroundOFFsets[2] += deltaTime * backgroudMaxScrollingSpeed / 2;

        for(int layer = 0 ; layer < backgroundOFFsets.length; layer++)
        {
            if(backgroundOFFsets[layer]>WORLD_HEIGHT){
                backgroundOFFsets[layer] = 0;
            }
            batch.draw(backgrounds[layer],0,-backgroundOFFsets[layer],WORLD_WIDTH,WORLD_HEIGHT);
            batch.draw(backgrounds[layer],0,-backgroundOFFsets[layer] + WORLD_HEIGHT,WORLD_WIDTH,WORLD_HEIGHT);


        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        batch.setProjectionMatrix(camera.combined);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
		dispose();
    }

    @Override
    public void show() {

    }

    @Override
    public void dispose() {
    }
}
