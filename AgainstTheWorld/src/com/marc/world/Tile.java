package com.marc.world;

import com.marc.main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {

    public static BufferedImage TILE_SPACE_BACKGROUND = Game.spriteSheet.getSpritesheet(200, 160, 40, 40);
    public static BufferedImage TILE_SPACE_BARRIER = Game.spriteSheet.getSpritesheet(280, 160, 80, 40);

    private BufferedImage tileSprite;
    private int tileAxisX;
    private int tileAxisY;

    //Constructor
    public Tile(int tileAxisX, int tileAxisY, BufferedImage tileSprite) {
        this.tileAxisX = tileAxisX;
        this.tileAxisY = tileAxisY;
        this.tileSprite = tileSprite;
    }

    public void renderTile(Graphics tileGraphics) {
        tileGraphics.drawImage(tileSprite, tileAxisX - Camera.cameraOffsetX, tileAxisY - Camera.cameraOffsetY, null);
    }
}
