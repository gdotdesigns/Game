package net.gdotdesigns.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by Todd on 9/20/2016.
 */
public class Assets {

    //private static final String GAME_ATLAS = "GameAssets.txt";
    private static final String MENU_ATLAS = "Game-Menu-Assets.txt";
    private static final String MENU_SKIN = "neon-ui.json";



    public AssetManager manager = new AssetManager();

    //public void loadGameAssets(){manager.load(GAME_ATLAS, TextureAtlas.class);}

    public void loadMenuAssets() {
        manager.load(MENU_ATLAS,TextureAtlas.class);
        manager.load(MENU_SKIN, Skin.class);
    }

    //public TextureAtlas getGameAssets(){return manager.get(GAME_ATLAS,TextureAtlas.class);}

    public Skin getMenuAssets(){return manager.get(MENU_SKIN,Skin.class);}


    public TextureAtlas getMenuAtlas(){return manager.get(MENU_ATLAS,TextureAtlas.class);}


    //public void unloadGameAssets(){manager.unload(GAME_ATLAS);}

    //public void unloadMenuAssets(){manager.unload(MENU_SKIN);}


    public void dispose(){
        manager.dispose();
    }
}
