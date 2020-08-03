package com.marc.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.marc.entities.Ammo;
import com.marc.entities.Enemy;
import com.marc.entities.Entity;
import com.marc.entities.Gun;
import com.marc.entities.LifePack;
import com.marc.entities.Player;
import com.marc.graphics.Spritesheet;
import com.marc.main.Game;

public class World
{
    public static Tile[] tiles;

    public static int WORLD_WIDTH;
    public static int WORLD_HEIGHT;

    public static final int TILE_SIZE = 16;

    public World(String path)
    {
        try
        {
            BufferedImage map = ImageIO.read(getClass().getResource(path));
            int[] pixels = new int[map.getWidth() * map.getHeight()];

            WORLD_WIDTH = map.getWidth();
            WORLD_HEIGHT = map.getHeight();

            tiles = new Tile[map.getWidth() * map.getHeight()];
            map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());

            for (int indexWidth = 0; indexWidth < map.getWidth(); indexWidth++)
            {
                for (int indexHeight = 0; indexHeight < map.getHeight(); indexHeight++)
                {
                    int currentPixel = pixels[indexWidth + (indexHeight * map.getWidth())];

                    //Floor
                    tiles[indexWidth + (indexHeight * WORLD_WIDTH)] = new FloorTile(indexWidth * 16, indexHeight * 16, Tile.TILE_FLOOR);

                    if (currentPixel == 0xFF000000)
                    {
                        //Floor - Map Color: Black
                        tiles[indexWidth + (indexHeight * WORLD_WIDTH)] = new FloorTile(indexWidth * 16, indexHeight * 16, Tile.TILE_FLOOR);
                    }
                    else if (currentPixel == 0xFFFFFFFF)
                    {
                        //Wall - Map Color: White
                        tiles[indexWidth + (indexHeight * WORLD_WIDTH)] = new WallTile(indexWidth * 16, indexHeight * 16, Tile.TILE_WALL);
                    }
                    else if (currentPixel == 0xFF4800FF)
                    {
                        //Player - Map Color: Electric Purple
                        //tiles[indexWidth + (indexHeight * WORLD_WIDTH)] = new FloorTile(indexWidth * 16, indexHeight * 16, Tile.TILE_FLOOR);
                        Game.player.setEntityX(indexWidth * 16);
                        Game.player.setEntityY(indexHeight * 16);
                    }
                    else if (currentPixel == 0xFF7F0000)
                    {
                        //Enemy - Map Color: Maroon (Dark Red)
                        BufferedImage[] bfEnemy = new BufferedImage[2];
                        bfEnemy[0] = Game.spritesheet.getSprite(128, 0, 16, 16);
                        bfEnemy[1] = Game.spritesheet.getSprite(128 + 16, 0, 16, 16);
                        Enemy enemy = new Enemy(indexWidth * 16, indexHeight * 16, 16, 16, bfEnemy);
                        Game.entities.add(enemy);
                        Game.enemies.add(enemy);
                    }
                    else if (currentPixel == 0xFF0026FF)
                    {
                        //Gun - Map Color: Blue
                        Game.entities.add(new Gun(indexWidth * 16, indexHeight * 16, 16, 16, Entity.GUN_ENTITY));
                    }
                    else if (currentPixel == 0xFFFF0000)
                    {
                        //Life Pack - Map Color: Red
                        LifePack lifePack = new LifePack(indexWidth * 16, indexHeight * 16, 16, 16, Entity.LIFEPACK_ENTITY);
                        //lifePack.setEntityMask(8, 8, 8, 8); <-//Just for debugging
                        Game.entities.add(lifePack);
                    }
                    else if (currentPixel == 0xFF303030)
                    {
                        //Gun Magazines - Map Color: Night Rider (Dark Gray)
                        Game.entities.add(new Ammo(indexWidth * 16, indexHeight * 16, 16, 16, Entity.AMMO_ENTITY));
                    }
                    else
                    {
                        //to do
                    }
                }
            }
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }

    public static boolean placeIsFree(int nextX, int nextY)
    {
        int x1 = nextX / TILE_SIZE;
        int y1 = nextY / TILE_SIZE;

        int x2 = ((nextX + TILE_SIZE) - 1) / TILE_SIZE;
        int y2 = nextY / TILE_SIZE;

        int x3 = nextX / TILE_SIZE;
        int y3 = ((nextY + TILE_SIZE) - 1) / TILE_SIZE;

        int x4 = ((nextX + TILE_SIZE) - 1) / TILE_SIZE;
        int y4 = ((nextY + TILE_SIZE) - 1) / TILE_SIZE;

        return !((tiles[x1 + (y1 * World.WORLD_WIDTH)] instanceof WallTile) ||
                (tiles[x2 + (y2 * World.WORLD_WIDTH)] instanceof WallTile) ||
                (tiles[x3 + (y3 * World.WORLD_WIDTH)] instanceof WallTile) ||
                (tiles[x4 + (y4 * World.WORLD_WIDTH)] instanceof WallTile));
    }

    public static void restartGame(String gameLevel)
    {
        Game.entities.clear();
        Game.enemies.clear();
        Game.entities = new ArrayList<Entity>();
        Game.enemies = new ArrayList<Enemy>();
        Game.spritesheet = new Spritesheet("/spriteSheet.png");
        //Based on the spriteSheetNewPosition.png File - set the coordinates in getSprite
        Game.player = new Player(0, 0, 16, 16, Game.spritesheet.getSprite(32, 0, 16, 16));
        Game.entities.add(Game.player);
        Game.world = new World("/" + gameLevel);
        return;
    }

    public void renderWorld(Graphics worldGraphics)
    {
        int startX = (Camera.cameraX >> 4);
        int startY = (Camera.cameraY >> 4);

        int finalX = startX + (Game.WIDTH >> 4);
        int finalY = startY + (Game.HEIGHT >> 4);

        for (int indexWidth = startX; indexWidth <= finalX; indexWidth++)
        {
            for (int indexHeight = startY; indexHeight <= finalY; indexHeight++)
            {
                if (indexWidth < 0 || indexHeight < 0 || indexWidth >= WORLD_WIDTH || indexHeight >= WORLD_HEIGHT)
                {
                    continue;
                }
                Tile tile = tiles[indexWidth + (indexHeight * WORLD_WIDTH)];
                tile.renderTile(worldGraphics);
            }
        }
    }
}