package com.moskuza.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class DeveloperView extends JPanel {
    private final Image backgroundImage;
    private final Image mosImage;
    private final Image anmImage;
    private final Image tonImage;

    public DeveloperView(Runnable onBackClick) {
        setLayout(null);
        setBackground(Color.BLACK);

        URL backgroundUrl = getClass().getResource("/images/bg.png");
        backgroundImage = Toolkit.getDefaultToolkit().createImage(backgroundUrl);

        mosImage = Toolkit
            .getDefaultToolkit()
            .createImage(getClass().getResource("/images/mos.png"));

        anmImage = Toolkit
            .getDefaultToolkit()
            .createImage(getClass().getResource("/images/anm.png"));

        tonImage = Toolkit
            .getDefaultToolkit()
            .createImage(getClass().getResource("/images/ton.png"));

        JLabel title = new JLabel("Developer");
        title.setFont(new Font("Tahoma", Font.BOLD, 70));
        title.setBounds(343, 84, 500, 85);
        title.setForeground(Color.YELLOW);

        JTextArea mos = new JTextArea("นายนนท์ปวิธ โพธิ์นิล"+"\n"+"66011212105");
        JTextArea anm = new JTextArea("นายนฤพล ท่าสะอาด\n66011212182");
        JTextArea ton = new JTextArea("นายมงคลวิทย์ ชาวงษ์\n66011212207");

        //ทำให้ JTextarea ไม่สามารถแก้ไขได้
        mos.setEditable(false);
        anm.setEditable(false);
        ton.setEditable(false);
        //ตั้งค่าสีของกรอบ
        mos.setOpaque(false);
        ton.setOpaque(false);
        anm.setOpaque(false);

        mos.setForeground(Color.WHITE);
        anm.setForeground(Color.WHITE);
        ton.setForeground(Color.WHITE);

        mos.setBounds(150, 500, 300, 50);
        mos.setFont(new Font("Tahoma", 1, 20));
        anm.setBounds(450, 500, 300, 50);
        anm.setFont(new Font("Tahoma", 1, 20));
        ton.setBounds(700, 500, 300, 50);
        ton.setFont(new Font("Tahoma", 1, 20));

        add(title);
        add(mos);
        add(anm);
        add(ton);

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

        g.drawImage(mosImage, 150, 300, 200, 200, this);
        g.drawImage(anmImage, 450, 300, 200, 200, this);
        g.drawImage(tonImage, 700, 300, 200, 200, this);
    }
}
