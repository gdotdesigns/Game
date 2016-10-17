package net.gdotdesigns.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Todd on 9/27/2016.
 */
public class MainMenu implements Screen{

    MainGameScreen mainGameScreen;
    OrthographicCamera camera;
    SpriteBatch spriteBatch;
    Assets assets;
    Stage stage;
    Table table;
    Skin skin;
    TextButton start;
    TextButton stop;
    ImageButton imageButton1;
    ImageButton imageButton2;
    ImageButton imageButton3;

    Label gameTitle;
    int highScore;
    Label highScoreLabel;
    Viewport viewport;
    SaveScore saveScore = new SaveScore();

    public MainMenu(MainGameScreen mainGameScreen,Assets assets, OrthographicCamera camera, SpriteBatch spriteBatch){

        this.mainGameScreen = mainGameScreen;
        this.camera=camera;
        this.spriteBatch=spriteBatch;
        this.assets=assets;

    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        //viewport = new FitViewport(1920, 1080,camera); //A ratio did not give correct response, probably due to the stage actors are not images...
        viewport = new ScreenViewport(camera);
        viewport.apply();
        stage=new Stage(viewport,spriteBatch);
        table = new Table();
        skin = assets.getMenuAssets();
        //TODO find formula to scale fonts to screen size.
        //TODO 9-patch buttons are not wide enough by default for the text
        //TODO figure out how to load fonts with mip-maping MipMapLinear, Linear to help with text blurriness when scaling

        gameTitle = new Label("Title of Game",skin);
        gameTitle.setFontScale(2f);
        start=new TextButton("Play",skin);
        //start.getLabelCell().padBottom(40f);


        start.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x,float y){
                mainGameScreen.setScreen(new Game(assets, camera, spriteBatch));
                dispose();
            }
        });


        stop=new TextButton("Exit",skin);
        //stop.getLabelCell().padBottom(30f);
        stop.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x,float y){
                Gdx.app.exit();
            }
        });

        highScore=saveScore.readScore();
        highScoreLabel = new Label("High Score: " + String.valueOf(highScore),skin);

        imageButton1 = new ImageButton(skin.getDrawable("games_achievements_green"),skin.getDrawable("games_achievements"));
        imageButton2 = new ImageButton(skin.getDrawable("games_controller"),skin.getDrawable("games_controller_grey"));
        imageButton3 = new ImageButton(skin.getDrawable("games_leaderboards_green"),skin.getDrawable("games_leaderboards"));


        table.setFillParent(true);
        table.setDebug(true);
        table.add(gameTitle).colspan(3);
        table.row();
        table.add(start).colspan(3).fillX().uniform();
        table.row();
        table.add(stop).colspan(3).fillX().uniform();
        table.row();
        table.add(highScoreLabel).colspan(3);
        table.row();
        table.add(imageButton1);
        table.add(imageButton2);
        table.add(imageButton3);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,true);
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
        stage.dispose();
    }
}
