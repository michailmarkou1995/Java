package io.github.michailmarkou1995.robotmaze;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;

/**
 * Logic of Finding Path
 */
public class RobotPath {
    final int START_POSITION = 1, END_POSITION = 36, MAZE_WIDTH = 6, MAZE_HEIGHT = 6;
    static Button btnExit;
    static boolean byPassedKaboom = false, doOnce = true;
    static int levelCounter = 0, alternateExit;
    int current_x, current_y, positionRecord = 0; // positionRecord we can capture it here or fetch it from Active Green-Stepped-Block
    int[] imgView = {0, R.id.imageView1, R.id.imageView2, R.id.imageView3, R.id.imageView4, R.id.imageView5,
            R.id.imageView6, R.id.imageView7, R.id.imageView8, R.id.imageView9, R.id.imageView10,
            R.id.imageView11, R.id.imageView12, R.id.imageView13, R.id.imageView14, R.id.imageView15,
            R.id.imageView16, R.id.imageView17, R.id.imageView18, R.id.imageView19, R.id.imageView20,
            R.id.imageView21, R.id.imageView22, R.id.imageView23, R.id.imageView24, R.id.imageView25,
            R.id.imageView26, R.id.imageView27, R.id.imageView28, R.id.imageView29, R.id.imageView30,
            R.id.imageView31, R.id.imageView32, R.id.imageView33, R.id.imageView34, R.id.imageView35,
            R.id.imageView36};
    int[] robotView = {0, R.id.robotView1, R.id.robotView2, R.id.robotView3, R.id.robotView4, R.id.robotView5,
            R.id.robotView6, R.id.robotView7, R.id.robotView8, R.id.robotView9, R.id.robotView10,
            R.id.robotView11, R.id.robotView12, R.id.robotView13, R.id.robotView14, R.id.robotView15,
            R.id.robotView16, R.id.robotView17, R.id.robotView18, R.id.robotView19, R.id.robotView20,
            R.id.robotView21, R.id.robotView22, R.id.robotView23, R.id.robotView24, R.id.robotView25,
            R.id.robotView26, R.id.robotView27, R.id.robotView28, R.id.robotView29, R.id.robotView30,
            R.id.robotView31, R.id.robotView32, R.id.robotView33, R.id.robotView34, R.id.robotView35,
            R.id.robotView36};
    ImageView imgSteppedV, robotV;
    TextView positionRecordText, levelName;
    FrameLayout overlayLevel;
    boolean moveToWidth, moveToHeight, robotSteppedOut, takeUIcontrol;
    static boolean isExitAltered;
    Spawn spawn;
    MainActivity mainAct;

    // Initialization values and Reset Game Status Variables and UI
    RobotPath(MainActivity mainAct, boolean isResetGameExit) {
        this(mainAct);
        try {
            current_x = START_POSITION;
            current_y = START_POSITION;
            positionRecord = 1;
            robotSteppedOut = true; // is at Door?
            positionRecordText = mainAct.findViewById(R.id.currentPositionText);
        } catch (NullPointerException e1) {
            current_x = START_POSITION;
            current_y = START_POSITION;
            positionRecord = 0;
            robotSteppedOut = true; // is at Door?
        }
        if (isResetGameExit) {
            robotV = findViewImgId(this.mainAct, robotView[current_x]);
            robotV.setImageResource(R.drawable.robot);
            btnExit.setBackgroundColor(Color.RED);
            btnExit.setTextColor(Color.BLACK); // contrast colors
            positionRecordText.setText(String.format("Position: Total = %s, X = %s, Y = %s,",
                    positionRecord, current_x % MAZE_WIDTH, current_y));
            for (int i = 2; i < END_POSITION; i++) {
                imgSteppedV = findViewImgId(this.mainAct, imgView[i]);
                imgSteppedV.setImageResource(R.drawable.white_non_stepped);
            }
            Bomb.isTrickExit = true;
        }
    }

    RobotPath(MainActivity mainAct) {
        takeUIcontrol = false;
        this.mainAct = mainAct;
        levelCounter++;
        alternateExit = 99;
        spawn = new Spawn(levelCounter, this.mainAct);
        //alternateExit = spawn.mainAct.robot.alternateExit;
        btnExit.setVisibility(View.INVISIBLE);
        levelName = mainAct.findViewById(R.id.levelName);
        levelName.setVisibility(View.VISIBLE);
        levelName.setText(String.format("LEVEL: %d", levelCounter));
        initLevelDisplay(this.mainAct);
    }

