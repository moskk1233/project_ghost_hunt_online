package com.moskuza.views;

import com.moskuza.util.Util;

import javax.swing.*;
import java.awt.*;

public class JoinView extends JPanel {
    public JoinView(Runnable onBackClick) {
        setLayout(null);
        setBackground(Color.BLACK);

        JLabel ipAddressLabel = new JLabel("Input IP to play");
        ipAddressLabel.setFont(new Font("Tahoma", Font.PLAIN, 48));
        ipAddressLabel.setForeground(Color.WHITE);
        ipAddressLabel.setBounds(342, 260, 339, 58);

        JTextField ipAddressField = new JTextField();
        ipAddressField.setBounds(178, 360, 668, 88);
        ipAddressField.setFont(new Font("Tahoma", Font.PLAIN, 48));

        JButton joinButton = new JButton("Join");
        joinButton.setBounds(370, 500, 284, 88);

        JButton backButton = new JButton("Back");
        backButton.setBounds(370, 650, 284, 88);
        backButton.addActionListener(e -> onBackClick.run());


        add(ipAddressLabel);
        add(ipAddressField);
        add(joinButton);
        add(backButton);
    }
}
