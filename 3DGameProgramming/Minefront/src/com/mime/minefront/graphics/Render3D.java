package com.mime.minefront.graphics;

import com.mime.minefront.Game;
import com.mime.minefront.entity.Entity;
import com.mime.minefront.input.PlayerController;
import com.mime.minefront.level.Block;
import com.mime.minefront.level.Level;

import java.util.Random;

/**
 * <h2>calc's for pixel formation</h2>
 */
public class Render3D extends Render {

    public static boolean wallHit, wallHitInverse;
    public static int test;
    private final int spriteSheetWidth = 24;
    private final int spriteSheetWidthEnemy = 52;
    public double[] zBuffer;
    public double[] zBufferWall;
    Random random = new Random();
    int c = 0;
    double h = 0.5;
    private double renderDistance = 5000;  // Distance of View
    private double forwardGlobal;
    private double forward, right, cosine, sine, up, walking, rotationUp, cosine1, sine1;

    public Render3D(int width, int height) {
        super(width, height);
        zBuffer = new double[width * height];
        zBufferWall = new double[width];
    }

    // Create Celling + floor by manipulating pixels
    public void floor(Game game) {

        for (int x = 0; x < WIDTH; x++) {
            zBufferWall[x] = 0;
        }

        forward = Entity.z;
        forwardGlobal = forward;
        right = Entity.x;
        double floorPosition = 8;
        double ceilPosition = 16;  // put bigger than RenderDistanceLimiter e.g. 800 Ceil disappear
        double rotation = PlayerController.rotation;  // 0;  // Math.sin(game.time % 1000.0 / 80.0);  // game.controls.rotation;  // game.time % 100.0 /10.0; // Math.sin(game.time % 1000.0 / 80.0); // Math.sin(game.time / 40.0) * 0.5;
        rotationUp = PlayerController.rotationUp;
        cosine1 = Math.cos(rotationUp);
        sine1 = Math.sin(rotationUp);
        cosine = Math.cos(rotation);  // double
        sine = Math.sin(rotation);
        up = PlayerController.y;
        walking = 0;


        for (int y = 0; y < HEIGHT; y++) {

            double yDepthCeiling;
            if (!PlayerController.turnUpM && !PlayerController.turnDownM) {
                yDepthCeiling = (y - HEIGHT / 2.0) / HEIGHT;
            }
            yDepthCeiling = (y - HEIGHT / 2.0 * rotationUp) / HEIGHT;

            double z = (floorPosition + up) / yDepthCeiling;  // +wallking
            c = 0;
            if (PlayerController.walk) {
                walking = Math.sin(game.time / 6.0) * 0.4;
                z = (floorPosition + up + walking) / yDepthCeiling;  // +wallking
            }
            if (PlayerController.jumpedStop) {
                up = 0;
            }
            if (PlayerController.crouchWalk && PlayerController.walk) {
                walking = Math.sin(game.time / 6.0) * 0.2;
                z = (floorPosition + up + walking) / yDepthCeiling;
            }
            if (PlayerController.runWalk && PlayerController.walk) {
                walking = Math.sin(game.time / 6.0) * 0.7;
                z = (floorPosition + up + walking) / yDepthCeiling;
            }


            if (yDepthCeiling < 0) {
                z = (ceilPosition - up) / -yDepthCeiling;//z = -yDepthCeiling wipes ceiling
                c = 1;
                if (PlayerController.walk) {
                    z = (ceilPosition - up - walking) / -yDepthCeiling;//+wallking
                }
            }
            //System.out.println(up);
            for (int x = 0; x < WIDTH; x++) {
                double xDepth = (x - WIDTH / 2.0) / HEIGHT;
                xDepth *= z;
                double xx = (xDepth * cosine + z * sine) * 1; //change
                double yy = (z * cosine - xDepth * sine) * 1;
                int xPix = (int) ((xx + right) * 4);// * 4 to for 128 tex size to decrease the size but keep high resolution BECAUSE 4 times bigger
                int yPix = (int) ((yy + forward) * 4);
                zBuffer[x + y * WIDTH] = z;
                //PIXELS[x+y*WIDTH] = ((xPix & 15)<<4) | ((yPix & 15)<<4) << 8;
                int spriteSheetWidthHigh = 128;
                if (c == 0)
                    //PIXELS[x+y*WIDTH] = Texture.floor.PIXELS[(xPix & 7) + (yPix & 7) * spriteSheetWidth];	//from 8 to * 16 in the end now width
                    PIXELS[x + y * WIDTH] = Texture.blocks.PIXELS[((xPix & 30) + 0) + (yPix & 30) * spriteSheetWidthHigh];    //from 8 to * 16 in the end now width // 32 offset see in .NET paint
                else
                    //PIXELS[x+y*WIDTH] = Texture.floor.PIXELS[((xPix & 7)+8) + (yPix & 7) * spriteSheetWidth];	//from 8 to * 16 in the end now width
                    PIXELS[x + y * WIDTH] = Texture.blocks.PIXELS[((xPix & 30)) + (yPix & 30) * spriteSheetWidthHigh];    //from 8 to * 16 in the end now width

                //limit Render Distance and the renderDistanceLimiter() is just smoothing the brigthness
                if (z > 400) {//x,y <,> etc 50 100
                    PIXELS[x + y * WIDTH] = 0;
                }
            }
        }
        //wall();
        Level level = game.level;
        int size = 20;//20
        for (int xBlock = -size; xBlock <= size; xBlock++) {
            for (int zBlock = -size; zBlock <= size; zBlock++) {
                Block block = level.create(xBlock, zBlock);
                Block east = level.create(xBlock + 1, zBlock);
                Block south = level.create(xBlock, zBlock + 1);
                //System.out.println(block.block_overlap.toString());

                //System.out.println(Arrays.toString(block.block_overlap));
                //MAZE like GENERATION
                if (block.solid) {
                    if (!east.solid) {
                        renderWallRandom(xBlock + 1, xBlock + 1, zBlock, zBlock + 1, 0);
                        //Block blockCoords = new Block();
                        east.addCoords(xBlock + 1, xBlock + 1, zBlock, zBlock + 1);
                        block.addBlock(east);

                    }
                    if (!south.solid) {
                        renderWallRandom(xBlock + 1, xBlock, zBlock + 1, zBlock + 1, 0);
                        //block.addCoords(xBlock + 1, xBlock, zBlock + 1, zBlock + 1);

                        south.addCoords(xBlock + 1, xBlock, zBlock + 1, zBlock + 1);
                        block.addBlock(south);
                    }
                } else {
                    if (east.solid) {
                        renderWallRandom(xBlock + 1, xBlock + 1, zBlock + 1, zBlock, 0);
                        //block.addCoords(xBlock + 1, xBlock + 1, zBlock + 1, zBlock);

                        east.addCoords(xBlock + 1, xBlock + 1, zBlock + 1, zBlock);
                        block.addBlock(east);
                    }
                    if (south.solid) {
                        renderWallRandom(xBlock, xBlock + 1, zBlock + 1, zBlock + 1, 0);
                        //block.addCoords(xBlock, xBlock + 1, zBlock + 1, zBlock + 1);

                        south.addCoords(xBlock, xBlock + 1, zBlock + 1, zBlock + 1);
                        block.addBlock(south);
                    }
                }
            }
        }

        for (int xBlock = -size; xBlock <= size; xBlock++) {
            for (int zBlock = -size; zBlock <= size; zBlock++) {
                Block block = level.create(xBlock, zBlock);
                Block east = level.create(xBlock + 1, zBlock);
                Block south = level.create(xBlock, zBlock + 1);

                if (block.solid) {
                    if (!east.solid) {
                        renderWallRandom(xBlock + 1, xBlock + 1, zBlock, zBlock + 1, 0.5);
                    }
                    if (!south.solid) {
                        renderWallRandom(xBlock + 1, xBlock, zBlock + 1, zBlock + 1, 0.5);
                    }
                } else {
                    if (east.solid) {
                        renderWallRandom(xBlock + 1, xBlock + 1, zBlock + 1, zBlock, 0.5);
                    }
                    if (south.solid) {
                        renderWallRandom(xBlock, xBlock + 1, zBlock + 1, zBlock + 1, 0.5);
                    }
                }
            }
        }

        for (int xBlock = -size; xBlock <= size; xBlock++) {
            for (int zBlock = -size; zBlock <= size; zBlock++) {
                Block block = level.create(xBlock, zBlock);
                for (int s = 0; s < block.sprites.size(); s++) {
                    Sprite sprite = block.sprites.get(s);
                    renderSprite(xBlock + sprite.x, sprite.y, zBlock + sprite.z, h);
                }
            }
        }
    }

