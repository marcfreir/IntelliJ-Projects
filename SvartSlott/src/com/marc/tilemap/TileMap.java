package com.marc.tilemap;

import com.marc.main.GamePanel;

import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class TileMap
{
    //Tile Map position
    private double axisX;
    private double axisY;

    //Tile Map bounds
    private int boundXMin;
    private int boundYMin;
    private int boundXMax;
    private int boundYMax;

    private double tween;

    //For the map
    private int[][] map;
    private int tileSize;
    private int numRows;
    private int numCols;
    private int mapWidth;
    private int mapHeight;

    //For the tile set
    private BufferedImage tileSet;
    private int numTilesAcross;
    private Tile[][] tiles;

    //Drawing Tile Map
    private int rowOffSet;
    private int colOffSet;
    private int numRowsToDraw;
    private int numColsToDraw;

    //Constructor
    public TileMap(int tileSize)
    {
        this.tileSize = tileSize;
        numRowsToDraw = GamePanel.HEIGHT / (tileSize + 2);
        numColsToDraw = GamePanel.WIDTH / (tileSize + 2);
        tween = 0.07;
    }

    public void loadTiles(String set)
    {
        try
        {
            tileSet = ImageIO.read(getClass().getResourceAsStream(set));
            numTilesAcross = tileSet.getWidth() / tileSize;
            tiles = new Tile[2][numTilesAcross];

            BufferedImage subImage;

            for (int col = 0; col < numTilesAcross; col++)
            {
                subImage = tileSet.getSubimage(col * tileSize, 0, tileSize, tileSize);
                tiles[0][col] = new Tile(subImage, Tile.NORMAL);
                subImage = tileSet.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
                tiles[1][col] = new Tile(subImage, Tile.BLOCKED);
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void loadMap(String set)
    {
        try
        {
            InputStream input = getClass().getResourceAsStream(set);
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(input));

            numRows = Integer.parseInt(buffReader.readLine());
            numCols = Integer.parseInt(buffReader.readLine());
            map = new int[numRows][numCols];
            mapWidth = numCols * tileSize;
            mapHeight = numRows * tileSize;
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
