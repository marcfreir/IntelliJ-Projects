package com.marc.entities;

import com.marc.main.Game;
import com.marc.world.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    public boolean playerDirectionRight;
    public boolean playerDirectionLeft;
    public boolean playerDirectionUp;
    public boolean playerDirectionDown;

    public int playerVectorRight = 0;
    public int playerVectorLeft = 1;
    public int playerDirectionVector = playerVectorRight;

    public double playerSpeed = 1.2;

    private int playerFrames = 0;
    private int playerMaxFrames = 5;
    private int indexFrames = 0;
    private int maxIndexFrames = 3;

    private boolean playerMoved = false;

    private BufferedImage[] playerOrientationRight;
    private BufferedImage[] playerOrientationLeft;

    public Player(int entityX, int entityY, int entityWidth, int entityHeight, BufferedImage entitySprite) {
        super(entityX, entityY, entityWidth, entityHeight, entitySprite);

        int playerFramesPerOrientation = 4;
        playerOrientationRight = new BufferedImage[playerFramesPerOrientation];
        playerOrientationLeft = new BufferedImage[playerFramesPerOrientation];
        //playerOrientationUp
        //playerOrientationDown

        //Player flies forward
        for(int indexOrientationRight = 0; indexOrientationRight < playerFramesPerOrientation; indexOrientationRight++) {
            playerOrientationRight[indexOrientationRight] = Game.spriteSheet.getSpritesheet((indexOrientationRight * 40), 80, 40, 40);
        }
        //Player flies backward
        for(int indexOrientationLeft = 0; indexOrientationLeft < playerFramesPerOrientation; indexOrientationLeft++) {
            playerOrientationLeft[indexOrientationLeft] = Game.spriteSheet.getSpritesheet((indexOrientationLeft * 40), 120, 40, 40);
        }
        //Implement Player flies upward and downward

    }

    @Override
    public void updateEntity() {
        playerMoved = false;
        if(playerDirectionRight) {
            playerMoved = true;
            playerDirectionVector = playerVectorRight;
            entityX += playerSpeed;
        }
        else if(playerDirectionLeft) {
            playerMoved = true;
            playerDirectionVector = playerVectorLeft;
            entityX -= playerSpeed;
        }
        if(playerDirectionUp) {
            playerMoved  =true;
            //playerCurrentVector = playerVectorUp;
            entityY -= playerSpeed;
        }
        else if(playerDirectionDown) {
            playerMoved = true;
            //playerCurrentVector = playerVectorDown;
            entityY += playerSpeed;
        }

        //Check if player moved and animate
        if(playerMoved) {
            playerFrames++;

            if(playerFrames == playerMaxFrames) {
                playerFrames = 0;
                indexFrames++;

                if(indexFrames > maxIndexFrames) {
                    indexFrames = 0;
                }
            }
        }

        //Camera
        Camera.cameraOffsetX = this.getEntityX() - (Game.WIDTH / 2);
        Camera.cameraOffsetY = this.getEntityY() - (Game.HEIGHT / 2);
    }

    @Override
    public void renderEntity(Graphics entityGraphics) {
        //super.renderEntity(entityGraphics);
        if(playerDirectionVector == playerVectorRight) {
            entityGraphics.drawImage(playerOrientationRight[indexFrames], this.getEntityX() - Camera.cameraOffsetX, this.getEntityY() - Camera.cameraOffsetY, null);
        } else if(playerDirectionVector == playerVectorLeft) {
            entityGraphics.drawImage(playerOrientationLeft[indexFrames], this.getEntityX() - Camera.cameraOffsetX, this.getEntityY() - Camera.cameraOffsetY, null);
        }

    }
}
