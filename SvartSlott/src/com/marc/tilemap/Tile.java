package com.marc.tilemap;

import java.awt.image.BufferedImage;

public class Tile
{
    private BufferedImage tileImage;
    private int tileType;

    //Tile types
    public static final int NORMAL = 0;
    public static final int BLOCKED = 1;

    public Tile(BufferedImage tileImage, int tileType)
    {
        this.tileImage = tileImage;
        this.tileType = tileType;
    }

    public BufferedImage getTileImage()
    {
        return tileImage;
    }

    public int getTileType()
    {
        return  tileType;
    }
}
