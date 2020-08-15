package com.marc.entities;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    private int entityX;
    private int entityY;
    private int entityWidth;
    private int entityHeight;

    private BufferedImage entitySprite;

    //Constructor
    public Entity(int entityX, int entityY, int entityWidth, int entityHeight, BufferedImage entitySprite) {
        this.entityX = entityX;
        this.entityY = entityY;
        this.entityWidth = entityWidth;
        this.entityHeight = entityHeight;
        this.entitySprite = entitySprite;
    }

    //Getters

    public int getEntityX() {
        return this.entityX;
    }

    public int getEntityY() {
        return this.entityY;
    }

    public int getEntityWidth() {
        return this.entityWidth;
    }

    public int getEntityHeight() {
        return this.entityHeight;
    }

    public void renderEntity(Graphics entityGraphics) {
        entityGraphics.drawImage(entitySprite, this.getEntityX(), this.getEntityY(), null);
    }
}
