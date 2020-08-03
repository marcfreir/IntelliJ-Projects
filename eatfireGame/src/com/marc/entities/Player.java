package com.marc.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.marc.graphics.Spritesheet;
import com.marc.main.Game;
import com.marc.sounds.Sound;
import com.marc.world.Camera;
import com.marc.world.World;

public class Player extends Entity
{

    public boolean playerRight;
    public boolean playerLeft;
    public boolean playerUp;
    public boolean playerDown;

    public int rightDirection = 0;
    public int leftDirection = 1;
    public int forwardDirection = rightDirection;

    public double playerSpeed = 1.2;

    private int frames = 0;
    private int maxFrames = 5;
    private int indexFrames = 0;
    private int maxIndexFrames = 3;

    private boolean playerMoved = false;

    private BufferedImage[] rightPlayerOrientation;
    private BufferedImage[] leftPlayerOrientation;

    private BufferedImage playerDamage;
    private boolean hasGun;

    public boolean isDamaged = false;
    private int damageFrames = 0;

    //Ammo
    public int ammo = 0;

    //Shoot
    public boolean shootWithKeyboard = false;
    public boolean shootWithMouse = false;


    public double playerLife = 100;
    public double playerMaxLife = 100;

    public int mousePositionX;
    public int mousePositionY;

    public Player(int entityX, int entityY, int entityWidth, int entityHeight, BufferedImage sprite)
    {
        super(entityX, entityY, entityWidth, entityHeight, sprite);

        int amountOfImagesPerOrientation = 4;
        int spriteSheetWidth = 16;
        int spriteSheetHeight = 16;

        rightPlayerOrientation = new BufferedImage[4];
        leftPlayerOrientation = new BufferedImage[4];
        playerDamage = Game.spritesheet.getSprite(0, 16, spriteSheetWidth, spriteSheetHeight);

        //For the Sprite List to the Right in the spriteSheet.png
        for (int index = 0; index < amountOfImagesPerOrientation; index++)
        {
            rightPlayerOrientation[index] = Game.spritesheet.getSprite((32 + (index * 16)), 0, spriteSheetWidth, spriteSheetHeight);
        }

        //For the Sprite List to the Left in the spriteSheet.png
        for (int index = 0; index < amountOfImagesPerOrientation; index++)
        {
            leftPlayerOrientation[index] = Game.spritesheet.getSprite((32 + (index * 16)), 16, spriteSheetWidth, spriteSheetHeight);
        }
    }

    //public void updatePlayer() #changed to updateEntity()
    /**
     * Explanation - The class Player extends Entity
     * then, since the Player is a kind of Entity,
     * the methods of Entity are called and Overridden
     */
    @Override
    public void updateEntity()
    {
        playerMoved = false;
        if (playerRight && World.placeIsFree((int)(entityX + playerSpeed), this.getEntityY()))
        {
            playerMoved = true;
            forwardDirection = rightDirection;
            entityX += playerSpeed;
        }
        else if (playerLeft && World.placeIsFree((int)(entityX - playerSpeed), this.getEntityY()))
        {
            playerMoved = true;
            forwardDirection = leftDirection;
            entityX -= playerSpeed;
        }

        if (playerUp && World.placeIsFree(this.getEntityX(), (int)(entityY - playerSpeed)))
        {
            playerMoved = true;
            entityY -= playerSpeed;
        }
        else if (playerDown && World.placeIsFree(this.getEntityX(), (int)(entityY + playerSpeed)))
        {
            playerMoved = true;
            entityY += playerSpeed;
        }

        //For the animation
        if (playerMoved)
        {
            frames++;

            if (frames == maxFrames)
            {
                frames = 0;
                indexFrames++;

                if (indexFrames > maxIndexFrames)
                {
                    indexFrames = 0;
                }
            }
        }

        checkCollisionLifePack();
        checkCollisionAmmo();
        checkIsDamaged();
        playerLifeIsOverRestartGame();
        checkCollisionGun();
        playerShootWithKeyboard();
        playerShootWithMouse();
        updateCamera();
    }

    public void updateCamera()
    {
        Camera.cameraX = Camera.clamp((this.getEntityX() - (Game.WIDTH / 2)), 0, (World.WORLD_WIDTH * 16 - Game.WIDTH));
        Camera.cameraY = Camera.clamp((this.getEntityY() - (Game.HEIGHT / 2)), 0, (World.WORLD_HEIGHT * 16 - Game.HEIGHT));
    }

    public void playerShootWithKeyboard()
    {
        if (shootWithKeyboard)
        {
            //Create bullet and shoot
            shootWithKeyboard = false;

            if (hasGun && (ammo > 0))
            {
                ammo--;

                //System.out.println("Shooting"); <-//Just for debugging
                int shootDirectionX = 0;
                int shootPointX = 0;
                int shootPointY = 7;

                if (forwardDirection == rightDirection)
                {
                    shootPointX = 12;
                    shootDirectionX = 1;
                }
                else
                {
                    shootPointX = 0;
                    shootDirectionX = -1;
                }

                BulletShoot bullet = new BulletShoot((this.getEntityX() + shootPointX), (this.getEntityY() + shootPointY), 3, 3, null, shootDirectionX, 0);
                Game.bullets.add(bullet);
            }
        }
    }


