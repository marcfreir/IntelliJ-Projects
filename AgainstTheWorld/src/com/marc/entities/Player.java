package com.marc.entities;

import java.awt.image.BufferedImage;

public class Player extends Entity {

    public boolean playerDirectionRight;
    public boolean playerDirectionLeft;
    public boolean playerDirectionUp;
    public boolean playerDirectionDown;

    public int playerSpeed = 4;

    public Player(int entityX, int entityY, int entityWidth, int entityHeight, BufferedImage entitySprite) {
        super(entityX, entityY, entityWidth, entityHeight, entitySprite);
    }

    @Override
    public void updateEntity() {
        if(playerDirectionRight) {
            entityX += playerSpeed;
        }
        else if(playerDirectionLeft) {
            entityX -= playerSpeed;
        }
        if(playerDirectionUp) {
            entityY -= playerSpeed;
        }
        else if(playerDirectionDown) {
            entityY += playerSpeed;
        }
    }
}