    //pixels further gets less brightness
    public void renderDistanceLimiter() {
        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            int color = PIXELS[i];
            int brightness = (int) (renderDistance / (zBuffer[i])); //System.out.println(brightness);

            if (brightness < 0) {//0-255 brightness e.g. photoshop or .net paint
                brightness = 0;
            }
            if (brightness > 255) {
                brightness = 255;
            }

            int r = (color >> 16) & 0xff;// >> is "/" , << is "*"
            int g = (color >> 8) & 0xff;
            int b = (color) & 0xff;

            r = r * brightness >>> 8; // / 255;
            g = g * brightness >>> 8; // / 255;
            b = b * brightness >>> 8; // / 255;

            //modified pixels basically brightness adjusted below put them
            PIXELS[i] = r << 16 | g << 8 | b;// << is * 2^x ==255? e.g. is 8.. 2^x==? 255!
        }
    }

    //sprite 2.5 in 3D world
    public void renderSprite(double x, double y, double z, double hOffset) {
        double upCorrect = -0.125;//-0.0625;// moving with 0.0625 good for gathering lifes?
        double rightCorrect = 0.0625;
        double forwardCorrect = 0.0625;
        double walkingCorrect = +0.0625;


        double xc = ((x / 2) - (right * rightCorrect)) * 2 + 0.5;
        double yc = ((y / 2) - (up * upCorrect)) + (walking * walkingCorrect) * 2 + hOffset;//sprite up down on walk move with "player"
        double zc = ((z / 2) - (forward * forwardCorrect)) * 2;

        double rotX = xc * cosine - zc * sine;
        double rotY = yc;
        double rotZ = zc * cosine + xc * sine;

        double xCenter = 400.0;
        double yCenter = 300.0;

        //System.out.println(rotationUp);
        double xPixel = rotX / rotZ * HEIGHT + xCenter * 1; //fixes moving Spirtes Y axis
        double yPixel = rotY / rotZ * HEIGHT + yCenter * rotationUp;

        //** Resolution of "Tile"
        double xPixelL = xPixel - HEIGHT / 2.0 / rotZ; //16 is size of Sprites
        double xPixelR = xPixel + HEIGHT / 2.0 / rotZ;//sprite WIDTH change here e.g. /1 stretch
        double yPixelL = yPixel - HEIGHT / 2.0 / rotZ;
        double yPixelR = yPixel + HEIGHT / 2.0 / rotZ;

        //pixels are ints
        int xpl = (int) xPixelL;//x pixel left
        int xpr = (int) xPixelR;
        int ypl = (int) yPixelL;
        int ypr = (int) yPixelR;

        //clipping
        if (xpl < 0) xpl = 0;
        if (xpr > WIDTH) xpr = WIDTH;
        if (ypl < 0) ypl = 0;
        if (ypr > HEIGHT) ypr = HEIGHT;

        rotZ *= 8;//increase zBuffer "decrease" brightness

        //rendering 8x8 no matter the Resolution**
        for (int yp = ypl; yp < ypr; yp++) {
            double pixelRotationY = (yp - yPixelR) / (yPixelL - yPixelR);
            int yt = (int) (pixelRotationY * 8);
            for (int xp = xpl; xp < xpr; xp++) {
                double pixelRotationX = (xp - xPixelR) / (xPixelL - xPixelR);
                int xTexture = (int) (pixelRotationX * 8);//change coords HERE for Texture location applied
                //int yTexture = (int) (8 * pixelRotationY);
                if (zBuffer[xp + yp * WIDTH] > rotZ) {
                    int col = Texture.floor.PIXELS[((xTexture & 7) + 16) + (yt & 7) * spriteSheetWidth];
                    if (col != 0xFFFF00FF) {//FF00FF pink
                        //PIXELS[xp + yp * WIDTH] = xTexture * 16 + yt * 16 * 256; // Gradient does not ovveride below
                        PIXELS[xp + yp * WIDTH] = Texture.floor.PIXELS[(int) (x + y * WIDTH)];//col;//0x00FFFF;//0x236DCF; //0xFF0000
                        zBuffer[xp + yp * WIDTH] = rotZ;
                    }
                }
            }
        }

    }


    public void renderWall(double xLeft, double xRight, double zDistanceLeft
            , double zDistanceRight, double yHeight) {
        double upCorrect = 0.0625;
        double rightCorrect = 0.0625;
        double forwardCorrect = 0.0625;
        double backCorrect = 0.0625;
        double walkingCorrect = -0.0625;
        double rotationUpCorrect = -0.0625;
        //System.out.println(Controller.distanceTravelY - zDistanceRight);//Controller.distanceTravelX - xLeft
        //System.out.println(Controller.distanceTravelX- (xLeft*8));//System.out.println(xLeft*8);
        //System.out.println((Controller.distanceTravelY - (zDistanceLeft*8)));
        //System.out.println(Controller.distanceTravelX- (xRight*8));
        //System.out.println((Controller.distanceTravelY - (zDistanceRight*8)));
        //System.out.println(yPixelLeftTop);
        double collisionX = PlayerController.distanceTravelX;
        double collisionY = PlayerController.distanceTravelY;
        //wallHit=false;
        //if(Controller.distanceTravelY - zDistanceLeft <= 0.9 &&
//		 if(Controller.distanceTravelX - (xLeft*8) <= 0.9 &&
//			Controller.distanceTravelX - (xLeft*8) >= +0.0 &&
//			Controller.distanceTravelY - (zDistanceLeft*8) <= 0.0 &&
//			Controller.distanceTravelY - (zDistanceLeft*8) >= -6 ||
//			Controller.distanceTravelX- (xRight*8) <=0.0 &&
//			Controller.distanceTravelX- (xRight*8) >= -8.0 &&
//			Controller.distanceTravelY - (zDistanceRight*8) <= 0.0 &&
//			Controller.distanceTravelY - (zDistanceRight*8) >= -6 ||
//			Controller.distanceTravelX - (xLeft*8) >= -0.9 &&
//			Controller.distanceTravelX - (xLeft*8) <= -0.0 &&
//			Controller.distanceTravelY - (zDistanceLeft*8) <= 0.0 &&
//			Controller.distanceTravelY - (zDistanceLeft*8) >= -6 ||
//			Controller.distanceTravelX- (xRight*8) >=0.0 &&
//			Controller.distanceTravelX- (xRight*8) <= 8.0 &&
//			Controller.distanceTravelY - (zDistanceRight*8) <= 0.0 &&
//			Controller.distanceTravelY - (zDistanceRight*8) >= -6
//			) {
//			//System.out.println("WATCH OUT");
//			wallHit = true;
//		} //else wallHit=false;

        //collisionDetection(xLeft,xRight,zDistanceLeft,zDistanceRight);

        ////Basic corner pins below
        //left side
        double xcLeft = ((xLeft / 2) - (right * rightCorrect)) * 2;//left calc of wall
        double zcLeft = ((zDistanceLeft / 2) - (forward * forwardCorrect)) * 2;

        double rotLeftSideX = xcLeft * cosine - zcLeft * sine; //System.out.println(cosine);
        double rotLeftSideX1 = xcLeft * cosine1 - zcLeft * sine1;
        double yCornerTL = ((-yHeight) - (-up * upCorrect + (walking * walkingCorrect))) * 2;//top left corner
        double yCornerBL = ((+0.5 - yHeight) - (-up * upCorrect + (walking * walkingCorrect))) * 2;
        double rotLeftSideZ = zcLeft * (cosine * 1) + xcLeft * (sine * 1);
        double rotLeftSideZ1 = zcLeft * cosine1 + xcLeft * sine1;
        //////
        //right side
        double xcRight = ((xRight / 2) - (right * rightCorrect)) * 2;//left calc of wall
        double zcRight = ((zDistanceRight / 2) - (forward * forwardCorrect)) * 2;

        double rotRightSideX = xcRight * (cosine * 1) - zcRight * (sine * 1);
        double rotRightSideX1 = xcRight * cosine1 - zcRight * sine1;
        double yCornerTR = ((-yHeight) - (-up * upCorrect + (walking * walkingCorrect))) * 2;//top left corner
        double yCornerBR = ((+0.5 - yHeight) - (-up * upCorrect + (walking * walkingCorrect))) * 2;
        double rotRightSideZ = zcRight * (cosine * 1) + xcRight * (sine * 1);
        double rotRightSideZ1 = zcRight * cosine1 + xcRight * sine1;
        //////
        //System.out.println(Controller.turnUpM);

        double tex30 = 0, tex40 = 8, clip = 0.5;

        if (rotLeftSideZ < clip && rotRightSideZ < clip) {
            return;
        }

        //clipping fix
        //Cohen-SutherLand Algorithm Line Cliping
        if (rotLeftSideZ < clip) {
            double clip0 = (clip - rotLeftSideZ) / (rotRightSideZ - rotLeftSideZ);
            rotLeftSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ) * clip0;
            rotLeftSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX) * clip0;
            tex30 = tex30 + (tex40 - tex30) * clip0;
        }
        if (rotRightSideZ < clip) {
            double clip0 = (clip - rotLeftSideZ) / (rotRightSideZ - rotLeftSideZ);
            rotRightSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ) * clip0;
            rotRightSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX) * clip0;
            tex40 = tex30 + (tex40 - tex30) * clip0;
        }
        //compute pixel location
        //left and right edges of wall
        double xPixelLeft, xPixelRight;
        xPixelLeft = (rotLeftSideX / rotLeftSideZ * HEIGHT + WIDTH / 2);
        xPixelRight = (rotRightSideX / rotRightSideZ * HEIGHT + WIDTH / 2);

        //protection of negative pixels e.g. the horizontal coordinates wall is bigger than right side
        if (xPixelLeft >= xPixelRight) {
            return;//no render
        }

        //convert pixels to int to fit square grid
        int xPixelLeftInt = (int) (xPixelLeft);
        int xPixelRightInt = (int) (xPixelRight);

        if (xPixelLeftInt < 0) {
            xPixelLeftInt = 0;
        }
        if (xPixelRightInt > WIDTH) {//Int? or xPixelRight
            xPixelRightInt = WIDTH;
        }

        //same as int yPixelLeftTop
