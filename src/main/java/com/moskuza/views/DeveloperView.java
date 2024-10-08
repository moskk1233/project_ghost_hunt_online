package com.moskuza.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class DeveloperView extends JPanel {
    private final Image backgroundImage;

    public DeveloperView(Runnable onBackClick) {
        setLayout(null);
        setBackground(Color.BLACK);

        URL backgroundUrl = getClass().getResource("/images/bg.png");
        backgroundImage = Toolkit.getDefaultToolkit().createImage(backgroundUrl);

        JLabel title = new JLabel("Developer");
        title.setFont(new Font("Tahoma", Font.BOLD, 70));
        title.setBounds(343, 84, 500, 85);
        title.setForeground(Color.YELLOW);

        JTextArea devInfo = new JTextArea();
        devInfo.setText("""
                นายนนท์ปวิธ โพธิ์นิล\t66011212105

                นายมงคลวิทย์ ชาวงษ์\t66011212207

                นายนฤพล ท่าสะอาด\t66011212182
                """);
        devInfo.setEditable(false);
        devInfo.setDragEnabled(false);
        devInfo.setFont(new Font("Tahoma", Font.PLAIN, 48));
        devInfo.setForeground(Color.WHITE);
        devInfo.setBackground(new Color(0, 0, 0, 0)); // Transparent background
        devInfo.setBounds(144, 223, 750, 300); // Position the text area manually

        add(title);
        add(devInfo);

        JButton backButton = new JButton("Back");
        backButton.setSize(new Dimension(389, 97));
        backButton.setLocation(new Point(317, 650));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onBackClick.run();
            }
        });
        add(backButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, 1024, 1024, this);
        }
    }
}
