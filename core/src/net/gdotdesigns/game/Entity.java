package net.gdotdesigns.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Todd on 8/22/2016.
 */
public class Entity {

    Body body;
    BodyDef bodyDef;
    FixtureDef fixtureDef;
    PolygonShape shape;
    World world;
    Sprite sprite;
    float bodyloc_x;
    float bodyloc_y;
    float shapesize_x,shapesize_y;
    float density;
    float restitution;

    public Entity(float bodyloc_x,float bodyloc_y,float shapesize_x,float shapesize_y,float density, float restitution, World world, Sprite sprite){
        this.bodyloc_x = bodyloc_x;
        this.bodyloc_y =bodyloc_y;
        this.shapesize_x= shapesize_x;
        this.shapesize_y=shapesize_y;
        this.density=density;
        this.restitution=restitution;
        this.world=world;
        this.sprite=sprite;
        createBody();
    }

    private void createBody() {
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
}
