package com.moskuza;

import com.moskuza.views.DeveloperView;
import com.moskuza.views.JoinView;
import com.moskuza.views.LobbyView;
import com.moskuza.views.PlayView;

import javax.swing.*;
import java.awt.*;

public class Game extends JFrame{
    private final CardLayout cardLayout;

    public Game() {
        setTitle("Ghost Hunt Online");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1024, 800));
        setResizable(false);

        cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);

        LobbyView lobbyView = new LobbyView(() -> cardLayout.show(cardPanel, "Join"),
                                            () -> cardLayout.show(cardPanel, "Developer"),
                                            () -> System.exit(0));

        DeveloperView developerView = new DeveloperView(() -> cardLayout.show(cardPanel, "Lobby"));

        PlayView playView = new PlayView();
        JoinView joinView = new JoinView(playView.getGameController(),
                                        () -> cardLayout.show(cardPanel, "Lobby"),
                                        () -> cardLayout.show(cardPanel, "Play"));


        cardPanel.add(lobbyView, "Lobby");
        cardPanel.add(developerView, "Developer");
        cardPanel.add(joinView, "Join");
        cardPanel.add(playView, "Play");

        add(cardPanel);

        cardLayout.show(cardPanel, "Lobby");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Game game = new Game();
            game.setVisible(true);
        });
    }
}
