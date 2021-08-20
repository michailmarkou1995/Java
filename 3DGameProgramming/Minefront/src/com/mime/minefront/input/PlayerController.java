package com.mime.minefront.input;

import com.mime.minefront.Display;
import com.mime.minefront.HealthRecoveryThread;
import com.mime.minefront.RunGame;
import com.mime.minefront.entity.mob.Mob;
import com.mime.minefront.graphics.Render3D;
import com.mime.minefront.gui.Pause;

public class PlayerController extends Mob {

    //to x kai y pige sto Entity edw mono y for jump
    public static double y, rotation, xa, za, rotationa, rotationUpa, rotationUp = 1, distanceTravel = 0.0,
            distanceTravelX = 0.0, distanceTravelY = 0.0;//rest On Entity which this class extends Mob to Entity link
    public static boolean turnLeftM = false;
    public static boolean turnRightM = false;
    public static boolean jumped = false;
    public static boolean turnUpM = false;
    public static boolean turnDownM = false;
    public static boolean not_paused = true;
    public static long timeJ1;
    public static boolean walk = false;
    public static boolean crouchWalk = false;
    public static boolean runWalk = false;
    public static boolean jumpedStop = false;
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
        double crouchHeight = 0.35;
        double xa = 0, za = 0;

        // Mouse Sensitivity on rotation axis
        if (Display.mouseSpeed < 3 && Display.mouseSpeed > 0) rotationSpeed *= 0.05;
        if (Display.mouseSpeed >= 3) rotationSpeed *= 0.5;
        if (Display.mouseSpeed < 0 && Display.mouseSpeed >= -3) rotationSpeed *= 0.05;
        if (Display.mouseSpeed < -3) rotationSpeed *= 0.5;
        if (Display.mouseSpeed == 0) rotationSpeed *= 0.0;


        if (input.esc) {
            if (InputHandler.KeyPressedButton.compareAndSet(true, false)) {
                not_paused = false;
                RunGame.getGameInstance().render();
                new Pause();
            }
        }

        if (input.regenH) {
            HealthRecoveryThread tt1 = new HealthRecoveryThread();
            System.out.println(tt1.getName()); // Regen Health Console output
            tt1.start();
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
            if (!jumped) {
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
}
