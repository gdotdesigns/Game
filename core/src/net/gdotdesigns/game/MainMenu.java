package net.gdotdesigns.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * Created by Todd on 9/27/2016.
 */
public class MainMenu implements Screen{

    MainGameScreen mainGameScreen;
    OrthographicCamera camera;
    SpriteBatch spriteBatch;
    Assets assets;
    Stage stage;
    Table menuTable;
    Table googlePlayTable;
    Skin skin;
    TextButton start;
    TextButton stop;
    ImageButton googlePlayButton;
    ImageTextButton achievementButton;
    ImageTextButton leaderBoardButton;
    ImageTextButton signOutButton;
    ImageTextButton continueButton;
    Label gameTitle;
    int highScore;
    Label highScoreLabel;
    Viewport viewport;
    SaveScore saveScore = new SaveScore();
    MainMenu mainMenu;
    GooglePlayServices googlePlayServices;
    private static final String LEADERBOARD_ID = "CgkIhoTXsMgaEAIQBg";
    private static final String TAG = "MainMenu";
    private boolean displayGoogleTable = false;
    private boolean switchedMenu = false;

    public MainMenu(final MainGameScreen mainGameScreen, final Assets assets, final SpriteBatch spriteBatch,GooglePlayServices googlePlayServices){

        this.mainGameScreen = mainGameScreen;
        this.spriteBatch=spriteBatch;
        this.assets=assets;
        this.mainMenu=this;
        this.googlePlayServices = googlePlayServices;

        camera = new OrthographicCamera();
        //viewport = new FitViewport(1920, 1080,camera); //A ratio did not give correct response, probably due to the stage actors are not images...
        viewport = new ScreenViewport(camera);
        viewport.apply();
        stage=new Stage(viewport,spriteBatch);
        menuTable = new Table();
        googlePlayTable = new Table();
        skin = assets.getMenuAssets();

        gameTitle = new Label("Title of Game",skin);
        gameTitle.setFontScale(2f);
        start=new TextButton("Play",skin);
        stop=new TextButton("Exit",skin);
        highScore=saveScore.readScore();
        highScoreLabel = new Label("High Score: " + String.valueOf(highScore),skin);
        googlePlayButton = new ImageButton(skin.getDrawable("games_controller"),skin.getDrawable("games_controller_grey"));
        achievementButton = new ImageTextButton("Achievements",skin,"games_achievements");
        achievementButton.getLabel().setAlignment(Align.center);
        leaderBoardButton = new ImageTextButton("LeaderBoard",skin,"games_leaderboard");
        leaderBoardButton.getLabel().setAlignment(Align.center);
        signOutButton = new ImageTextButton("Sign Out",skin,"signout");
        signOutButton.getLabel().setAlignment(Align.center);
        continueButton = new ImageTextButton("Continue",skin,"continue");
        continueButton.getLabel().setAlignment(Align.center);

        registerListeners();

        menuTable.setFillParent(true);
        //menuTable.setDebug(true);
        menuTable.add(gameTitle).colspan(3);
        menuTable.row();
;       menuTable.add(start).colspan(3);
        menuTable.row();
        menuTable.add(stop).colspan(3);
        menuTable.row();
        menuTable.add(highScoreLabel).colspan(3);
        menuTable.row();
        menuTable.add(googlePlayButton).expandX();
        stage.addActor(menuTable);

        googlePlayTable.setFillParent(true);
        googlePlayTable.add(achievementButton).align(Align.center);
        googlePlayTable.row();
        googlePlayTable.add(leaderBoardButton).align(Align.center);
        googlePlayTable.row();
        googlePlayTable.add(signOutButton).align(Align.center);
        googlePlayTable.row();
        googlePlayTable.add(continueButton).align(Align.center);
        stage.addActor(googlePlayTable);
        googlePlayTable.setVisible(false);

        if(!googlePlayServices.isSignedInGPGS() && saveScore.readPlayServiceStatus()){
            googlePlayServices.signInGPGS();
        }
    }

    private void registerListeners() {
        start.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x,float y){
                mainGameScreen.setScreen(new Game(assets,spriteBatch,mainGameScreen,mainMenu));// Couldnt use "this" because this is inside inner class...
                //dispose();
            }
        });


        stop.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x,float y){
                Gdx.app.exit();
            }
        });

        googlePlayButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!googlePlayServices.isSignedInGPGS() && !saveScore.readPlayServiceStatus()){
                    googlePlayServices.signInGPGS();
                    saveScore.writePlayServiceStatus(true);
                    displayGoogleTable = true;
                }

                else if(!googlePlayServices.isSignedInGPGS() && saveScore.readPlayServiceStatus()){
                    googlePlayServices.signInGPGS();
                    displayGoogleTable = true;
                }

                else if (googlePlayServices.isSignedInGPGS()){
                    displayGoogleTable = true;
                }
            }
        });

        achievementButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(googlePlayServices.isSignedInGPGS()){
                    googlePlayServices.getAchievementsGPGS();
                }
                else{
                    displayGoogleTable = false;
                }
            }
        });

        leaderBoardButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(googlePlayServices.isSignedInGPGS()){
                    googlePlayServices.getLeaderboardGPGS(LEADERBOARD_ID);
                }
                else{
                    displayGoogleTable = false;
                }
            }
        });

        signOutButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(googlePlayServices.isSignedInGPGS()){
                    googlePlayServices.signOutGPGS();
                    displayGoogleTable =false;
                    saveScore.writePlayServiceStatus(false);
                }
                else{
                    displayGoogleTable = false;
                }
            }
        });

        continueButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                displayGoogleTable = false;
            }
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        highScore=saveScore.readScore();
        highScoreLabel.setText("High Score: " + String.valueOf(highScore));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

        if(displayGoogleTable && !switchedMenu){
            if(googlePlayServices.getConnectionStatus()){
                switchedMenu = true;
                showGoogleTable();
            }
        }
        else if (!displayGoogleTable && switchedMenu) {
            switchedMenu = false;
            showMenuTable();
        }

        else if(displayGoogleTable && !googlePlayServices.getConnectionStatus()){
            displayGoogleTable = false;
        }

    }

    public void showMenuTable(){
        googlePlayTable.setVisible(false);
        menuTable.setVisible(true);

    }

    public void showGoogleTable(){
        menuTable.setVisible(false);
        googlePlayTable.setVisible(true);
        if(!saveScore.readScoreSavedStatus()){
            googlePlayServices.submitScoreGPGS(saveScore.readScore(),LEADERBOARD_ID);
            saveScore.writeScoreSavedStatus(true);
        }
    }



    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    public void updateLeaderBoard(int score){
            if(googlePlayServices != null && googlePlayServices.getConnectionStatus()){
                googlePlayServices.submitScoreGPGS(score, LEADERBOARD_ID);
                if(!saveScore.readScoreSavedStatus()){
                    saveScore.writeScoreSavedStatus(true);
                }
            }
            else if(saveScore.readScoreSavedStatus()){
                saveScore.writeScoreSavedStatus(false);
            }
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        System.out.println("MAINMENU.DISPOSE");
        stage.dispose();
    }
}
