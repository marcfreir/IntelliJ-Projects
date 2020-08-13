package com.marc.entity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class DepartureExplosion
{
    //Coordinates
    private int explosionPositionX;
    private int explosionPositionY;

    //Dimensions
    private int mapExplosionWidth;
    private int mapExplosionHeight;

    private Animation animation;
    private BufferedImage[] explosionSprites;

    private boolean removeExplosion;

    //Constructor
    public DepartureExplosion(int explosionPositionX, int explosionPositionY)
    {
        this.explosionPositionX = explosionPositionX;
        this.explosionPositionY = explosionPositionY;

        mapExplosionWidth = 30;
        mapExplosionHeight = 30;

        try
        {
            BufferedImage explosionSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/departureExplosion.png"));

            explosionSprites = new BufferedImage[6];


        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
