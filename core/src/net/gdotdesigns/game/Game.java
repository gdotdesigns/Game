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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.gdotdesigns.game.States.GameStateManager;
import net.gdotdesigns.game.States.MenuState;

public class Game extends ApplicationAdapter {

    public static final int WIDTH=960;
	public static final int HEIGHT=540;

	public static final String TITLE = "Game";




    private SpriteBatch batch;
	private TextureAtlas textureAtlas;
	private Animation animation;
	private float elapsedTime;
	private OrthographicCamera cam;
	private Viewport vp;
	private TextureRegion currentframe;
	private float width;
	private float height;
	private float aspectRatio;


	@Override
	public void create () {
		aspectRatio =(float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();
		cam =new OrthographicCamera();
		//cam.position.set(cam.viewportWidth/2,cam.viewportHeight/2,0);
		vp=new ScreenViewport(cam);
		vp.apply();
		batch = new SpriteBatch();
		textureAtlas = new TextureAtlas("greenguy.txt");
		animation = new Animation(1/6f,textureAtlas.getRegions());
        Gdx.gl.glClearColor(0, 0,0, 1);
		currentframe = new TextureRegion();



	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		vp.update(width,height);
		//cam.position.set(cam.viewportWidth/2,cam.viewportHeight/2,0);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		elapsedTime+=Gdx.graphics.getDeltaTime();
		currentframe = animation.getKeyFrame(elapsedTime,true);
		width = currentframe.getRegionWidth();
		height= currentframe.getRegionHeight();
		batch.draw(currentframe,0,0,0,0,width,height,1,1,0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		textureAtlas.dispose();

	}
}
