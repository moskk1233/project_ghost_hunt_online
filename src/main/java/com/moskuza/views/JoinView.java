package com.moskuza.views;

import com.moskuza.controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
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

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        // ตั้งค่า anti-aliasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // ข้อความและฟอนต์
        String text = "Ghost Hunt Online";
        Font font = new Font("Arial", Font.BOLD, 70);
        g2d.setFont(font);

        // วาด outline (stroke) ของข้อความ
        g2d.setColor(Color.YELLOW);
        TextLayout textLayout = new TextLayout(text, font, g2d.getFontRenderContext());
        textLayout.draw(g2d, 200, 150);
        g2d.setStroke(new BasicStroke(3)); // ขนาดของ stroke
        g2d.setColor(Color.YELLOW);
        textLayout.draw(g2d, 200, 150);

        // วาดตัวหนังสือจริง (fill)
        g2d.setColor(Color.BLUE);
        g2d.fill(textLayout.getOutline(AffineTransform.getTranslateInstance(200, 150)));
    }
}
