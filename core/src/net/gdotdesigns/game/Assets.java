package net.gdotdesigns.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver.Resolution;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by Todd on 9/20/2016.
 */
public class Assets {

    private static final String MENU_SKIN = "GameAssets.json";

    //http://andrew.hedges.name/experiments/aspect_ratio/
    AssetManager manager = new AssetManager();
    Resolution small =new Resolution(320, 480, "small"); //3:2
    Resolution medium =new Resolution(480, 800, "medium"); //5:3
    Resolution large =new Resolution(1080, 1920, "large"); //16:9
    ResolutionFileResolver resolver = new ResolutionFileResolver(new InternalFileHandleResolver(),small,medium,large);
    Resolution res = ResolutionFileResolver.choose(small,medium,large);


   public void loadMenuAssets() {
       System.out.println(res.folder);
       manager.setLoader(Skin.class,new SkinLoader(resolver));
       manager.load(MENU_SKIN, Skin.class);// Delete the _0,_1, ... in the atlas and the .fnt file.

   }

    public Skin getMenuAssets(){return manager.get(MENU_SKIN,Skin.class);}

    public void dispose(){
        manager.dispose();
    }
}
