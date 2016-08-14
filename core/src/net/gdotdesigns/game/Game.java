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
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.gdotdesigns.game.States.GameStateManager;
import net.gdotdesigns.game.States.MenuState;

public class Game extends ApplicationAdapter {

	//public static final int WIDTH=960;
	//public static final int HEIGHT=540;
	public static final int WIDTH=1920;
	public static final int HEIGHT=1080;

	public static final String TITLE = "Game";

	private static final float WORLD_HEIGHT=9f;
	private static final float TEXTURE_WIDTH=1f;
	private static final float TEXTURE_HEIGHT=1f;

    private SpriteBatch batch;
	private TextureAtlas textureAtlas;
	private Animation animation;
	private float elapsedTime;
	private OrthographicCamera cam;
	private Viewport vp;
	private TextureRegion currentframe;
	private static Texture backgroundTexture;




	@Override
	public void create () {
		cam =new OrthographicCamera();
		cam.update();
		//vp=new FillViewport(16,9,cam);
		//vp.apply();
		batch = new SpriteBatch();
		textureAtlas = new TextureAtlas("greenguy.txt");
		animation = new Animation(1/6f,textureAtlas.getRegions());
        Gdx.gl.glClearColor(0, 0,0, 1);
		currentframe = new TextureRegion();
		backgroundTexture = new Texture("background.png");
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		//vp.update(width,height);
        cam.setToOrtho(false, WORLD_HEIGHT * (float)width / (float)height, WORLD_HEIGHT);
        cam.update();
        batch.setProjectionMatrix(cam.combined);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(backgroundTexture,(cam.viewportWidth/2-(16/2f)),(cam.viewportHeight/2-(9/2f)),16,9);
		elapsedTime+=Gdx.graphics.getDeltaTime();
		currentframe = animation.getKeyFrame(elapsedTime,true);
        batch.draw(currentframe,0,0,TEXTURE_WIDTH,TEXTURE_HEIGHT);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		textureAtlas.dispose();

	}
}
