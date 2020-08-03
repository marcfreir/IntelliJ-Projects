package com.marc.sounds;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound
{
    private AudioClip clip;

    public static final Sound musicbg = new Sound("/theme.wav");
    public static final Sound hurtEffect = new Sound("/damage.au");
    public static final Sound shootEffect = new Sound("/playerGun.au");
    public static final Sound getItemEffect = new Sound("/getItem.au");
    public static final Sound enemyDeathScreamEffect = new Sound("/enemydeathscream.wav");

    private Sound(String soundName)
    {
        try
        {
            clip = Applet.newAudioClip(Sound.class.getResource(soundName));
        }
        catch (Throwable ignored)
        {

        }
    }

    public void play()
    {
        try
        {
            new Thread()
            {
                @Override
                public void run()
                {
                    clip.play();
                }
            }.start();
        }
        catch (Throwable ignored)
        {

        }
    }

    public void loop()
    {
        try
        {
            new Thread()
            {
                @Override
                public void run()
                {
                    clip.loop();
                }
            }.start();
        }
        catch (Throwable ignored)
        {

        }
    }
}