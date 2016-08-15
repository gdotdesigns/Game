package net.gdotdesigns.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLTexture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.gdotdesigns.game.States.GameStateManager;
import net.gdotdesigns.game.States.MenuState;

import java.util.ArrayList;

public class Game extends ApplicationAdapter {

	//public static final int WIDTH=960;
	//public static final int HEIGHT=540;
	public static final int WIDTH=1920;
	public static final int HEIGHT=1080;

	public static final String TITLE = "Game";

	private static final float WORLD_HEIGHT=9f;
	private static final float TEXTURE_WIDTH=2f*1.305f;
	private static final float TEXTURE_HEIGHT=2f;
	public static final float BACKGROUND_WIDTH=16;
	public static final float BACKGROUND_HEIGHT=9;

    private SpriteBatch batch;
	private TextureAtlas textureAtlas;
	private Animation animation;
	private float elapsedTime;
	private OrthographicCamera cam;
	private Viewport vp;
	private TextureRegion currentframe;
	private static Texture backgroundTexture1;
    private static Texture backgroundTexture2;
    private static Texture backgroundTexture3;
    private static Texture backgroundTexture4;
    private static Texture backgroundTexture5;
    private static Texture backgroundTexture6;
    private static Texture backgroundTexture7;
    private static float viewportCenterWidth;
    private static float viewportCenterHeight;
    private static ParallaxBackground parallaxBackground;
    private static ParallaxLayer parallaxLayer1;
    private static ParallaxLayer parallaxLayer2;
    private static ParallaxLayer parallaxLayer3;
    private static ParallaxLayer parallaxLayer4;
    private static ParallaxLayer parallaxLayer5;
    private static ParallaxLayer parallaxLayer6;
    private static ParallaxLayer parallaxLayer7;





	@Override
	public void create () {
		cam =new OrthographicCamera();
		cam.update();
		//vp=new FillViewport(16,9,cam);
		//vp.apply();
		batch = new SpriteBatch();
		textureAtlas = new TextureAtlas("monster.txt");
		animation = new Animation(1/7f,textureAtlas.getRegions());
        Gdx.gl.glClearColor(0, 0,0, 1);
		currentframe = new TextureRegion();

        //TODO Put these files into a texture atlas.
        backgroundTexture1 = new Texture("layer_01_1920 x 1080.png");
        backgroundTexture2 = new Texture("layer_02_1920 x 1080.png");
        backgroundTexture3 = new Texture("layer_03_1920 x 1080.png");
        backgroundTexture4 = new Texture("layer_04_1920 x 1080.png");
        backgroundTexture5 = new Texture("layer_05_1920 x 1080.png");
        backgroundTexture6 = new Texture("layer_06_1920 x 1080.png");
        backgroundTexture7 = new Texture("layer_07_1920 x 1080.png");


        parallaxLayer1 = new ParallaxLayer(backgroundTexture1,new Vector2(2f,0));
        parallaxLayer2 = new ParallaxLayer(backgroundTexture2,new Vector2(1f,0));
        parallaxLayer3 = new ParallaxLayer(backgroundTexture3,new Vector2(.90f,0));
        parallaxLayer4 = new ParallaxLayer(backgroundTexture4,new Vector2(.80f,0));
        parallaxLayer5 = new ParallaxLayer(backgroundTexture5,new Vector2(.75f,0));
        parallaxLayer6 = new ParallaxLayer(backgroundTexture6,new Vector2(0,0));
        parallaxLayer7 = new ParallaxLayer(backgroundTexture7,new Vector2(0f,0));
        ParallaxLayer[] parallaxArray = {parallaxLayer7,parallaxLayer6,parallaxLayer5,parallaxLayer4,parallaxLayer3,parallaxLayer2,parallaxLayer1};
        parallaxBackground = new ParallaxBackground(parallaxArray,cam,batch,new Vector2(1,0));

	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		//vp.update(width,height);
        cam.setToOrtho(false, WORLD_HEIGHT * (float)width / (float)height, WORLD_HEIGHT);
        cam.update();
        viewportCenterWidth=cam.viewportWidth/2;
        viewportCenterHeight=cam.viewportHeight/2;
        batch.setProjectionMatrix(cam.combined);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        parallaxBackground.render(Gdx.graphics.getDeltaTime());
		batch.begin();
        elapsedTime+=Gdx.graphics.getDeltaTime();
		//batch.draw(backgroundTexture,viewportCenterWidth-(BACKGROUND_WIDTH/2f),viewportCenterHeight-(BACKGROUND_HEIGHT/2f),BACKGROUND_WIDTH,BACKGROUND_HEIGHT);
		currentframe = animation.getKeyFrame(elapsedTime,true);
        batch.draw(currentframe,-TEXTURE_WIDTH/2,-TEXTURE_HEIGHT/2,TEXTURE_WIDTH,TEXTURE_HEIGHT);
		batch.end();
	}

	
	@Override
	public void dispose () {
		batch.dispose();
		textureAtlas.dispose();

	}
}
