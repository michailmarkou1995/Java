package com.mime.minefront.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResourceMini {

    BufferedImage image = null;
    int w, h;

    public BufferedImage loadImage(String path) {
        try {
            image = ImageIO.read(ResourceMini.class.getResource(path));
            this.w = image.getWidth();
            this.h = image.getHeight();
            return image;
        } catch (IOException e) {
            return null;
        }
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    //as integer
    public int getColorInt(int x, int y) {
        if (image == null) {
            return 0;
        }
        int col = image.getRGB(x, y);
        return col;
    }

    //as Hex
    public int getColorHex(int x, int y) {
        if (image == null) {
            return 0;
        }
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x > getWidth() - 1) x = getWidth() - 1;
        if (y > getHeight() - 1) y = getHeight() - 1;
        int col = image.getRGB(x, y);
        int red = (col & 0xFF0000) >> 16;
        int green = (col & 0x00FF00) >> 8;
        int blue = (col & 0x0000FF);
        return red << 16 | green << 8 | blue;
    }

}
