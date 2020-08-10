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
        numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
        numColsToDraw = GamePanel.WIDTH / tileSize + 2;
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

            numCols = Integer.parseInt(buffReader.readLine());
            numRows = Integer.parseInt(buffReader.readLine());
            map = new int[numRows][numCols];
            mapWidth = numCols * tileSize;
            mapHeight = numRows * tileSize;

            String delimiters = "\\s+";

            for (int row = 0; row < numRows; row++)
            {
                String line = buffReader.readLine();
                String[] tokens = line.split(delimiters);

                for (int col = 0; col < numCols; col++)
                {
                    map[row][col] = Integer.parseInt(tokens[col]);
                }
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    //Getters
    public int getTileSize()
    {
        return tileSize;
    }

    public int getAxisX()
    {
        return (int)axisX;
    }

    public int getAxisY()
    {
        return (int)axisY;
    }

    public int getMapWidth()
    {
        return mapWidth;
    }

    public int getMapHeight()
    {
        return mapHeight;
    }

    public int getTileType(int row, int col)
    {
        int rowCol = map[row][col];
        int rows = rowCol / numTilesAcross;
        int cols = rowCol %  numTilesAcross;

        return tiles[rows][cols].getTileType();
    }

    //Setters
    public void setTween(double tween)
    {
        this.tween = tween;
    }

    public void setPosition(double axisX, double axisY)
    {
        this.axisX += (axisX - this.axisX) * tween;
        this.axisY += (axisY - this.axisY) * tween;

        fixBounds();

        //Where start drawing
        colOffSet = (int)-this.axisX / tileSize;
        rowOffSet = (int)-this.axisY / tileSize;
    }

    //Helper function
    public void fixBounds()
    {
        if (axisX < boundXMin)
        {
            axisX = boundXMin;
        }

        if (axisY < boundYMin)
        {
            axisY = boundYMin;
        }

        if (axisX > boundXMax)
        {
            axisX = boundXMax;
        }

        if (axisY > boundYMax)
        {
            axisY = boundYMax;
        }
    }

    public void drawTileMap(Graphics2D tileMapGraphics)
    {
        for (int row = rowOffSet; row < rowOffSet + numRowsToDraw; row++)
        {
            if (row >= numRows)
            {
                break;
            }

            for (int col = colOffSet; col < colOffSet + numColsToDraw; col++)
            {
                if (col >= numCols)
                {
                    break;
                }

                if (map[row][col] == 0)
                {
                    continue;
                }

                int rowCol = map[row][col];
                int rows = rowCol / numTilesAcross;
                int cols = rowCol % numTilesAcross;

                tileMapGraphics.drawImage(tiles[rows][cols].getTileImage(), (int)axisX + col * tileSize, (int)axisY + row * tileSize, null);
            }
        }
    }
}
