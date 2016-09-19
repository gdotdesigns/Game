package net.gdotdesigns.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Game implements Screen{

	public static final int WIDTH=1920;
	public static final int HEIGHT=1080;
    public static final String TITLE = "Game";
	private static final float WORLD_HEIGHT=9f;

    private static final float BIRD_HEIGHT=1.5f;
	private static final float BIRD_WIDTH=BIRD_HEIGHT*1.305f;

    private static final float ENEMY_BIRD_HEIGHT=1.0f;
    private static final float ENEMY_BIRD_WIDTH=ENEMY_BIRD_HEIGHT*1.28f;
    private static final float ENEMY_SPAWN_TIME =1.0f;

	public static final float BACKGROUND_WIDTH=16f;
	public static final float BACKGROUND_HEIGHT=9f;
    public static final float GRAVITY = -9.8f;


    public  World world;
    public  static Body groundBody;
    public  static Body leftWallBody;
    public  static Body rightWallBody;
    public  static Body topWallBody;

    private  Box2DDebugRenderer debugRenderer;
    private float worldWidth;

    private SpriteBatch batch;
	private TextureAtlas textureAtlas;
    private Array<TextureRegion> playerBird;
    private Array<TextureRegion> enemyBird;
    private Array<TextureRegion> enemyBirdHit;
    public OrthographicCamera cam;
    //private Viewport vp;
    private  ParallaxBackground parallaxBackground;

    private EnemyPool enemyPool;
    private float elapsedTime;


	@Override
	public void show () {
        worldWidth = WORLD_HEIGHT * (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        cam = new OrthographicCamera(worldWidth, WORLD_HEIGHT);
        //cam.setToOrtho(false, WORLD_HEIGHT * (float)Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight(), WORLD_HEIGHT);
        cam.update();
        //vp=new FillViewport(16,9,cam);
        //vp.apply();
        batch = new SpriteBatch();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        loadTextures();
        loadBackground();
        createWorld();
        enemyPool = new EnemyPool(100,100,world,textureAtlas);
        EntityManager.addEntity(new Player(0, 0, BIRD_WIDTH, BIRD_HEIGHT, 1f, .8f, world,playerBird));

        Inputs inputs = new Inputs(cam, world);
        Gdx.input.setInputProcessor(inputs);
        world.setContactListener(new EntityCollision());
    }

    private void loadTextures() {
        textureAtlas = new TextureAtlas("GameAssets.txt");
        enemyBird = new Array<TextureRegion>();
        TextureRegion region1 = textureAtlas.findRegion("frame-1");
        TextureRegion region2 = textureAtlas.findRegion("frame-2");
        TextureRegion region3 = textureAtlas.findRegion("frame-3");
        TextureRegion region4 = textureAtlas.findRegion("frame-4");
        region1.flip(true,false);
        region2.flip(true,false);
        region3.flip(true,false);
        region4.flip(true,false);
        enemyBird.add(region1);
        enemyBird.add(region2);
        enemyBird.add(region3);
        enemyBird.add(region4);
        playerBird = new Array<TextureRegion>();
        playerBird.add(textureAtlas.findRegion("0"));
        playerBird.add(textureAtlas.findRegion("1"));
        playerBird.add(textureAtlas.findRegion("2"));
        playerBird.add(textureAtlas.findRegion("3"));

        enemyBirdHit = new Array<TextureRegion>();
        TextureRegion hitRegion1 = textureAtlas.findRegion("hit-frame-1");
        TextureRegion hitRegion2 = textureAtlas.findRegion("hit-frame-2");
        hitRegion1.flip(true,false);
        hitRegion2.flip(true,false);
        enemyBirdHit.add(hitRegion1);
        enemyBirdHit.add(hitRegion2);
    }

    public Body createStaticBody(float bodyloc_x,float bodyloc_y,float shapesize_x1,float shapesize_y1,float shapesize_x2,float shapesize_y2) {
        Body body;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type= BodyDef.BodyType.StaticBody;
        bodyDef.position.set(bodyloc_x,bodyloc_y);
        body=world.createBody(bodyDef);
        EdgeShape staticShape= new EdgeShape();
        staticShape.set(shapesize_x1,shapesize_y1,shapesize_x2,shapesize_y2);
        FixtureDef groundFictureDef=new FixtureDef();
        groundFictureDef.shape=staticShape;
        body.createFixture(groundFictureDef);
        body.setUserData(body);
        staticShape.dispose();
        return body;
    }

    public void update(float deltaTime) {
        elapsedTime+=deltaTime;
        world.step(1f/60f,6,2);
        EntityManager.destroyEntity(world);

        if(elapsedTime - deltaTime > ENEMY_SPAWN_TIME){
            spawnEnemy();
        }
        EntityManager.update(deltaTime,cam);

    }

    public void spawnEnemy(){
        Enemy enemy = enemyPool.obtain();
        enemy.init(cam.viewportWidth/2f+ENEMY_BIRD_WIDTH, 0, ENEMY_BIRD_WIDTH, ENEMY_BIRD_HEIGHT, 1f, .001f, world, enemyBird,enemyBirdHit,enemyPool);
        EntityManager.addEntity(enemy);
        elapsedTime=0;
    }

    private void createWorld() {
        Box2D.init();
        world = new World(new Vector2(0,GRAVITY),true);
        groundBody=createStaticBody(0,0,-cam.viewportWidth/2f,-cam.viewportHeight/2f,cam.viewportWidth/2f,-cam.viewportHeight/2f);
        //leftWallBody=createStaticBody(0,0,-cam.viewportWidth/2f,-cam.viewportHeight/2f,-cam.viewportWidth/2f,cam.viewportHeight/2f);
        //rightWallBody=createStaticBody(0,0,cam.viewportWidth/2f,-cam.viewportHeight/2f,cam.viewportWidth/2f,cam.viewportHeight/2f);
        topWallBody=createStaticBody(0,0,-cam.viewportWidth/2f,cam.viewportHeight/2f,cam.viewportWidth/2f,cam.viewportHeight/2f);
        debugRenderer=new Box2DDebugRenderer();
        }

    public void loadBackground() {
            ParallaxLayer parallaxLayer1 = new ParallaxLayer(textureAtlas.findRegion("layer_01_1920 x 1080"),new Vector2(2f,0));
            ParallaxLayer parallaxLayer2 = new ParallaxLayer(textureAtlas.findRegion("layer_02_1920 x 1080"),new Vector2(1f,0));
            ParallaxLayer parallaxLayer3 = new ParallaxLayer(textureAtlas.findRegion("layer_03_1920 x 1080"),new Vector2(.90f,0));
            ParallaxLayer parallaxLayer4 = new ParallaxLayer(textureAtlas.findRegion("layer_04_1920 x 1080"),new Vector2(.80f,0));
            ParallaxLayer parallaxLayer5 = new ParallaxLayer(textureAtlas.findRegion("layer_05_1920 x 1080"),new Vector2(.75f,0));
            ParallaxLayer parallaxLayer6 = new ParallaxLayer(textureAtlas.findRegion("layer_06_1920 x 1080"),new Vector2(0,0));
            ParallaxLayer parallaxLayer7 = new ParallaxLayer(textureAtlas.findRegion("layer_07_1920 x 1080"),new Vector2(0f,0));
            ParallaxLayer[] parallaxArray = {parallaxLayer7,parallaxLayer6,parallaxLayer5,parallaxLayer4,parallaxLayer3,parallaxLayer2,parallaxLayer1};
            parallaxBackground = new ParallaxBackground(parallaxArray,batch,new Vector2(5,0),worldWidth,WORLD_HEIGHT);
        }

    @Override
	public void resize(int width, int height) {
		//super.resize(width, height);
		//vp.update(width,height);
        //cam.setToOrtho(false, WORLD_HEIGHT * (float)width / (float)height, WORLD_HEIGHT);
        //cam =new OrthographicCamera(WORLD_HEIGHT * (float)Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight(), WORLD_HEIGHT);
        cam.update();
        batch.setProjectionMatrix(cam.combined);
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
	public void render (float delta) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        update(deltaTime);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Matrix4 debugMatrix=batch.getProjectionMatrix().cpy().scale(1f,1f,0);
		batch.begin();
        parallaxBackground.render(deltaTime);
        EntityManager.render(batch);
        batch.end();
        debugRenderer.render(world,debugMatrix);
	}


	@Override
	public void dispose () {
        EntityManager.dispose();
		batch.dispose();
		textureAtlas.dispose();
        world.dispose();
    }
}
