package net.gdotdesigns.game;

import com.badlogic.gdx.Gdx;
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
    MainMenu mainMenu;

    public MainMenu(final MainGameScreen mainGameScreen, final Assets assets, final SpriteBatch spriteBatch){

        this.mainGameScreen = mainGameScreen;
        this.spriteBatch=spriteBatch;
        this.assets=assets;
        this.mainMenu=this;

        camera = new OrthographicCamera();
        //viewport = new FitViewport(1920, 1080,camera); //A ratio did not give correct response, probably due to the stage actors are not images...
        viewport = new ScreenViewport(camera);
        viewport.apply();
        stage=new Stage(viewport,spriteBatch);
        table = new Table();
        skin = assets.getMenuAssets();

        gameTitle = new Label("Title of Game",skin);
        gameTitle.setFontScale(2f);
        start=new TextButton("Play",skin);
        stop=new TextButton("Exit",skin);
        highScore=saveScore.readScore();
        highScoreLabel = new Label("High Score: " + String.valueOf(highScore),skin);
        imageButton1 = new ImageButton(skin.getDrawable("games_achievements_green"),skin.getDrawable("games_achievements"));
        imageButton2 = new ImageButton(skin.getDrawable("games_controller"),skin.getDrawable("games_controller_grey"));
        imageButton3 = new ImageButton(skin.getDrawable("games_leaderboards_green"),skin.getDrawable("games_leaderboards"));
        registerListeners();

        table.setFillParent(true);
        //table.setDebug(true);
        table.add(gameTitle).colspan(3);
        table.row();
;       table.add(start).colspan(3);
        table.row();
        table.add(stop).colspan(3);
        table.row();
        table.add(highScoreLabel).colspan(3);
        table.row();
        table.add(imageButton1);
        table.add(imageButton2);
        table.add(imageButton3);
        stage.addActor(table);
    }

    private void registerListeners() {
        start.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x,float y){
                mainGameScreen.setScreen(new Game(assets,spriteBatch,mainGameScreen,mainMenu));// Couldnt use "this" because this is inside inner class...
                //dispose();
            }
        });


        stop.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x,float y){
                Gdx.app.exit();
            }
        });

        imageButton1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        imageButton2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainGameScreen.googlePlayServices.loginGPGS();
            }
        });

        imageButton3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainGameScreen.googlePlayServices.signOutGPGS();
            }
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        MainGameScreen.googlePlayServices.loginGPGS();

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
        System.out.println("MAINMENU.DISPOSE");
        stage.dispose();
    }
}
