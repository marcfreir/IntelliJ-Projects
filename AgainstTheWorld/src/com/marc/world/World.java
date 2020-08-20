package com.marc.world;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class World {

    //Constructor
    public World(String path) {
        try {
            BufferedImage map = ImageIO.read(getClass().getResource(path));

            int[] mapPixels = new int[map.getWidth() * map.getHeight()];
            map.getRGB(0, 0, map.getWidth(), map.getHeight(), mapPixels, 0, map.getWidth());

            for(int indexMapX = 0; indexMapX < map.getWidth(); indexMapX++) {
                for(int indexMapY = 0; indexMapY < map.getHeight(); indexMapY++) {

                    int currentPixel = mapPixels[indexMapX + (indexMapY * map.getWidth())];

                    if(currentPixel == 0xFF000000) {
                        //Space - background
                    } else if(currentPixel == 0xFFFFFFFF) {
                        //Barrier
                    }
                }
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void renderWorld(Graphics worldGraphics) {
        //
    }
}
