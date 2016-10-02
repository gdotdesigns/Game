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
    TextButton start;
    TextButton stop;
    TextureAtlas textureAtlas;

    //boolean loadGame=false;

    public MainMenu(MainGameScreen mainGameScreen,Assets assets, OrthographicCamera camera, SpriteBatch spriteBatch){

        this.mainGameScreen = mainGameScreen;
        this.camera=camera;
        this.spriteBatch=spriteBatch;
        this.assets=assets;

    }

    @Override
    public void show() {
        //TODO add viewport and spriteBatch to Stage. this way spritebatch is not created again.
        stage=new Stage();
        table = new Table();
        table.setFillParent(true);
        table.center();
        textureAtlas=assets.getMenuAtlas();
        skin = assets.getMenuAssets();
        skin.addRegions(textureAtlas);

        start=new TextButton("Start",skin);
        start.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x,float y){
                //assets.unloadMenuAssets();
                //assets.loadGameAssets();
                mainGameScreen.setScreen(new Game(assets, camera, spriteBatch));
                //loadGame=true;
            }
        });


        stop=new TextButton("Stop",skin);
        stop.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x,float y){
                Gdx.app.exit();
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

            //if (assets.manager.update()&& loadGame){
                //mainGameScreen.setScreen(new Game(assets, camera, spriteBatch));
        //}
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        dispose();
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        assets.dispose();
        stage.dispose();

    }
}
