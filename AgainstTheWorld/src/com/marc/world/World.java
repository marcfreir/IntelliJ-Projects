package com.marc.world;

import com.marc.entities.Enemy;
import com.marc.entities.Entity;
import com.marc.entities.Life;
import com.marc.entities.MagicPower;
import com.marc.main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class World {

    public static Tile[] tiles;
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
                        tiles[indexMapX + (indexMapY * WORLD_WIDTH)] = new SpaceBarrierTile(indexMapX * 40, indexMapY * 40, Tile.TILE_SPACE_BARRIER);
                    } else if(currentPixel == 0xFFFFD800) {
                        //Player
                        //tiles[indexMapX + (indexMapY * WORLD_WIDTH)] = new SpaceBackgroundTile(indexMapX * 40, indexMapY * 40, Tile.TILE_SPACE_BACKGROUND);
                        Game.player.setEntityX(indexMapX * 40);
                        Game.player.setEntityY(indexMapY * 40);
                    } else if(currentPixel == 0xFFFF0000) {
                        //Enemy
                        Game.entityList.add(new Enemy(indexMapX * 40, indexMapY * 40, 40, 40, Entity.ENEMY_ENTITY));
                    } else if(currentPixel == 0xFF4800FF) {
                        //Life
                        Game.entityList.add(new Life(indexMapX * 40, indexMapY * 40, 16, 14, Entity.LIFE_ENTITY));
                    } else if(currentPixel == 0xFF4CFF00) {
                        //Magic Power
                        Game.entityList.add(new MagicPower(indexMapX * 40, indexMapY * 40, 16, 14, Entity.MAGIC_POWER_ENTITY));
                    }
                }
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void renderWorld(Graphics worldGraphics) {

        int cameraStartPositionX = Camera.cameraOffsetX / 40;
        int cameraStartPositionY = Camera.cameraOffsetY / 40;

        int cameraFinalPositionX = cameraStartPositionX + (Game.WIDTH / 40);
        int cameraFinalPositionY = cameraStartPositionY + (Game.HEIGHT / 40);

        for(int indexAxisX = cameraStartPositionX; indexAxisX <= cameraFinalPositionX; indexAxisX++) {
            for(int indexAxisY = cameraStartPositionY; indexAxisY <= cameraFinalPositionY; indexAxisY++) {

                if(indexAxisX < 0 || indexAxisY < 0 || indexAxisX >= WORLD_WIDTH || indexAxisY >= WORLD_HEIGHT) {
                    continue;
                }
                Tile tile = tiles[indexAxisX + (indexAxisY * WORLD_WIDTH)];
                tile.renderTile(worldGraphics);
            }
        }
    }
}
