package com.moskuza;

import com.moskuza.entity.Ghost;
import com.moskuza.entity.Player;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class Server extends JFrame {

    private JButton startButton;
    private final ArrayList<ClientHandler> clients = new ArrayList<>();
    private final ArrayList<Ghost> ghosts = new ArrayList<>();
    private final Random random = new Random();

    public Server() {
        setTitle("Server Control Panel");
        setSize(new Dimension(200, 100));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startButton = new JButton("Start Game");
        startButton.addActionListener(e -> startGame());
        add(startButton);

        setVisible(true);
    }

    private void startGame() {
        System.out.println("Game Started...");
        createGhosts(); // สร้างผี
        Thread.ofVirtual().start(this::startServer);
        Thread.ofVirtual().start(this::moveGhosts); // เริ่มการขยับผี
    }

    private void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            while (true) {
                Socket socket = serverSocket.accept();

                System.out.println("Client connected: " + socket);

                ClientHandler clientHandler = new ClientHandler(socket);
                clients.add(clientHandler);
                Thread.ofVirtual().start(clientHandler);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createGhosts() {
        for (int i = 0; i < 5; i++) {
            Ghost ghost = new Ghost();
            ghost.setX(random.nextInt(0, 1024));
            ghost.setY(random.nextInt(0, 800));
            ghosts.add(ghost);
        }
    }

    private void moveGhosts() {
        while (true) {
            for (Ghost ghost : ghosts) {
                // สุ่มทิศทางการขยับ (บวกลบ MOVE_RANGE)
                int deltaX = random.nextInt(-10, 11); // Random movement range
                int deltaY = random.nextInt(-10, 11);

                // อัปเดตตำแหน่งผี
                ghost.setX(Math.max(0, Math.min(ghost.getX() + deltaX, 1024)));
                ghost.setY(Math.max(0, Math.min(ghost.getY() + deltaY, 800)));
            }
            broadcastGhosts(); // Broadcast ข้อมูลผี
            try {
                Thread.sleep(100); // Delay เพื่อไม่ให้ขยับเร็วเกินไป
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void broadcastGhosts() {
        for (ClientHandler client : clients) {
            client.sendObject(ghosts);
        }
    }

    private class ClientHandler implements Runnable {
        private Socket socket;
        private ObjectOutputStream out;
        private ObjectInputStream in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                this.out = new ObjectOutputStream(socket.getOutputStream());
                this.in = new ObjectInputStream(socket.getInputStream());

                while (true) {
                    Object obj = in.readObject();
                    if (obj instanceof Player player) {
                        broadcast(player);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                    out.close();
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
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

    private void broadcast(Object obj) {
        for (ClientHandler client : clients) {
            client.sendObject(obj);
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
