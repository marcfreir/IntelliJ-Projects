package com.marc.entity;

import com.marc.main.GamePanel;
import com.marc.tilemap.Tile;
import com.marc.tilemap.TileMap;

import java.awt.*;

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

    //Collision
    protected int currentRow;
    protected int currentCol;
    protected double destinationX;
    protected double destinationY;
    protected double tempPositionX;
    protected double tempPositionY;
    protected boolean topLeftCorner;
    protected boolean topRightCorner;
    protected boolean bottomLeftCorner;
    protected boolean bottomRightCorner;

    //Animation
    protected Animation animation;
    protected int animationCurrentAction;
    protected int animationPreviousAction;
    protected boolean animationFacingRight;

    //Movements
    protected boolean movementLeft;
    protected boolean movementRight;
    protected boolean movementUp;
    protected boolean movementDown;
    protected boolean movementJumping;
    protected boolean movementFalling;

    //Movements attributes
    protected double objectMoveSpeed;
    protected double objectMaxSpeed;
    protected double objectStopSpeed;
    protected double objectFallSpeed;
    protected double objectMaxFallSpeed;
    protected double objectJumpStart;
    protected double objectStopJumpSpeed;

    //Constructor
    public MapObject(TileMap tileMap)
    {
        this.tileMap = tileMap;
        tileSize = tileMap.getTileSize();
    }

    //Check collision
    public boolean intersects(MapObject other)
    {
        Rectangle rectangle1 = getRectangle();
        Rectangle rectangle2 = other.getRectangle();
        return rectangle1.intersects(rectangle2);
    }

    public Rectangle getRectangle()
    {
        return new Rectangle((int)vectorPositionX - collisionWidth, (int)vectorPositionY - collisionHeight, collisionWidth, collisionHeight);
    }

    public void calculateCorners(double vectorPositionX, double vectorPositionY)
    {
        int leftSideTile = (int)(vectorPositionX - collisionWidth / 2) / tileSize;
        int rightSideTile = (int)(vectorPositionX + collisionWidth / 2 - 1) / tileSize;
        int topSideTile = (int)(vectorPositionY - collisionHeight / 2) / tileSize;
        int bottomSideTile = (int)(vectorPositionY + collisionHeight / 2 - 1) / tileSize;

        int topLeftSideTile = tileMap.getTileType(topSideTile, leftSideTile);
        int topRightSideTile = tileMap.getTileType(topSideTile, rightSideTile);
        int bottomLeftSideTile = tileMap.getTileType(bottomSideTile, leftSideTile);
        int bottomRightSideTile = tileMap.getTileType(bottomSideTile, rightSideTile);

        topLeftCorner = topLeftSideTile == Tile.BLOCKED;
        topRightCorner = topRightSideTile == Tile.BLOCKED;
        bottomLeftCorner = bottomLeftSideTile == Tile.BLOCKED;
        bottomRightCorner = bottomRightSideTile == Tile.BLOCKED;
    }

    public void checkTileMapCollision()
    {
        currentCol = (int)vectorPositionX / tileSize;
        currentRow = (int)vectorPositionY / tileSize;

        destinationX = vectorPositionX + directionX;
        destinationY = vectorPositionY + directionY;

        tempPositionX = vectorPositionX;
        tempPositionY = vectorPositionY;

        calculateCorners(vectorPositionX, destinationY);
        if (directionY < 0)
        {
            if (topLeftCorner || topRightCorner)
            {
                directionY = 0;
                tempPositionY = currentRow * tileSize + collisionHeight / 2.0;
            }
            else
            {
                tempPositionY += directionY;
            }
        }

        if (directionY > 0)
        {
            if (bottomLeftCorner || bottomRightCorner)
            {
                directionY = 0;
                movementFalling = false;
                tempPositionY = (currentRow + 1) * tileSize - collisionHeight / 2.0;
            }
            else
            {
                tempPositionY += directionY;
            }
        }

        calculateCorners(destinationX, vectorPositionY);

        if (directionX < 0)
        {
            if (topLeftCorner || bottomLeftCorner)
            {
                directionX = 0;
                tempPositionX = currentCol * tileSize + collisionWidth / 2.0;
            }
            else
            {
                tempPositionX += directionX;
            }
        }

        if (directionX > 0)
        {
            if (topRightCorner || bottomRightCorner)
            {
                directionX = 0;
                tempPositionX = (currentCol + 1) * tileSize - collisionWidth / 2.0;
            }
            else
            {
                tempPositionX += directionX;
            }
        }

        if (!movementFalling)
        {
            calculateCorners(vectorPositionX, destinationY + 1);

            if (!bottomLeftCorner && !bottomRightCorner)
            {
                movementFalling = true;
            }
        }
    }

    //Getters
    public int getVectorPositionX()
    {
        return (int)vectorPositionX;
    }

    public int getVectorPositionY()
    {
        return (int)vectorPositionY;
    }

    public int getMapObjectWidth()
    {
        return mapObjectWidth;
    }

    public int getMapObjectHeight()
    {
        return mapObjectHeight;
    }

    public int getCollisionWidth()
    {
        return collisionWidth;
    }

    public int getCollisionHeight()
    {
        return collisionHeight;
    }

    //Setters
    public void setPosition(double vectorPositionX, double vectorPositionY)
    {
        this.vectorPositionX = vectorPositionX;
        this.vectorPositionY = vectorPositionY;
    }

    public void setVector(double directionX, double directionY)
    {
        this.directionX = directionX;
        this.directionY = directionY;
    }

    public void setMapObjectPosition()
    {
        mapObjectPositionX = tileMap.getAxisX();
        mapObjectPositionY = tileMap.getAxisY();
    }

    public void setMovementLeft(boolean b)
    {
        movementLeft = b;
    }

    public void setMovementRight(boolean b)
    {
        movementRight = b;
    }

    public void setMovementUp(boolean b)
    {
        movementUp = b;
    }

    public void setMovementDown(boolean b)
    {
        movementDown = b;
    }

    public void setMovementJumping(boolean b)
    {
        movementJumping = b;
    }

    public boolean notOnScreen()
    {
        return (vectorPositionX + mapObjectPositionX + mapObjectWidth < 0) || (vectorPositionX + mapObjectPositionX - mapObjectWidth > GamePanel.WIDTH) ||
                (vectorPositionY + mapObjectPositionY + mapObjectHeight < 0) || (vectorPositionY + mapObjectPositionY - mapObjectHeight > GamePanel.HEIGHT);
    }

    public void drawMapObject(Graphics2D mapObjectGraphics)
    {
        if (animationFacingRight)
        {
            mapObjectGraphics.drawImage(animation.getImage(), (int)(vectorPositionX + mapObjectPositionX - mapObjectWidth / 2), (int)(vectorPositionY + mapObjectPositionY - mapObjectHeight / 2), null);
        }
        else
        {
            mapObjectGraphics.drawImage(animation.getImage(), (int)(vectorPositionX + mapObjectPositionX - mapObjectWidth / 2 + mapObjectWidth), (int)(vectorPositionY + mapObjectPositionY - mapObjectHeight / 2), -mapObjectWidth, mapObjectHeight, null);
        }
    }
}
