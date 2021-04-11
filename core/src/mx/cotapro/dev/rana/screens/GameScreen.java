package mx.cotapro.dev.rana.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.World;
import mx.cotapro.dev.rana.utils.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.cotapro.dev.Tutifruti;
import mx.cotapro.dev.rana.App;
import static mx.cotapro.dev.rana.utils.B2DConstants.PPM;;

public class GameScreen extends AbstractScreen{
    //Camara - Camera
    OrthographicCamera camera;
	Viewport port;
    // Box2D
    World world;
    Box2DDebugRenderer b2dr;

    //Game Bodies
    Body ball;
    Body paddleLeft, goalLeft;
    Body paddleRight, goalRight;

	// Textures
	Texture nenufar, rana;
	Sound ribbit;

    public GameScreen(final Tutifruti game) {
        super(new App(game));
		
		nenufar = new Texture(Gdx.files.internal("rana/nenu.png"));
		rana = new Texture(Gdx.files.internal("rana/rana.png"));
		ribbit = Gdx.audio.newSound(Gdx.files.internal("rana/ribbit.mp3"));

        this.camera = new OrthographicCamera();
		camera.translate(App.V_WIDTH/2f/PPM, App.V_HEIGHT/2f/PPM);
		// camera.rotate(90f);
		this.port = new FitViewport(App.V_WIDTH/PPM, App.V_HEIGHT/PPM, camera);
	}
    public GameScreen(final App app) {
        super(app);

        this.camera = new OrthographicCamera();
		camera.translate(App.V_WIDTH/2f/PPM, App.V_HEIGHT/2f/PPM);
		// camera.rotate(90f);
		this.port = new FitViewport(App.V_WIDTH/PPM, App.V_HEIGHT/PPM, camera);
		ribbit = Gdx.audio.newSound(Gdx.files.internal("rana/ribbit.mp3"));
	}

    @Override
    public void show() {
        this.b2dr = new Box2DDebugRenderer();
        this.world = new World(new Vector2(0f, 0f), false);
		ribbit.play();
		initArena();
	}

	@Override
	public void resize(int width, int height) {
		port.update(width, height);
	};
	
    public void update(float delta) {
        world.step(1f/60f, 6, 6);

        // Get Mouse Position - Move Paddle
        //TODO: Revise
		Vector2 mousepos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
		port.unproject(mousepos);
        float mouseLerp = mousepos.x;
		
        if (mousepos.x > port.getWorldWidth() - (20f/PPM)) {
             mouseLerp = port.getWorldWidth() - (20f/PPM);
    	 } else if(mousepos.x < 20/PPM) {
             mouseLerp = 20f/PPM;
        }
        paddleLeft.setTransform(mouseLerp, paddleLeft.getPosition().y, paddleLeft.getAngle());
		float diffx, diffy;
		diffy = paddleRight.getPosition().y - ball.getPosition().y;
		diffx = paddleRight.getPosition().x - ball.getPosition().x;
		paddleRight.setLinearVelocity(-diffx, 0);
		stage.act(delta);
        camera.update();
        app.batch.setProjectionMatrix(camera.combined);
        app.shapeBatch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

		app.batch.setProjectionMatrix(camera.combined);
		app.batch.begin();
		app.batch.draw(nenufar,
				paddleLeft.getPosition().x - 20/PPM,
				paddleLeft.getPosition().y,
				40f/PPM, 10f/PPM);		
		app.batch.draw(nenufar,
				paddleRight.getPosition().x - 20/PPM,
				paddleRight.getPosition().y,
				40f/PPM, 10f/PPM);		
		app.batch.draw(rana,
				ball.getPosition().x - 10/PPM,
				ball.getPosition().y - 10/PPM,
				20/PPM, 20/PPM);		
		app.batch.end();
        
		// b2dr.render(world, camera.combined);
		stage.draw();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        super.dispose();
        world.dispose();
    }

    private void initArena() {
        createWalls();
        ball = B2DBodyBuilder.createBall(world, port.getWorldWidth() / 2, port.getWorldHeight() / 2, 10f);
		ball.setLinearVelocity(MathUtils.random(-15f, 15f), 12f);
		// ball.
		//Setup Paddles
        paddleLeft = B2DBodyBuilder.createBox(world, 
				port.getWorldWidth() / 2,
				40/PPM, 40, 10, 
				false, false);
        paddleRight = B2DBodyBuilder.createBox(world, 
				port.getWorldWidth() / 2,
				port.getWorldHeight() - (40/PPM), 
				40, 10, false, false);

        //Setup Goals Sensors
        goalLeft = B2DBodyBuilder.createBox(world, 
				port.getWorldWidth() / 2, 5/PPM, 
				port.getWorldHeight()*PPM, 10,
				true, true);
        goalRight = B2DBodyBuilder.createBox(world, 
				port.getWorldWidth() / 2, 
				port.getWorldHeight() - (5/PPM), 
				port.getWorldHeight()*PPM, 10, 
				true, true);

    }

    private void createWalls() {
        Vector2[] verts = new Vector2[5];
        verts[0] = new Vector2(1f/PPM, 0);
        verts[1] = new Vector2(port.getWorldWidth(), 0);
        verts[2] = new Vector2(port.getWorldWidth(), port.getWorldHeight() - (1/PPM));
        verts[3] = new Vector2(1f/PPM, port.getWorldHeight() - (1/PPM));
        verts[4] = new Vector2(1f/PPM, 0);

        B2DBodyBuilder.createChainShape(world, verts);
    }
}
