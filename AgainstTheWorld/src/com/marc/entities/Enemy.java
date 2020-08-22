package com.marc.entities;

import com.marc.main.Game;
import com.marc.world.World;

import java.awt.image.BufferedImage;

public class Enemy extends Entity {

    private double enemySpeed = 0.6;

    public Enemy(int entityX, int entityY, int entityWidth, int entityHeight, BufferedImage entitySprite) {
        super(entityX, entityY, entityWidth, entityHeight, entitySprite);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        //AI for the enemy (axis x)
        if((int) entityX < Game.player.getEntityX() && World.placeIsFree((int) (entityX + enemySpeed), this.getEntityY())) {
            entityX += enemySpeed;
        } else if((int) entityX > Game.player.getEntityX() && World.placeIsFree((int) (entityX - enemySpeed), this.getEntityY())) {
            entityX -= enemySpeed;
        }
        //AI for the enemy (axis y)
        if((int) entityY < Game.player.getEntityY() && World.placeIsFree(this.getEntityX(), (int) (entityY + enemySpeed))) {
            entityY += enemySpeed;
        } else if((int) entityY > Game.player.getEntityY() && World.placeIsFree(this.getEntityX(), (int) (entityY - enemySpeed))) {
            entityY -= enemySpeed;
        }
    }
}
