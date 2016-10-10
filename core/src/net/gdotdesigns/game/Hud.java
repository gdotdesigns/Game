package net.gdotdesigns.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Todd on 10/4/2016.
 */
public class Hud {

    OrthographicCamera camera;
    Viewport viewport;
    Stage stage;
    Skin skin;
    Table table;
    Label label;
    public int score=0;
    public String scoreLabel;

    public Hud(Skin skin, SpriteBatch spriteBatch){
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),camera);
        viewport.apply();
        stage = new Stage(viewport,spriteBatch);
        table= new Table();
        scoreLabel = new String("Enemies Killed: " + score);
        label= new Label(scoreLabel,skin);
        label.setFontScale(3f);
        table.setFillParent(true);
        table.align(Align.topLeft);
        table.add(label).pad(20f);
        stage.addActor(table);

    }

    public void update(float deltaTime){
        stage.act(deltaTime);

    }

    public void draw(float deltaTime){
        stage.draw();
    }

    public  void killCount(){
        score++;
        label.setText("Enemies Killed: " + score);
    }

}
