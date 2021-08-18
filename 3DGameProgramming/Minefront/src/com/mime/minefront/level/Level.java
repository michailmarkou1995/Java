package com.mime.minefront.level;

import com.mime.minefront.entity.Entity;
import com.mime.minefront.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level {

    public static boolean temp_generate_overlap = false;
    public final int width, height;
    private final List<Entity> players_mobs_entities = new ArrayList<Entity>();
    public Block[] blocks;
    Random random = new Random();

    public Level(int width, int height) {
        this.width = width;
        this.height = height;
        blocks = new Block[width * height];
        generateLevel();

    }

    public Block create(int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return Block.solidWall; //return till now just true ..nothing!
        }
        return blocks[x + y * width];
    }

    public Block createSimple(int x, int y) {
        return blocks[x + y * width];
    }

    public void generateLevel() {
        //here generate changes before render Happens e.g. change amount of volume
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Block block = null;
                if (random.nextInt(28) == 0) {
                    block = new SolidBlock();//true block
                    //block.block_overlap.add(block);
                    //temp_generate_overlap=true;
                } else {//if(!temp_generate_overlap)
                    block = new Block();//false no block
                    if (random.nextInt(15) == 0)//every 5 without it 1 sprite every block("tile")
                        block.addSprite(new Sprite(0, 0, 0));
                    //temp_generate_overlap=false;
                }
                blocks[x + y * width] = block;
            }
        }
    }

    public void update() {//PlayerController.tick() extends mob which extends THIS Level.java Class
        for (int i = 0; i < players_mobs_entities.size(); i++) {
            players_mobs_entities.get(i).tick();
        }
    }

    public void addEntity(Entity e) {
        players_mobs_entities.add(e);
    }
}
