package net.gdotdesigns.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLTexture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.gdotdesigns.game.States.GameStateManager;
import net.gdotdesigns.game.States.MenuState;

import java.util.ArrayList;

public class Game extends ApplicationAdapter {

	public static final int WIDTH=1920;
	public static final int HEIGHT=1080;
    public static final String TITLE = "Game";
	private static final float WORLD_HEIGHT=9f;
	private static final float TEXTURE_WIDTH=2f;
	private static final float TEXTURE_HEIGHT=2f;
	public static final float BACKGROUND_WIDTH=16;
	public static final float BACKGROUND_HEIGHT=9;
    public static final float GRAVITY = -.1f;

    private  World world;
    private  Body body;
    private  BodyDef bodyDef;
    private  PolygonShape shape;
    private  FixtureDef fixtureDef;
    private  Box2DDebugRenderer debugRenderer;
    private  Matrix4 debugMatrix;
    private  float torque = 0.0f;

    private SpriteBatch batch;
    private Sprite sprite;
	private TextureAtlas textureAtlas;
	private Animation animation;
    private OrthographicCamera cam;
    private Viewport vp;
	private float elapsedTime;
    //private float viewportCenterWidth;
    //private float viewportCenterHeight;
    private TextureRegion currentframe;

    //TODO Put these files into a texture atlas.
    private  Texture backgroundTexture1;
    private  Texture backgroundTexture2;
    private  Texture backgroundTexture3;
    private  Texture backgroundTexture4;
    private  Texture backgroundTexture5;
    private  Texture backgroundTexture6;
    private  Texture backgroundTexture7;

    private  ParallaxBackground parallaxBackground;
    private  ParallaxLayer parallaxLayer1;
    private  ParallaxLayer parallaxLayer2;
    private  ParallaxLayer parallaxLayer3;
    private  ParallaxLayer parallaxLayer4;
    private  ParallaxLayer parallaxLayer5;
    private  ParallaxLayer parallaxLayer6;
    private  ParallaxLayer parallaxLayer7;


	@Override
	public void create () {
		cam =new OrthographicCamera();
		cam.update();
		//vp=new FillViewport(16,9,cam);
		//vp.apply();
		batch = new SpriteBatch();
		textureAtlas = new TextureAtlas("monster.txt");
		animation = new Animation(1/7f,textureAtlas.getRegions());
        currentframe = new TextureRegion();
        sprite = new Sprite();
        Gdx.gl.glClearColor(0, 0,0, 1);
        loadBackground();
        createWorld();
}

        private void createWorld() {
            world = new World(new Vector2(0,GRAVITY),true);
            bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.position.set(-TEXTURE_WIDTH/2,-TEXTURE_HEIGHT/2);
            body=world.createBody(bodyDef);
            shape=new PolygonShape();
            shape.setAsBox(TEXTURE_WIDTH*1.305f,TEXTURE_HEIGHT);
            fixtureDef=new FixtureDef();
            fixtureDef.shape=shape;
            fixtureDef.density=1f;
            body.createFixture(fixtureDef);
            debugRenderer=new Box2DDebugRenderer();
        }

    public void loadBackground() {
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
        batch.setProjectionMatrix(cam.combined);
        //viewportCenterWidth=cam.viewportWidth/2;
        //viewportCenterHeight=cam.viewportHeight/2;
	}

	@Override
	public void render () {
        world.step(1f/60f,6,2);
        body.applyTorque(torque,true);
        //sprite.setPosition(body.getPosition().x-sprite.getWidth()/2,body.getPosition().y-sprite.getHeight()/2);

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        parallaxBackground.render(Gdx.graphics.getDeltaTime());
        debugMatrix=batch.getProjectionMatrix().cpy().scale(1f,1f,0);
		batch.begin();
        elapsedTime+=Gdx.graphics.getDeltaTime();
		currentframe = animation.getKeyFrame(elapsedTime,true);
        sprite.setRegion(currentframe);
        //batch.draw(currentframe,sprite.getX(),sprite.getY(),TEXTURE_WIDTH*1.305f,TEXTURE_HEIGHT);
        batch.draw(sprite,body.getPosition().x-sprite.getWidth()/2,body.getPosition().y-sprite.getHeight()/2,sprite.getOriginX(),sprite.getOriginY(),TEXTURE_WIDTH*1.305f,TEXTURE_HEIGHT,1,1,body.getAngle());
		batch.end();
        debugRenderer.render(world,debugMatrix);
	}

	
	@Override
	public void dispose () {
		batch.dispose();
		textureAtlas.dispose();
        shape.dispose();
        world.dispose();
	}
}
