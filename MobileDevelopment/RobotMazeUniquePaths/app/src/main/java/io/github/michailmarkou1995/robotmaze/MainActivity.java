package io.github.michailmarkou1995.robotmaze;

import static io.github.michailmarkou1995.robotmaze.R.id;
import static io.github.michailmarkou1995.robotmaze.R.layout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public final String TAG = "MainActivity";
    static boolean isDifficult;
    public RobotPath robot;
    private Button btn_easy, btn_hard, buttonExit;
    private AdView adView;
    private InterstitialAd mInterstitialAd;
    private RewardedAd mRewardedAd;

    public RewardedAd getmRewardedAd() {
        return mRewardedAd;
    }

    FrameLayout screenStartChoose;
    MainActivity mainAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
            }
        });
        adView = findViewById(id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        loadInterstitialAd(adRequest);
        /*Ads Banner*/
        adView.loadAd(adRequest);
        loadRewaredVideoAd(adRequest);

        screenStartChoose = findViewById(id.screenStartBackground);
        buttonExit = findViewById(id.buttonEXIT);
        btn_easy = findViewById(id.btn_easy);
        btn_hard = findViewById(id.btn_hard);
        btn_easy.setOnClickListener(this);
        btn_hard.setOnClickListener(this);
        buttonExit.setOnClickListener(this);
        mainAct = this;
    }

    public void buttonUP(View view) {
        robot.calculatePathId(view.getId());
    }

    public void buttonDOWN(View view) {
        robot.calculatePathId(view.getId());
    }

    public void buttonLEFT(View view) {
        robot.calculatePathId(view.getId());
    }

    public void buttonRIGHT(View view) {
        robot.calculatePathId(view.getId());
    }

    // Reset Game on Pre-Condition
    @Override
    public void onClick(View view) {
        /*Ads InterstitialAd*/
        if (mInterstitialAd != null) {
            mInterstitialAd.show(MainActivity.this);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
        switch (view.getId()) {
            case id.btn_easy:
                isDifficult = false;
                RobotPath.btnExit = buttonExit;
                robot = new RobotPath(mainAct, false);
                screenStartChoose.animate()
                        .alpha(0.5f)
                        .setDuration(1000L).setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                btn_easy.animate().rotation(btn_easy.getRotation() - 360).start();
                                screenStartChoose.animate()
                                        .alpha(0.0f)
                                        .setDuration(1000L).setListener(new AnimatorListenerAdapter() {
                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                btn_easy.setVisibility(View.GONE);
                                                btn_hard.setVisibility(View.GONE);
                                            }
                                        });
                            }
                        });
                break;
            case id.btn_hard:
                isDifficult = true;
                RobotPath.btnExit = buttonExit;
                robot = new RobotPath(mainAct, false);
                screenStartChoose.animate()
                        .alpha(0.5f)
                        .setDuration(1000L).setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                btn_hard.animate().rotation(btn_hard.getRotation() - 360).start();
                                screenStartChoose.animate()
                                        .alpha(0.0f)
                                        .setDuration(1000L).setListener(new AnimatorListenerAdapter() {
                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                btn_easy.setVisibility(View.GONE);
                                                btn_hard.setVisibility(View.GONE);
                                            }
                                        });
                            }
                        });
                break;
            case id.buttonEXIT:
                resetGame();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }

    public void resetGame() {
        if (!robot.robotSteppedOut) {
            RobotPath.byPassedKaboom = false;
            robot.spawn.deSpawnBombs();
            if (robot.spawn.bombList.isEmpty()) {
                robot = new RobotPath(this, true);
                robot.takeUIcontrol = false;
            } else throw new ArrayStoreException(); // leak error
        }
    }

    private void loadRewaredVideoAd(AdRequest adRequest) {
        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d(TAG, loadAdError.getMessage());
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Log.d(TAG, "Ad was loaded.");
                    }
                });
    }

    private void loadInterstitialAd(AdRequest adRequest) {
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });
    }
}