package com.marc.world;

import com.marc.main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class World {

    private Tile[] tiles;
    public static int WORLD_WIDTH;
    public static int WORLD_HEIGHT;

    //Constructor
    public World(String path) {
        try {
            BufferedImage map = ImageIO.read(getClass().getResource(path));
            int[] mapPixels = new int[map.getWidth() * map.getHeight()];
            WORLD_WIDTH = map.getWidth();
            WORLD_HEIGHT = map.getHeight();
            tiles = new Tile[map.getWidth() * map.getHeight()];
            map.getRGB(0, 0, map.getWidth(), map.getHeight(), mapPixels, 0, map.getWidth());

            for(int indexMapX = 0; indexMapX < map.getWidth(); indexMapX++) {
                for(int indexMapY = 0; indexMapY < map.getHeight(); indexMapY++) {

                    int currentPixel = mapPixels[indexMapX + (indexMapY * map.getWidth())];

                    //Space - background
                    tiles[indexMapX + (indexMapY * WORLD_WIDTH)] = new SpaceBackgroundTile(indexMapX * 40, indexMapY * 40, Tile.TILE_SPACE_BACKGROUND);

                    if(currentPixel == 0xFF000000) {
                        //Space - background
                        tiles[indexMapX + (indexMapY * WORLD_WIDTH)] = new SpaceBackgroundTile(indexMapX * 40, indexMapY * 40, Tile.TILE_SPACE_BACKGROUND);
                    } else if(currentPixel == 0xFFFFFFFF) {
                        //Barrier
                        tiles[indexMapX + (indexMapY * WORLD_WIDTH)] = new SpaceBackgroundTile(indexMapX * 40, indexMapY * 40, Tile.TILE_SPACE_BARRIER);
                    } else if(currentPixel == 0xFFFFD800) {
                        //Player
                        //tiles[indexMapX + (indexMapY * WORLD_WIDTH)] = new SpaceBackgroundTile(indexMapX * 40, indexMapY * 40, Tile.TILE_SPACE_BACKGROUND);
                        Game.player.setEntityX(indexMapX * 40);
                        Game.player.setEntityY(indexMapY * 40);
                    } else if(currentPixel == 0xFFFF0000) {
                        //Enemy
                    } else if(currentPixel == 0xFF4800FF) {
                        //Life
                    } else if(currentPixel == 0xFF4CFF00) {
                        //Magic Power
                    }
                }
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void renderWorld(Graphics worldGraphics) {

        for(int indexAxisX = 0; indexAxisX < WORLD_WIDTH; indexAxisX++) {
            for(int indexAxisY = 0; indexAxisY < WORLD_HEIGHT; indexAxisY++) {
                Tile tile = tiles[indexAxisX + (indexAxisY * WORLD_WIDTH)];
                tile.renderTile(worldGraphics);
            }
        }
    }
}
