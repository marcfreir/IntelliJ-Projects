package com.marc.entity.enemies;

import com.marc.entity.Animation;
import com.marc.entity.Enemy;
import com.marc.tilemap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Spoke extends Enemy
{
    private BufferedImage[] enemySprites;

    //Constructor
    public Spoke(TileMap tileMap)
    {
        super(tileMap);

        objectMoveSpeed = 0.3;
        objectMaxSpeed = 0.3;
        objectFallSpeed = 0.2;
        objectMaxFallSpeed = 10.0;

        mapObjectWidth = 40;
        mapObjectHeight = 40;
        collisionWidth = 36;
        collisionHeight = 36;

        enemyHealth = enemyMaxHeath = 2;
        enemyDamage = 1;

        //Load enemy Spöke sprites
        try
        {
            BufferedImage enemySpokeSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/spriteSheetEnemySpoke40x40.png"));
            enemySprites = new BufferedImage[3];

            for (int indexRow = 0; indexRow < enemySprites.length; indexRow++)
            {
                enemySprites[indexRow] = enemySpokeSpriteSheet.getSubimage(indexRow * mapObjectWidth, 0, mapObjectWidth, mapObjectHeight);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(enemySprites);
        animation.setDelay(300);

        movementRight = true;
        animationFacingRight = true;
    }

    private void getEnemyNextPosition()
    {
        //Movement
        if (movementLeft)
        {
            directionX -= objectMoveSpeed;

            if (directionX < -objectMaxSpeed)
            {
                directionX = -objectMaxSpeed;
            }
        }
        else if (movementRight)
        {
            directionX += objectMoveSpeed;

            if (directionX > objectMaxSpeed)
            {
                directionX = objectMaxSpeed;
            }
        }

        //Falling
        if (movementFalling)
        {
            directionY += objectFallSpeed;
        }
    }

    public void updateEnemy()
    {
        //Update position
        getEnemyNextPosition();
        checkTileMapCollision();
        setPosition(tempPositionX, tempPositionY);

        //Check flinching
        if (enemyFlinching)
        {
            long elapsed = (System.nanoTime() - enemyFlinchTimer) / 1000000;

            if (elapsed > 400)
            {
                enemyFlinching = false;
            }
        }

        //If enemy hits a wall, go other direction
        if (movementRight && directionX == 0)
        {
            movementRight = false;
            movementLeft = true;
            animationFacingRight = false;
        }
        else if (movementLeft && directionX == 0)
        {
            movementRight = true;
            movementLeft = false;
            animationFacingRight = true;
        }

        //Update animation
        animation.updateAnimation();
    }

    public void drawEnemy(Graphics2D enemyGraphics)
    {
        if (notOnScreen())
        {
            return;
        }

        setMapObjectPosition();

        super.drawMapObject(enemyGraphics);
    }
}
