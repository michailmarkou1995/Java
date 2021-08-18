package com.mime.minefront.input;

import com.mime.minefront.Display;
import com.mime.minefront.RunGame;
import com.mime.minefront.ThreadCheck;
import com.mime.minefront.entity.mob.Mob;
import com.mime.minefront.graphics.Render3D;
import com.mime.minefront.gui.Pause;

import java.awt.*;

public class PlayerController extends Mob {

    //to x kai y pige sto Entity edw mono y for jump
    public static double y, rotation, xa, za, rotationa, rotationUpa, rotationUp = 1, distanceTravel = 0.0, distanceTravelX = 0.0, distanceTravelY = 0.0;//rest On Entity which this class extends Mob to Entity link
    public static boolean turnLeftM = false, turnRightM = false, jumped = false, jumpAgain = true, turnUpM = false, turnDownM = false, not_paused = true;
    public static long timeJ, timeJ1;
    public static boolean walk = false;
    public static boolean crouchWalk = false;
    public static boolean runWalk = false;
    public static boolean jumpedStop = false;
    public static Pause Pause_Menu;
    Robot robot;
    ThreadCheck threadCheck = new ThreadCheck();
    boolean jumpIf = false;
    boolean[] key;

    private InputHandler input;

    public PlayerController(InputHandler input) {
        this.input = input;
    }

    public PlayerController() {

    }

    public void tick() {
        double rotationSpeed = 0.1;//myWay 0.1
        double rotationSpeed1 = 0.002 * Display.MouseSpeed;//theCherno ... calc and Y for correct but wont work good for X Y only for X
        double walkSpeed = 0.35;
        double jumpHeight = 0.4;
        double crouchHeight = 0.35;
        double xMove = 0;
        double zMove = 0;
        double xa = 0, za = 0;//int

        //if this NOT comment then Key left right wont work obviously need another var or literal int val
        if (Display.mouseSpeed < 3 && Display.mouseSpeed > 0
                && Display.mouseSpeed != 0) rotationSpeed *= 0.05;
        if (Display.mouseSpeed >= 3) rotationSpeed *= 0.5;
        if (Display.mouseSpeed < 0 && Display.mouseSpeed >= -3
                && Display.mouseSpeed != 0) rotationSpeed *= 0.05;
        if (Display.mouseSpeed < -3) rotationSpeed *= 0.5;
        if (Display.mouseSpeed == 0) rotationSpeed *= 0.0;


        if (input.esc) {//&& !InputHandler.KeyPressedButton
            if (InputHandler.KeyPressedButton.compareAndSet(true, false)) {

                not_paused = false;
                //display.renderMenu();
                RunGame.getGameInstance().render();/************/ //this method for terminate use thread
                //Pause_Menu= new Pause();
                new Pause();//here is ok but above Graphics error because RunGame.getGameInstance().render() called afterwards
            }

        }

        if (input.regenH) {

        }

        if (input.forward && !Render3D.wallHit) {
            //zMove++;
            za++;
            walk = true;
        }

        if (input.back) {
            za--;
            walk = true;
        }

        if (input.left) {
            xa--;
            walk = true;
        }

        if (input.right) {
            xa++;
            walk = true;
        }

        if (input.turnLeft) {

            rotationa -= 0.044;
        }

        if (input.turnRight) {

            rotationa += 0.044;

        }

        if (turnLeftM) {
            rotationa -= rotationSpeed;
        }

        if (turnRightM) {
            rotationa += rotationSpeed;
        }

        if (turnUpM) {
            rotationUpa += rotationSpeed;
        }

        if (turnDownM) {
            rotationUpa -= rotationSpeed;
        }

        if (input.crouch) {
            y -= crouchHeight;
            input.sprint = false;
            crouchWalk = true;
            walkSpeed = 0.1;
        }

        if (!input.crouch) {
            crouchWalk = false;
        }


        if (input.sprint) {
            walkSpeed = 1;
            walk = true;
            runWalk = true;
        }

        if (!input.sprint) {
            runWalk = false;
        }

        if (!input.forward && !input.back && !input.left && !input.right) {
            walk = false;
            //Render3D.wallHit=false;
        }

        if (!input.jump) {
            jumpedStop = false;
        }

        if (input.jump) {

            if (jumped == false) {

                for (double i = 0.0; i <= 3.5; i++) {//i+=0.001 //not 4 very fast and bad

                    y += i;
                }
                jumped = true;
            }
        }


        distanceTravelX = x;
        distanceTravelY = z;

        rotation += rotationa;
        rotationa *= 0.5;

        rotationUp += rotationUpa;
        rotationUpa *= 0.2;
        if (xa != 0 || za != 0) {
            move(xa, za, rotation, walkSpeed);
        }

        y *= 0.9;//max height can reach

    }


