package com.marc.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.marc.main.Game;

public class Tile
{
    public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(0, 0, 16, 16);
    public static BufferedImage TILE_WALL = Game.spritesheet.getSprite(16, 0, 16, 16);

    private BufferedImage sprite;
    private int tileX;
    private int tileY;

    public Tile(int tileX, int tileY, BufferedImage sprite)
    {
        this.tileX = tileX;
        this.tileY = tileY;
        this.sprite = sprite;
    }

    public void renderTile(Graphics tileGraphics)
    {
        tileGraphics.drawImage(sprite, (tileX - Camera.cameraX), (tileY - Camera.cameraY), null);
    }
}