//		double yPixelLeftTop = (int) (yCornerTL / rotLeftSideZ * HEIGHT + HEIGHT / 2);
//		//System.out.println(yPixelLeftTop);
//		double yPixelLeftBottom = (int) (yCornerBL / rotLeftSideZ * HEIGHT + HEIGHT / 2);
//		double yPixelRightTop = (int) (yCornerTR / rotRightSideZ * HEIGHT + HEIGHT / 2);
//		double yPixelRightBottom = (int) (yCornerBR / rotRightSideZ * HEIGHT + HEIGHT / 2);
//		
        //corner pins
        double yPixelLeftTop = (yCornerTL / rotLeftSideZ * HEIGHT + HEIGHT / 2.0 * rotationUp);//double yPixelLeftTop = (yCornerTL / rotLeftSideZ * (HEIGHT *( rotationUp/2)) + HEIGHT / 2.0 * rotationUp);
        double yPixelLeftBottom = (yCornerBL / rotLeftSideZ * HEIGHT + HEIGHT / 2.0 * rotationUp);
        double yPixelRightTop = (yCornerTR / rotRightSideZ * HEIGHT + HEIGHT / 2.0 * rotationUp);
        double yPixelRightBottom = (yCornerBR / rotRightSideZ * HEIGHT + HEIGHT / 2.0 * rotationUp);

        double tex1 = 1 / rotLeftSideZ;
        double tex2 = 1 / rotRightSideZ;
        double tex3 = tex30 / rotLeftSideZ;
        double tex4 = tex40 / rotRightSideZ - tex3;
        //System.out.println(tex4);
        for (int x = xPixelLeftInt; x < xPixelRightInt; x++) {
            if (!PlayerController.turnUpM && !PlayerController.turnDownM) {

            }
            collisionDetection(xLeft, xRight, zDistanceLeft, zDistanceRight);

            //System.out.println("Left: "+xPixelLeftInt + " RIGHT: " + xPixelRightInt);
            double pixelRotation = (x - xPixelLeft) / (xPixelRight - xPixelLeft);
            double zWall = (tex1 + (tex2 - tex1) * pixelRotation);

            if (zBufferWall[x] > zWall) {
                continue; //fix xray
            }
            zBufferWall[x] = zWall;//fix xray

            int xTexture = (int) ((tex3 + tex4 * pixelRotation) / zWall);

            double yPixelTop = yPixelLeftTop + (yPixelRightTop - yPixelLeftTop) * pixelRotation;
            double yPixelBottom = yPixelLeftBottom + (yPixelRightBottom - yPixelLeftBottom) * pixelRotation;

            int yPixelTopInt = (int) (yPixelTop);
            int yPixelBottomInt = (int) (yPixelBottom);

            if (yPixelTopInt < 0) {
                yPixelTopInt = 0;
            }
            if (yPixelBottomInt > HEIGHT) {
                yPixelBottomInt = HEIGHT;
            }
            int textureCoordinates = 8;
            for (int y = yPixelTopInt; y < yPixelBottomInt; y++) {
                try {
                    double pixelRotationY = (y - yPixelTop) / (yPixelBottom - yPixelTop);
                    int yTexture = (int) (8 * pixelRotationY);//change coords HERE for Texture location applied
                    //PIXELS[x+y*WIDTH] = xTexture * 100;//0x1B91E0;// HORIZONTAL TEXTURE not vertical here
                    //PIXELS[x+y*WIDTH] = xTexture * 100 + yTexture * 100 * 256;
                    //PIXELS[x+y*WIDTH] = Texture.floor.PIXELS[(xTexture & 7) + (yTexture & 7) * 8];
                    PIXELS[x + y * WIDTH] = Texture.floor.PIXELS[((xTexture & 7) + textureCoordinates) + (yTexture & 7) * spriteSheetWidth];    //16 for 8x16 tex size //change coords HERE for Texture location applied
                    zBuffer[x + y * WIDTH] = 1 / (tex1 + (tex2 - tex1) * pixelRotation) * 8;//100
                } catch (ArrayIndexOutOfBoundsException e) {//quick fix
                    e.printStackTrace();
                    continue;
                }
            }
        }
        //collisionDetection(xLeft,xRight,zDistanceLeft,zDistanceRight);
    }

    public void renderWallRandom(double xLeft, double xRight, double zDistanceLeft
            , double zDistanceRight, double yHeight) {
        double upCorrect = 0.0625;
        double rightCorrect = 0.0625;
        double forwardCorrect = 0.0625;
        double backCorrect = 0.0625;
        double walkingCorrect = -0.0625;
        double rotationUpCorrect = -0.0625;


        ////Basic corner pins below
        //left side
        double xcLeft = ((xLeft / 2) - (right * rightCorrect)) * 2;//left calc of wall
        double zcLeft = ((zDistanceLeft / 2) - (forward * forwardCorrect)) * 2;

        double rotLeftSideX = xcLeft * cosine - zcLeft * sine; //System.out.println(cosine);
        double rotLeftSideX1 = xcLeft * cosine1 - zcLeft * sine1;
        double yCornerTL = ((-yHeight) - (-up * upCorrect + (walking * walkingCorrect))) * 2;//top left corner
        double yCornerBL = ((+0.5 - yHeight) - (-up * upCorrect + (walking * walkingCorrect))) * 2;
        double rotLeftSideZ = zcLeft * (cosine * 1) + xcLeft * (sine * 1);
        double rotLeftSideZ1 = zcLeft * cosine1 + xcLeft * sine1;
        //////
        //right side
        double xcRight = ((xRight / 2) - (right * rightCorrect)) * 2;//left calc of wall
        double zcRight = ((zDistanceRight / 2) - (forward * forwardCorrect)) * 2;

        double rotRightSideX = xcRight * (cosine * 1) - zcRight * (sine * 1);
        double rotRightSideX1 = xcRight * cosine1 - zcRight * sine1;
        double yCornerTR = ((-yHeight) - (-up * upCorrect + (walking * walkingCorrect))) * 2;//top left corner
        double yCornerBR = ((+0.5 - yHeight) - (-up * upCorrect + (walking * walkingCorrect))) * 2;
        double rotRightSideZ = zcRight * (cosine * 1) + xcRight * (sine * 1);
        double rotRightSideZ1 = zcRight * cosine1 + xcRight * sine1;
        //////
        //System.out.println(Controller.turnUpM);

        double tex30 = 0, tex40 = 8, clip = 0.5;

        if (rotLeftSideZ < clip && rotRightSideZ < clip) {
            return;
        }

        //clipping fix
        //Cohen-SutherLand Algorithm Line Cliping
        if (rotLeftSideZ < clip) {
            double clip0 = (clip - rotLeftSideZ) / (rotRightSideZ - rotLeftSideZ);
            rotLeftSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ) * clip0;
            rotLeftSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX) * clip0;
            tex30 = tex30 + (tex40 - tex30) * clip0;
        }
        if (rotRightSideZ < clip) {
            double clip0 = (clip - rotLeftSideZ) / (rotRightSideZ - rotLeftSideZ);
            rotRightSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ) * clip0;
            rotRightSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX) * clip0;
            tex40 = tex30 + (tex40 - tex30) * clip0;
        }
        //compute pixel location
        //left and right edges of wall
        double xPixelLeft, xPixelRight;
        xPixelLeft = (rotLeftSideX / rotLeftSideZ * HEIGHT + WIDTH / 2);
        xPixelRight = (rotRightSideX / rotRightSideZ * HEIGHT + WIDTH / 2);

        //protection of negative pixels e.g. the horizontal coordinates wall is bigger than right side
        if (xPixelLeft >= xPixelRight) {
            return;//no render
        }

        //convert pixels to int to fit square grid
        int xPixelLeftInt = (int) (xPixelLeft);
        int xPixelRightInt = (int) (xPixelRight);

        if (xPixelLeftInt < 0) {
            xPixelLeftInt = 0;
        }
        if (xPixelRightInt > WIDTH) {//Int? or xPixelRight
            xPixelRightInt = WIDTH;
        }

        //corner pins
        double yPixelLeftTop = (yCornerTL / rotLeftSideZ * HEIGHT + HEIGHT / 2.0 * rotationUp);//double yPixelLeftTop = (yCornerTL / rotLeftSideZ * (HEIGHT *( rotationUp/2)) + HEIGHT / 2.0 * rotationUp);
        double yPixelLeftBottom = (yCornerBL / rotLeftSideZ * HEIGHT + HEIGHT / 2.0 * rotationUp);
        double yPixelRightTop = (yCornerTR / rotRightSideZ * HEIGHT + HEIGHT / 2.0 * rotationUp);
        double yPixelRightBottom = (yCornerBR / rotRightSideZ * HEIGHT + HEIGHT / 2.0 * rotationUp);

        double tex1 = 1 / rotLeftSideZ;
        double tex2 = 1 / rotRightSideZ;
        double tex3 = tex30 / rotLeftSideZ;
        double tex4 = tex40 / rotRightSideZ - tex3;
        for (int x = xPixelLeftInt; x < xPixelRightInt; x++) {
            if (!PlayerController.turnUpM && !PlayerController.turnDownM) {

            }
            //collisionDetection(xLeft,xRight,zDistanceLeft,zDistanceRight);

            double pixelRotation = (x - xPixelLeft) / (xPixelRight - xPixelLeft);
            double zWall = (tex1 + (tex2 - tex1) * pixelRotation);

            if (zBufferWall[x] > zWall) {
                continue; //fix xray
            }
            zBufferWall[x] = zWall;//fix xray

            int xTexture = (int) ((tex3 + tex4 * pixelRotation) / zWall);

            double yPixelTop = yPixelLeftTop + (yPixelRightTop - yPixelLeftTop) * pixelRotation;
            double yPixelBottom = yPixelLeftBottom + (yPixelRightBottom - yPixelLeftBottom) * pixelRotation;

            int yPixelTopInt = (int) (yPixelTop);
            int yPixelBottomInt = (int) (yPixelBottom);

            if (yPixelTopInt < 0) {
                yPixelTopInt = 0;
            }
            if (yPixelBottomInt > HEIGHT) {
                yPixelBottomInt = HEIGHT;
            }
            int textureCoordinates = 8;
            for (int y = yPixelTopInt; y < yPixelBottomInt; y++) {
                try {
                    double pixelRotationY = (y - yPixelTop) / (yPixelBottom - yPixelTop);
                    int yTexture = (int) (8 * pixelRotationY);
                    PIXELS[x + y * WIDTH] = Texture.floor.PIXELS[((xTexture & 7) + textureCoordinates) + (yTexture & 7) * spriteSheetWidth];    //16 for 8x16 tex size
                    zBuffer[x + y * WIDTH] = 1 / (tex1 + (tex2 - tex1) * pixelRotation) * 8;
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

    public void collisionDetection(double xLeft, double xRight, double zDistanceLeft
            , double zDistanceRight) {
        //System.out.println(Controller.distanceTravelY - zDistanceRight);//Controller.distanceTravelX - xLeft
        //System.out.println(Controller.distanceTravelX- (xLeft*8));//System.out.println(xLeft*8);
        //System.out.println((Controller.distanceTravelY - (zDistanceLeft*8)));
        //System.out.println(Controller.distanceTravelX- (xRight*8));
        //System.out.println((Controller.distanceTravelY - (zDistanceRight*8)));
        //System.out.println((wallHit));
//		if(xLeft <=0.0 && xRight <=0.0 && zDistanceLeft <= 0.0 && zDistanceRight <= 0.0
//			|| xLeft >=0.0 && xRight >= 0.0 && zDistanceLeft >= 0.0 && zDistanceRight >= 0.0)
        if (
                PlayerController.distanceTravelX - (xLeft * 8) <= 0.9 && // /*for horizontal sides*/
                        PlayerController.distanceTravelX - (xLeft * 8) >= +0.0 &&
                        PlayerController.distanceTravelY - (zDistanceLeft * 8) <= 0.0 &&
                        PlayerController.distanceTravelY - (zDistanceLeft * 8) >= -6 ||
                        PlayerController.distanceTravelX - (xRight * 8) <= 0.0 &&
                                PlayerController.distanceTravelX - (xRight * 8) >= -8.0 &&
                                PlayerController.distanceTravelY - (zDistanceRight * 8) <= 0.0 &&
                                PlayerController.distanceTravelY - (zDistanceRight * 8) >= -6 ||
                        PlayerController.distanceTravelX - (xLeft * 8) >= -0.9 &&
                                PlayerController.distanceTravelX - (xLeft * 8) <= -0.0 &&
                                PlayerController.distanceTravelY - (zDistanceLeft * 8) <= 0.0 &&
                                PlayerController.distanceTravelY - (zDistanceLeft * 8) >= -6 ||
                        PlayerController.distanceTravelX - (xRight * 8) >= 0.0 &&
                                PlayerController.distanceTravelX - (xRight * 8) <= 8.0 &&
                                PlayerController.distanceTravelY - (zDistanceRight * 8) <= 0.0 &&
                                PlayerController.distanceTravelY - (zDistanceRight * 8) >= -6 || /*for vertical sides*/
                        PlayerController.distanceTravelX - (xLeft * 8) >= -5.8 && /*-5.3 is between those*/
                                PlayerController.distanceTravelX - (xLeft * 8) <= -4.7 &&
                                PlayerController.distanceTravelY - (zDistanceLeft * 8) <= -0.0 && /*-0.72 is between those x axis is like y here when walking*/
                                PlayerController.distanceTravelY - (zDistanceLeft * 8) >= -2.0 ||
                        PlayerController.distanceTravelX - (xRight * 8) >= -5.8 && /*corners always close to +-0.* */
                                PlayerController.distanceTravelX - (xRight * 8) <= -4.7 &&/*will be same xRight xLeft general the axis of "forward"*/
                                PlayerController.distanceTravelY - (zDistanceRight * 8) >= 0.0 && /*1.398*/
                                PlayerController.distanceTravelY - (zDistanceRight * 8) <= 8//+2.0
        ) {
            wallHit = true;
        } else wallHit = false;

    }

    public void collisionDetectionOld(double xLeft, double xRight, double zDistanceLeft
            , double zDistanceRight) {
        //System.out.println(Controller.distanceTravelY - zDistanceRight);//Controller.distanceTravelX - xLeft
        System.out.println(PlayerController.distanceTravelX - (xLeft * 8));//System.out.println(xLeft*8);
        //System.out.println((Controller.distanceTravelY - (zDistanceLeft*8)));
        //System.out.println(Controller.distanceTravelX- (xRight*8));
        //System.out.println((Controller.distanceTravelY - (zDistanceRight*8)));
        //System.out.println((wallHit));
        if (xLeft <= 0.0 && xRight <= 0.0 && zDistanceLeft <= 0.0 && zDistanceRight <= 0.0
                || xLeft >= 0.0 && xRight >= 0.0 && zDistanceLeft >= 0.0 && zDistanceRight >= 0.0)
            if (PlayerController.distanceTravelX - (xLeft * 8) <= 0.9 &&
                    PlayerController.distanceTravelX - (xLeft * 8) >= +0.0 &&
                    PlayerController.distanceTravelY - (zDistanceLeft * 8) <= 0.0 &&
                    PlayerController.distanceTravelY - (zDistanceLeft * 8) >= -6 ||
                    PlayerController.distanceTravelX - (xRight * 8) <= 0.0 &&
                            PlayerController.distanceTravelX - (xRight * 8) >= -8.0 &&
                            PlayerController.distanceTravelY - (zDistanceRight * 8) <= 0.0 &&
                            PlayerController.distanceTravelY - (zDistanceRight * 8) >= -6 ||
                    PlayerController.distanceTravelX - (xLeft * 8) >= -0.9 &&
                            PlayerController.distanceTravelX - (xLeft * 8) <= -0.0 &&
                            PlayerController.distanceTravelY - (zDistanceLeft * 8) <= 0.0 &&
                            PlayerController.distanceTravelY - (zDistanceLeft * 8) >= -6 ||
                    PlayerController.distanceTravelX - (xRight * 8) >= 0.0 &&
                            PlayerController.distanceTravelX - (xRight * 8) <= 8.0 &&
                            PlayerController.distanceTravelY - (zDistanceRight * 8) <= 0.0 &&
                            PlayerController.distanceTravelY - (zDistanceRight * 8) >= -6 //||
//			Controller.distanceTravelX - (xLeft*8) <= -5.8 &&
//			Controller.distanceTravelX - (xLeft*8) >= -5.5 //&&
//			Controller.distanceTravelY - (zDistanceLeft*8) <= -6.8 &&
//			Controller.distanceTravelY - (zDistanceLeft*8) >= -7w 
            ) {
                //System.out.println("WATCH OUT");
                wallHit = true;
            } else wallHit = false;
//		 else 		 if(!(Controller.distanceTravelX - (xLeft*8) <= 0.9) &&
//					!(Controller.distanceTravelX - (xLeft*8) >= +0.0) &&
//					!(Controller.distanceTravelY - (zDistanceLeft*8) <= 0.0) &&
//					!(Controller.distanceTravelY - (zDistanceLeft*8) >= -6) ||
//					!(Controller.distanceTravelX- (xRight*8) <=0.0) &&
//					!(Controller.distanceTravelX- (xRight*8) >= -8.0) &&
//					!(Controller.distanceTravelY - (zDistanceRight*8) <= 0.0) &&
//					!(Controller.distanceTravelY - (zDistanceRight*8) >= -6) ||
//					!(Controller.distanceTravelX - (xLeft*8) >= -0.9) &&
//					!(Controller.distanceTravelX - (xLeft*8) <= -0.0) &&
//					!(Controller.distanceTravelY - (zDistanceLeft*8) <= 0.0) &&
//					!(Controller.distanceTravelY - (zDistanceLeft*8) >= -6) ||
//					!(Controller.distanceTravelX- (xRight*8) >=0.0) &&
//					!(Controller.distanceTravelX- (xRight*8) <= 8.0) &&
//					!(Controller.distanceTravelY - (zDistanceRight*8) <= 0.0) &&
//					!(Controller.distanceTravelY - (zDistanceRight*8) >= -6)
//					) {
//					//System.out.println("WATCH OUT");
//					wallHit = false;
//				}  
    }

    public void renderEnemy(double x, double y, double z, double hOffset) {
        double upCorrect = -0.125;//-0.0625;// moving with 0.0625 good for gathering lifes?
        double rightCorrect = 0.0625;
        double forwardCorrect = 0.0625;
        double walkingCorrect = +0.0625;


        double xc = ((x / 2) - (right * rightCorrect)) * 2 + 0.5;
        double yc = ((y / 2) - (up * upCorrect)) + (walking * walkingCorrect) * 2 + hOffset;//sprite up down on walk move with "player"
        double zc = ((z / 2) - (forward * forwardCorrect)) * 2;

        double rotX = xc * cosine - zc * sine;
        double rotY = yc;
        double rotZ = zc * cosine + xc * sine;

        double xCenter = 400.0;
        double yCenter = 300.0;

        //System.out.println(rotationUp);
        double xPixel = rotX / rotZ * HEIGHT + xCenter * 1; //fixes moving Spirtes Y axis
        double yPixel = rotY / rotZ * HEIGHT + yCenter * rotationUp;

        //** Resolution of "Tile"
        double xPixelL = xPixel - HEIGHT / 2.0 / rotZ; //16 is size of Sprites
        double xPixelR = xPixel + HEIGHT / 2.0 / rotZ;//sprite WIDTH change here e.g. /1 stretch
        double yPixelL = yPixel - HEIGHT / 2.0 / rotZ;
        double yPixelR = yPixel + HEIGHT / 2.0 / rotZ;

        //pixels are ints
        int xpl = (int) xPixelL;//x pixel left
        int xpr = (int) xPixelR;
        int ypl = (int) yPixelL;
        int ypr = (int) yPixelR;

        //clipping
        if (xpl < 0) xpl = 0;
        if (xpr > WIDTH) xpr = WIDTH;
        if (ypl < 0) ypl = 0;
        if (ypr > HEIGHT) ypr = HEIGHT;

        rotZ *= 8;//increase zBuffer "decrease" brightness

        //rendering 8x8 no matter the Resolution**
        for (int yp = ypl; yp < ypr; yp++) {
            double pixelRotationY = (yp - yPixelR) / (yPixelL - yPixelR);
            int yt = (int) (pixelRotationY * 8);
            for (int xp = xpl; xp < xpr; xp++) {
                double pixelRotationX = (xp - xPixelR) / (xPixelL - xPixelR);
                int xTexture = (int) (pixelRotationX * 8);//change coords HERE for Texture location applied
                //int yTexture = (int) (8 * pixelRotationY);
                if (zBuffer[xp + yp * WIDTH] > rotZ) {
                    int col = Texture.enemy1.PIXELS[((xTexture & 31)) + (yt & 31) * spriteSheetWidth];
                    if (col != 0xFFF6F6F6) {//FF00FF pink
                        //PIXELS[xp + yp * WIDTH] = xTexture * 16 + yt * 16 * 256; // Gradient does not ovveride below
                        //PIXELS[xp + yp * WIDTH] = Texture.enemy1.PIXELS[(int) (x + y * WIDTH)];//col;//0x00FFFF;//0x236DCF; //0xFF0000
                        PIXELS[xp + yp * WIDTH] = Texture.enemy1.PIXELS[((xTexture & 31)) + (yt & 31) * spriteSheetWidth];
                        zBuffer[xp + yp * WIDTH] = rotZ;
                    }
                }
            }
        }

    }

    public void renderWallDoubleSide(double xLeft, double xRight, double zDistanceLeft
            , double zDistanceRight, double yHeight) {
        double upCorrect = 0.0625;
        double rightCorrect = 0.0625;
        double forwardCorrect = 0.0625;
        double walkingCorrect = -0.0625;

        //System.out.println("xLeft: "+ xLeft + " xRight: " + xRight);
        //InverseNormals
        double xcLeftInverse;
        double xcRightInverse;

        /* xcLeftInverse = ((-(-0.5)) - (right * rightCorrect)) * 2;*/ //System.out.println((-xLeft + xRight));

        /*xcRightInverse = ((-(0)) - (right * rightCorrect)) * 2;*/ // ((-(-0.5)) ((-(-0)) != (-0.5) (-0)


        xcLeftInverse = ((-(xLeft / 2)) - (right * rightCorrect)) * 2;
        xcRightInverse = ((-(xRight / 2)) - (right * rightCorrect)) * 2;

        double zcLeft2 = ((zDistanceLeft / 2) - (forward * forwardCorrect)) * 2;
        double rotLeftSideX2 = xcLeftInverse * cosine - zcLeft2 * sine;
        double rotLeftSideZ2 = zcLeft2 * (cosine * 1) + xcLeftInverse * (sine * 1);
        //double xcRightInverse = ((-xRight) - (right * rightCorrect)) * 2; System.out.println((-xRight + xLeft));//System.out.println((-xRight + (xRight)));//System.out.println(xRight + (Math.abs(xLeft)));
        double zcRight2 = ((zDistanceRight / 2) - (forward * forwardCorrect)) * 2;
        double rotRightSideX2 = xcRightInverse * (cosine * 1) - zcRight2 * (sine * 1);
        double rotRightSideZ2 = zcRight2 * (cosine * 1) + xcRightInverse * (sine * 1);

        //System.out.println(-xRight );

        ////Basic corner pins below
        //left side
        /*double xcLeft = ((0) - (right * rightCorrect)) * 2;*/
        double xcLeft = ((-(xRight / 2)) - (right * rightCorrect)) * 2;//1.0
        double zcLeft = ((zDistanceLeft / 2) - (forward * forwardCorrect)) * 2;
        //System.out.println(-(xRight));
        double rotLeftSideX = xcLeft * cosine - zcLeft * sine;
        double yCornerTL = ((-yHeight) - (-up * upCorrect + (walking * walkingCorrect))) * 2;
        double yCornerBL = ((+0.5 - yHeight) - (-up * upCorrect + (walking * walkingCorrect))) * 2;
        double rotLeftSideZ = zcLeft * (cosine * 1) + xcLeft * (sine * 1);
        //////
        //right side
        /*double xcRight = ((0.5) - (right * rightCorrect)) * 2;//left calc of wall*/
        double xcRight = ((-(xLeft / 2)) - (right * rightCorrect)) * 2;//1.5
        double zcRight = ((zDistanceRight / 2) - (forward * forwardCorrect)) * 2;

        double rotRightSideX = xcRight * (cosine * 1) - zcRight * (sine * 1);
        double yCornerTR = ((-yHeight) - (-up * upCorrect + (walking * walkingCorrect))) * 2;
        double yCornerBR = ((+0.5 - yHeight) - (-up * upCorrect + (walking * walkingCorrect))) * 2;
        double rotRightSideZ = zcRight * (cosine * 1) + xcRight * (sine * 1);
        //////
        //System.out.println(Controller.turnUpM);

        double tex30 = 0, tex40 = 8, clip = 0.5;

        if (rotLeftSideZ < clip && rotRightSideZ < clip) {
            return;
        }

        //Cohen-SutherLand Algorithm Line Cliping inverse
        if (rotLeftSideZ2 < clip) {
            double clip0 = (clip - rotLeftSideZ2) / (rotRightSideZ2 - rotLeftSideZ2);
            rotLeftSideZ2 = rotLeftSideZ2 + (rotRightSideZ2 - rotLeftSideZ2) * clip0;
            rotLeftSideX2 = rotLeftSideX2 + (rotRightSideX2 - rotLeftSideX2) * clip0;
            tex30 = tex30 + (tex40 - tex30) * clip0;
        }
        if (rotRightSideZ2 < clip) {
            double clip0 = (clip - rotLeftSideZ2) / (rotRightSideZ2 - rotLeftSideZ2);
            rotRightSideZ2 = rotLeftSideZ2 + (rotRightSideZ2 - rotLeftSideZ2) * clip0;
            rotRightSideX2 = rotLeftSideX2 + (rotRightSideX2 - rotLeftSideX2) * clip0;
            tex30 = tex30 + (tex40 - tex30) * clip0;
        }

        //Cohen-SutherLand Algorithm Line Cliping
        if (rotLeftSideZ < clip) {
            double clip0 = (clip - rotLeftSideZ) / (rotRightSideZ - rotLeftSideZ);
            rotLeftSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ) * clip0;
            rotLeftSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX) * clip0;
            tex30 = tex30 + (tex40 - tex30) * clip0;
        }
        if (rotRightSideZ < clip) {
            double clip0 = (clip - rotLeftSideZ) / (rotRightSideZ - rotLeftSideZ);
            rotRightSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ) * clip0;
            rotRightSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX) * clip0;
            tex40 = tex30 + (tex40 - tex30) * clip0;
        }
        //compute pixel location
        //left and right edges of wall
        double xPixelLeft, xPixelRight, xPixelLeft2, xPixelRight2;

        xPixelLeft2 = (rotLeftSideX2 / rotLeftSideZ2 * HEIGHT + WIDTH / 2);
        xPixelRight2 = (rotRightSideX2 / rotRightSideZ2 * HEIGHT + WIDTH / 2);

        //below comment out if flip normal activated double side
