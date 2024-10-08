package com.moskuza.views;

import com.moskuza.util.Util;

import javax.swing.*;
import java.awt.*;

public class LobbyView extends JPanel {
    public LobbyView(Runnable onPlayClick, Runnable onDeveloperClick, Runnable onExitClick) {
        setLayout(null);
        setBackground(Color.BLACK);

        JButton playButton = new JButton("Play");
        JButton developerButton = new JButton("Developer");
        JButton exitButton = new JButton("Exit");

        playButton.setSize(new Dimension(389, 97));
        playButton.setLocation(new Point(318, 302 - Util.CONTEXT_BAR_OFFSET));

        developerButton.setSize(new Dimension(389, 97));
        developerButton.setLocation(new Point(318, 480 - Util.CONTEXT_BAR_OFFSET));

        exitButton.setSize(new Dimension(389, 97));
        exitButton.setLocation(new Point(318, 680 - Util.CONTEXT_BAR_OFFSET));

        playButton.addActionListener(e -> onPlayClick.run());
        developerButton.addActionListener(e -> onDeveloperClick.run());
        exitButton.addActionListener(e -> onExitClick.run());

        add(playButton);
        add(developerButton);
        add(exitButton);

    }
}
