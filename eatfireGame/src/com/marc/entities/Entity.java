package com.marc.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.marc.main.Game;
import com.marc.world.Camera;

public class Entity
{
    public static int lifePackPosition = 6;
    public static int gunPosition = 7;
    public static int gunToTheRightPosition = 0;
    public static int gunToTheLeftPosition = 1;
    public static int gunToTheRightDamagedPosition = 3;
    public static int gunToTheLeftDamagedPosition = 4;
    public static int enemyPosition = 8;
    public static int enemyDamagedPosition = 1;
    public static int ammoPosition = 6;
    public static int spriteSheetWidth = 16;
    public static int spriteSheetHeight = 16;
    public static int firstLineAxisY = 0;
    public static int secondLineAxisY = 16;
    public static int thirdLineAxisY = 32;
    public static int fourthLineAxisY = 48;

    public static BufferedImage LIFEPACK_ENTITY = Game.spritesheet.getSprite((lifePackPosition * spriteSheetWidth), firstLineAxisY, spriteSheetWidth, spriteSheetHeight);
    public static BufferedImage GUN_ENTITY = Game.spritesheet.getSprite((gunPosition * spriteSheetWidth), firstLineAxisY, spriteSheetWidth, spriteSheetHeight);
    public static BufferedImage GUN_RIGHT_ENTITY = Game.spritesheet.getSprite((gunToTheRightPosition * spriteSheetWidth), thirdLineAxisY, spriteSheetWidth, spriteSheetHeight);
    public static BufferedImage GUN_LEFT_ENTITY = Game.spritesheet.getSprite((gunToTheLeftPosition * spriteSheetWidth), thirdLineAxisY, spriteSheetWidth, spriteSheetHeight);
    public static BufferedImage GUN_RIGHT_DAMAGED_ENTITY = Game.spritesheet.getSprite((gunToTheRightDamagedPosition * spriteSheetWidth), fourthLineAxisY, spriteSheetWidth, spriteSheetHeight);
    public static BufferedImage GUN_LEFT_DAMAGED_ENTITY = Game.spritesheet.getSprite((gunToTheLeftDamagedPosition * spriteSheetWidth), fourthLineAxisY, spriteSheetWidth, spriteSheetHeight);
    public static BufferedImage ENEMY_ENTITY = Game.spritesheet.getSprite((enemyPosition * spriteSheetWidth), firstLineAxisY, spriteSheetWidth, spriteSheetHeight);
    public static BufferedImage ENEMY_DAMAGED_FEEDBACK_ENTITY = Game.spritesheet.getSprite((enemyDamagedPosition * spriteSheetWidth), fourthLineAxisY, spriteSheetWidth, spriteSheetHeight);
    public static BufferedImage AMMO_ENTITY = Game.spritesheet.getSprite((ammoPosition * spriteSheetWidth), secondLineAxisY, spriteSheetWidth, spriteSheetHeight);

    protected double entityX;
    protected double entityY;
    protected int entityWidth;
    protected int entityHeight;

    public boolean debug = false;

    //
    private BufferedImage sprite;

    private int entityMaskX;
    private int entityMaskY;
    private int entityMaskWidth;
    private int entityMaskHeight;

    //Constructor
    public Entity(int entityX, int entityY, int entityWidth, int entityHeight, BufferedImage sprite)
    {
        this.entityX = entityX;
        this.entityY = entityY;
        this.entityWidth = entityWidth;
        this.entityHeight = entityHeight;
        this.sprite = sprite;

        this.entityMaskX = 0;
        this.entityMaskY = 0;
        this.entityMaskWidth = entityWidth;
        this.entityMaskHeight = entityHeight;
    }

    public void setEntityMask(int entityMaskX, int entityMaskY, int entityMaskWidth, int entityMaskHeight)
    {
        this.entityMaskX = entityMaskX;
        this.entityMaskY = entityMaskY;
        this.entityMaskWidth = entityMaskWidth;
        this.entityMaskHeight = entityMaskHeight;
    }

    //Getters
    public int getEntityX()
    {
        return (int)this.entityX;
    }

    public int getEntityY()
    {
        return (int)this.entityY;
    }

    public int getEntityWidth()
    {
        return this.entityWidth;
    }

    public int getEntityHeight()
    {
        return this.entityHeight;
    }

    //Setters
    public void setEntityX(int newEntityX)
    {
        this.entityX = newEntityX;
    }

    public void setEntityY(int newEntityY)
    {
        this.entityY = newEntityY;
    }

    public void updateEntity()
    {
        //To do
    }

    public static boolean isCollidingEntity(Entity entityOne, Entity entityTwo)
    {
        Rectangle maskEntityOne = new Rectangle((entityOne.getEntityX() + entityOne.entityMaskX), (entityOne.getEntityY() + entityOne.entityMaskY), entityOne.entityMaskWidth, entityOne.entityMaskHeight);
        Rectangle maskEntityTwo = new Rectangle((entityTwo.getEntityX() + entityTwo.entityMaskX), (entityTwo.getEntityY() + entityTwo.entityMaskY), entityTwo.entityMaskWidth, entityTwo.entityMaskHeight);

        return maskEntityOne.intersects(maskEntityTwo);
    }

    //Render
    public void renderEntity(Graphics entityGraphics)
    {
        entityGraphics.drawImage(sprite, (this.getEntityX() - Camera.cameraX), (this.getEntityY() - Camera.cameraY), null);
        //entityGraphics.setColor(Color.red); <-//Just for debugging
        //entityGraphics.fillRect(((this.getEntityX() + entityMaskX) - Camera.cameraX), ((this.getEntityY() + entityMaskY) - Camera.cameraY), entityMaskWidth, entityMaskHeight); <-//Just for debugging
    }
}