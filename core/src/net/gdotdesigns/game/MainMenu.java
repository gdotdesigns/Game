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
    ImageButton twitterbutton;
    ImageButton facebookButton;
    ImageTextButton achievementButton;
    ImageTextButton leaderBoardButton;
    ImageTextButton signOutButton;
    ImageTextButton continueButton;
    Label gameTitle;
    int highScore;
    Label highScoreLabel;
    Viewport viewport;
    SaveState saveScore = new SaveState();
    MainMenu mainMenu;
    private static final String LEADERBOARD_ID = "CgkIhoTXsMgaEAIQBg";
    private static final String ACHIEVEMENT_ID_NUM_1 = "CgkIhoTXsMgaEAIQAQ";
    private static final String ACHIEVEMENT_ID_NUM_2 = "CgkIhoTXsMgaEAIQAg";
    private static final String ACHIEVEMENT_ID_NUM_3 = "CgkIhoTXsMgaEAIQAw";
    private static final String ACHIEVEMENT_ID_NUM_4 = "CgkIhoTXsMgaEAIQBA";
    private static final String ACHIEVEMENT_ID_NUM_5 = "CgkIhoTXsMgaEAIQBQ";
    private static final int LOCAL_SAVE = 1;
    private static final int GOOGLE_SAVE = 2;

    private static final String TAG = "MainMenu";
    private boolean displayGoogleTable = false;
    private boolean switchedMenu = false;

    public MainMenu(final MainGameScreen mainGameScreen, final Assets assets, final SpriteBatch spriteBatch){

        this.mainGameScreen = mainGameScreen;
        this.spriteBatch=spriteBatch;
        this.assets=assets;
        this.mainMenu=this;

        camera = new OrthographicCamera();
//        viewport = new FitViewport(1920, 1080,camera); //A ratio did not give correct response, probably due to the stage actors are not images...
        viewport = new ScreenViewport(camera);
        viewport.apply();
        stage=new Stage(viewport,spriteBatch);
        menuTable = new Table();
        googlePlayTable = new Table();
        skin = assets.getGameAssets();

        gameTitle = new Label("Title of Game",skin);
        gameTitle.setFontScale(2f);
        start=new TextButton("Play",skin);
        stop=new TextButton("Exit",skin);
        //highScore=saveScore.readScore();
        //highScoreLabel = new Label("High Score: " + String.valueOf(highScore),skin)
        highScoreLabel = new Label("",skin);
        googlePlayButton = new ImageButton(skin.getDrawable("games_controller"),skin.getDrawable("games_controller_grey"));
        twitterbutton = new ImageButton(skin.getDrawable("Twitter_Social_Icon_Rounded_Square_Color"));
        facebookButton = new ImageButton(skin.getDrawable("FB-f-Logo__blue"));
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
        menuTable.add(twitterbutton);
        menuTable.add(googlePlayButton);
        menuTable.add(facebookButton);
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

        if(!mainGameScreen.googlePlayServices.isSignedInGPGS() && saveScore.readPlayServiceStatus()){
            mainGameScreen.googlePlayServices.signInGPGS();
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
                if(!mainGameScreen.googlePlayServices.isSignedInGPGS() && !saveScore.readPlayServiceStatus()){
                    mainGameScreen.googlePlayServices.signInGPGS();
                    saveScore.writePlayServiceStatus(true);
                    displayGoogleTable = true;
                }

                else if(!mainGameScreen.googlePlayServices.isSignedInGPGS() && saveScore.readPlayServiceStatus()){
                    mainGameScreen.googlePlayServices.signInGPGS();
                    displayGoogleTable = true;
                }

                else if (mainGameScreen.googlePlayServices.isSignedInGPGS()){
                    displayGoogleTable = true;
                }
            }
        });

        twitterbutton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainGameScreen.socialMedia.shareTwitter();
            }
        });

        facebookButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainGameScreen.socialMedia.shareFacebook();
            }
        });

        achievementButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(mainGameScreen.googlePlayServices.isSignedInGPGS()){
                    mainGameScreen.googlePlayServices.getAchievementsGPGS();
                }
                else{
                    displayGoogleTable = false;
                }
            }
        });

        leaderBoardButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(mainGameScreen.googlePlayServices.isSignedInGPGS()){
                    mainGameScreen.googlePlayServices.getLeaderboardGPGS(LEADERBOARD_ID);
                }
                else{
                    displayGoogleTable = false;
                }
            }
        });

        signOutButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(mainGameScreen.googlePlayServices.isSignedInGPGS()){
                    mainGameScreen.googlePlayServices.signOutGPGS();
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
            if(mainGameScreen.googlePlayServices.getConnectionStatus()){
                switchedMenu = true;
                showGoogleTable();
            }
        }
        else if (!displayGoogleTable && switchedMenu) {
            switchedMenu = false;
            showMenuTable();
        }

        else if(displayGoogleTable && !mainGameScreen.googlePlayServices.getConnectionStatus()){
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
            mainGameScreen.googlePlayServices.submitScoreGPGS(saveScore.readScore(),LEADERBOARD_ID);
            saveScore.writeScoreSavedStatus(true);
        }
        if (saveScore.readAchievement1() == 1 ){
            mainGameScreen.googlePlayServices.unlockAchievementGPGS(ACHIEVEMENT_ID_NUM_1);
            saveScore.writeAchievement1(2);
        }
        if (saveScore.readAchievement2() == 1 ){
            mainGameScreen.googlePlayServices.unlockAchievementGPGS(ACHIEVEMENT_ID_NUM_2);
            saveScore.writeAchievement2(2);
        }
        if (saveScore.readAchievement3() == 1 ){
            mainGameScreen.googlePlayServices.unlockAchievementGPGS(ACHIEVEMENT_ID_NUM_3);
            saveScore.writeAchievement3(2);
        }
        if (saveScore.readAchievement4() == 1 ){
            mainGameScreen.googlePlayServices.unlockAchievementGPGS(ACHIEVEMENT_ID_NUM_4);
            saveScore.writeAchievement4(2);
        }
        if (saveScore.readAchievement5() == 1 ){
            mainGameScreen.googlePlayServices.unlockAchievementGPGS(ACHIEVEMENT_ID_NUM_5);
            saveScore.writeAchievement5(2);
        }
        saveScore.flush();
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

    public void updateGooglePlayStats(int score){
        if(score > saveScore.readScore()){
            saveScore.writeScore(score);
            if(mainGameScreen.googlePlayServices != null && mainGameScreen.googlePlayServices.getConnectionStatus()) {
                mainGameScreen.googlePlayServices.submitScoreGPGS(score, LEADERBOARD_ID);
                if (!saveScore.readScoreSavedStatus()) {
                    saveScore.writeScoreSavedStatus(true);
                }
                achievementSaveState(score, GOOGLE_SAVE);
            }

            else {
                if(saveScore.readScoreSavedStatus()) {
                    saveScore.writeScoreSavedStatus(false);
                }
               achievementSaveState(score, LOCAL_SAVE);
            }
        }
    }

    public void achievementSaveState(int score, int saveStateValue){
        if (score >= 10) {
        if (saveScore.readAchievement1() != LOCAL_SAVE || saveScore.readAchievement1() != GOOGLE_SAVE) {
            mainGameScreen.googlePlayServices.unlockAchievementGPGS(ACHIEVEMENT_ID_NUM_1);
            saveScore.writeAchievement1(saveStateValue);
        }
    }
        if (score >= 20) {
            if (saveScore.readAchievement2() != LOCAL_SAVE || saveScore.readAchievement2() != GOOGLE_SAVE) {
                mainGameScreen.googlePlayServices.unlockAchievementGPGS(ACHIEVEMENT_ID_NUM_2);
                saveScore.writeAchievement2(saveStateValue);
            }
        }
        if (score >= 30) {
            if (saveScore.readAchievement3() != LOCAL_SAVE || saveScore.readAchievement3() != GOOGLE_SAVE) {
                mainGameScreen.googlePlayServices.unlockAchievementGPGS(ACHIEVEMENT_ID_NUM_3);
                saveScore.writeAchievement3(saveStateValue);
            }
        }
        if (score >= 40) {
            if (saveScore.readAchievement4() != LOCAL_SAVE || saveScore.readAchievement4() != GOOGLE_SAVE) {
                mainGameScreen.googlePlayServices.unlockAchievementGPGS(ACHIEVEMENT_ID_NUM_4);
                saveScore.writeAchievement4(saveStateValue);
            }
        }
        if (score >= 50) {
            if (saveScore.readAchievement5() != LOCAL_SAVE || saveScore.readAchievement5() != GOOGLE_SAVE) {
                mainGameScreen.googlePlayServices.unlockAchievementGPGS(ACHIEVEMENT_ID_NUM_5);
                saveScore.writeAchievement5(saveStateValue);

            }
        }
        saveScore.flush();
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
