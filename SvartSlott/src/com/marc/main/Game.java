package com.marc.main;

import javax.swing.*;

public class Game {

    public static void main(String[] args)
    {
        JFrame window = new JFrame("Svart Slott");
        window.setContentPane(new GamePanel());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        //Center window
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
