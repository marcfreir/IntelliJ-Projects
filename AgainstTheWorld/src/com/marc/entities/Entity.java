package com.marc.entities;

import com.marc.main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    public static BufferedImage LIFE_ENTITY = Game.spriteSheet.getSpritesheet(12, 54, 14, 14);
    public static BufferedImage MAGIC_POWER_ENTITY = Game.spriteSheet.getSpritesheet(52, 54, 14, 14);
    public static BufferedImage ENEMY_ENTITY = Game.spriteSheet.getSpritesheet(280, 80, 40, 40);


    protected double entityX;
    protected double entityY;
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
        return (int)entityX;
    }

    public int getEntityY() {
        return (int)entityY;
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
