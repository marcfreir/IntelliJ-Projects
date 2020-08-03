package com.marc.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.marc.main.Game;
import com.marc.sounds.Sound;
import com.marc.world.Camera;

public class BulletShoot extends Entity
{

    private double shootDirectionX;
    private double shootDirectionY;
    private double shootSpeed = 4;

    private int shootLength = 20;
    private int currentShootLength = 0;

    public BulletShoot(int entityX, int entityY, int entityWidth, int entityHeight, BufferedImage sprite, double shootDirectionX, double shootDirectionY)
    {
        super(entityX, entityY, entityWidth, entityHeight, sprite);
        this.shootDirectionX = shootDirectionX;
        this.shootDirectionY = shootDirectionY;
    }

    @Override
    public void updateEntity()
    {
        entityX += shootDirectionX * shootSpeed;
        entityY += shootDirectionY * shootSpeed;

        currentShootLength++;

        if (currentShootLength == shootLength)
        {
            Game.bullets.remove(this);
            return;
        }
    }

    public void renderEntity(Graphics entityGraphics)
    {
        entityGraphics.setColor(Color.yellow);
        entityGraphics.fillOval((this.getEntityX() - Camera.cameraX), (this.getEntityY() - Camera.cameraY), entityWidth, entityHeight);
        Sound.shootEffect.play();
    }

}