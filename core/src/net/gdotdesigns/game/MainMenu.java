package net.gdotdesigns.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
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
    Viewport viewport;

    public MainMenu(MainGameScreen mainGameScreen,Assets assets, OrthographicCamera camera, SpriteBatch spriteBatch){

        this.mainGameScreen = mainGameScreen;
        this.camera=camera;
        this.spriteBatch=spriteBatch;
        this.assets=assets;

    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),camera);
        viewport.apply();
        stage=new Stage(viewport,spriteBatch);
        table = new Table();
        skin = assets.getMenuAssets();
        skin.addRegions(assets.getMenuAtlas());

        gameTitle = new Label("Title of Game",skin);
        gameTitle.setFontScale(8f);

        start=new TextButton("Play",skin);
        start.getLabel().setFontScale(3f);
        //TODO generate new fonts for atlas to eliminate need for scaling

        start.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x,float y){
                mainGameScreen.setScreen(new Game(assets, camera, spriteBatch));
                dispose();
            }
        });


        stop=new TextButton("Exit",skin);
        stop.getLabel().setFontScale(3f);
        stop.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x,float y){
                Gdx.app.exit();
            }
        });

        imageButton1 = new ImageButton(skin.getDrawable("games_achievements_green"),skin.getDrawable("games_achievements"));
        imageButton2 = new ImageButton(skin.getDrawable("games_achievements_green"),skin.getDrawable("games_achievements"));
        imageButton3 = new ImageButton(skin.getDrawable("games_achievements_green"),skin.getDrawable("games_achievements"));


        table.setFillParent(true);
        table.setDebug(true);
        table.add(gameTitle).colspan(3);
        table.row();
        table.add(start).width(400f).pad(50f).colspan(3);
        table.row();
        table.add(stop).width(400f).pad(50f).colspan(3);
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
