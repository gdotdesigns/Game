package net.gdotdesigns.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Todd on 9/9/2016.
 */
public class Enemy extends Entity implements Pool.Poolable{

    Body body;
    BodyDef bodyDef;
    FixtureDef fixtureDef;
    PolygonShape shape;
    World world;
    EnemyPool pool;
    TextureRegion currentFrame;
    Array<TextureRegion> enemyBird;
    Array<TextureRegion> enemyBirdHit;
    Animation animation;
    Animation hitAnimation;
    Sprite sprite;
    Vector2 enemyBirdVector;
    Vector2 bodyPosition;
    Fixture temp;
    float bodyloc_x;
    float bodyloc_y;
    float shapesize_x,shapesize_y;
    float density;
    float restitution;
    float elapsedTime;
    float timeToFlap = MathUtils.random(0.6f,.8f);
    float flapHeight = MathUtils.random(2.5f,9f);
    float flapTimer;
    boolean isAlive;
    Hud hud;



    public void init(float bodyloc_x, float bodyloc_y, float shapesize_x, float shapesize_y, float density, float restitution, World world, Array<TextureRegion> enemyBird,Array<TextureRegion> enemyBirdHit, EnemyPool pool,Hud hud){
        this.bodyloc_x = bodyloc_x;
        this.bodyloc_y =bodyloc_y;
        this.shapesize_x= shapesize_x;
        this.shapesize_y=shapesize_y;
        this.density=density;
        this.restitution=restitution;
        this.world=world;
        this.pool=pool;
        this.enemyBird =enemyBird;
        this.enemyBirdHit =enemyBirdHit;
        animation = new Animation(1 / 7f, enemyBird);
        hitAnimation = new Animation(1 / 7f, enemyBirdHit);
        sprite=new Sprite();
        sprite.setSize(shapesize_x,shapesize_y);
        sprite.setOriginCenter();
        sprite.setScale(1f,1f);
        enemyBirdVector = new Vector2();
        createDynamicBody();
        this.hud=hud;
    }

    private void createDynamicBody() {
        bodyDef = new BodyDef();
        bodyDef.type= BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(bodyloc_x,bodyloc_y);
        body=world.createBody(bodyDef);
        body.setUserData(this);
        shape = new PolygonShape();
        shape.setAsBox(shapesize_x/2f,shapesize_y/2f);
        fixtureDef=new FixtureDef();
        fixtureDef.shape=shape;
        fixtureDef.density=density;
        fixtureDef.restitution=restitution;
        //fixtureDef.isSensor=true;
        body.createFixture(fixtureDef);
        body.setFixedRotation(true);
        body.setLinearVelocity(-5f, 0f);
        shape.dispose();
    }

    @Override
    public Body getBody() {
        return body;
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public void setAlive() {
        isAlive=true;
    }

    @Override
    public void setDead() {
        if(isAlive) {
            temp = body.getFixtureList().first();
            temp.setSensor(true);
            isAlive = false;
            body.setGravityScale(3f);
            body.setLinearVelocity(-3f,12f);
            hud.killCount();
        }
    }

    @Override
    public Vector2 findEntityLocation() {
        enemyBirdVector.x = body.getPosition().x+shapesize_x/2f;
        enemyBirdVector.y = body.getPosition().y+shapesize_y/2f;
        return enemyBirdVector;
    }

    @Override
    public void freeEntity() {
        pool.free(this);
    }

    @Override
    public void update(float deltaTime) {
        if(isAlive) {
            currentFrame = animation.getKeyFrame(elapsedTime, true);
        }
        else{
            currentFrame = hitAnimation.getKeyFrame(elapsedTime, true);
        }
        sprite.setRegion(currentFrame);
        sprite.setPosition(body.getPosition().x-shapesize_x/2f,body.getPosition().y-shapesize_y/2f);
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
        flapTimer+= deltaTime;
        elapsedTime+= deltaTime;
        if(isAlive && flapTimer>=timeToFlap){
            body.setLinearVelocity(-5f,flapHeight);
            flapTimer=0;
            timeToFlap = MathUtils.random(0.6f,1.1f);
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        sprite.draw(spriteBatch);
    }

    @Override
    public void dispose() {
    }

    @Override
    public Vector2 getPosition() {
        bodyPosition = new Vector2(this.getBody().getPosition().x,this.getBody().getPosition().y);
        return bodyPosition;
    }

    @Override
    public float getAngle() {
        float bodyAngle = this.getBody().getAngle();
        return bodyAngle;
    }

    @Override
    public void setBody(float x, float y,float angle) {
        this.getBody().setTransform(x,y,angle);
        //sprite.setPosition(x,y);
        //sprite.setRotation(angle);
    }

    @Override
    public void reset() {
        body.setUserData(null);
        body=null;
    }
}
