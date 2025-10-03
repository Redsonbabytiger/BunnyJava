import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class BunnyGameLevel2 {

    public static void start() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Bunny Dodge Level 2");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 500);
            frame.setLocationRelativeTo(null);

            JPanel panel = new JPanel(null);
            panel.setBackground(Color.WHITE);

            int bunnyWidth = 100, bunnyHeight = 100;
            int[] bunnyPos = {200, 400};

            JLabel bunnyLabel = new JLabel("🐰");
            bunnyLabel.setBounds(bunnyPos[0], bunnyPos[1], bunnyWidth, bunnyHeight);
            bunnyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            bunnyLabel.setFont(new Font("Arial", Font.PLAIN, 60));
            panel.add(bunnyLabel);

            Random random = new Random();
            ArrayList<JLabel> carrots = new ArrayList<>();
            JLabel scoreLabel = new JLabel("Score: 0");
            scoreLabel.setBounds(10, 10, 100, 30);
            panel.add(scoreLabel);
            final int[] score = {0};

            // Bunny movement interface
            @FunctionalInterface
            interface MoveBunny { void move(int dx); }
            MoveBunny moveBunny = dx -> {
                bunnyPos[0] += dx;
                bunnyPos[0] = Math.max(0, Math.min(panel.getWidth() - bunnyWidth, bunnyPos[0]));
                bunnyLabel.setLocation(bunnyPos[0], bunnyPos[1]);
            };

            // Left/Right buttons
            JButton leftButton = new JButton("←");
            JButton rightButton = new JButton("→");
            leftButton.setBounds(100, 420, 50, 30);
            rightButton.setBounds(200, 420, 50, 30);
            leftButton.addActionListener(e -> moveBunny.move(-20));
            rightButton.addActionListener(e -> moveBunny.move(20));
            panel.add(leftButton);
            panel.add(rightButton);

            // Arrow keys
            panel.setFocusable(true);
            panel.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) moveBunny.move(-20);
                    if (e.getKeyCode() == KeyEvent.VK_RIGHT) moveBunny.move(20);
                }
            });

            // Game loop
            new Timer(50, e -> {
                for (JLabel carrot : carrots) {
                    carrot.setLocation(carrot.getX(), carrot.getY() + 5);
                    if (carrot.getY() > panel.getHeight()) {
                        carrot.setLocation(random.nextInt(panel.getWidth() - 40), -40);
                        score[0]++;
                        scoreLabel.setText("Score: " + score[0]);
                    }
                    if (carrot.getBounds().intersects(bunnyLabel.getBounds())) {
                        JOptionPane.showMessageDialog(frame, "Game Over! Score: " + score[0]);
                        ((Timer) e.getSource()).stop();
                        frame.dispose();
                    }
                }
            }).start();

            // Spawn carrots
            for (int i = 0; i < 5; i++) {
                JLabel carrot = new JLabel("🥕");
                carrot.setBounds(random.nextInt(panel.getWidth() - 40), -random.nextInt(500), 40, 40);
                carrot.setFont(new Font("Arial", Font.PLAIN, 40));
                carrots.add(carrot);
                panel.add(carrot);
            }

            frame.setContentPane(panel);
            frame.setVisible(true);
        });
    }
}