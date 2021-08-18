package com.mime.minefront;

import com.mime.minefront.input.InputHandler;
import com.mime.minefront.input.PlayerController;
import com.mime.minefront.level.Level;

public class Game {

    public int time;
    public PlayerController controls; /* controls == PLAYER kinda*/
    //public PlayerController controls_Player2;/**use ArrayList instead of creating new Players./mobs */
    /*public PlayerController[] players;*///if dies remove from array for memory save //you cant do it this way because array is not DYNAMIC no index out of bounds
    public Level level;
    public boolean jumpIf;
    ThreadCheck threadCheck;
    ThreadTest tt1;

    public Game(InputHandler input) {
        controls = new PlayerController(input);
        level = new Level(16, 16);
        level.addEntity(controls);//which is player !
        threadCheck = new ThreadCheck();

        tt1 = new ThreadTest();
    }

    public void tick() {//boolean[] key
        time++;
        level.update();//Old Player.tick
    }
}
