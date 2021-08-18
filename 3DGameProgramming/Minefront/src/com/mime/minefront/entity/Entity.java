package com.mime.minefront.entity;

public abstract class Entity {

    //Template

    public static double x, z;
    protected boolean removed = false;

    protected Entity() {//disallows instantiate Entity but abstract does it already or private?

    }

    public void remove() {//from arraylist remove notification? it will stop updating and rendering it
        removed = true;
    }

    public void tick() {
        //the PlayerController does and inherits this its used in Level.java Class
        //			players_mobs_entities.get(i).tick(); update METHOD
    }
}
