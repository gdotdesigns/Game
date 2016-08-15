package net.gdotdesigns.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Todd on 8/14/2016.
 */
public class ParallaxLayer {

       // public TextureRegion region ;
        public Texture texture ;
        public Vector2 parallaxRatio;
        public Vector2 startPosition;

        public ParallaxLayer(Texture texture, Vector2 parallaxRatio){
            this(texture, parallaxRatio, new Vector2(0,0));
        }
        /**
         * @param texture   the TextureRegion to draw , this can be any width/height
         * @param parallaxRatio   the relative speed of x,y {@link ParallaxBackground#ParallaxBackground(ParallaxLayer[], float, float, Vector2)}
         * @param startPosition the init position of x,y
         */
        public ParallaxLayer(Texture texture,Vector2 parallaxRatio,Vector2 startPosition){
            this.texture  = texture;
            this.parallaxRatio = parallaxRatio;
            this.startPosition = startPosition;
        }
    }


