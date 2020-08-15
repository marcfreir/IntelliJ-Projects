package com.marc.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class SpriteSheet {

    private BufferedImage spritesheet;

    //Constructor
    public SpriteSheet(String path) {
        try {
            spritesheet = ImageIO.read(getClass().getResourceAsStream("/"));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public BufferedImage getSpritesheet(int spriteSheetX, int spriteSheetY, int spriteSheetWidth, int spriteSheetHeight) {
        return spritesheet.getSubimage(spriteSheetX, spriteSheetY, spriteSheetWidth, spriteSheetHeight);
    }
}
