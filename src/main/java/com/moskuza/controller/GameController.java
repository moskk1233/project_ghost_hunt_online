package com.moskuza.controller;

import com.moskuza.entity.Ghost;
import com.moskuza.entity.Player;
import com.moskuza.views.PlayView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class GameController {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private Map<UUID, Player> players;

    public GameController(PlayView playView) {
        this.players = new HashMap<>();
        Thread.ofVirtual().start(() -> {
            try {
                this.socket = new Socket("127.0.0.1", 12345);
                this.out = new ObjectOutputStream(socket.getOutputStream());
                this.in = new ObjectInputStream(socket.getInputStream());

                while (true) {

                    Object obj = in.readObject();
                    if (obj instanceof Player player) {
                        updatePlayer(player.getId(), player);
                        playView.repaint();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public void updatePlayer(UUID playerId, Player player) {
        this.players.put(playerId, player);
    }

    public Map<UUID, Player> getPlayers() {
        return this.players;
    }

    public synchronized void sendObject(Object obj) {
        try {
            this.out.writeObject(obj);
            this.out.flush();
            this.out.reset();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
