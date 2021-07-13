package com.mime.minefront;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Configuration {
	
	Properties properties = new Properties();

	public void saveConfiguration(String key, int value) {
		
		String path = "resources/settings/config.xml";
		try {
			File file = new File(path);
			boolean exists = file.exists();
			if(!exists) {
				file.createNewFile();
			}
			OutputStream write = new FileOutputStream(path);
			properties.setProperty(key, Integer.toString(value));
			properties.storeToXML(write, "Resolution");
		} catch (IOException e){//Exception e is general catches all IO is  specific
			e.printStackTrace();
		}
	}
	
	//define path so many config files in future e.g. load player location not in same as res settings
	public void loadConfiguration (String path) {//String key
		try {
			InputStream read = new FileInputStream(path);
			properties.loadFromXML(read);
			String width = properties.getProperty("width");
			String height = properties.getProperty("height");
			//System.out.println("width: " + width + " height: " + height);
			setResolution(Integer.parseInt(width), Integer.parseInt(height));
			read.close();
		}catch (FileNotFoundException e) {
			saveConfiguration("width", 800);// 4:3 800/4*3
			saveConfiguration("height", 600);
			loadConfiguration(path);//rerun so try already run
			//e.printStackTrace();
			//Display.HEIGHT=600;
			//Display.WIDTH=800;
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setResolution(int width, int height) {
		
//		if(width == 640 && height == 480) {
//			Display.selection = 0;
//		}
//		if(width == 800 && height == 600) {
//			Display.selection = 1;
//		}
//		if(width == 1024 && height == 768) {
//			Display.selection = 2;
//		}
		Display.WIDTH=width;
		Display.HEIGHT=height;
		
	}
}
