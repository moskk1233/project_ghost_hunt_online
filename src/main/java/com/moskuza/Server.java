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
    private int currentWave = 1;

    private final ArrayList<Ghost> ghosts = new ArrayList<>();
    private final Random random = new Random();

    public final ArrayList<ClientHandler> clients = new ArrayList<>();

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
        new Thread(this::startServer).start();
        new Thread(this::moveGhosts).start();
    }

    private void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            while (true) {
                Socket socket = serverSocket.accept();

                System.out.println("Client connected: " + socket);

                ClientHandler clientHandler = new ClientHandler(this, socket);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
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
            boolean allGhostDead = true;

            for (Ghost ghost : ghosts) {
                if (!ghost.isDead()) {
                    // สุ่มทิศทางการขยับ (บวกลบ MOVE_RANGE)
                    int deltaX = random.nextInt(-10, 11); // Random movement range
                    int deltaY = random.nextInt(-10, 11);

                    // อัปเดตตำแหน่งผี
                    ghost.setX(Math.max(0, Math.min(ghost.getX() + deltaX, 1024)));
                    ghost.setY(Math.max(0, Math.min(ghost.getY() + deltaY, 800)));

                    if (isGhostCollision(ghost)) {
                        handleGhostCollision(ghost);
                    }

                    allGhostDead = false;
                }
            }

            if (allGhostDead && currentWave < 10) {
                currentWave++;
                retreiveGhosts();
            }

            broadcastGhosts(); // Broadcast ข้อมูลผี
            try {
                Thread.sleep(100); // Delay เพื่อไม่ให้ขยับเร็วเกินไป
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void retreiveGhosts() {
        for (Ghost ghost : ghosts) {
            ghost.setDead(false);
            ghost.setX(random.nextInt(0, 1024));
            ghost.setY(random.nextInt(0, 800));
        }
    }

    private boolean isGhostCollision(Ghost ghost) {
        return ghost.getX() > 944 || ghost.getX() < 50 || ghost.getY() > 670 || ghost.getY() < 10;
    }

    private void handleGhostCollision(Ghost ghost) {
        if (ghost.getX() >= 944) {
            ghost.setX(944);
        } else if (ghost.getX() <= 50) {
            ghost.setX(50);
        }

        if (ghost.getY() >= 670) {
            ghost.setY(670);
        } else if (ghost.getY() <= 10) {
            ghost.setY(10);
        }
    }

    private void broadcastGhosts() {
        for (ClientHandler client : clients) {
            client.sendObject(ghosts);
        }
    }

    private class ClientHandler implements Runnable {
        private Socket socket;
        private Server server;
        private ObjectOutputStream out;
        private ObjectInputStream in;

        public ClientHandler(Server server, Socket socket) {
            this.server = server;
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

                    if (obj instanceof Ghost updatedGhost) {
                        for (Ghost ghost : ghosts) {
                            if (ghost.getId().equals(updatedGhost.getId())) {
                                System.out.println(ghost.getId());
                                ghost.setDead(true);
                            }
                        }
                    }

                    broadcastWaveInfo();
                }
            } catch (Exception e) {
                server.clients.removeIf(client -> client.socket == socket);
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

    private void broadcastWaveInfo() {
        for (ClientHandler client : clients) {
            client.sendObject(currentWave);
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
