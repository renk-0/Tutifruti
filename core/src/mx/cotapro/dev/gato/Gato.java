package mx.cotapro.dev.gato;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.Color;

import mx.cotapro.dev.Tutifruti;
import mx.cotapro.dev.selectscreen.Select;

public class Gato implements Screen {
	private final Tutifruti game;
	private Btn[][] stat;
	public AssetManager manager;
	public boolean turno, change;
	public Stage stage;
	private Color bgcolor;
	private Image cross;

	public Gato(final Tutifruti game) {
		this.game = game;
		change = turno = false;
		bgcolor = new Color(0xd5ffb8ff);
		manager = new AssetManager();
		stage = new Stage(new FitViewport(400, 700), game.batch);
		Gdx.input.setInputProcessor(stage);
		loadAssets();
		cross = new Image(manager.get("gato/gto.png", Texture.class));
		cross.setSize(300, 300);
		cross.setPosition(50, 200);
		stage.addActor(cross);
		makeTable();
	}

	private void makeTable() {
		stat = new Btn[3][3];
		Table table = new Table();
		table.setPosition(200, 350);
		table.debug();
		table.align(Align.center);
		for(int i = 0; i < stat.length; i++) {
			table.row();
			for(int j = 0; j < stat[i].length; j++) {
				stat[i][j] = new Btn(this);
				table.add(stat[i][j])
					.size(80f)
					.pad(10f);
			}
		}
		stage.addActor(table);
	}

	private void loadAssets() {
		manager.load("gato/X.png", Texture.class);
		manager.load("gato/rueda.png", Texture.class);
		manager.load("gato/gto.png", Texture.class);
		manager.finishLoading();
	}

	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(bgcolor.r, bgcolor.g, bgcolor.b, bgcolor.a);
		stage.act(delta);
		stage.draw();
		if(!change)
			return;
		boolean match = false;
		change = false;

		for(int i = 0; i < 3; i++) {
			boolean cstat;
			cstat = stat[i][0].status && stat[i][1].status && stat[i][2].status;
			if(cstat && stat[i][0].user != 0) {
				if((stat[i][0].user == stat[i][1].user) && (stat[i][1].user == stat[i][2].user)) {
					match = true;
					break;
				}
			}
		}
		if(match) {
			game.setScreen(new Select(game));
			return;
		}
		for(int i = 0; i < 3; i++) {
			boolean rstat;
			rstat = stat[0][i].status && stat[1][i].status && stat[2][i].status;
			if (rstat && stat[0][i].user != 0) {
				if(stat[0][i].user == stat[1][i].user && stat[1][i].user == stat[2][i].user) {
					match = true;
					break;
				}
			}
		}
		if(match) {
			game.setScreen(new Select(game));
			return;
		}

		if(stat[0][0].user == stat[1][1].user && stat[1][1].user == stat[2][2].user
		&& stat[0][0].status && stat[1][1].status && stat[2][2].status) {
			game.setScreen(new Select(game));
			return;
		} else if(stat[0][2].user == stat[1][1].user && stat[1][1].user == stat[2][0].user
		&& stat[0][2].status && stat[1][1].status && stat[2][0].status) {
			game.setScreen(new Select(game));
			return;
		}
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
