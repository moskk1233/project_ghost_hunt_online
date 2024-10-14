package com.moskuza;

import com.moskuza.entity.Player;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends JFrame {

    private JButton startButton;
    private final ArrayList<ClientHandler> clients = new ArrayList<>();


    public Server() {
        setTitle("Server Control Panel");
        setSize(new Dimension(200, 100));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startButton = new JButton("Start Game");
        add(startButton);

        setVisible(true);

        Thread.ofVirtual().start(this::startServer);
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
