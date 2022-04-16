package io.github.michailmarkou1995.robotmaze;

import android.widget.ImageView;

/**
 * Bomb itself as the object
 */
public class Bomb {
    public int bombNumber2;
    int[] bombView = {0, 0, R.id.bombView2, R.id.bombView3, R.id.bombView4, R.id.bombView5,
            R.id.bombView6, R.id.bombView7, R.id.bombView8, R.id.bombView9, R.id.bombView10,
            R.id.bombView11, R.id.bombView12, R.id.bombView13, R.id.bombView14, R.id.bombView15,
            R.id.bombView16, R.id.bombView17, R.id.bombView18, R.id.bombView19, R.id.bombView20,
            R.id.bombView21, R.id.bombView22, R.id.bombView23, R.id.bombView24, R.id.bombView25,
            R.id.bombView26, R.id.bombView27, R.id.bombView28, R.id.bombView29, R.id.bombView30,
            R.id.bombView31, R.id.bombView32, R.id.bombView33, R.id.bombView34, R.id.bombView35,
            R.id.bombView36None1};
    ImageView bombImgSpawned, bombImgSpawnedTrickExit, fakeBomb;
    int bombPosition_x;
    static int bombPosition_x_trick = 99;
    static boolean isTrickExit = true;

    Bomb(MainActivity mainAct) {
        int max = bombView.length - 2;
        int min = 3;
        int bombNumber = (int) Math.floor(Math
                .random() * (max - min) + min);
        bombPosition_x = bombNumber;
        if (!MainActivity.isDifficult) {
            if (bombNumber == bombPosition_x_trick) {
                bombImgSpawned = RobotPath.findViewImgId(mainAct, bombView[bombNumber]);
                bombImgSpawned.setImageResource(R.drawable.bomb_pre_explode);
            } else {
                bombImgSpawned = RobotPath.findViewImgId(mainAct, bombView[bombNumber]);
                bombImgSpawned.setImageResource(R.drawable.bomb_inactive);
            }
        } else {
            if (bombNumber != bombPosition_x_trick) {
                bombImgSpawned = RobotPath.findViewImgId(mainAct, bombView[bombNumber]);
                bombImgSpawned.setImageResource(R.drawable.empty_alpha); // bomb_inactive for testing
            } else {
                bombImgSpawned = RobotPath.findViewImgId(mainAct, bombView[bombNumber]);
                bombImgSpawned.setImageResource(R.drawable.bomb_pre_explode); // pre-explode for testing
            }
        }
    }

    public int adjustRandomExitHelper(int bombAt36, int bombN2) {
        int temp = (int) Math.floor(Math.random() * (4));
        if (temp == 0) {
            RobotPath.isExitAltered = false;
            return bombN2;
        } else {
            RobotPath.isExitAltered = true;
            return bombAt36;
        }
    }
}
