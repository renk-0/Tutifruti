package mx.cotapro.dev.elcerdito;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import mx.cotapro.dev.elcerdito.entities.cerditoEntity;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import mx.cotapro.dev.Tutifruti;
import mx.cotapro.dev.elcerdito.BaseScreen;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import mx.cotapro.dev.elcerdito.entities.atrapaEntity;

public class GameScreen extends BaseScreen {
    private World world;
    private Stage stage;
    private cerditoEntity cochi;
    private Texture fondo;
    private Sprite sprite;
    private Batch batch;
    private TextButton oink;
    private AssetManager manager;


    public GameScreen(final Tutifruti game) {
		super(game);
        manager = new AssetManager();
        manager.load("cerdo/cerdito.png", Texture.class);
        manager.load("cerdo/cochi.png", Texture.class);
        manager.load("cerdo/fondo.png", Texture.class);
        manager.load("cerdo/oink.mp3", Sound.class);
		manager.finishLoading();


        Texture cochiTexture = manager.get("cerdo/cerdito.png", Texture.class);
        // Camera cm = new OrtographicCamera();
    	stage= new Stage(new FitViewport(1080, 2210));

		world = new World(new Vector2(0, -10), true);
        fondo = manager.get("cerdo/fondo.png", Texture.class);
        sprite= new Sprite(fondo);

        sprite.setSize(1080, 2210);
		batch = game.batch;
        cochi= new cerditoEntity(
				manager.get("cerdo/oink.mp3", Sound.class), 
				cochiTexture, 
				new Vector2 (5f, 10.5f));
		cochi.setPosition(
				(stage.getWidth() - cochiTexture.getWidth())/2f, 
				(stage.getHeight() - cochiTexture.getHeight())/2f);
		stage.addActor(cochi);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        cochi.remove();
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        sprite.draw(batch);
        batch.end();

        stage.act();
        stage.draw();

    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
        fondo.dispose();
    }

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
	}
}