//			
//			if(xPixelLeft2 >= xPixelRight2) {
//				return;//no render
//			}
        int xPixelLeftInt2 = (int) (xPixelLeft2);
        int xPixelRightInt2 = (int) (xPixelRight2);

        if (xPixelLeftInt2 < 0) {
            xPixelLeftInt2 = 0;
        }
        if (xPixelRightInt2 > WIDTH) {//Int? or xPixelRight
            xPixelRightInt2 = WIDTH;
        }

        double yPixelLeftTop2 = (yCornerTL / rotLeftSideZ2 * HEIGHT + HEIGHT / 2.0 * rotationUp);//double yPixelLeftTop = (yCornerTL / rotLeftSideZ * (HEIGHT *( rotationUp/2)) + HEIGHT / 2.0 * rotationUp);
        double yPixelLeftBottom2 = (yCornerBL / rotLeftSideZ2 * HEIGHT + HEIGHT / 2.0 * rotationUp);
        double yPixelRightTop2 = (yCornerTR / rotRightSideZ2 * HEIGHT + HEIGHT / 2.0 * rotationUp);
        double yPixelRightBottom2 = (yCornerBR / rotRightSideZ2 * HEIGHT + HEIGHT / 2.0 * rotationUp);

        double tex1a = 1 / rotLeftSideZ2;
        double tex2a = 1 / rotRightSideZ2;
        double tex3a = tex30 / rotLeftSideZ2;
        double tex4a = tex40 / rotRightSideZ2 - tex3a;

        //comment out one of two to understand for in for
        //---------------------------------------
        xPixelLeft = (rotLeftSideX / rotLeftSideZ * HEIGHT + WIDTH / 2);
        xPixelRight = (rotRightSideX / rotRightSideZ * HEIGHT + WIDTH / 2);

        //below comment out if flip normal activated double side
        //protection of negative pixels e.g. the horizontal coordinates wall is bigger than right side
