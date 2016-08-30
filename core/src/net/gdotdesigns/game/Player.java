package net.gdotdesigns.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Todd on 8/22/2016.
 */
public class Player extends Entity {
    private static final float BIRD_HEIGHT=2f;
    private static final float BIRD_WIDTH=BIRD_HEIGHT*1.305f;
    Body body;
    BodyDef bodyDef;
    FixtureDef fixtureDef;
    PolygonShape shape;
    World world;
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

    public Player(float bodyloc_x, float bodyloc_y, float shapesize_x, float shapesize_y, float density, float restitution, World world, TextureAtlas textureAtlas){
        this.bodyloc_x = bodyloc_x;
        this.bodyloc_y =bodyloc_y;
        this.shapesize_x= shapesize_x;
        this.shapesize_y=shapesize_y;
        this.density=density;
        this.restitution=restitution;
        this.world=world;
        this.textureAtlas = textureAtlas;
        animation = new Animation(1 / 7f, textureAtlas.getRegions());
        sprite=new Sprite();
        sprite.setSize(BIRD_WIDTH,BIRD_HEIGHT);
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
        shape.setAsBox(shapesize_x,shapesize_y);
        fixtureDef=new FixtureDef();
        fixtureDef.shape=shape;
        fixtureDef.density=density;
        fixtureDef.restitution=restitution;
        body.createFixture(fixtureDef);
        body.setFixedRotation(true);
    }

    @Override
    public void update(float deltaTime) {
        elapsedTime+= Gdx.graphics.getDeltaTime();
        currentFrame = animation.getKeyFrame(elapsedTime,true);
        sprite.setRegion(currentFrame);
        sprite.setPosition(body.getPosition().x-BIRD_WIDTH/2f,body.getPosition().y-BIRD_HEIGHT/2f);
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        sprite.draw(spriteBatch);
    }

    @Override
    public void dispose() {
        shape.dispose();
        textureAtlas.dispose();
    }
}
