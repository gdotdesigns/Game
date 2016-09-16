package net.gdotdesigns.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Todd on 8/20/2016.
 */
public class Inputs implements InputProcessor{

    Vector3 vec3 = new Vector3();
    Camera camera;
    World world;
    Body body;



    public Inputs(Camera camera, World world){
        this.camera = camera;
        this.world = world;
    }

    private QueryCallback queryCallback = new QueryCallback() {
        @Override
        public boolean reportFixture(Fixture fixture) {

            if (!fixture.testPoint(vec3.x, vec3.y)){
                return true;}
            body=fixture.getBody();
            if(!(body.getUserData() instanceof Enemy)) {
                body.setLinearVelocity(1f, 6f);
                body.setAngularVelocity(0f);
            }
                return false;

        }

    };


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }



    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        vec3.set(screenX,screenY,0);
        camera.unproject(vec3);
        world.QueryAABB(queryCallback,vec3.x,vec3.y,vec3.x,vec3.y);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
    return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
