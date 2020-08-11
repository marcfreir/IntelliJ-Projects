package com.marc.entity;

import com.marc.tilemap.TileMap;

public class Enemy extends MapObject
{
    //Enemy assets
    private int enemyHealth;
    private int enemyMaxHeath;
    private boolean enemyDead;
    private int enemyDamage;
    private boolean enemyFlinching;
    private long enemyFlinchTimer;

    //Constructor
    public Enemy(TileMap tileMap)
    {
        super(tileMap);
    }

    public boolean isEnemyDead()
    {
        return enemyDead;
    }

    //Setters
    public int getEnemyDamage()
    {
        return enemyDamage;
    }

    public void enemyHit(int enemyDamage)
    {
        if (enemyDead || enemyFlinching)
        {
            return;
        }

        enemyHealth -= enemyDamage;

        if (enemyHealth < 0)
        {
            enemyHealth = 0;
        }
        if (enemyHealth == 0)
        {
            enemyDead = true;
        }

        enemyFlinching = true;
        enemyFlinchTimer = System.nanoTime();
    }
}
