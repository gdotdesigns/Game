package net.gdotdesigns.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by Todd on 9/20/2016.
 */
public class Assets {

    private static final String ATLAS = "GameAssets.txt";

    public AssetManager manager = new AssetManager();

    public void loadGameAssets(){
        manager.load(ATLAS, TextureAtlas.class);
    }

    public TextureAtlas getGameAssets(){
        return manager.get(ATLAS,TextureAtlas.class);
    }

    public void unloadAssets(){
        manager.unload(ATLAS);
    }


    public void dispose(){
        manager.dispose();
    }
}
