package io.github.michailmarkou1995.robotmaze;

import java.util.ArrayList;
import java.util.List;

/**
 * Handle Object Spawning of the Level
 */
public class Spawn {
    public final MainActivity mainAct;
    private final int levelCounter;
    List<Bomb> bombList = new ArrayList<>();
    Bomb bomb;

    Spawn(int levelCounter, MainActivity mainAct) {
        this.levelCounter = levelCounter;
        this.mainAct = mainAct;
        spawnBombs();
    }

    public void spawnBombs() {
        for (int i = 0; i <= levelCounter; i++) {
            if (levelCounter > 1) {
                bomb = new Bomb(this.mainAct);
                while (Bomb.isTrickExit) {
                    int max = bomb.bombView.length - 2;
                    int min = 3;
                    bomb.bombNumber2 = (int) Math.floor(Math
                            .random() * (max - min) + min);
                    Bomb.bombPosition_x_trick = bomb.bombNumber2;
                    Bomb.isTrickExit = false;
                    RobotPath.alternateExit = bomb.bombNumber2;
                    bomb.bombImgSpawnedTrickExit = RobotPath.findViewImgId(mainAct, bomb.bombView[RobotPath.alternateExit]);
                    bomb.bombImgSpawnedTrickExit.setImageResource(R.drawable.door_exit);
                    RobotPath.alternateExit = bomb.adjustRandomExitHelper(36, RobotPath.alternateExit);//5
                }
                bombList.add(bomb);
            } else {
                Bomb.isTrickExit = true;
                bomb = new Bomb(this.mainAct);
                bombList.add(bomb);
            }
        }
    }

    public void deSpawnBombs() {
        for (Bomb kaboom : bombList
        ) {
            kaboom.bombImgSpawned = RobotPath.findViewImgId(mainAct, kaboom.bombView[kaboom.bombPosition_x]);
            kaboom.bombImgSpawned.setImageResource(R.drawable.empty_alpha);
            if (Bomb.bombPosition_x_trick != 99) {
                kaboom.bombImgSpawnedTrickExit = RobotPath.findViewImgId(mainAct, kaboom.bombView[Bomb.bombPosition_x_trick]);
                kaboom.bombImgSpawnedTrickExit.setImageResource(R.drawable.empty_alpha);
            }

            if (RobotPath.alternateExit != 99) {
                kaboom.bombImgSpawnedTrickExit = RobotPath.findViewImgId(mainAct, kaboom.bombView[RobotPath.alternateExit]);
                kaboom.bombImgSpawnedTrickExit.setImageResource(R.drawable.empty_alpha);
            }
            mainAct.robot.imgSteppedV.setImageResource(R.drawable.door_exit);
            Bomb.bombPosition_x_trick = 99;
            RobotPath.alternateExit = 99;
        }
        bombList.clear();
    }

    public boolean hasStepped() {
        for (Bomb kaboom : bombList
        ) {
            if (kaboom.bombPosition_x == mainAct.robot.current_x)
                return true;
        }
        return false;
    }
}