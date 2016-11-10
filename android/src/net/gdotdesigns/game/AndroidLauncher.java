package net.gdotdesigns.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
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

public class AndroidLauncher extends AndroidApplication implements AdController, GooglePlayServices, GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {

    //TODO Google play Icons are too small.
    //TODO Need to add Google sign in button to atlas.
    //TODO Need to add sign out button to atlas.
    //TODO Implement way to remember if someone wants to remain signed out.
    //TODO Implement way to record score while signed out so that it can be uploaded when signed in.
    //TODO Change to selective imports for google play services.

    private InterstitialAd interstitialAd;
    private AdRequest adRequest;
    private GoogleApiClient googleApiClient;
    private int playCount = 0;
    private static final int MAX_PLAY_COUNT = 1;
	private static final String INTERSTITIAL_UNIT_ID ="ca-app-pub-2895382750471159/5257221423";
	private static final String TEST_DEVICE= "8ABB25975BCF7ED6E7C49D16043D1A12";
    private static int RC_SIGN_IN = 9001;
    private static int REQUEST_LEADERBOARD = 1;
    private static int REQUEST_ACHIEVEMENTS = 2;

    public boolean resolvingConnectionFailure = false;
    private boolean signingOut = false;
    private boolean isConnected = false;
    public static final String TAG = "AndroidLauncher";

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


        interstitialAd = new InterstitialAd(this);
        adRequest = new AdRequest.Builder()
                .addTestDevice(TEST_DEVICE)
                .build();
        interstitialAd.setAdUnitId(INTERSTITIAL_UNIT_ID);
        interstitialAd.loadAd(adRequest);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new MainGameScreen(this,this), config);
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
                            Toast.makeText(getContext(),"Signed out of Google Play.", Toast.LENGTH_SHORT).show();

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
        Toast.makeText(getContext(),"Signed in.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
            googleApiClient.connect();
    }

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
}