    public void playerShootWithMouse()
    {
        if (shootWithMouse)
        {
            //System.out.println("Shoot!"); <-//Just for debugging
            //Create bullet and shoot
            shootWithMouse = false;

            //Calculate the angle of the mouse - to see the Angle add Math.toDegrees(mouseAngle "content")
            //double mouseAngle = Math.atan2(mousePositionY - ((this.getEntityY() + 8) - Camera.cameraY), mousePositionX - ((this.getEntityX() + 8) - Camera.cameraX));
            //System.out.println(mouseAngle); <-//Just for debugging

            if (hasGun && (ammo > 0))
            {
                ammo--;

                //System.out.println("Shooting"); <-//Just for debugging

                int shootPointX = 0;
                int shootPointY = 8;

                double mouseAngle = 0;

                if (forwardDirection == rightDirection)
                {
                    shootPointX = 12;
                    //shootDirectionX = 1;
                    mouseAngle = Math.atan2(mousePositionY - ((this.getEntityY() + shootPointY) - Camera.cameraY), mousePositionX - ((this.getEntityX() + shootPointX) - Camera.cameraX));
                }
                else
                {
                    shootPointX = 0;
                    //shootDirectionX = -1;
                    mouseAngle = Math.atan2(mousePositionY - ((this.getEntityY() + shootPointY) - Camera.cameraY), mousePositionX - ((this.getEntityX() + shootPointX) - Camera.cameraX));
                }

                double shootDirectionX = Math.cos(mouseAngle);
                double shootDirectionY = Math.sin(mouseAngle);


                BulletShoot bullet = new BulletShoot((this.getEntityX() + shootPointX), (this.getEntityY() + shootPointY), 3, 3, null, shootDirectionX, shootDirectionY);
                Game.bullets.add(bullet);
            }
        }
    }


    public void playerLifeIsOverRestartGame()
    {
        if (playerLife <= 0)
        {
            //Game Over!
            Game.gameState = "GAME_OVER";
        }
    }

    public void checkIsDamaged()
    {
        if (isDamaged)
        {
            this.damageFrames++;
            if (this.damageFrames == 8)
            {
                this.damageFrames = 0;
                isDamaged = false;
            }
        }
    }

    public void checkCollisionGun()
    {
        for (int index = 0; index < Game.entities.size(); index++)
        {
            Entity currentGun = Game.entities.get(index);

            if (currentGun instanceof Gun)
            {
                if (Entity.isCollidingEntity(this, currentGun))
                {
                    hasGun = true;
                    //System.out.println("Current Gun: " + hasGun); <-//Just for debugging
                    Game.entities.remove(currentGun);
                    Sound.getItemEffect.play();
                }
            }
        }
    }

    public void checkCollisionAmmo()
    {
        for (int index = 0; index < Game.entities.size(); index++)
        {
            Entity currentAmmo = Game.entities.get(index);

            if (currentAmmo instanceof Ammo)
            {
                if (Entity.isCollidingEntity(this, currentAmmo))
                {
                    ammo +=100;
                    //System.out.println("Current Ammo: " + ammo); <-//Just for debugging
                    Game.entities.remove(currentAmmo);
                    Sound.getItemEffect.play();
                }
            }
        }
    }

    public void checkCollisionLifePack()
    {
        for (int index = 0; index < Game.entities.size(); index++)
        {
            Entity currentLifePack = Game.entities.get(index);

            if (currentLifePack instanceof LifePack)
            {
                if (Entity.isCollidingEntity(this, currentLifePack))
                {
                    playerLife += 10;

                    if (playerLife >= 100)
                    {
                        playerLife = 100;
                    }
                    Game.entities.remove(currentLifePack);
                    Sound.getItemEffect.play();
                    //Game.entities.remove(index);
                    //return;
                }
            }
        }
    }

    @Override
    public void renderEntity(Graphics entityGraphics)
    {
        if (!isDamaged)
        {
            if (forwardDirection == rightDirection)
            {
                entityGraphics.drawImage(rightPlayerOrientation[indexFrames], (this.getEntityX() - Camera.cameraX), (this.getEntityY() - Camera.cameraY), null);

                if (hasGun)
                {
                    //Draw Gun to the Right
                    entityGraphics.drawImage(Entity.GUN_RIGHT_ENTITY, ((this.getEntityX() + 2) - Camera.cameraX), ((this.getEntityY() + 2) - Camera.cameraY), null);
                }
            }
            else if (forwardDirection == leftDirection)
            {
                entityGraphics.drawImage(leftPlayerOrientation[indexFrames], (this.getEntityX() - Camera.cameraX), (this.getEntityY() - Camera.cameraY), null);

                if (hasGun)
                {
                    //Draw Gun to the Left
                    entityGraphics.drawImage(Entity.GUN_LEFT_ENTITY, ((this.getEntityX() - 2) - Camera.cameraX), ((this.getEntityY() + 2) - Camera.cameraY), null);
                }
            }
        }
        else
        {
            entityGraphics.drawImage(playerDamage, (this.getEntityX() - Camera.cameraX), (this.getEntityY() - Camera.cameraY), null);

            if (hasGun)
            {
                if (forwardDirection == leftDirection)
                {
                    entityGraphics.drawImage(Entity.GUN_LEFT_DAMAGED_ENTITY, ((this.getEntityX() - 2) - Camera.cameraX), (this.getEntityY() - Camera.cameraY), null);
                }
                else
                {
                    entityGraphics.drawImage(Entity.GUN_RIGHT_DAMAGED_ENTITY, ((this.getEntityX() + 2) - Camera.cameraX), (this.getEntityY() - Camera.cameraY), null);
                }
            }
        }
    }

}