//		if(xPixelLeft >= xPixelRight) {
//			return;//no render
//		}

        //convert pixels to int to fit square grid
        int xPixelLeftInt = (int) (xPixelLeft);
        int xPixelRightInt = (int) (xPixelRight);

        if (xPixelLeftInt < 0) {
            xPixelLeftInt = 0;
        }
        if (xPixelRightInt > WIDTH) {
            xPixelRightInt = WIDTH;
        }

        //corner pins
        double yPixelLeftTop = (yCornerTL / rotLeftSideZ * HEIGHT + HEIGHT / 2.0 * rotationUp);//double yPixelLeftTop = (yCornerTL / rotLeftSideZ * (HEIGHT *( rotationUp/2)) + HEIGHT / 2.0 * rotationUp);
        double yPixelLeftBottom = (yCornerBL / rotLeftSideZ * HEIGHT + HEIGHT / 2.0 * rotationUp);
        double yPixelRightTop = (yCornerTR / rotRightSideZ * HEIGHT + HEIGHT / 2.0 * rotationUp);
        double yPixelRightBottom = (yCornerBR / rotRightSideZ * HEIGHT + HEIGHT / 2.0 * rotationUp);

        double tex1 = 1 / rotLeftSideZ;
        double tex2 = 1 / rotRightSideZ;
        double tex3 = tex30 / rotLeftSideZ;
        double tex4 = tex40 / rotRightSideZ - tex3;

        for (int x = xPixelLeftInt; x < xPixelRightInt; x++) {

            double pixelRotation = (x - xPixelLeft) / (xPixelRight - xPixelLeft);
            double zWall = (tex1 + (tex2 - tex1) * pixelRotation);

            if (zBufferWall[x] > zWall) {
                continue; //fix xray
            }
            zBufferWall[x] = zWall;//fix xray

            int xTexture = (int) ((tex3 + tex4 * pixelRotation) / zWall);

            double yPixelTop = yPixelLeftTop + (yPixelRightTop - yPixelLeftTop) * pixelRotation;
            double yPixelBottom = yPixelLeftBottom + (yPixelRightBottom - yPixelLeftBottom) * pixelRotation;

            int yPixelTopInt = (int) (yPixelTop);
            int yPixelBottomInt = (int) (yPixelBottom);

            if (yPixelTopInt < 0) {
                yPixelTopInt = 0;
            }
            if (yPixelBottomInt > HEIGHT) {
                yPixelBottomInt = HEIGHT;
            }
            int textureCoordinates = 8;
            for (int y = yPixelTopInt; y < yPixelBottomInt; y++) {
                try {
                    double pixelRotationY = (y - yPixelTop) / (yPixelBottom - yPixelTop);
                    int yTexture = (int) (8 * pixelRotationY);
                    PIXELS[x + y * WIDTH] = Texture.floor.PIXELS[((xTexture & 7) + textureCoordinates) + (yTexture & 7) * 16];
                    zBuffer[x + y * WIDTH] = 1 / (tex1 + (tex2 - tex1) * pixelRotation) * 8;//100
                } catch (ArrayIndexOutOfBoundsException e) {//quick fix
                    e.printStackTrace();
                    continue;
                }
            }
        }

        //Inverse----------------------
        for (int x = xPixelLeftInt2; x < xPixelRightInt2; x++) {
            double pixelRotation = (x - xPixelLeft2) / (xPixelRight2 - xPixelLeft2);

            int xTexture = (int) ((tex3a + tex4a * pixelRotation) / (tex1a + (tex2a - tex1a) * pixelRotation));

            double yPixelTop = yPixelLeftTop2 + (yPixelRightTop2 - yPixelLeftTop2) * pixelRotation;
            double yPixelBottom = yPixelLeftBottom2 + (yPixelRightBottom2 - yPixelLeftBottom2) * pixelRotation;

            int yPixelTopInt = (int) (yPixelTop);
            int yPixelBottomInt = (int) (yPixelBottom);

            if (yPixelTopInt < 0) {
                yPixelTopInt = 0;
            }
            if (yPixelBottomInt > HEIGHT) {
                yPixelBottomInt = HEIGHT;
            }
            int textureCoordinates = 8;
            for (int y = yPixelTopInt; y < yPixelBottomInt; y++) {
                try {
                    double pixelRotationY = (y - yPixelTop) / (yPixelBottom - yPixelTop);
                    int yTexture = (int) (8 * pixelRotationY);
                    PIXELS[x + y * WIDTH] = Texture.floor.PIXELS[((xTexture & 7) + textureCoordinates) + (yTexture & 7) * 16];
                    zBuffer[x + y * WIDTH] = 1 / (tex1a + (tex2a - tex1a) * pixelRotation) * 8;//100
                } catch (ArrayIndexOutOfBoundsException e) {//quick fix
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

    public void renderWallDoubleSideUnFixed(double xLeft, double xRight, double zDistanceLeft
            , double zDistanceRight, double yHeight) {
        double upCorrect = 0.0625;
        double rightCorrect = 0.0625;
        double forwardCorrect = 0.0625;
        double walkingCorrect = -0.0625;

        //System.out.println("xLeft: "+ xLeft + " xRight: " + xRight);
        //InverseNormals
        double xcLeftInverse;
        double xcRightInverse;

        xcLeftInverse = ((-(xLeft)) - (right * rightCorrect)) * 2;
        xcRightInverse = ((-(xRight)) - (right * rightCorrect)) * 2;

        double zcLeft2 = ((zDistanceLeft) - (forward * forwardCorrect)) * 2;
        double rotLeftSideX2 = xcLeftInverse * cosine - zcLeft2 * sine;
        double rotLeftSideZ2 = zcLeft2 * (cosine * 1) + xcLeftInverse * (sine * 1);
        double zcRight2 = ((zDistanceRight) - (forward * forwardCorrect)) * 2;
        double rotRightSideX2 = xcRightInverse * (cosine * 1) - zcRight2 * (sine * 1);
        double rotRightSideZ2 = zcRight2 * (cosine * 1) + xcRightInverse * (sine * 1);

        ////Basic corner pins below
        //left side
        /*double xcLeft = ((0) - (right * rightCorrect)) * 2;*/
        double xcLeft = ((-(xRight)) - (right * rightCorrect)) * 2;//1.0
        double zcLeft = ((zDistanceLeft) - (forward * forwardCorrect)) * 2;
        //System.out.println(-(xRight));
        double rotLeftSideX = xcLeft * cosine - zcLeft * sine;
        double yCornerTL = ((-yHeight) - (-up * upCorrect + (walking * walkingCorrect))) * 2;
        double yCornerBL = ((+0.5 - yHeight) - (-up * upCorrect + (walking * walkingCorrect))) * 2;
        double rotLeftSideZ = zcLeft * (cosine * 1) + xcLeft * (sine * 1);
        //////
        //right side
        /*double xcRight = ((0.5) - (right * rightCorrect)) * 2;//left calc of wall*/
        double xcRight = ((-(xLeft)) - (right * rightCorrect)) * 2;//1.5
        double zcRight = ((zDistanceRight) - (forward * forwardCorrect)) * 2;

        double rotRightSideX = xcRight * (cosine * 1) - zcRight * (sine * 1);
        double yCornerTR = ((-yHeight) - (-up * upCorrect + (walking * walkingCorrect))) * 2;
        double yCornerBR = ((+0.5 - yHeight) - (-up * upCorrect + (walking * walkingCorrect))) * 2;
        double rotRightSideZ = zcRight * (cosine * 1) + xcRight * (sine * 1);
        //////
        //System.out.println(Controller.turnUpM);

        double tex30 = 0, tex40 = 8, clip = 0.5;

        if (rotLeftSideZ < clip && rotRightSideZ < clip) {
            return;
        }

        //Cohen-SutherLand Algorithm Line Cliping inverse
        if (rotLeftSideZ2 < clip) {
            double clip0 = (clip - rotLeftSideZ2) / (rotRightSideZ2 - rotLeftSideZ2);
            rotLeftSideZ2 = rotLeftSideZ2 + (rotRightSideZ2 - rotLeftSideZ2) * clip0;
            rotLeftSideX2 = rotLeftSideX2 + (rotRightSideX2 - rotLeftSideX2) * clip0;
            tex30 = tex30 + (tex40 - tex30) * clip0;
        }
        if (rotRightSideZ2 < clip) {
            double clip0 = (clip - rotLeftSideZ2) / (rotRightSideZ2 - rotLeftSideZ2);
            rotRightSideZ2 = rotLeftSideZ2 + (rotRightSideZ2 - rotLeftSideZ2) * clip0;
            rotRightSideX2 = rotLeftSideX2 + (rotRightSideX2 - rotLeftSideX2) * clip0;
            tex30 = tex30 + (tex40 - tex30) * clip0;
        }

        //Cohen-SutherLand Algorithm Line Cliping
        if (rotLeftSideZ < clip) {
            double clip0 = (clip - rotLeftSideZ) / (rotRightSideZ - rotLeftSideZ);
            rotLeftSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ) * clip0;
            rotLeftSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX) * clip0;
            tex30 = tex30 + (tex40 - tex30) * clip0;
        }
        if (rotRightSideZ < clip) {
            double clip0 = (clip - rotLeftSideZ) / (rotRightSideZ - rotLeftSideZ);
            rotRightSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ) * clip0;
            rotRightSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX) * clip0;
            tex30 = tex30 + (tex40 - tex30) * clip0;
        }
        //compute pixel location
        //left and right edges of wall
        double xPixelLeft, xPixelRight, xPixelLeft2, xPixelRight2;

        xPixelLeft2 = (rotLeftSideX2 / rotLeftSideZ2 * HEIGHT + WIDTH / 2.0);
        xPixelRight2 = (rotRightSideX2 / rotRightSideZ2 * HEIGHT + WIDTH / 2.0);

        //below comment out if flip normal activated double side
