package com.marc.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.marc.main.Game;
import com.marc.sounds.Sound;
import com.marc.world.Camera;
import com.marc.world.World;

public class Enemy extends Entity
{
    private double enemySpeed = 0.7;

    private int maskX = 8;
    private int maskY = 8;
    private int maskWidth = 10;
    private int maskHeight = 10;

    private int frames = 0;
    private int maxFrames = 20;
    private int indexFrames = 0;
    private int maxIndexFrames = 1;

    private BufferedImage[] enemiesSprites;

    private int enemyLife = 10;

    private boolean enemyIsDamaged = false;
    private int currentDamage = 0;
    private int enemyDamageFrames = 10;

    public Enemy(int entityX, int entityY, int entityWidth, int entityHeight, BufferedImage[] sprite)
    {
        super(entityX, entityY, entityWidth, entityHeight, null);
        enemiesSprites = new BufferedImage[2];
        this.enemiesSprites[0] = sprite[0];
        this.enemiesSprites[1] = sprite[1];
    }

    /**
     * Explanation - The class Enemy extends Entity
     * then, since the Enemy is a kind of Entity,
     * the methods of Entity are called and Overridden
     */
    @Override
    public void updateEntity()
    {
		/*
		maskX = 8;
		maskY = 8;
		maskWidth = 5;
		maskHeight = 5;
		*/

        //Wrap all the IF/ELSE IF statements bellow this first IF to disperse the enemies randomly
        //if (Game.random.nextInt(100) < 50)

        if (isCollidingWithPlayer() == false)
        {
            if (((int)entityX < Game.player.getEntityX()) && (World.placeIsFree((int)(entityX + enemySpeed), this.getEntityY()))
                    && !(isCollidingEnemy((int)(entityX + enemySpeed), this.getEntityY())))
            {
                entityX += enemySpeed;
            }
            else if ((int)entityX > Game.player.getEntityX() && World.placeIsFree((int)(entityX - enemySpeed), this.getEntityY())
                    && !(isCollidingEnemy((int)(entityX - enemySpeed), this.getEntityY())))
            {
                entityX -= enemySpeed;
            }

            else if ((int)entityY < Game.player.getEntityY() && World.placeIsFree(this.getEntityX(), (int)(entityY + enemySpeed))
                    && !(isCollidingEnemy(this.getEntityX(), (int)(entityY + enemySpeed))))
            {
                entityY += enemySpeed;
            }
            else if ((int)entityY > Game.player.getEntityY() && World.placeIsFree(this.getEntityX(), (int)(entityY - enemySpeed))
                    && !(isCollidingEnemy(this.getEntityX(), (int)(entityY - enemySpeed))))
            {
                entityY -= enemySpeed;
            }
        }
        else
        {
            if (Game.random.nextInt(100) < 10)
            {
                Sound.hurtEffect.play();
                //We're colliding - The player is losing life
                Game.player.playerLife -= Game.random.nextInt(3);
                Game.player.isDamaged = true;

                System.out.println("Life: " + Game.player.playerLife);
            }
        }

        //For the animation
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

        collidingBullet();

        checkEnemyLife();

        checkIfEnemyIsDamaged();

    }

    public void checkEnemyLife()
    {
        if (enemyLife <= 0)
        {
            destroySelf();
            return;
        }
    }

    public void checkIfEnemyIsDamaged()
    {
        if (enemyIsDamaged)
        {
            this.currentDamage++;

            if (this.currentDamage == this.enemyDamageFrames)
            {
                this.currentDamage = 0;
                this.enemyIsDamaged = false;
            }
        }
    }

    public void destroySelf()
    {
        Game.enemies.remove(this);
        Game.entities.remove(this);
        Sound.enemyDeathScreamEffect.play();
    }

    public void collidingBullet()
    {
        for (int index = 0; index < Game.bullets.size(); index++)
        {
            Entity enemy = Game.bullets.get(index);

            if (enemy instanceof BulletShoot)
            {
                if (Entity.isCollidingEntity(this, enemy))
                {
                    enemyIsDamaged = true;
                    enemyLife--;
                    Game.bullets.remove(index);
                    return;
                }
            }
        }
    }

    public boolean isCollidingWithPlayer()
    {
        Rectangle currentEnemy = new Rectangle((this.getEntityX() + maskX), (this.getEntityY() + maskY), maskWidth, maskHeight);
        Rectangle playerCollision = new Rectangle(Game.player.getEntityX(), Game.player.getEntityY(), 16, 16);

        return currentEnemy.intersects(playerCollision);
    }

    public boolean isCollidingEnemy(int nextX, int nextY)
    {
        Rectangle currentEnemy = new Rectangle((nextX + maskX), (nextY + maskY), maskWidth, maskHeight);

        for (int index = 0; index < Game.enemies.size(); index++)
        {
            Enemy enemy = Game.enemies.get(index);

            if (enemy == this)
            {
                continue;
            }

            Rectangle targetEnemy = new Rectangle((enemy.getEntityX() + maskX), (enemy.getEntityY() + maskY), maskWidth, maskHeight);

            if (currentEnemy.intersects(targetEnemy))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void renderEntity(Graphics entityGraphics)
    {
        if (!enemyIsDamaged)
        {
            entityGraphics.drawImage(enemiesSprites[indexFrames], (this.getEntityX() - Camera.cameraX), (this.getEntityY() - Camera.cameraY), null);
        }
        else
        {
            entityGraphics.drawImage(Entity.ENEMY_DAMAGED_FEEDBACK_ENTITY, (this.getEntityX() - Camera.cameraX), (this.getEntityY() - Camera.cameraY), null);
        }

        //super.renderEntity(entityGraphics);

        //Set Mask
        //entityGraphics.setColor(Color.magenta);
        //entityGraphics.fillRect(((this.getEntityX() + maskX) - Camera.cameraX), ((this.getEntityY() + maskY) - Camera.cameraY), maskWidth, maskHeight);
    }

}