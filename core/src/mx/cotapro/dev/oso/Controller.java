package mx.cotapro.dev.oso;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.cotapro.dev.Tutifruti;


public class Controller {
    Viewport viewport;
    Stage stage;
    boolean upPressed, downPressed, leftPressed, rigthPressed;
    OrthographicCamera cam;

    public Controller(final Tutifruti game){
        cam = new OrthographicCamera();
        viewport = new FitViewport(
				Oso.V_WIDTH, 
				Oso.V_HEIGHT, cam);
        stage = new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(stage);
		stage.setDebugAll(true);
        Table table =  new Table();
 		table.bottom().left();

        Image upImage = new Image (new Texture("oso/up.png"));
        upImage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;
            }
        });

        Image downImage = new Image (new Texture("oso/down.png"));
        downImage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = false;
            }
        });

        Image rightImage = new Image (new Texture("oso/right.png"));
    	rightImage.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rigthPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rigthPressed = false;
            }
        });

        Image leftImage = new Image (new Texture("oso/left.png"));
        leftImage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });

        table.add().size(30f).pad(2.5f);
        table.add(upImage).size(30f).pad(2.5f);
        table.add().size(30f).pad(2.5f);
        table.row();
        table.add(leftImage).size(30f).pad(2.5f);
        table.add(downImage).size(30f).pad(2.5f);
        table.add(rightImage).size(30f).pad(2.5f);
        stage.addActor(table);
    }

    public void draw(){
		stage.act();
        stage.draw();
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRigthPressed() {
        return rigthPressed;
    }

    public void resize(int width, int heigth){
        viewport.update(width,heigth);
    }
}
