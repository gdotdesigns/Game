package net.gdotdesigns.game;

import com.badlogic.gdx.Gdx;
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
        viewport = new FitViewport(1920,1080,camera);
        viewport.apply();
        stage = new Stage(viewport,spriteBatch);
        table= new Table();
        scoreLabel = new String("Enemies Killed: " + score);
        label= new Label(scoreLabel,skin);
        //label.setFontScale(6f); //When setting the scale of the font in skin(MainMenu), it changes the size for all usages afterwards...
        table.setFillParent(true);
        table.align(Align.topLeft);
        table.add(label).pad(10f);
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