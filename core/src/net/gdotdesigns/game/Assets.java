package net.gdotdesigns.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by Todd on 9/20/2016.
 */
public class Assets {

    private static final String MENU_SKIN = "GameAssets.json";


    public AssetManager manager = new AssetManager();


    //public void loadMenuAssets() {manager.load(MENU_SKIN, Skin.class);} //This automatically loads the corresponding atlas file with the same name as JSON file...

   public void loadMenuAssets() {manager.load(MENU_SKIN, Skin.class, new SkinLoader.SkinParameter("GameAssets64.atlas"));}

    public Skin getMenuAssets(){return manager.get(MENU_SKIN,Skin.class);}





    public void dispose(){
        manager.dispose();
    }
}
