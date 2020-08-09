package com.marc.entity;

import com.marc.tilemap.TileMap;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends MapObject
{
    //Player assets
    private int playerHealth;
    private int playerMaxHeath;
    private int playerFire;
    private int playerMaxFire;
    private boolean playerDead;
    private boolean playerFlinching;
    private long playerFlinchTimer;

    //Shoot/Throw/Fire the beam/fireball/magical power
    private boolean playerFiring;
    private int fireCost;
    private int fireBallDamage;
    //private ArrayList<FireBall> fireBalls;

    //Scratch (can be changed to bump)
    private boolean scratching;
    private int scratchDamage;
    private int scratchRange;

    //Gliding
    private boolean playerGliding;

    //Animations
    private ArrayList<BufferedImage[]> playerSprites;
    private final int[] playerNumFrames = {};

    //Animation actions
    private static final int PLAYER_IDLE = 0;
    private static final int PLAYER_WALKING = 1;
    private static final int PLAYER_JUMPING = 2;
    private static final int PLAYER_FALLING = 3;
    private static final int PLAYER_GLIDING = 4;
    private static final int PLAYER_FIREBALL = 5;
    private static final int PLAYER_SCRATCHING = 6;


    public Player(TileMap tileMap) {
        super(tileMap);
    }
}
