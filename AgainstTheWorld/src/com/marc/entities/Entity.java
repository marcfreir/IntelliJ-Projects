package com.marc.entities;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    protected int entityX;
    protected int entityY;
    protected int entityWidth;
    protected int entityHeight;

    private BufferedImage entitySprite;

    //Constructor
    public Entity(int entityX, int entityY, int entityWidth, int entityHeight, BufferedImage entitySprite) {
        this.entityX = entityX;
        this.entityY = entityY;
        this.entityWidth = entityWidth;
        this.entityHeight = entityHeight;
        this.entitySprite = entitySprite;
    }

    //Setters
    public void setEntityX(int entityX) {
        this.entityX = entityX;
    }

    public void setEntityY(int entityY) {
        this.entityY = entityY;
    }

    //Getters
    public int getEntityX() {
        return entityX;
    }

    public int getEntityY() {
        return entityY;
    }

    public int getEntityWidth() {
        return entityWidth;
    }

    public int getEntityHeight() {
        return entityHeight;
    }

    public void updateEntity() {
        //
    }

    public void renderEntity(Graphics entityGraphics) {
        entityGraphics.drawImage(entitySprite, this.getEntityX(), this.getEntityY(), null);
    }


}
