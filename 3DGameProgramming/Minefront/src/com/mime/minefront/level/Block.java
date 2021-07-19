package com.mime.minefront.level;

import java.util.ArrayList;
import java.util.List;

import com.mime.minefront.graphics.Sprite;

public class Block {

	public boolean solid = false;
	public int x0,x1, z0,z1;
	
	public static Block solidWall = new SolidBlock();
	//public static Block nonsolidWall = new NonSolidWall();
	
	public Block() {}
	public Block(int x0, int x1, int z0, int z1) {
		this.x0=x0;
		this.x1=x1;
		this.z0=z0;
		this.z1=z1;
	}
	
	public List<Sprite> sprites = new ArrayList<Sprite>();
	public static List<Block> block_overlap = new ArrayList<Block>();//or hashmap or per object store xy coords
	
	public void addSprite(Sprite sprite) {
		sprites.add(sprite);
	}
	
	public void addBlock(Block block) {
		block_overlap.add(block);
	}
	
	public void addCoords(int x0, int x1, int z0, int z1) {
		this.x0=x0;
		this.x1=x1;
		this.z0=z0;
		this.z1=z1;
		//block_overlap.add(block);
	}
	@Override
	public String toString() {
		return "Block [x0=" + x0 + ", x1=" + x1 + ", z0=" + z0 + ", z1=" + z1 + "]";
	}
	
	
	
//	@Override
//	public String toString() {
//		return "Block [solid=" + solid + ", x0=" + x0 + ", x1=" + x1 + ", z0=" + z0 + ", z1=" + z1 + ", sprites="
//				+ sprites + ", block_overlap=" + block_overlap + "]";
//	}
	
}
