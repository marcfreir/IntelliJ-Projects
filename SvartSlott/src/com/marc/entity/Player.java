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
        collisionWidth = 36;
        collisionHeight = 36;

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

            playerSprites = new ArrayList<BufferedImage[]>();

            int playerSpritesNumRows = 7;

            for (int indexRow = 0; indexRow < playerSpritesNumRows; indexRow++)
            {
                BufferedImage[] bufferedImage = new BufferedImage[playerNumFrames[indexRow]];

                for (int indexCol = 0; indexCol < playerNumFrames[indexRow]; indexCol++)
                {
                    if (indexRow != PLAYER_PUNCHING)
                    {
                        bufferedImage[indexCol] = playerSpriteSheet.getSubimage(indexCol * mapObjectWidth, indexRow * mapObjectHeight, mapObjectWidth, mapObjectHeight);
                    }
                    else
                    {
                        bufferedImage[indexCol] = playerSpriteSheet.getSubimage(indexCol * mapObjectWidth, indexRow * mapObjectHeight, mapObjectWidth, mapObjectHeight);
                    }
                }

                playerSprites.add(bufferedImage);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        animation = new Animation();
        animationCurrentAction = PLAYER_IDLE;
        animation.setFrames(playerSprites.get(PLAYER_IDLE));
        animation.setDelay(400);
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

    private void getPlayerNextPosition()
    {
        //Movement
        if (movementLeft)
        {
            directionX -= objectMoveSpeed;

            if (directionX < -objectMaxSpeed)
            {
                directionX = -objectMaxSpeed;
            }
        }
        else if (movementRight)
        {
            directionX += objectMoveSpeed;

            if (directionX > objectMaxSpeed)
            {
                directionX = objectMaxSpeed;
            }
        }
        else
        {
            if (directionX > 0)
            {
                directionX -= objectStopSpeed;

                if (directionX < 0)
                {
                    directionX = 0;
                }
            }
            else if (directionX < 0)
            {
                directionX += objectStopSpeed;

                if (directionX > 0)
                {
                    directionX = 0;
                }
            }
        }

        //Cannot move while attacking, except in air
        if ((animationCurrentAction == PLAYER_PUNCHING || animationCurrentAction == PLAYER_MAGIC_POWER) && !(movementJumping || movementFalling))
        {
            directionX = 0;
        }

        //Jumping
        if (movementJumping && !movementFalling)
        {
            directionY = objectJumpStart;
            movementFalling = true;
        }

        //Falling
        if (movementFalling)
        {
            if (directionY > 0 && playerFlying)
            {
                directionY += objectFallSpeed * 0.1;
            }
            else
            {
                directionY += objectFallSpeed;
            }

            if (directionY > 0)
            {
                movementJumping = false;
            }

            if (directionY < 0 && !movementJumping)
            {
                directionY += objectStopJumpSpeed;
            }

            if (directionY > objectMaxFallSpeed)
            {
                directionY = objectMaxFallSpeed;
            }
        }
    }

    public void updatePlayer()
    {
        //Update player position
        getPlayerNextPosition();
        checkTileMapCollision();
        setPosition(tempPositionX, tempPositionY);

        //Check if punching has stopped
        if (animationCurrentAction == PLAYER_PUNCHING)
        {
            if (animation.hasPlayedOnce())
            {
                playerPunching = false;
            }
        }

        //Check if shooting magic power has stopped
        if (animationCurrentAction == PLAYER_MAGIC_POWER)
        {
            if (animation.hasPlayedOnce())
            {
                playerShootingMagicPower = false;
            }
        }

        //Set animation
        if (playerPunching)
        {
            if (animationCurrentAction != PLAYER_PUNCHING)
            {
                animationCurrentAction = PLAYER_PUNCHING;
                animation.setFrames(playerSprites.get(PLAYER_PUNCHING));
                animation.setDelay(50);
                mapObjectWidth = 40;
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

        //Draw player
        if (playerFlinching)
        {
            long elapsed = (System.nanoTime() - playerFlinchTimer) / 1000000;

            if (elapsed / 100 % 2 == 0)
            {
                return;
            }
        }
        if (animationFacingRight)
        {
            playerGraphics.drawImage(animation.getImage(), (int)(vectorPositionX + mapObjectPositionX - mapObjectWidth / 2), (int)(vectorPositionY + mapObjectPositionY - mapObjectHeight / 2), null);
        }
        else
        {
            playerGraphics.drawImage(animation.getImage(), (int)(vectorPositionX + mapObjectPositionX - mapObjectWidth / 2 + mapObjectWidth), (int)(vectorPositionY + mapObjectPositionY - mapObjectHeight / 2), -mapObjectWidth, mapObjectHeight, null);
        }
    }
}
