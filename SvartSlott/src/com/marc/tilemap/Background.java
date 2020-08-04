package com.marc.tilemap;

import com.marc.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Background
{
    private BufferedImage backgroundImage;

    private double positionX;
    private double positionY;
    private double distanceX;
    private double distanceY;

    private double moveScale;

    //Constructor
    public Background(String scale, double moveScale)
    {
        try
        {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream(scale));
            this.moveScale = moveScale;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void setBackgroundPosition(double positionX, double positionY)
    {
        this.positionX = (positionX * moveScale) % GamePanel.WIDTH;
        this.positionY = (positionY * moveScale) % GamePanel.HEIGHT;
    }

    public void setBackgroundVector(double distanceX, double distanceY)
    {
        this.distanceX = distanceX;
        this.distanceY = distanceY;
    }

    public void updateBackground()
    {
        positionX += distanceX;
        positionY += distanceY;
    }

    public void drawBackground(Graphics2D backgroundGraphics)
    {
        backgroundGraphics.drawImage(backgroundImage, (int)positionX, (int)positionY, null);

        if (positionX < 0)
        {
            backgroundGraphics.drawImage(backgroundImage, (int)positionX + GamePanel.WIDTH, (int)positionY, null);
        }
        if (positionX > 0)
        {
            backgroundGraphics.drawImage(backgroundImage, (int)positionX - GamePanel.WIDTH, (int)positionY, null);
        }
    }
}
