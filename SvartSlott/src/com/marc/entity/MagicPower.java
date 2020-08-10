package com.marc.entity;

import com.marc.tilemap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MagicPower extends MapObject
{

    private boolean magicPowerHit;
    private boolean removeMagicPower;
    private BufferedImage[] magicPowerSprites;
    private BufferedImage[] magicPowerHitSprites;

    //Constructor
    public MagicPower(TileMap tileMap, boolean magicPowerDirectionRight)
    {
        super(tileMap);

        objectMoveSpeed = 3.8;

        if (magicPowerDirectionRight)
        {
            directionX = objectMoveSpeed;
        }
        else
        {
            directionX = -objectMoveSpeed;
        }

        mapObjectWidth = 30;
        mapObjectHeight = 30;
        collisionWidth = 14;
        collisionHeight = 14;

        //Load sprites
        try
        {
            BufferedImage magicPowerSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/magicPower.png"));

            int magicPowerAmount = 4;
            magicPowerSprites = new BufferedImage[magicPowerAmount];

            for (int indexMagicPower = 0; indexMagicPower < magicPowerSprites.length; indexMagicPower++)
            {
                magicPowerSprites[indexMagicPower] = magicPowerSpriteSheet.getSubimage(indexMagicPower * mapObjectWidth, 0, mapObjectWidth, mapObjectHeight);
            }

            int magicPowerHitAmount = 3;
            magicPowerHitSprites = new BufferedImage[magicPowerHitAmount];

            for (int indexMagicPowerHit = 0; indexMagicPowerHit < magicPowerHitSprites.length; indexMagicPowerHit++)
            {
                magicPowerHitSprites[indexMagicPowerHit] = magicPowerSpriteSheet.getSubimage(indexMagicPowerHit * mapObjectWidth, mapObjectHeight, mapObjectWidth, mapObjectHeight);
            }

            animation = new Animation();
            animation.setFrames(magicPowerSprites);
            animation.setDelay(70);

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    //Setters
    public void setMagicPowerHit()
    {
        if (magicPowerHit)
        {
            return;
        }
        magicPowerHit = true;
        animation.setFrames(magicPowerHitSprites);
        animation.setDelay(70);
        directionX = 0;
    }

    public boolean shouldRemoveMagicPower()
    {
        return removeMagicPower;
    }

    public void updateMagicPower()
    {
        checkTileMapCollision();
        setPosition(tempPositionX, tempPositionY);

        animation.updateAnimation();

        if (magicPowerHit && animation.hasPlayedOnce())
        {
            removeMagicPower = true;
        }
    }

    public void drawMagicPower(Graphics2D magicPowerGraphics)
    {
        setMapObjectPosition();

        if (animationFacingRight)
        {
            magicPowerGraphics.drawImage(animation.getImage(), (int)(vectorPositionX + mapObjectPositionX - mapObjectWidth / 2), (int)(vectorPositionY + mapObjectPositionY - mapObjectHeight / 2), null);
        }
        else
        {
            magicPowerGraphics.drawImage(animation.getImage(), (int)(vectorPositionX + mapObjectPositionX - mapObjectWidth / 2 + mapObjectWidth), (int)(vectorPositionY + mapObjectPositionY - mapObjectHeight / 2), -mapObjectWidth, mapObjectHeight, null);
        }
    }
}
