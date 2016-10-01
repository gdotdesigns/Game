package net.gdotdesigns.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

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
    TextureAtlas textureAtlas;
    TextButton start;
    TextButton stop;

    public MainMenu(MainGameScreen mainGameScreen,Assets assets, OrthographicCamera camera, SpriteBatch spriteBatch){

        this.mainGameScreen = mainGameScreen;
        this.camera=camera;
        this.spriteBatch=spriteBatch;
        this.assets=assets;

    }

    @Override
    public void show() {
        stage=new Stage();
        table = new Table();
        table.setFillParent(true);
        table.center();
        textureAtlas = new TextureAtlas("neon-ui.atlas");
        skin = new Skin(Gdx.files.internal("neon-ui.json"),textureAtlas);

        start=new TextButton("Start",skin);
        //start.setWidth(100f);
        //start.setHeight(100f);
        //start.setPosition(Gdx.graphics.getWidth()/2f-start.getWidth()/2,Gdx.graphics.getHeight()/2-start.getHeight()/2);
        start.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x,float y){
                mainGameScreen.setScreen(new Game(assets, camera, spriteBatch));
                dispose();

            }
        });


        stop=new TextButton("Stop",skin);
        //stop.setWidth(100f);
        //stop.setHeight(100f);
        //stop.setPosition(Gdx.graphics.getWidth()/2f-start.getWidth()/2,Gdx.graphics.getHeight()/2-start.getHeight()/2);
        stop.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x,float y){
                Gdx.app.exit();
                dispose();

            }
        });

        table.add(start);
        table.row();
        table.add(stop);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        skin.dispose();
        textureAtlas.dispose();
    }
}
