package com.marc.entity;

import com.marc.tilemap.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends MapObject
{
    //Player assets
    private int playerHealth;
    private int playerMaxHeath;
    private int playerMagicPower;
    private int playerMaxMagicPower;
    private boolean playerDead;
    private boolean playerFlinching;
    private long playerFlinchTimer;

    //Shoot/Throw/Fire the beam/fireball/magical power
    private boolean playerShootingMagicPower;
    private int magicPowerCost;
    private int magicPowerDamage;
    //private ArrayList<MagicPower> magicPowers;

    //Punch (can be changed to bump)
    private boolean playerPunching;
    private int punchDamage;
    private int punchRange;

    //Flying
    private boolean playerFlying;

    //Animations
    private ArrayList<BufferedImage[]> playerSprites;
    private final int[] playerNumFrames = {2, 8, 1, 2, 4, 2, 5};

    //Animation actions
    private static final int PLAYER_IDLE = 0;
    private static final int PLAYER_WALKING = 1;
    private static final int PLAYER_JUMPING = 2;
    private static final int PLAYER_FALLING = 3;
    private static final int PLAYER_FLYING = 4;
    private static final int PLAYER_MAGIC_POWER = 5;
    private static final int PLAYER_PUNCHING = 6;


    public Player(TileMap tileMap) {
        super(tileMap);

        mapObjectWidth = 40;
        mapObjectHeight = 40;
        collisionWidth = 26;
        collisionHeight = 26;

        objectMoveSpeed = 0.3;
        objectMaxSpeed = 1.6;
        objectStopSpeed = 0.4;
        objectFallSpeed = 0.15;
        objectMaxFallSpeed = 4.0;
        objectJumpStart = -4.8;
        objectStopJumpSpeed = 0.3;

        animationFacingRight = true;

        playerHealth = playerMaxHeath = 5;
        playerMagicPower = playerMaxMagicPower = 2500;

        magicPowerCost = 200;
        magicPowerDamage = 5;
        //magicPowers = new ArrayList<MagicPower>();

        punchDamage = 8;
        punchRange = 40;

        //Load sprites
        try
        {
            BufferedImage playerSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/spriteSheetPlayer40x40.png"));

            int playerSpritesNumRows = 7;
            for (int indexRow = 0; indexRow < playerSpritesNumRows; indexRow++)
            {
                BufferedImage[] bufferedImage = new BufferedImage[playerNumFrames[indexRow]];

                for (int indexCol = 0; indexCol < playerNumFrames[indexRow]; indexCol++)
                {
                    if (indexRow != 6)
                    {
                        bufferedImage[indexCol] = playerSpriteSheet.getSubimage(indexCol * mapObjectWidth, indexRow * mapObjectHeight, mapObjectWidth, mapObjectHeight);
                    }
                    else
                    {
                        bufferedImage[indexCol] = playerSpriteSheet.getSubimage(indexCol * mapObjectWidth * 2, indexRow * mapObjectHeight, mapObjectWidth, mapObjectHeight);
                    }
                }

                playerSprites.add(bufferedImage);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    //Getters
    public int getPlayerHealth()
    {
        return playerHealth;
    }

    public int getPlayerMaxHeath()
    {
        return playerMaxHeath;
    }

    public int getPlayerMagicPower()
    {
        return playerMagicPower;
    }

    public int getPlayerMaxMagicPower()
    {
        return playerMaxMagicPower;
    }

    //Setters
    public void setPlayerShootingMagicPower()
    {
        playerShootingMagicPower = true;
    }

    public void setPlayerPunching()
    {
        playerPunching = true;
    }

    public void setPlayerFlying(boolean playerFlying)
    {
        this.playerFlying = playerFlying;
    }

    public void updatePlayer()
    {
        //Update player position
        getPlayerNextPosition();
        checkTileMapCollision();
        setPosition(tempPositionX, tempPositionY);

        //Set animation
        if (playerPunching)
        {
            if (animationCurrentAction != PLAYER_PUNCHING)
            {
                animationCurrentAction = PLAYER_PUNCHING;
                animation.setFrames(playerSprites.get(PLAYER_PUNCHING));
                animation.setDelay(50);
                mapObjectWidth = 80;
            }
        }
        else if (playerShootingMagicPower)
        {
            if (animationCurrentAction != PLAYER_MAGIC_POWER)
            {
                animationCurrentAction = PLAYER_MAGIC_POWER;
                animation.setFrames(playerSprites.get(PLAYER_MAGIC_POWER));
                animation.setDelay(100);
                mapObjectWidth = 40;
            }
        }
        else if (directionY > 0)
        {
            if (playerFlying)
            {
                if (animationCurrentAction != PLAYER_FLYING)
                {
                    animationCurrentAction = PLAYER_FLYING;
                    animation.setFrames(playerSprites.get(PLAYER_FLYING));
                    animation.setDelay(100);
                    mapObjectWidth = 40;
                }
            }
            else if (animationCurrentAction != PLAYER_FALLING)
            {
                animationCurrentAction = PLAYER_FALLING;
                animation.setFrames(playerSprites.get(PLAYER_FALLING));
                animation.setDelay(100);
                mapObjectWidth = 40;
            }
        }
        else if (directionY < 0)
        {
            if (animationCurrentAction != PLAYER_JUMPING)
            {
                animationCurrentAction = PLAYER_JUMPING;
                animation.setFrames(playerSprites.get(PLAYER_JUMPING));
                animation.setDelay(-1);
                mapObjectWidth = 40;
            }
        }
        else if (movementLeft || movementRight)
        {
            if (animationCurrentAction != PLAYER_WALKING)
            {
                animationCurrentAction = PLAYER_WALKING;
                animation.setFrames(playerSprites.get(PLAYER_WALKING));
                animation.setDelay(40);
                mapObjectWidth = 40;
            }
        }
        else
        {
            if (animationCurrentAction != PLAYER_IDLE)
            {
                animationCurrentAction = PLAYER_IDLE;
                animation.setFrames(playerSprites.get(PLAYER_IDLE));
                animation.setDelay(400);
                mapObjectWidth = 40;
            }
        }

        animation.updateAnimation();

        //Set player direction
        if (animationCurrentAction != PLAYER_PUNCHING && animationCurrentAction != PLAYER_MAGIC_POWER)
        {
            if (movementRight)
            {
                animationFacingRight = true;
            }
            if (movementLeft)
            {
                animationFacingRight = false;
            }
        }
    }

    public void drawPlayer(Graphics2D playerGraphics)
    {
        setMapObjectPosition();
    }
}
