package com.moskuza.views;

import com.moskuza.util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;

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
