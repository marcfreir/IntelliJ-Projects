package com.marc.world;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class World {

    //Constructor
    public World(String path) {
        try {
            BufferedImage map = ImageIO.read(getClass().getResource(path));

            int[] mapPixels = new int[map.getWidth() * map.getHeight()];
            map.getRGB(0, 0, map.getWidth(), map.getHeight(), mapPixels, 0, map.getWidth());

            for(int indexMap = 0; indexMap < mapPixels.length; indexMap++) {
                if(mapPixels[indexMap] == 0xFFFFD800) {
                    //
                }
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
