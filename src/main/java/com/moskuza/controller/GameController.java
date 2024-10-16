package com.moskuza.controller;

import com.moskuza.entity.Ghost;
import com.moskuza.entity.Player;
import com.moskuza.views.PlayView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GameController {
    private final PlayView playView;
    private final Map<UUID, Player> players = new ConcurrentHashMap<>();
    private final ArrayList<Ghost> ghosts = new ArrayList<>();
    private String currentWave = "";  ;
    private ObjectOutputStream out;

    public GameController(PlayView playView) {
        this.playView = playView;
        new Thread(this::connectToServer).start();
    }

    private void connectToServer() {
        try {
            Socket socket = new Socket("localhost", 12345);
            out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            while (true) {
                Object obj = in.readObject();
                if (obj instanceof Player player) {
                    players.put(player.getId(), player);
                    playView.repaint(); // เรียก repaint เมื่อมีการเปลี่ยนแปลงตำแหน่งผี
                }

                if (obj instanceof ArrayList<?>) {
                    ghosts.clear();
                    ghosts.addAll((ArrayList<Ghost>) obj);
                    playView.repaint(); // เรียก repaint เมื่อมีการเปลี่ยนแปลงตำแหน่งผี
                }

                if (obj instanceof String message && message.startsWith("Wave")) {
                    currentWave = message;
                    playView.getPlayer().setAmmo(5);
                    playView.repaint();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendObject(Object obj) {
        try {
            out.writeObject(obj);
            out.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<UUID, Player> getPlayers() {
        return players;
    }

    public ArrayList<Ghost> getGhosts() {
        return ghosts;
    }

    public String getWave() {
        return currentWave;
    }
}