package com.marc.entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DepartureExplosion
{
    //Coordinates
    private int explosionPositionX;
    private int explosionPositionY;
    private int explosionMapPositionX;
    private int explosionMapPositionY;

    //Dimensions
    private int explosionMapWidth;
    private int explosionMapHeight;

    private Animation animation;
    private BufferedImage[] explosionSprites;

    private boolean removeExplosion;

    //Constructor
    public DepartureExplosion(int explosionPositionX, int explosionPositionY)
    {
        this.explosionPositionX = explosionPositionX;
        this.explosionPositionY = explosionPositionY;

        explosionMapWidth = 30;
        explosionMapHeight = 30;

        try
        {
            BufferedImage explosionSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/departureExplosion30x30.png"));

            int numExplosionSprites = 6;
            explosionSprites = new BufferedImage[numExplosionSprites];

            for (int indexExplosion  = 0; indexExplosion < explosionSprites.length; indexExplosion++)
            {
                explosionSprites[indexExplosion] = explosionSpriteSheet.getSubimage(indexExplosion * explosionMapWidth, 0, explosionMapWidth, explosionMapHeight);
            }


        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(explosionSprites);
        animation.setDelay(70);
    }

    public void updateDepartureExplosion()
    {
        animation.updateAnimation();

        if (animation.hasPlayedOnce())
        {
            removeExplosion = true;
        }
    }

    public boolean shouldRemoveExplosion()
    {
        return removeExplosion;
    }

    public void setExplosionMapPosition(int explosionPositionX, int explosionPositionY)
    {
        this.explosionMapPositionX = explosionPositionX;
        this.explosionMapPositionY = explosionPositionY;
    }

    public void drawDepartureExplosion(Graphics2D departureExplosionGraphics)
    {
        departureExplosionGraphics.drawImage(animation.getImage(), explosionPositionX + explosionMapPositionX - explosionMapWidth / 2, explosionPositionY + explosionMapPositionY - explosionMapHeight / 2, null);
    }
}
