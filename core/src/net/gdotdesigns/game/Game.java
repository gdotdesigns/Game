package net.gdotdesigns.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Game implements Screen{

    private float playerBirdHeight =1.5f;
	private float playerBirdWidth = playerBirdHeight *1.305f;

    private static final float ENEMY_BIRD_HEIGHT=1.0f;
    private static final float ENEMY_BIRD_WIDTH=ENEMY_BIRD_HEIGHT*1.28f;
    private static final float ENEMY_SPAWN_TIME =1.0f;

    public static float backgroundWidth;
	public static final float BACKGROUND_HEIGHT=MainGameScreen.WORLD_HEIGHT;
    public static final float GRAVITY = -9.8f;

    public  World world;
    public  static Body groundBody;
    public  static Body leftWallBody;
    public  static Body rightWallBody;
    public  static Body topWallBody;

    private Box2DDebugRenderer debugRenderer;
    private Assets assets;
    private SpriteBatch spriteBatch;
    private Array<TextureRegion> playerBird;
    private Array<TextureRegion> enemyBird;
    private Array<TextureRegion> enemyBirdHit;
    public OrthographicCamera camera;
    private  ParallaxBackground parallaxBackground;
    private EntityManager entityManager;
    private Hud hud;
    SaveScore saveScore;
    InputMultiplexer inputMultiplexer;
    Inputs inputs;
    private boolean gameRunning=true;
    float deltaTime;

    private EnemyPool enemyPool;
    private float elapsedTime;
    private Skin skin;
    Viewport viewport;

    MainGameScreen mainGameScreen;
    MainMenu mainMenu;

    public Game(Assets assets,SpriteBatch spriteBatch,MainGameScreen mainGameScreen,MainMenu mainMenu){
        this.assets=assets;
        this.camera= new OrthographicCamera();
        this.spriteBatch=spriteBatch;
        this.entityManager=new EntityManager();//Made an object instead of a static class due to Android issues with glitched textures...
        saveScore = new SaveScore();
        this.mainGameScreen=mainGameScreen;
        this.mainMenu=mainMenu;
    }


	@Override
	public void show () {
        camera.setToOrtho(false);
        float ratio=(float)Gdx.graphics.getWidth()/(float)Gdx.graphics.getHeight();
        backgroundWidth =BACKGROUND_HEIGHT*ratio;
        viewport = new FitViewport(MainGameScreen.WORLD_HEIGHT * ratio, MainGameScreen.WORLD_HEIGHT,camera);
        viewport.apply(); //You must viewport.apply(), or viewport does not function...
        camera.position.set(0,0,0);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        loadTextures();
        loadBackground();
        hud = new Hud(skin,spriteBatch,this);
        createWorld();
        enemyPool = new EnemyPool(10,10);
        Player player = new Player(0, 0, playerBirdWidth, playerBirdHeight, 1f, .8f, world,playerBird);
        //entityManager.addEntity(new Player(0, 0, playerBirdWidth, playerBirdHeight, 1f, .8f, world,playerBird));
        entityManager.addEntity(player);
        player.getBody().setGravityScale(0f);
        inputMultiplexer = new InputMultiplexer();
        inputs = new Inputs(camera, world,viewport);
        inputMultiplexer.addProcessor(hud.stage);
        inputMultiplexer.addProcessor(inputs);
        Gdx.input.setInputProcessor(inputMultiplexer);
        world.setContactListener(new EntityCollision(entityManager,this));
    }



    public  void gameOver(){
        gameRunning=false;
        hud.gameOver();
        inputMultiplexer.removeProcessor(inputs);
        System.out.println("SAVING SCORE");
        saveScore.writeScore(hud.score);
    }

    public void quitGame(){
        mainGameScreen.setScreen(mainMenu); //Saving reference to mainMenu, so I dont have to reintialize...
        dispose();
    }

    public void playAgain(){
        mainGameScreen.setScreen(new Game(assets,spriteBatch,mainGameScreen,mainMenu));
        dispose();
    }

    @Override
    public void render (float delta) {
        //TODO Add delta time to the physics movement and travel to accomodate for different framerates.
            camera.update();
            if(gameRunning) {
                deltaTime = Gdx.graphics.getDeltaTime();
                update(deltaTime);
            }
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            Matrix4 debugMatrix = spriteBatch.getProjectionMatrix().cpy().scale(1f, 1f, 0);
            spriteBatch.begin();
            if(gameRunning) {
                parallaxBackground.render(deltaTime);
            }
            else {
                parallaxBackground.render(0f);
            }

            spriteBatch.setProjectionMatrix(camera.combined);
            camera.update();
            entityManager.render(spriteBatch);
            spriteBatch.end();
            hud.update(delta);
            hud.draw(delta);
            debugRenderer.render(world, debugMatrix);
    }

    public void update(float deltaTime) {
        elapsedTime+=deltaTime;
        world.step(1f / 60f, 6, 2);
        entityManager.destroyEntity(world);

        if(elapsedTime - deltaTime > ENEMY_SPAWN_TIME){
            spawnEnemy();
        }
        entityManager.update(deltaTime, camera);
    }

    public void spawnEnemy(){
        Enemy enemy = enemyPool.obtain();
        enemy.init(camera.viewportWidth/2f+ENEMY_BIRD_WIDTH, 0, ENEMY_BIRD_WIDTH, ENEMY_BIRD_HEIGHT, 1f, .001f, world, enemyBird,enemyBirdHit,enemyPool,hud);
        entityManager.addEntity(enemy);
        elapsedTime=0;
    }


    private void loadTextures() {
            skin = assets.getMenuAssets();
            enemyBird = new Array<TextureRegion>();
            TextureRegion region1 = skin.getRegion("frame-1");
            TextureRegion region2 = skin.getRegion("frame-2");
            TextureRegion region3 = skin.getRegion("frame-3");
            TextureRegion region4 = skin.getRegion("frame-4");
            if(!region1.isFlipX()) {     //This keeps the textureregion from flipping again, since this persists through the restart of the screen...
                region1.flip(true, false);
                region2.flip(true, false);
                region3.flip(true, false);
                region4.flip(true, false);
            }
            enemyBird.add(region1);
            enemyBird.add(region2);
            enemyBird.add(region3);
            enemyBird.add(region4);

            playerBird = new Array<TextureRegion>();
            playerBird.add(skin.getRegion("0"));
            playerBird.add(skin.getRegion("1"));
            playerBird.add(skin.getRegion("2"));
            playerBird.add(skin.getRegion("3"));

            enemyBirdHit = new Array<TextureRegion>();
            TextureRegion hitRegion1 = skin.getRegion("hit-frame-1");
            TextureRegion hitRegion2 = skin.getRegion("hit-frame-2");
            if(!hitRegion1.isFlipX()) {
                hitRegion1.flip(true, false);
                hitRegion2.flip(true, false);
            }
            enemyBirdHit.add(hitRegion1);
            enemyBirdHit.add(hitRegion2);
    }

    public void loadBackground() {
        ParallaxLayer parallaxLayer1 = new ParallaxLayer(skin.getRegion("layer_01_1920 x 1080"),new Vector2(2f,0));
        ParallaxLayer parallaxLayer2 = new ParallaxLayer(skin.getRegion("layer_02_1920 x 1080"),new Vector2(1f,0));
        ParallaxLayer parallaxLayer3 = new ParallaxLayer(skin.getRegion("layer_03_1920 x 1080"),new Vector2(.90f,0));
        ParallaxLayer parallaxLayer4 = new ParallaxLayer(skin.getRegion("layer_04_1920 x 1080"),new Vector2(.80f,0));
        ParallaxLayer parallaxLayer5 = new ParallaxLayer(skin.getRegion("layer_05_1920 x 1080"),new Vector2(.75f,0));
        ParallaxLayer parallaxLayer6 = new ParallaxLayer(skin.getRegion("layer_06_1920 x 1080"),new Vector2(0,0));
        ParallaxLayer parallaxLayer7 = new ParallaxLayer(skin.getRegion("layer_07_1920 x 1080"),new Vector2(0f,0));
        ParallaxLayer[] parallaxArray = {parallaxLayer7,parallaxLayer6,parallaxLayer5,parallaxLayer4,parallaxLayer3,parallaxLayer2,parallaxLayer1};
        parallaxBackground = new ParallaxBackground(parallaxArray, spriteBatch,new Vector2(5,0));
    }

    private void createWorld() {
        Box2D.init();
        world = new World(new Vector2(0,GRAVITY),true);
        groundBody=createStaticBody(0f,0f,-camera.viewportWidth/2f,-camera.viewportHeight/2f,camera.viewportWidth,-camera.viewportHeight/2f);
        //leftWallBody=createStaticBody(0,0,-camera.viewportWidth/2f,-camera.viewportHeight/2f,-camera.viewportWidth/2f,camera.viewportHeight/2f);
        //rightWallBody=createStaticBody(0,0,camera.viewportWidth/2f,-camera.viewportHeight/2f,camera.viewportWidth/2f,camera.viewportHeight/2f);
        topWallBody=createStaticBody(0f,0f,-camera.viewportWidth/2f,camera.viewportHeight/2f,camera.viewportWidth/2f,camera.viewportHeight/2f);

        debugRenderer=new Box2DDebugRenderer();
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



    @Override
	public void resize(int width, int height) {
        viewport.update(width ,height);  //You must update viewport in resize or viewport will not function...
        parallaxBackground.viewport.update(width,height);
        hud.stage.getViewport().update(width,height);
    }


    @Override
    public void pause() {
        System.out.println("GAME.PAUSE");
        if(gameRunning) {
            gameRunning = false;
            hud.pause();
            inputMultiplexer.removeProcessor(inputs);
        }
    }

    public void gameResume(){
        if(!gameRunning) {
            gameRunning = true;
            inputMultiplexer.addProcessor(inputs);
        }
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

	@Override
	public void dispose () {
        System.out.println("GAME.DISPOSE");
        world.dispose();
        entityManager.dispose();
        debugRenderer.dispose();
        enemyPool.clear();
        hud.stage.dispose();
    }
}