//			
//			if(xPixelLeft2 >= xPixelRight2) {
//				return;//no render
//			}
        int xPixelLeftInt2 = (int) (xPixelLeft2);
        int xPixelRightInt2 = (int) (xPixelRight2);

        if (xPixelLeftInt2 < 0) {
            xPixelLeftInt2 = 0;
        }
        if (xPixelRightInt2 > WIDTH) {//Int? or xPixelRight
            xPixelRightInt2 = WIDTH;
        }

        double yPixelLeftTop2 = (yCornerTL / rotLeftSideZ2 * HEIGHT + HEIGHT / 2.0 * rotationUp);//double yPixelLeftTop = (yCornerTL / rotLeftSideZ * (HEIGHT *( rotationUp/2)) + HEIGHT / 2.0 * rotationUp);
        double yPixelLeftBottom2 = (yCornerBL / rotLeftSideZ2 * HEIGHT + HEIGHT / 2.0 * rotationUp);
        double yPixelRightTop2 = (yCornerTR / rotRightSideZ2 * HEIGHT + HEIGHT / 2.0 * rotationUp);
        double yPixelRightBottom2 = (yCornerBR / rotRightSideZ2 * HEIGHT + HEIGHT / 2.0 * rotationUp);

        double tex1a = 1 / rotLeftSideZ2;
        double tex2a = 1 / rotRightSideZ2;
        double tex3a = tex30 / rotLeftSideZ2;
        double tex4a = tex40 / rotRightSideZ2 - tex3a;

        //comment out one of two to understand for in for
        //---------------------------------------
        xPixelLeft = (rotLeftSideX / rotLeftSideZ * HEIGHT + WIDTH / 2.0);
        xPixelRight = (rotRightSideX / rotRightSideZ * HEIGHT + WIDTH / 2.0);

        //below comment out if flip normal activated double side
        //protection of negative pixels e.g. the horizontal coordinates wall is bigger than right side
