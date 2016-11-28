package net.gdotdesigns.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Todd on 8/22/2016.
 */
public class Player extends Entity {

    Body body;
    BodyDef bodyDef;
    FixtureDef fixtureDef;
    PolygonShape shape;
    World world;
    TextureRegion currentFrame;
    Array<TextureRegion> playerBird;
    Animation animation;
    Sprite sprite;
    float bodyloc_x;
    float bodyloc_y;
    float shapesize_x,shapesize_y;
    float density;
    float restitution;
    float elapsedTime;
    boolean isAlive;
    Vector2 bodyPosition;
    public Vector2 previousPosition;
    public float previousAngle;


    public Player(float bodyloc_x, float bodyloc_y, float shapesize_x, float shapesize_y, float density, float restitution, World world, Array<TextureRegion> playerBird){
        this.bodyloc_x = bodyloc_x;
        this.bodyloc_y =bodyloc_y;
        this.shapesize_x= shapesize_x;
        this.shapesize_y=shapesize_y;
        this.density=density;
        this.restitution=restitution;
        this.world=world;
        this.playerBird=playerBird;
        animation = new Animation(1 / 7f, playerBird);
        sprite=new Sprite();
        sprite.setSize(shapesize_x,shapesize_y);
        sprite.setOriginCenter();
        sprite.setScale(1f,1f);
        createDynamicBody();
        previousPosition = new Vector2();
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
        body.setFixedRotation(false);
        body.setGravityScale(1.5f);
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
        isAlive=false;
    }

    @Override
    public Vector2 findEntityLocation() {
        return null;
    }

    @Override
    public void freeEntity() {

    }

    @Override
    public void update(float deltaTime) {
        elapsedTime+= deltaTime;
        currentFrame = animation.getKeyFrame(elapsedTime,true);
//        Sprite setPosition not needed in update due to using interpolation in EntityManager.
//        sprite.setPosition(body.getPosition().x-shapesize_x/2f,body.getPosition().y-shapesize_y/2f);
//        sprite.setRotation((float) Math.toDegrees(body.getAngle()));

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        sprite.draw(spriteBatch);
    }

    @Override
    public void dispose() {

    }

    @Override
    public Vector2 getCurrentPosition() {
        bodyPosition = new Vector2(this.getBody().getPosition().x,this.getBody().getPosition().y);
        return bodyPosition;    }

    @Override
    public float getCurrentAngle() {
        float bodyAngle = this.getBody().getAngle();
        return bodyAngle;   }

    @Override
    public void savePreviousPosition() {
        if(this.body != null){
            this.previousPosition.x = this.getBody().getPosition().x;
            this.previousPosition.y = this.getBody().getPosition().y;
            this.previousAngle = this.getBody().getAngle();
        }

    }

    @Override
    public Vector2 getPreviousPosition() {
        return previousPosition;
    }

    @Override
    public float getPreviousAngle() {
        return previousAngle;
    }

    @Override
    public void setBody(float x, float y,float angle) {
        //this.getBody().setTransform(x,y,angle);
        sprite.setPosition(x - shapesize_x / 2f,y - shapesize_y / 2f);
        sprite.setRotation((float) Math.toDegrees(angle));
    }
}
