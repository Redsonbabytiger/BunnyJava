import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class BunnyGame {
    public static void start() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Original BunnyGame");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.setLocationRelativeTo(null);

            JPanel panel = new JPanel();
            panel.setLayout(null);

            final int bunnyWidth = 100;
            final int bunnyHeight = 100;

            // Load bunny image
            ImageIcon bunnyIcon = null;
            try {
                URL url = java.net.URI.create("https://thumbs.dreamstime.com/b/rabbit-hand-drawn-contour-line-drawing-black-white-image-easter-bunny-postcards-printing-fabric-cute-animal-doodles-171134457.jpg").toURL();
                Image img = new ImageIcon(url).getImage();
                Image scaledImg = img.getScaledInstance(bunnyWidth, bunnyHeight, Image.SCALE_SMOOTH);
                bunnyIcon = new ImageIcon(scaledImg);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to load bunny image!");
                System.exit(1);
            }

            JLabel bunnyLabel = new JLabel(bunnyIcon);
            final int[] pos = {50, 50};
            bunnyLabel.setBounds(pos[0], pos[1], bunnyWidth, bunnyHeight);
            panel.add(bunnyLabel);

            // Movement step
            final int step = 20;

            // Arrow buttons
            JButton upBtn = new JButton("↑");
            JButton downBtn = new JButton("↓");
            JButton leftBtn = new JButton("←");
            JButton rightBtn = new JButton("→");

            upBtn.setBounds(50, 300, 50, 30);
            downBtn.setBounds(50, 340, 50, 30);
            leftBtn.setBounds(10, 340, 50, 30);
            rightBtn.setBounds(90, 340, 50, 30);

            upBtn.setToolTipText("Move bunny up");
            downBtn.setToolTipText("Move bunny down");
            leftBtn.setToolTipText("Move bunny left");
            rightBtn.setToolTipText("Move bunny right");

            upBtn.addActionListener(e -> {
                pos[1] = Math.max(0, pos[1] - step);
                bunnyLabel.setLocation(pos[0], pos[1]);
            });
            downBtn.addActionListener(e -> {
                pos[1] = Math.min(panel.getHeight() - bunnyHeight, pos[1] + step);
                bunnyLabel.setLocation(pos[0], pos[1]);
            });
            leftBtn.addActionListener(e -> {
                pos[0] = Math.max(0, pos[0] - step);
                bunnyLabel.setLocation(pos[0], pos[1]);
            });
            rightBtn.addActionListener(e -> {
                pos[0] = Math.min(panel.getWidth() - bunnyWidth, pos[0] + step);
                bunnyLabel.setLocation(pos[0], pos[1]);
            });

            panel.add(upBtn);
            panel.add(downBtn);
            panel.add(leftBtn);
            panel.add(rightBtn);

            // Key movement

            panel.setFocusable(true);
            panel.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    int key = e.getKeyCode();
                    if (key == KeyEvent.VK_UP) pos[1] = Math.max(0, pos[1] - step);
                    if (key == KeyEvent.VK_DOWN) pos[1] = Math.min(panel.getHeight() - bunnyHeight, pos[1] + step);
                    if (key == KeyEvent.VK_LEFT) pos[0] = Math.max(0, pos[0] - step);
                    if (key == KeyEvent.VK_RIGHT) pos[0] = Math.min(panel.getWidth() - bunnyWidth, pos[0] + step);
                    bunnyLabel.setLocation(pos[0], pos[1]);
                }
            });

            frame.setContentPane(panel);
            frame.setVisible(true);
            panel.requestFocusInWindow();
        });
    }
}