    private void initLevelDisplay(MainActivity mainAct) {
        overlayLevel = mainAct.findViewById(R.id.overlayLevel);
        overlayLevel.setVisibility(View.VISIBLE);
        overlayLevel.setAlpha(1f);
        overlayLevel.animate()
                .alpha(0f)
                .setDuration(2000L).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        levelName.setVisibility(View.INVISIBLE);
                        btnExit.setVisibility(View.VISIBLE);
                        takeUIcontrol = true;
                    }
                });
    }

    /**
     * Moves The Robot x=x+1 OR x=x-1 same for y AND sets images appropriate swapping
     *
     * @param idButton view.getid() of Component from UI;
     */
    void calculatePathId(int idButton) {
        if (takeUIcontrol) {
            switch (idButton) {
                case R.id.buttonUP:
                    if (current_y > START_POSITION) {
                        moveToHeight = true;
                        moveToWidth = false;
                        current_y--;
                        current_x = current_x - MAZE_WIDTH;
                        calculatePath(this.mainAct);
                        robotV = findViewImgId(this.mainAct, robotView[current_x + MAZE_WIDTH]);
                        robotV.setImageResource(R.drawable.empty_alpha);
                    }
                    break;
                case R.id.buttonDOWN:
                    if (current_y < MAZE_HEIGHT) {
                        moveToHeight = true;
                        moveToWidth = false;
                        current_y++;
                        current_x = current_x + MAZE_WIDTH;
                        calculatePath(this.mainAct);
                        robotV = findViewImgId(this.mainAct, robotView[current_x - MAZE_WIDTH]);
                        robotV.setImageResource(R.drawable.empty_alpha);
                    }
                    break;
                case R.id.buttonLEFT:
                    if (current_x > START_POSITION && mazeEdgesLeft(current_x)) {
                        moveToHeight = false;
                        moveToWidth = true;
                        current_x--;
                        calculatePath(this.mainAct);
                        robotV = findViewImgId(this.mainAct, robotView[current_x + 1]);
                        robotV.setImageResource(R.drawable.empty_alpha);
                    }
                    break;
                case R.id.buttonRIGHT:
                    if (current_x <= END_POSITION && mazeEdgesRight(current_x)) {
                        moveToHeight = false;
                        moveToWidth = true;
                        current_x++;
                        calculatePath(this.mainAct);
                        robotV = findViewImgId(this.mainAct, robotView[current_x - 1]);
                        robotV.setImageResource(R.drawable.empty_alpha);
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + idButton);
            }
        }
    }

    /**
     * Edges Detection of Right Side and Left in order to avoid tile-wrapping/Jumping from 6To7 etc..
     *
     * @param MaxEdgeCurrent All Right Edges
     * @return If zero means you are on the Edge Right Side If 1 then you are at on the Left Side
     */
    boolean mazeEdgesRight(int MaxEdgeCurrent) {
        int temp = END_POSITION / MAZE_WIDTH;
        return MaxEdgeCurrent % temp != 0;
    }

    // same for Left side it always equals 1
    boolean mazeEdgesLeft(int MaxEdgeCurrent) {
        int temp = END_POSITION / MAZE_WIDTH;
        return MaxEdgeCurrent % temp != 1;
    }

    /**
     * Sub-Function of proper image assign/swap on UI tiles
     */
    void calculatePath(MainActivity mainAct) {
        if ((imgSteppedV != null) &&
                imgSteppedV.getDrawable().getCurrent().equals(R.drawable.green_stepped)) {
            robotV = findViewImgId(mainAct, robotView[(current_x + current_y)]);
            robotV.setImageResource(R.drawable.robot);
        } else {
            if (current_x != END_POSITION && current_x != alternateExit && current_x != Bomb.bombPosition_x_trick) {
                if (!robotSteppedOut) {
                    // Exit OFF
                    btnExit.setBackgroundColor(Color.RED);
                    btnExit.setTextColor(Color.BLACK);
                    robotSteppedOut = true;
                }
                if (moveToHeight && !moveToWidth && !this.mainAct.robot.spawn.hasStepped()) {
                    // Move Robot to Green tile
                    imgSteppedV = findViewImgId(mainAct, imgView[current_x]);
                    imgSteppedV.setImageResource(R.drawable.green_stepped);
                    robotV = findViewImgId(mainAct, robotView[current_x]);
                    robotV.setImageResource(R.drawable.robot);
                } else if (!moveToHeight && moveToWidth && !this.mainAct.robot.spawn.hasStepped()) {
                    // Move Robot to Green tile
                    imgSteppedV = findViewImgId(mainAct, imgView[current_x]);
                    imgSteppedV.setImageResource(R.drawable.green_stepped);
                    robotV = findViewImgId(mainAct, robotView[current_x]);
                    robotV.setImageResource(R.drawable.robot);
                } else {
                    if (this.mainAct.robot.spawn.hasStepped()) {
                        kaboom(mainAct);
                    }
                    kaboom(mainAct);
                }
            } else {
                boolean allow = true;
                for (Bomb bomb : spawn.bombList
                ) {
                    if (current_x == alternateExit) {
                        if (isExitAltered) {
                            takeUIcontrol = false;
                            bomb.bombImgSpawnedTrickExit = findViewImgId(mainAct, bomb.bombView[alternateExit]);
                            bomb.bombImgSpawnedTrickExit.setImageResource(R.drawable.red_stepped);
                            allow = false;
                            kaboom(mainAct);
                        }
                    }
                }
                if (isExitAltered && current_x == Bomb.bombPosition_x_trick) {
                    exitButtonOn();
                } else if (!isExitAltered && current_x == Bomb.bombPosition_x_trick) {
                    takeUIcontrol = false;
                    spawn.bomb.bombImgSpawnedTrickExit = findViewImgId(mainAct, spawn.bomb.bombView[alternateExit]);
                    spawn.bomb.bombImgSpawnedTrickExit.setImageResource(R.drawable.red_stepped);
                    allow = false;
                    kaboom(mainAct);
                }
                if (allow) {
                    exitButtonOn();
                }
            }
        }
        if (positionRecordText == null)
            positionRecordText = mainAct.findViewById(R.id.currentPositionText);
        positionRecord = current_x;
        // current_x for the program is just a huge single line the way we did it but we ended up splitting it up in cols and tuples/rows
        if (current_x % (MAZE_WIDTH) == 0)
            positionRecordText.setText(String.format("Position: Total = %s, X = %s, Y = %s,",
                    positionRecord, "6", current_y));
        else positionRecordText.setText(String.format("Position: Total = %s, X = %s, Y = %s,",
                positionRecord, current_x % MAZE_WIDTH, current_y));
    }

    // get view image id number in order to load the image on it (component/tile)
    static ImageView findViewImgId(AppCompatActivity activity, int imageNum) {
        return (ImageView) activity.findViewById(imageNum);
    }

    // stepped on BOMB KABOOOOOM
    private void kaboom(MainActivity mainAct) {
        /*Ads RewardedAd*/
        if (mainAct.getmRewardedAd() != null) {
            mainAct.getmRewardedAd().show(mainAct, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // Handle the reward.
                    Log.d(mainAct.TAG, "The user earned the reward.");
                    int rewardAmount = rewardItem.getAmount();
                    String rewardType = rewardItem.getType();
                }
            });
            if (doOnce) {
                byPassedKaboom = true;
            }
            doOnce = false;
        } else {
            Log.d(mainAct.TAG, "The rewarded ad wasn't ready yet.");
        }
        if (!byPassedKaboom) {
            takeUIcontrol = false;
            imgSteppedV = findViewImgId(mainAct, imgView[current_x]);
            for (Bomb bomb : spawn.bombList
            ) {
                if (current_x == bomb.bombPosition_x) {
                    bomb.bombImgSpawned.setImageResource(R.drawable.bomb_inactive);
                }
            }
            robotSteppedOut = false;
            levelCounter = 0;
            AnimatorListenerAdapter anim = new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    imgSteppedV.setImageResource(R.drawable.bomb_explode_stagetwo);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    levelName.setVisibility(View.INVISIBLE);
                    btnExit.setVisibility(View.VISIBLE);
                    mainAct.resetGame();
                }
            };
            imgSteppedV.animate()
                    .setDuration(700L)
                    .alpha(1f)
                    .setListener(anim);
        } else {
            takeUIcontrol = true;
        }
    }

    // Exit ON
    private void exitButtonOn() {
        btnExit.setBackgroundColor(Color.GREEN);
        btnExit.setTextColor(Color.WHITE);
        robotSteppedOut = false;
    }
}
