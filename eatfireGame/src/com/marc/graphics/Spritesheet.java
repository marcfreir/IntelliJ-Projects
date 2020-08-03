package com.marc.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import static javax.imageio.ImageIO.read;

public class Spritesheet
{
    private BufferedImage spritesheet;

    //Constructor
    public Spritesheet(String path)
    {
        try
        {
            spritesheet = read(getClass().getResource(path));
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }

    public BufferedImage getSprite(int axisXSpritesheet, int axisYSpritesheet, int widthSpritesheet, int heightSpritesheet)
    {
        return spritesheet.getSubimage(axisXSpritesheet, axisYSpritesheet, widthSpritesheet, heightSpritesheet);
    }
}