//		if(xPixelLeft >= xPixelRight) {
//			return;//no render
//		}

        //convert pixels to int to fit square grid
        int xPixelLeftInt = (int) (xPixelLeft);
        int xPixelRightInt = (int) (xPixelRight);

        if (xPixelLeftInt < 0) {
            xPixelLeftInt = 0;
        }
        if (xPixelRightInt > WIDTH) {//Int? or xPixelRight
            xPixelRightInt = WIDTH;
        }

        //corner pins
        double yPixelLeftTop = (yCornerTL / rotLeftSideZ * HEIGHT + HEIGHT / 2.0 * rotationUp);//double yPixelLeftTop = (yCornerTL / rotLeftSideZ * (HEIGHT *( rotationUp/2)) + HEIGHT / 2.0 * rotationUp);
        double yPixelLeftBottom = (yCornerBL / rotLeftSideZ * HEIGHT + HEIGHT / 2.0 * rotationUp);
        double yPixelRightTop = (yCornerTR / rotRightSideZ * HEIGHT + HEIGHT / 2.0 * rotationUp);
        double yPixelRightBottom = (yCornerBR / rotRightSideZ * HEIGHT + HEIGHT / 2.0 * rotationUp);

        double tex1 = 1 / rotLeftSideZ;
        double tex2 = 1 / rotRightSideZ;
        double tex3 = tex30 / rotLeftSideZ;
        double tex4 = tex40 / rotRightSideZ - tex3;

        for (int x = xPixelLeftInt; x < xPixelRightInt; x++) {
            //System.out.println("Left: "+xPixelLeftInt + " RIGHT: " + xPixelRightInt);

            double pixelRotation = (x - xPixelLeft) / (xPixelRight - xPixelLeft);

            int xTexture = (int) ((tex3 + tex4 * pixelRotation) / (tex1 + (tex2 - tex1) * pixelRotation));

            double yPixelTop = yPixelLeftTop + (yPixelRightTop - yPixelLeftTop) * pixelRotation;
            double yPixelBottom = yPixelLeftBottom + (yPixelRightBottom - yPixelLeftBottom) * pixelRotation;

            int yPixelTopInt = (int) (yPixelTop);
            int yPixelBottomInt = (int) (yPixelBottom);

            if (yPixelTopInt < 0) {
                yPixelTopInt = 0;
            }
            if (yPixelBottomInt > HEIGHT) {
                yPixelBottomInt = HEIGHT;
            }
            int textureCoordinates = 8;
            for (int y = yPixelTopInt; y < yPixelBottomInt; y++) {
                try {
                    double pixelRotationY = (y - yPixelTop) / (yPixelBottom - yPixelTop);
                    int yTexture = (int) (8 * pixelRotationY);
                    PIXELS[x + y * WIDTH] = Texture.floor.PIXELS[((xTexture & 7) + textureCoordinates) + (yTexture & 7) * 16];
                    zBuffer[x + y * WIDTH] = 1 / (tex1 + (tex2 - tex1) * pixelRotation) * 8;//100
                } catch (ArrayIndexOutOfBoundsException e) {//quick fix
                    e.printStackTrace();
                    continue;
                }
            }
        }

        //Inverse----------------------
        for (int x = xPixelLeftInt2; x < xPixelRightInt2; x++) {
            //System.out.println("Left: "+xPixelLeftInt + " RIGHT: " + xPixelRightInt);

            double pixelRotation = (x - xPixelLeft2) / (xPixelRight2 - xPixelLeft2);

            int xTexture = (int) ((tex3a + tex4a * pixelRotation) / (tex1a + (tex2a - tex1a) * pixelRotation));

            double yPixelTop = yPixelLeftTop2 + (yPixelRightTop2 - yPixelLeftTop2) * pixelRotation;
            double yPixelBottom = yPixelLeftBottom2 + (yPixelRightBottom2 - yPixelLeftBottom2) * pixelRotation;

            int yPixelTopInt = (int) (yPixelTop);
            int yPixelBottomInt = (int) (yPixelBottom);

            if (yPixelTopInt < 0) {
                yPixelTopInt = 0;
            }
            if (yPixelBottomInt > HEIGHT) {
                yPixelBottomInt = HEIGHT;
            }
            int textureCoordinates = 8;
            for (int y = yPixelTopInt; y < yPixelBottomInt; y++) {
                try {
                    double pixelRotationY = (y - yPixelTop) / (yPixelBottom - yPixelTop);
                    int yTexture = (int) (8 * pixelRotationY);
                    PIXELS[x + y * WIDTH] = Texture.floor.PIXELS[((xTexture & 7) + textureCoordinates) + (yTexture & 7) * 16];
                    zBuffer[x + y * WIDTH] = 1 / (tex1a + (tex2a - tex1a) * pixelRotation) * 8;//100
                } catch (ArrayIndexOutOfBoundsException e) {//quick fix
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

    //mirroring of wall "flip" to other side need manual value override in Screen to display same pos as the "Inverse one"
    public void renderWallInverse(double xLeft, double xRight, double zDistanceLeft
            , double zDistanceRight, double yHeight) {
        double upCorrect = 0.0625;
        double rightCorrect = 0.0625;
        double forwardCorrect = 0.0625;
        double walkingCorrect = -0.0625;
        //System.out.println(Controller.distanceTravelY - zDistanceRight);//Controller.distanceTravelX - xLeft
        //System.out.println(Controller.distanceTravelX- (-xLeft*8));//System.out.println(xLeft*8);
        //System.out.println((Controller.distanceTravelY - (zDistanceLeft*8)));
        //System.out.println(Controller.distanceTravelX- (-xRight*8));
        //System.out.println((Controller.distanceTravelY - (zDistanceRight*8)));
        //System.out.println(yPixelLeftTop);
//		double collisionX = Controller.distanceTravelX;
//		double collisionY = Controller.distanceTravelY;
//		 wallHitInverse=false;
//
        //need fixing compared to Non-Mirrored One
//		 //if here FLIPED values because mirrored from the renderWall method
//		 //cut and del and test below in each with sout result "Debug" style
//		//if(Controller.distanceTravelY - zDistanceLeft <= 0.9 &&
//		 if(Controller.distanceTravelX - (-xLeft*8) >= -0.9 &&
//			Controller.distanceTravelX - (-xLeft*8) <= +0.0 &&
//			Controller.distanceTravelY - (zDistanceLeft*8) >= -0.0 &&
//			Controller.distanceTravelY - (zDistanceLeft*8) <= 6 ||
//			Controller.distanceTravelX- (-xRight*8) >= -0.0 &&
//			Controller.distanceTravelX- (-xRight*8) <= 8.0 &&
//			Controller.distanceTravelY - (zDistanceRight*8) >= -0.0 &&
//			Controller.distanceTravelY - (zDistanceRight*8) <= 6) {
//			//System.out.println("WATCH OUT");
//			 wallHitInverse = true;
//		}


        ////Basic corner pins below
        //left side
        double xcLeft = ((-xLeft / 2) - (right * rightCorrect)) * 2;
        double zcLeft = ((zDistanceLeft / 2) - (forward * forwardCorrect)) * 2;

        double rotLeftSideX = xcLeft * cosine - zcLeft * sine;
        double yCornerTL = ((-yHeight) - (-up * upCorrect + (walking * walkingCorrect))) * 2;
        double yCornerBL = ((+0.5 - yHeight) - (-up * upCorrect + (walking * walkingCorrect))) * 2;
        double rotLeftSideZ = zcLeft * (cosine * 1) + xcLeft * (sine * 1);
        //////
        //right side
        double xcRight = ((-xRight / 2) - (right * rightCorrect)) * 2;//left calc of wall
        double zcRight = ((zDistanceRight / 2) - (forward * forwardCorrect)) * 2;

        double rotRightSideX = xcRight * (cosine * 1) - zcRight * (sine * 1);
        double yCornerTR = ((-yHeight) - (-up * upCorrect + (walking * walkingCorrect))) * 2;
        double yCornerBR = ((+0.5 - yHeight) - (-up * upCorrect + (walking * walkingCorrect))) * 2;
        double rotRightSideZ = zcRight * (cosine * 1) + xcRight * (sine * 1);
        //////
        //System.out.println(Controller.turnUpM);

        double tex30 = 0, tex40 = 8, clip = 0.5;

        if (rotLeftSideZ < clip && rotRightSideZ < clip) {
            return;
        }

        //Cohen-SutherLand Algorithm Line Cliping
        if (rotLeftSideZ < clip) {
            double clip0 = (clip - rotLeftSideZ) / (rotRightSideZ - rotLeftSideZ);
            rotLeftSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ) * clip0;
            rotLeftSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX) * clip0;
            tex30 = tex30 + (tex40 - tex30) * clip0;
        }
        if (rotRightSideZ < clip) {
            double clip0 = (clip - rotLeftSideZ) / (rotRightSideZ - rotLeftSideZ);
            rotRightSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ) * clip0;
            rotRightSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX) * clip0;
            tex40 = tex30 + (tex40 - tex30) * clip0;
        }
        //compute pixel location
        //left and right edges of wall
        double xPixelLeft, xPixelRight;
        if (PlayerController.turnUpM) {
            xPixelLeft = (rotLeftSideX / rotLeftSideZ * HEIGHT + WIDTH / 2);
        } else
            xPixelLeft = (rotLeftSideX / rotLeftSideZ * HEIGHT + WIDTH / 2);

        if (PlayerController.turnDownM) {
            xPixelRight = (rotRightSideX / rotRightSideZ * HEIGHT + WIDTH / 2);
            //System.out.println(xPixelRight);
        } else
            xPixelRight = (rotRightSideX / rotRightSideZ * HEIGHT + WIDTH / 2);

        //protection of negative pixels e.g. the horizontal coordinates wall is bigger than right side
        if (xPixelLeft >= xPixelRight) {
            return;//no render
        }

        //convert pixels to int to fit square grid
        int xPixelLeftInt = (int) (xPixelLeft);
        int xPixelRightInt = (int) (xPixelRight);

        if (xPixelLeftInt < 0) {
            xPixelLeftInt = 0;
        }
        if (xPixelRightInt > WIDTH) {//Int? or xPixelRight
            xPixelRightInt = WIDTH;
        }

        // corner pins
        double yPixelLeftTop = (yCornerTL / rotLeftSideZ * HEIGHT + HEIGHT / 2.0 * rotationUp);//double yPixelLeftTop = (yCornerTL / rotLeftSideZ * (HEIGHT *( rotationUp/2)) + HEIGHT / 2.0 * rotationUp);
        double yPixelLeftBottom = (yCornerBL / rotLeftSideZ * HEIGHT + HEIGHT / 2.0 * rotationUp);
        double yPixelRightTop = (yCornerTR / rotRightSideZ * HEIGHT + HEIGHT / 2.0 * rotationUp);
        double yPixelRightBottom = (yCornerBR / rotRightSideZ * HEIGHT + HEIGHT / 2.0 * rotationUp);

        double tex1 = 1 / rotLeftSideZ;
        double tex2 = 1 / rotRightSideZ;
        double tex3 = tex30 / rotLeftSideZ;
        double tex4 = tex40 / rotRightSideZ - tex3;

        for (int x = xPixelLeftInt; x < xPixelRightInt; x++) {
            ////////System.out.println("Left: "+xPixelLeftInt + " RIGHT: " + xPixelRightInt);

            double pixelRotation = (x - xPixelLeft) / (xPixelRight - xPixelLeft);
            double zWall = (tex1 + (tex2 - tex1) * pixelRotation);

            if (zBufferWall[x] > zWall) {
                continue; //fix xray
            }
            zBufferWall[x] = zWall;//fix xray

            int xTexture = (int) ((tex3 + tex4 * pixelRotation) / zWall);

            double yPixelTop = yPixelLeftTop + (yPixelRightTop - yPixelLeftTop) * pixelRotation;
            double yPixelBottom = yPixelLeftBottom + (yPixelRightBottom - yPixelLeftBottom) * pixelRotation;

            int yPixelTopInt = (int) (yPixelTop);
            int yPixelBottomInt = (int) (yPixelBottom);

            if (yPixelTopInt < 0) {
                yPixelTopInt = 0;
            }
            if (yPixelBottomInt > HEIGHT) {
                yPixelBottomInt = HEIGHT;
            }
            int textureCoordinates = 8;
            for (int y = yPixelTopInt; y < yPixelBottomInt; y++) {
                try {
                    double pixelRotationY = (y - yPixelTop) / (yPixelBottom - yPixelTop);
                    int yTexture = (int) (8 * pixelRotationY);
                    PIXELS[x + y * WIDTH] = Texture.floor.PIXELS[((xTexture & 7) + textureCoordinates) + (yTexture & 7) * 16];
                    zBuffer[x + y * WIDTH] = 1 / (tex1 + (tex2 - tex1) * pixelRotation) * 8;  // 100
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // flipped normals rendering
    public void renderDWall(double xLeft, double xRight, double zDistanceLeft
            , double zDistanceRight, double yHeight) {

        renderWall(xLeft, xRight, zDistanceLeft
                , zDistanceRight, yHeight);
        //xLeft-5, xRight-5
        //flipped normals below double side rendering
        renderWallInverse(-xRight, -xLeft, zDistanceLeft
                , zDistanceRight, yHeight);
    }

    public void wallCrap() {
        Random random = new Random(100);//if in beg of file its go crazy

        for (int i = 0; i < 10000; i++) {//250000 more solid bigger the pixels
//The 2D objects   are called "billboards" ^^ Sprites are REALLY static, like the FPS counter
            double xx = random.nextDouble(); //-1; // +1;
            double yy = random.nextDouble(); //-1; // +1;
            double zz = 1.5 - forwardGlobal / 16;

            int xPixel = (int) (xx / zz * HEIGHT / 2 + WIDTH / 2);
            int yPixel = (int) (yy / zz * HEIGHT / 2 + HEIGHT / 2);
            if (xPixel >= 0 && yPixel >= 0 && xPixel < WIDTH && yPixel < HEIGHT) {
                PIXELS[xPixel + yPixel * WIDTH] = 0x00ffff;
            }
        }
        for (int i = 0; i < 10000; i++) {

            double xx = random.nextDouble();
            double yy = random.nextDouble() - 1;

            double zz = 1.5 - forwardGlobal / 16;

            int xPixel = (int) (xx / zz * HEIGHT / 2 + WIDTH / 2);
            int yPixel = (int) (yy / zz * HEIGHT / 2 + HEIGHT / 2);
            if (xPixel >= 0 && yPixel >= 0 && xPixel < WIDTH && yPixel < HEIGHT) {
                PIXELS[xPixel + yPixel * WIDTH] = 0x00ffff;
            }
        }
        for (int i = 0; i < 10000; i++) {//250000 more solid bigger the pixels
            double xx = random.nextDouble(); //-1; // +1;
            double yy = random.nextDouble(); //-1; // +1;
            double zz = 1.5 - forwardGlobal / 16;

            int xPixel = (int) (xx / zz * HEIGHT / 2 + WIDTH / 2);
            int yPixel = (int) (yy / zz * HEIGHT / 2 + HEIGHT / 2);
            if (xPixel >= 0 && yPixel >= 0 && xPixel < WIDTH && yPixel < HEIGHT) {
                PIXELS[xPixel + yPixel * WIDTH] = 0x00ffff;
            }
        }
        for (int i = 0; i < 10000; i++) {

            double xx = random.nextDouble() - 1;
            double yy = random.nextDouble() - 1;

            double zz = 1.5 - forwardGlobal / 16;

            int xPixel = (int) (xx / zz * HEIGHT / 2 + WIDTH / 2);
            int yPixel = (int) (yy / zz * HEIGHT / 2 + HEIGHT / 2);
            if (xPixel >= 0 && yPixel >= 0 && xPixel < WIDTH && yPixel < HEIGHT) {
                PIXELS[xPixel + yPixel * WIDTH] = 0x00ffff;
            }
        }
        for (int i = 0; i < 10000; i++) {

            double xx = random.nextDouble() - 1;
            double yy = random.nextDouble();

            double zz = 1.5 - forwardGlobal / 16;

            int xPixel = (int) (xx / zz * HEIGHT / 2 + WIDTH / 2);
            int yPixel = (int) (yy / zz * HEIGHT / 2 + HEIGHT / 2);
            if (xPixel >= 0 && yPixel >= 0 && xPixel < WIDTH && yPixel < HEIGHT) {
                PIXELS[xPixel + yPixel * WIDTH] = 0x00ffff;
            }
        }
        for (int i = 0; i < 10000; i++) {

            double xx = random.nextDouble();
            double yy = random.nextDouble();

            double zz = 2 - forwardGlobal / 16;

            int xPixel = (int) (xx / zz * HEIGHT / 2 + WIDTH / 2);
            int yPixel = (int) (yy / zz * HEIGHT / 2 + HEIGHT / 2);
            if (xPixel >= 0 && yPixel >= 0 && xPixel < WIDTH && yPixel < HEIGHT) {
                PIXELS[xPixel + yPixel * WIDTH] = 0x00ffff;
            }
        }

    }
//cos sin wave make a rotation O istead of C or half O they are opossite null
}
