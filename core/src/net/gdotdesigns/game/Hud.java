package net.gdotdesigns.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Todd on 10/4/2016.
 */
public class Hud {

    OrthographicCamera camera;
    Viewport viewport;
    Stage stage;
    Skin skin;
    SpriteBatch spriteBatch;
    Table table;
    Label label;
    Label gameOver;
    Label fps;
    TextButton menu;
    TextButton playAgain;
    TextButton resume;
    public int score=0;
    public int calculateFrameRate;
    public String scoreLabel;
    public String frameRate;
    Game game;


    public Hud(Skin skin, SpriteBatch spriteBatch,Game game){
        this.skin=skin;
        this.spriteBatch = spriteBatch;
        this.game=game;
        camera = new OrthographicCamera();
        //viewport = new FitViewport(1920,1080,camera);
        viewport = new ScreenViewport(camera);
        viewport.apply();
        stage = new Stage(viewport,spriteBatch);
        table= new Table();
        scoreLabel = new String("Enemies Killed: " + score);
        frameRate= new String("Frame Rate: " + calculateFrameRate);
        label= new Label(scoreLabel,skin);
        fps=new Label(frameRate,skin);
        table.debug();
        table.setFillParent(true);
        table.align(Align.top);
        table.add(label).padBottom(30f).padLeft(15f).align(Align.left).expandX();
        table.add(fps).padBottom(30f).padRight(15f).align(Align.right).expandX();
        stage.addActor(table);
    }

    public void gameOver(){
        //TODO Make textbutton auto span the width of the text.
        table.clearChildren();
        table.setFillParent(true);
        table.center();
        gameOver = new Label("Game Over",skin);
        gameOver.setFontScale(2f);
        menu = new TextButton("Menu",skin);
        menu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.quitGame();
            }
        });
        playAgain = new TextButton("PLAY",skin);
        playAgain.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.playAgain();
            }
        });
        table.add(gameOver).colspan(2);
        table.row();
        table.add(menu).width(200f);
        table.add(playAgain).width(200f);
    }

    public void pause(){
        resume= new TextButton("RESUME",skin);
        resume.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.gameResume();
                table.removeActor(resume);
            }
        });
        table.add(resume).colspan(2).center();
    }


    public void update(float deltaTime){
        stage.act(deltaTime);
        calculateFrameRate=Gdx.graphics.getFramesPerSecond();
    }

    public void draw(float deltaTime){
        stage.draw();
        fps.setText(frameRate + calculateFrameRate);
    }


    public  void killCount(){
        score++;
        label.setText(scoreLabel + score);

    }

}
