package net.gdotdesigns.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
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
    TextureAtlas textureAtlas;
    TextureRegion currentFrame;
    Animation animation;
    Sprite sprite;
    float bodyloc_x;
    float bodyloc_y;
    float shapesize_x,shapesize_y;
    float density;
    float restitution;
    float elapsedTime;
    float timeToFlap = MathUtils.random(0.6f,1.1f);
    float flapTimer;


    public Enemy(float bodyloc_x, float bodyloc_y, float shapesize_x, float shapesize_y, float density, float restitution, World world, TextureAtlas textureAtlas,EnemyPool pool){
        this.bodyloc_x = bodyloc_x;
        this.bodyloc_y =bodyloc_y;
        this.shapesize_x= shapesize_x;
        this.shapesize_y=shapesize_y;
        this.density=density;
        this.restitution=restitution;
        this.world=world;
        this.pool=pool;
        this.textureAtlas = textureAtlas;
        Array<TextureRegion> bird = new Array<TextureRegion>();
        bird.add(textureAtlas.findRegion("0"));
        bird.add(textureAtlas.findRegion("1"));
        bird.add(textureAtlas.findRegion("2"));
        bird.add(textureAtlas.findRegion("3"));
        animation = new Animation(1 / 7f, bird);
        sprite=new Sprite();
        sprite.setSize(shapesize_x,shapesize_y);
        sprite.setOriginCenter();
        sprite.setScale(1f,1f);
        createDynamicBody();
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
    public void freeEntity() {
        pool.free(this);
    }

    @Override
    public void update(float deltaTime) {
        currentFrame = animation.getKeyFrame(elapsedTime,true);
        sprite.setRegion(currentFrame);
        sprite.setPosition(body.getPosition().x-shapesize_x/2f,body.getPosition().y-shapesize_y/2f);
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
        flapTimer+= deltaTime;
        elapsedTime+= deltaTime;
        if(flapTimer>=timeToFlap){
            body.setLinearVelocity(-5f,5f);
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
        textureAtlas.dispose();
    }

    @Override
    public void reset() {
        body.setUserData(null);
        body=null;
        createDynamicBody();
    }
}
