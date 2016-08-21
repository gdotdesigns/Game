package net.gdotdesigns.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Todd on 8/20/2016.
 */
public class Inputs implements InputProcessor{

    Game game = new Game();
    Vector3 vec3 = new Vector3();
    Camera camera;
    World world;



    public Inputs(Camera camera, World world){
        this.camera = camera;
        this.world = world;

    }




    private QueryCallback queryCallback = new QueryCallback() {
        @Override
        public boolean reportFixture(Fixture fixture) {
            System.out.println(vec3);
            if (!fixture.testPoint(vec3.x, vec3.y)){
                return true;}
            game.body.applyTorque(game.torque, true);
            game.body.applyAngularImpulse(game.torque, false);
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
