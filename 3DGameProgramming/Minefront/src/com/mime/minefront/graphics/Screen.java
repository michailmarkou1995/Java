package com.mime.minefront.graphics;

import com.mime.minefront.Game;

import java.util.Random;

public class Screen extends Render {

    private Render test;
    private final Render3D render3d;

    public Screen(int width, int height) {
        super(width, height);
        Random random = new Random();
        render3d = new Render3D(width, height);
        test = new Render(256, 256);//256, 256
        for (int i = 0; i < (256 * 256); i++) {//256,256
            test.PIXELS[i] = random.nextInt() * (random.nextInt(5) / 4);  // transparency like
        }
    }

    /**
     * assets render final hierarchy
     */
    public void render(Game game) {

        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            PIXELS[i] = 0;
        }

        render3d.floor(game);
        render3d.renderDWall(-22, -22, 0, +1, 0);
        render3d.renderDWall(-25, -26, 2, 2, 0);
        render3d.renderDWall(-22, -21, 1, 1, 0);
        render3d.renderDWall(-24, -23, 2, 2, 0);
        render3d.renderDWall(-22, -22, 0, -1, 0);
        render3d.renderEnemy(1, 1, 0, 0);
        render3d.renderDistanceLimiter();
        draw(render3d, 0, 0);
    }

}
