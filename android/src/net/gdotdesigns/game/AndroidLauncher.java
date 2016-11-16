package net.gdotdesigns.game;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesActivityResultCodes;
import com.google.example.games.basegameutils.BaseGameUtils;

import java.util.List;

public class AndroidLauncher extends AndroidApplication implements AdControllerInterface, GooglePlayServicesInterface, GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener, SocialMediaInterface {

    //TODO Enter app name for social media links when game is complete.

    private InterstitialAd interstitialAd;
    private AdRequest adRequest;
    private GoogleApiClient googleApiClient;
    private int playCount = 0;
    private static final int MAX_PLAY_COUNT = 1;
	private static final String INTERSTITIAL_UNIT_ID ="ca-app-pub-2895382750471159/5257221423";
	private static final String TEST_DEVICE= "8ABB25975BCF7ED6E7C49D16043D1A12";
    //These are arbitrary numbers to specify which activity called onActivityResult
    private static int RC_SIGN_IN = 9001;
    private static int REQUEST_LEADERBOARD = 1;
    private static int REQUEST_ACHIEVEMENTS = 2;

    public boolean resolvingConnectionFailure = false;
    private boolean signingOut = false;
    private boolean isConnected = false;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        //Builds an interstitial ad
        interstitialAd = new InterstitialAd(this);
        adRequest = new AdRequest.Builder()
                .addTestDevice(TEST_DEVICE)
                .build();
        interstitialAd.setAdUnitId(INTERSTITIAL_UNIT_ID);
        interstitialAd.loadAd(adRequest);

        //Builds a google api client for Games.api (Google Play Services)
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        //Pass this object to the maingamescreen to make native android code usable in libgdx
		initialize(new MainGameScreen(this,this,this), config);
	}

    @Override
    protected void onStop() {
        super.onStop();
        if(googleApiClient.isConnected()){
            disconnectGPGS();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //If Ad is loadedAfter, run code in runnable after ad is closed  , otherwise load an Ad.
    @Override
    public void showorLoadInterstitials(final Runnable runnable) {
        if(playCount >= MAX_PLAY_COUNT) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (runnable != null) {
                        if(interstitialAd.isLoaded()) {
                            interstitialAd.setAdListener(new AdListener() {
                                @Override
                                public void onAdClosed() {
                                    Gdx.app.postRunnable(runnable);
                                    interstitialAd.loadAd(adRequest);
                                }
                            });
                        }
                        else {
                            if(interstitialAd.isLoading() == false) {
                                interstitialAd.loadAd(adRequest);
                            }
                            Gdx.app.postRunnable(runnable);
                            return;

                        }
                    }
                    interstitialAd.show();
                    playCount = 0;

                }
            });
        }
        else{
            playCount++;
            Gdx.app.postRunnable(runnable);
        }
    }

    @Override
    public boolean isSignedInGPGS() {
        if(googleApiClient.isConnected()){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void signInGPGS() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(!googleApiClient.isConnected()){
                        googleApiClient.connect();
                    }
                }
            });
        } catch (Exception e) {
            Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");

        }
    }

    @Override
    public void submitScoreGPGS(int score, String id) {
        if(googleApiClient != null && googleApiClient.isConnected()){
            Games.Leaderboards.submitScore(googleApiClient,id,score);
        }
    }

    @Override
    public void unlockAchievementGPGS(String achievementId) {
        if(googleApiClient != null && googleApiClient.isConnected()){
            Games.Achievements.unlock(googleApiClient,achievementId);
        }

    }

    @Override
    public void getLeaderboardGPGS(final String id) {
        try{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(googleApiClient != null && googleApiClient.isConnected()){

                        startActivityForResult(Games.Leaderboards.getLeaderboardIntent(googleApiClient,
                                id), REQUEST_LEADERBOARD);
                    }

                }
            });
        }
        catch (Exception e) {
            Gdx.app.log("MainActivity", "LeaderBoard Failed: " + e.getMessage() + ".");
        }

    }

    @Override
    public void getAchievementsGPGS() {
        try{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(googleApiClient != null && googleApiClient.isConnected()){

                        startActivityForResult(Games.Achievements.getAchievementsIntent(googleApiClient),
                                REQUEST_ACHIEVEMENTS);
                    }

                }
            });
        }
        catch (Exception e) {
            Gdx.app.log("MainActivity", "AchievementBoard Failed: " + e.getMessage() + ".");
        }
    }

    @Override
    public void signOutGPGS() {

        try {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if(googleApiClient.isConnected() && !signingOut) {
                        signingOut = true;
                        Games.signOut(googleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            googleApiClient.disconnect();
                            signingOut = false;
                            isConnected = false;
                            Toast.makeText(getContext(),"Signed out.", Toast.LENGTH_SHORT).show();

                        }
                    });
        }
                }
            });
        } catch (Exception e) {
            Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
        }
    }

    @Override
    public void disconnectGPGS() {

        try {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if(googleApiClient.isConnected()) {
                        googleApiClient.disconnect();
                        isConnected=false;
                        Toast.makeText(getContext(),"Disconnected from Google Play.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            Gdx.app.log("MainActivity", "Disconnection failed: " + e.getMessage() + ".");
        }

    }

    @Override
    public boolean getConnectionStatus() {
        return isConnected;
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        isConnected = true;
    }

    @Override
    public void onConnectionSuspended(int i) {

            googleApiClient.connect();
    }

    //Call back for connection failure to signInGPGS.
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {

        if (resolvingConnectionFailure) {
            // already resolving
            return;
        }
            resolvingConnectionFailure = true;

            if (!BaseGameUtils.resolveConnectionFailure(this,
                    googleApiClient, result,
                    RC_SIGN_IN, getString(R.string.signin_other_error))) {

                        resolvingConnectionFailure=false;
            }

    }


    //Call back for successfull / unsuccessful resolution from onConnectionFailed.
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
      super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            resolvingConnectionFailure = false;
            if (resultCode == RESULT_OK) {
                signInGPGS();
            } else {
                // Bring up an error dialog to alert the user that sign-in failed.
                BaseGameUtils.showActivityResultError(this,
                        requestCode, resultCode, R.string.signin_failure);
            }
     }

        else if(requestCode == REQUEST_LEADERBOARD ){
            resolvingConnectionFailure = false;
            if(resultCode == GamesActivityResultCodes.RESULT_RECONNECT_REQUIRED){
                disconnectGPGS();
            }

        }

        else if(requestCode == REQUEST_ACHIEVEMENTS ){
            resolvingConnectionFailure = false;
            if(resultCode == GamesActivityResultCodes.RESULT_RECONNECT_REQUIRED){
                disconnectGPGS();
            }

        }

    }


    public void startSocialApp(String uri){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=net.gdot.badgeswipe");
        intent.setType("text/plain");

        PackageManager packManager = getPackageManager();
        List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(intent,  PackageManager.MATCH_DEFAULT_ONLY);

        boolean resolved = false;
        for(ResolveInfo resolveInfo: resolvedInfoList){
            if(resolveInfo.activityInfo.packageName.startsWith(uri)){
                intent.setClassName(
                        resolveInfo.activityInfo.packageName,
                        resolveInfo.activityInfo.name );
                resolved = true;
                break;
            }
        }
        if(resolved){
            startActivity(intent);
        }else{
            startActivity(Intent.createChooser(intent,"Share"));
        }
    }

    @Override
    public void shareTwitter() {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                startSocialApp("com.twitter.android");
            }
        });
    }



    @Override
    public void shareFacebook() {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                startSocialApp("com.facebook.katana");

            }
        });
    }

}

