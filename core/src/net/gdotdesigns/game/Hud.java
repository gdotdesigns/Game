package net.gdotdesigns.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
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
    Label scoreValueLabel;
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
        table= new Table(skin);

        scoreLabel = new String("Score:");
        frameRate= new String("Frame Rate: " + calculateFrameRate);
        label= new Label(scoreLabel,skin);
        scoreValueLabel = new Label(String.valueOf(" " + score),skin);
        fps=new Label(frameRate,skin);
        resume= new TextButton("RESUME",skin);
        gameOver = new Label("Game Over",skin);
        gameOver.setFontScale(2f);
        menu = new TextButton("Menu",skin);
        playAgain = new TextButton("PLAY",skin);
        registerListeners();

        //menuTable.debug();
        table.setFillParent(true);
        table.align(Align.top);
        table.add(label).padLeft(Value.percentWidth(.05f)).align(Align.left);
        table.add(scoreValueLabel).align(Align.left).expandX();
        table.add(fps).padRight(Value.percentWidth(.05f)).align(Align.right).expandX();
;       stage.addActor(table);
    }

    private void registerListeners() {
        resume.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resume.setOrigin(Align.center);
                resume.setClip(true);
                resume.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(resume.getX(),-stage.getViewport().getScreenHeight()/2f,.5f, Interpolation.Elastic.swingIn)),Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        resume.remove();
                        game.gameResume();
                        //resume.addAction(Actions.sequence(Actions.scaleTo(1,1),Actions.rotateTo(0f))); //only need to reset actions if changing scale or rotation
                    }
                })));
            }
        });

        menu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.quitGame();
            }
        });


        playAgain.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.playAgain();
            }
        });

    }

    public void gameOver(){
        table.clearChildren();
        table.setFillParent(true);
        table.center();
        table.add(gameOver).colspan(2);
        table.row();
        table.add(label).align(Align.right);
        table.add(scoreValueLabel).align(Align.left);
        table.row();
        table.add(menu);
        table.add(playAgain);
    }

    public void pause(){
        stage.addActor(resume);
        resume.setPosition(stage.getViewport().getWorldWidth()/2f-resume.getWidth()/2f,stage.getViewport().getWorldHeight()/2f-resume.getHeight()/2f);
    }


    public void update(float deltaTime){
        stage.act(deltaTime);
        calculateFrameRate=Gdx.graphics.getFramesPerSecond();
    }

    public void draw(float deltaTime){
        spriteBatch.setProjectionMatrix(camera.combined);
        camera.update();
        stage.draw();
        fps.setText(frameRate + calculateFrameRate);
    }


    public  void killCount(){
        score++;
        scoreValueLabel.setText(String.valueOf(" " + score));
    }

}
