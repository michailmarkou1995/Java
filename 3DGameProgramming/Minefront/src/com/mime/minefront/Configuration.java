package com.mime.minefront;

import java.io.*;
import java.util.Properties;

/**
 * XML configuration store
 */
public class Configuration {

    Properties properties = new Properties();

    public void saveConfiguration(String key, int value) {

        String path = "resources/settings/config.xml";
        try {
            File file = new File(path);
            boolean exists = file.exists();
            if (!exists) {
                file.createNewFile();
            }
            OutputStream write = new FileOutputStream(path);
            properties.setProperty(key, Integer.toString(value));
            properties.storeToXML(write, "Resolution");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // define path so many config files in future e.g. load player location not in same as res settings
    public void loadConfiguration(String path) {//String key
        try {
            InputStream read = new FileInputStream(path);
            properties.loadFromXML(read);
            String width = properties.getProperty("width");
            String height = properties.getProperty("height");
            setResolution(Integer.parseInt(width), Integer.parseInt(height));
            read.close();
        } catch (FileNotFoundException e) {
            saveConfiguration("width", 800);// 4:3 800/4*3
            saveConfiguration("height", 600);
            loadConfiguration(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setResolution(int width, int height) {
        Display.WIDTH = width;
        Display.HEIGHT = height;
    }
}
