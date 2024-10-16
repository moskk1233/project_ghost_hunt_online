package com.moskuza.views;

import com.moskuza.controller.GameController;
import com.moskuza.util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class JoinView extends JPanel {
    private final JLabel ipAddressLabel;
    private final JTextField ipAddressField;
    private final JButton joinButton;
    private final JButton backButton;

    public JoinView(GameController gameController, Runnable onBackClick, Runnable onJoinSuccess) {
        setLayout(null);
        setBackground(Color.BLACK);

        ipAddressLabel = new JLabel("Input IP to play");
        ipAddressLabel.setFont(new Font("Tahoma", Font.PLAIN, 48));
        ipAddressLabel.setForeground(Color.WHITE);
        ipAddressLabel.setBounds(342, 260, 339, 58);

        ipAddressField = new JTextField();
        ipAddressField.setBounds(178, 360, 668, 88);
        ipAddressField.setFont(new Font("Tahoma", Font.PLAIN, 48));

        joinButton = new JButton("Join");
        joinButton.setBounds(370, 500, 284, 88);
        joinButton.addActionListener(e -> {
            String serverIp = ipAddressField.getText();
            if (serverIp.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please input server IP", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                gameController.connectToServer(serverIp);
                onJoinSuccess.run();
            } catch (IOException err) {
                JOptionPane.showMessageDialog(this, "Unable connect to server, Please try again later", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton = new JButton("Back");
        backButton.setBounds(370, 650, 284, 88);
        backButton.addActionListener(e -> onBackClick.run());


        add(ipAddressLabel);
        add(ipAddressField);
        add(joinButton);
        add(backButton);
    }
}
