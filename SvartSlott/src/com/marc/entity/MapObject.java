package com.marc.entity;

import com.marc.tilemap.TileMap;

public abstract class MapObject
{
    //Tile attributes
    protected TileMap tileMap;
    protected int tileSize;
    protected double mapObjectPositionX;
    protected double mapObjectPositionY;

    //Position and Vector
    protected double vectorPositionX;
    protected double vectorPositionY;
    protected double directionX;
    protected double directionY;

    //Dimensions
    protected int mapObjectWidth;
    protected int mapObjectHeight;

    //Collision box
    protected int collisionWidth;
    protected int collisionHeight;
}
