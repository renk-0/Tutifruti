package mx.cotapro.dev.oso.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import mx.cotapro.dev.oso.Oso;

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private static Integer score;

    private Label countdownLabel;
    private static Label scoreLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label osoLabel;

    public Hud(SpriteBatch sb){
        worldTimer=300;
        timeCount=0;
        score=0;

		OrthographicCamera cam = new OrthographicCamera();
		cam.rotate(90f);
        viewport = new FitViewport(Oso.V_HEIGHT, Oso.V_WIDTH, cam);
        stage = new Stage(viewport, sb);
		Table table = new Table();
        table.top().left();
		table.setPosition((Oso.V_HEIGHT - Oso.V_WIDTH)/2f, (Oso.V_WIDTH - Oso.V_HEIGHT)/2f);
		table.setSize(Oso.V_WIDTH, Oso.V_HEIGHT);

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        osoLabel = new Label("OSO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(osoLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);
    }

    public void update(float dt){
        timeCount+=dt;
        if(timeCount>=1){
            worldTimer--;
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount=0;
        }
    }

    public static void addscore(int value){
        score += value;
        scoreLabel.setText(String.format("%06d", score));

    }


    @Override
    public void dispose() {
        stage.dispose();
    }
}