    public void tickOld(boolean forward, boolean back, boolean left
            , boolean right, boolean turnLeft, boolean turnRight
            , boolean jump, boolean crouch, boolean sprint, boolean regenH, boolean esc) {
        double rotationSpeed = 0.1;//myWay 0.1
        double rotationSpeed1 = 0.002 * Display.MouseSpeed;//theCherno ... calc and Y for correct but wont work good for X Y only for X
        double walkSpeed = 0.35;
        double jumpHeight = 0.4;
        double crouchHeight = 0.35;
        double xMove = 0;
        double zMove = 0;
        double xa = 0, za = 0;//int

        //if this NOT comment then Key left right wont work obviously need another var or literal int val
        if (Display.mouseSpeed < 3 && Display.mouseSpeed > 0
                && Display.mouseSpeed != 0) rotationSpeed *= 0.05;
        if (Display.mouseSpeed >= 3) rotationSpeed *= 0.5;
        if (Display.mouseSpeed < 0 && Display.mouseSpeed >= -3
                && Display.mouseSpeed != 0) rotationSpeed *= 0.05;
        if (Display.mouseSpeed < -3) rotationSpeed *= 0.5;
        if (Display.mouseSpeed == 0) rotationSpeed *= 0.0;

        if (esc) {//&& !InputHandler.KeyPressedButton
            if (InputHandler.KeyPressedButton.compareAndSet(true, false)) {
                not_paused = false;
                RunGame.getGameInstance().render();/************/ //this method for terminate use thread
                new Pause();//here is ok but above Graphics error because RunGame.getGameInstance().render() called afterwards
            }
        }

        if (regenH) {

        }

        if (forward && !Render3D.wallHit) {
            //zMove++;
            za++;
            walk = true;
            //distanceTravel++;
        }

        if (back) {
            //zMove--;
            za--;
            walk = true;
            //distanceTravel--;
        }

        if (left) {
            //xMove--;
            xa--;
            walk = true;
        }

        if (right) {
            //xMove++;
            xa++;
            walk = true;
        }

        if (turnLeft) {
//			if (InputHandler.MouseButton == 3) {
//				
//			} else {
//				rotationa -= rotationSpeed;
//			}
            rotationa -= 0.044;
        }

        if (turnRight) {
//		if (InputHandler.MouseButton == 3) {
//				//Debug only hold
//			} else {
//				rotationa += rotationSpeed;//rotationSpeed
//			}
            rotationa += 0.044;
        }

        if (turnLeftM) {
            rotationa -= rotationSpeed;
        }

        if (turnRightM) {
            rotationa += rotationSpeed;
        }

        if (turnUpM) {
            rotationUpa += rotationSpeed;
        }

        if (turnDownM) {
            rotationUpa -= rotationSpeed;
        }

        if (crouch) {
            y -= crouchHeight;
            sprint = false;
            crouchWalk = true;
            walkSpeed = 0.1;
        }

        if (!crouch) {
            crouchWalk = false;
        }


        if (sprint) {
            walkSpeed = 1;
            walk = true;
            runWalk = true;
        }

        if (!sprint) {
            runWalk = false;
        }

        if (!forward && !back && !left && !right) {
            walk = false;
        }

        if (!jump) {
            jumpedStop = false;
        }
        //one jump but fast
        if (jump) {
            if (jumped == false) {
                //like counter FPS but not very precise in update "wise" like real FPS on update method per tick
                //if(timeJ % 100 == 0 )
                for (double i = 0.0; i <= 3.5; i++) {//i+=0.001 //not 4 very fast and bad
                    y += i;
                }
                jumped = true;
            }
        }
        distanceTravelX = x;
        distanceTravelY = z;

        //move left right
        rotation += rotationa;
        rotationa *= 0.5;
        rotationUp += rotationUpa;
        rotationUpa *= 0.2;
        if (xa != 0 || za != 0) {
            move(xa, za, rotation, walkSpeed);
        }
        y *= 0.9;//max height can reach
    }
}
