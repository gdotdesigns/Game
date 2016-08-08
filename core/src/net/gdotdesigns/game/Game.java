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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.gdotdesigns.game.States.GameStateManager;
import net.gdotdesigns.game.States.MenuState;

public class Game extends ApplicationAdapter {

    public static final int WIDTH=480;
	public static final int HEIGHT=800;

	public static final String TITLE = "Game";

	private static final int MAX_FPS = 30;
    private static final int MAX_FRAMES_SKIPPED=5;
    private static final int FRAME_PERIOD =1000/MAX_FPS;

	private final float WORLD_WIDTH=100;
	private final float WORLD_HEIGHT=200;

    //private GameStateManager gsm;
    private SpriteBatch batch;
	private TextureAtlas textureAtlas;
	private Animation animation;
	private float elapsedTime;
	private OrthographicCamera cam;
	private Viewport vp;


	@Override
	public void create () {
		float aspectRatio =(float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();
		cam =new OrthographicCamera(aspectRatio*100,aspectRatio*200);
		//cam.position.set(cam.viewportWidth/2,cam.viewportHeight/2,0);
		vp=new ScreenViewport(cam);
		vp.apply();
		batch = new SpriteBatch();
		//GLTexture.setEnforcePotImages(false);
		textureAtlas = new TextureAtlas("greenguy.txt");
		animation = new Animation(1/6f,textureAtlas.getRegions());
        //gsm = new GameStateManager();
        Gdx.gl.glClearColor(0, 0,0, 1);

        //gsm.push(new MenuState(gsm));
	}

	@Override
	public void resize(int width, int height) {
		//super.resize(width, height);
		vp.update(width,height);
		cam.position.set(cam.viewportWidth/2,cam.viewportHeight/2,0);
	}

	@Override
	public void render () {

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //gsm.update(Gdx.graphics.getDeltaTime());
		//gsm.render(batch);
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		elapsedTime+=Gdx.graphics.getDeltaTime();
		batch.draw(animation.getKeyFrame(elapsedTime,true),0,0,0,0,34,43,10,10,90);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		textureAtlas.dispose();

	}
}
