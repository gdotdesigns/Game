package net.gdotdesigns.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Game extends ApplicationAdapter{

	public static final int WIDTH=1920;
	public static final int HEIGHT=1080;
    public static final String TITLE = "Game";
	private static final float WORLD_HEIGHT=9f;
    private static final float BIRD_HEIGHT=2f;
	private static final float BIRD_WIDTH=BIRD_HEIGHT*1.305f;
	public static final float BACKGROUND_WIDTH=16f;
	public static final float BACKGROUND_HEIGHT=9f;
    public static final float GRAVITY = -9.8f;

    public  World world;
    public   Body groundBody;
    public   Body leftWallBody;
    public  Body rightWallBody;
    public  static Body topWallBody;

    private EdgeShape staticShape;
    private  Box2DDebugRenderer debugRenderer;
    //public static float torque = -9.0f;
    private float worldWidth;

    private SpriteBatch batch;
	private TextureAtlas textureAtlas;
    public OrthographicCamera cam;
    //private Viewport vp;
	private float elapsedTime;


    //TODO Put these files into a texture atlas.
    private  Texture backgroundTexture1;
    private  Texture backgroundTexture2;
    private  Texture backgroundTexture3;
    private  Texture backgroundTexture4;
    private  Texture backgroundTexture5;
    private  Texture backgroundTexture6;
    private  Texture backgroundTexture7;

    private  ParallaxBackground parallaxBackground;


	@Override
	public void create () {
        worldWidth = WORLD_HEIGHT * (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        cam = new OrthographicCamera(worldWidth, WORLD_HEIGHT);
        //cam.setToOrtho(false, WORLD_HEIGHT * (float)Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight(), WORLD_HEIGHT);
        cam.update();
        //vp=new FillViewport(16,9,cam);
        //vp.apply();
        batch = new SpriteBatch();

        textureAtlas = new TextureAtlas("monster.txt");

        Gdx.gl.glClearColor(0, 0, 0, 1);
        loadBackground();
        createWorld();

        EntityManager.add(new Player(0, 0, BIRD_WIDTH / 2f, BIRD_HEIGHT / 2f, 1f, .8f, world,textureAtlas));
        EntityManager.add(new Player(-BIRD_WIDTH * 2f, 0, BIRD_WIDTH / 2f, BIRD_HEIGHT / 2f, 1f, .8f, world,textureAtlas));

        Inputs inputs = new Inputs(cam, world);
        Gdx.input.setInputProcessor(inputs);
        world.setContactListener(new EntityCollision());
    }

    public Body createStaticBody(float bodyloc_x,float bodyloc_y,float shapesize_x1,float shapesize_y1,float shapesize_x2,float shapesize_y2) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type= BodyDef.BodyType.StaticBody;
        bodyDef.position.set(bodyloc_x,bodyloc_y);
        body=world.createBody(bodyDef);
        staticShape= new EdgeShape();
        staticShape.set(shapesize_x1,shapesize_y1,shapesize_x2,shapesize_y2);
        FixtureDef groundFictureDef=new FixtureDef();
        groundFictureDef.shape=staticShape;
        body.createFixture(groundFictureDef);
        body.setUserData(body);
        return body;
    }

    public void update(float elapsedTime) {
        world.step(1f/60f,6,2);
    }

    private void createWorld() {
        Box2D.init();
        world = new World(new Vector2(0,GRAVITY),true);
        groundBody=createStaticBody(0,0,-cam.viewportWidth/2f,-2f,cam.viewportWidth/2f,-2f);
        leftWallBody=createStaticBody(0,0,-cam.viewportWidth/2f,-cam.viewportHeight/2f,-cam.viewportWidth/2f,cam.viewportHeight/2f);
        rightWallBody=createStaticBody(0,0,cam.viewportWidth/2f,-cam.viewportHeight/2f,cam.viewportWidth/2f,cam.viewportHeight/2f);
        topWallBody=createStaticBody(0,0,-cam.viewportWidth/2f,cam.viewportHeight/2f,cam.viewportWidth/2f,cam.viewportHeight/2f);
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


            ParallaxLayer parallaxLayer1 = new ParallaxLayer(backgroundTexture1,new Vector2(2f,0));
            ParallaxLayer parallaxLayer2 = new ParallaxLayer(backgroundTexture2,new Vector2(1f,0));
            ParallaxLayer parallaxLayer3 = new ParallaxLayer(backgroundTexture3,new Vector2(.90f,0));
            ParallaxLayer parallaxLayer4 = new ParallaxLayer(backgroundTexture4,new Vector2(.80f,0));
            ParallaxLayer parallaxLayer5 = new ParallaxLayer(backgroundTexture5,new Vector2(.75f,0));
            ParallaxLayer parallaxLayer6 = new ParallaxLayer(backgroundTexture6,new Vector2(0,0));
            ParallaxLayer parallaxLayer7 = new ParallaxLayer(backgroundTexture7,new Vector2(0f,0));
            ParallaxLayer[] parallaxArray = {parallaxLayer7,parallaxLayer6,parallaxLayer5,parallaxLayer4,parallaxLayer3,parallaxLayer2,parallaxLayer1};
            parallaxBackground = new ParallaxBackground(parallaxArray,batch,new Vector2(5,0),worldWidth,WORLD_HEIGHT);
        }

    @Override
	public void resize(int width, int height) {
		super.resize(width, height);
		//vp.update(width,height);
        //cam.setToOrtho(false, WORLD_HEIGHT * (float)width / (float)height, WORLD_HEIGHT);
        //cam =new OrthographicCamera(WORLD_HEIGHT * (float)Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight(), WORLD_HEIGHT);
        cam.update();
        batch.setProjectionMatrix(cam.combined);
	}

	@Override
	public void render () {
        elapsedTime+=Gdx.graphics.getDeltaTime();
        update(elapsedTime);
        EntityManager.update(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        parallaxBackground.render(Gdx.graphics.getDeltaTime());
        Matrix4 debugMatrix=batch.getProjectionMatrix().cpy().scale(1f,1f,0);
		batch.begin();
        EntityManager.render(batch);
        batch.end();
        debugRenderer.render(world,debugMatrix);
	}


	@Override
	public void dispose () {
		batch.dispose();
		textureAtlas.dispose();
        world.dispose();
        staticShape.dispose();
        backgroundTexture1.dispose();
        backgroundTexture2.dispose();
        backgroundTexture3.dispose();
        backgroundTexture4.dispose();
        backgroundTexture5.dispose();
        backgroundTexture6.dispose();
        backgroundTexture7.dispose();

    }
}
