package com.marc.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {

    private BufferedImage spritesheet;

    //Constructor
    public SpriteSheet(String path) {
        try {
            spritesheet = ImageIO.read(getClass().getResource(path));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public BufferedImage getSpritesheet(int spriteSheetX, int spriteSheetY, int spriteSheetWidth, int spriteSheetHeight) {
        return spritesheet.getSubimage(spriteSheetX, spriteSheetY, spriteSheetWidth, spriteSheetHeight);
    }
}
