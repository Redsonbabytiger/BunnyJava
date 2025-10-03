import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class BunnyGameLevel2BoxShooter {

    public static void start() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Box Shooter Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.setLocationRelativeTo(null);

            JPanel panel = new JPanel(null);
            panel.setBackground(Color.BLACK);

            JLabel player = new JLabel();
            player.setOpaque(true);
            player.setBackground(Color.WHITE);
            player.setBounds(190, 350, 20, 20);
            panel.add(player);

            ArrayList<JLabel> redBoxes = new ArrayList<>();
            ArrayList<JLabel> greenBoxes = new ArrayList<>();
            ArrayList<JLabel> orangeBoxes = new ArrayList<>();
            Random random = new Random();

            // Game loop timer
            Timer timer = new Timer(30, null);
            timer.addActionListener(e -> {
                // Green box movement & collision
                for (int i = greenBoxes.size() - 1; i >= 0; i--) {
                    JLabel g = greenBoxes.get(i);
                    g.setLocation(g.getX(), g.getY() - 5);
                    if (g.getY() < 0) { panel.remove(g); greenBoxes.remove(i); continue; }
                    for (int j = redBoxes.size() - 1; j >= 0; j--) {
                        JLabel r = redBoxes.get(j);
                        if (g.getBounds().intersects(r.getBounds())) {
                            panel.remove(r); redBoxes.remove(j);
                            panel.remove(g); greenBoxes.remove(i);
                            break;
                        }
                    }
                }
                // Orange box movement & collision
                for (int i = orangeBoxes.size() - 1; i >= 0; i--) {
                    JLabel o = orangeBoxes.get(i);
                    if (redBoxes.isEmpty()) continue;
                    JLabel closest = redBoxes.get(0);
                    double minDist = distance(o, closest);
                    for (JLabel r : redBoxes) {
                        double dist = distance(o, r);
                        if (dist < minDist) { minDist = dist; closest = r; }
                    }
                    int dx = Integer.compare(closest.getX(), o.getX());
                    int dy = Integer.compare(closest.getY(), o.getY());
                    o.setLocation(o.getX() + dx * 3, o.getY() + dy * 3);
                    for (int j = redBoxes.size() - 1; j >= 0; j--) {
                        JLabel r = redBoxes.get(j);
                        if (o.getBounds().intersects(r.getBounds())) {
                            panel.remove(r); redBoxes.remove(j);
                            panel.remove(o); orangeBoxes.remove(i);
                            break;
                        }
                    }
                }
                // Red box movement
                for (int i = redBoxes.size() - 1; i >= 0; i--) {
                    JLabel r = redBoxes.get(i);
                    r.setLocation(r.getX(), r.getY() + 2);
                    if (r.getY() > panel.getHeight()) { panel.remove(r); redBoxes.remove(i); }
                }
                panel.repaint();
            });
            timer.start();

            // Spawn red boxes
            Timer spawnTimer = new Timer(1000, e -> {
                JLabel red = new JLabel();
                red.setOpaque(true);
                red.setBackground(Color.RED);
                red.setBounds(random.nextInt(panel.getWidth() - 20), 0, 20, 20);
                redBoxes.add(red);
                panel.add(red);
            });
            spawnTimer.start();

            // Buttons for movement/shooting
            JButton leftBtn = new JButton("←");
            JButton rightBtn = new JButton("→");
            JButton shootGreenBtn = new JButton("Green");
            JButton shootOrangeBtn = new JButton("Orange");

            leftBtn.setBounds(10, 350, 60, 30);
            rightBtn.setBounds(80, 350, 60, 30);
            shootGreenBtn.setBounds(150, 350, 80, 30);
            shootOrangeBtn.setBounds(240, 350, 80, 30);

            leftBtn.addActionListener(e -> player.setLocation(Math.max(0, player.getX() - 10), player.getY()));
            rightBtn.addActionListener(e -> player.setLocation(Math.min(panel.getWidth() - 20, player.getX() + 10), player.getY()));

            shootGreenBtn.addActionListener(e -> {
                JLabel green = new JLabel();
                green.setOpaque(true);
                green.setBackground(Color.GREEN);
                green.setBounds(player.getX(), player.getY(), 20, 20);
                greenBoxes.add(green);
                panel.add(green);
            });

            shootOrangeBtn.addActionListener(e -> {
                JLabel orange = new JLabel();
                orange.setOpaque(true);
                orange.setBackground(Color.ORANGE);
                orange.setBounds(player.getX(), player.getY(), 20, 20);
                orangeBoxes.add(orange);
                panel.add(orange);
            });

            panel.add(leftBtn);
            panel.add(rightBtn);
            panel.add(shootGreenBtn);
            panel.add(shootOrangeBtn);

            // Arrow keys & shooting
            panel.setFocusable(true);
            panel.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    int key = e.getKeyCode();
                    if (key == KeyEvent.VK_LEFT) player.setLocation(Math.max(0, player.getX() - 10), player.getY());
                    if (key == KeyEvent.VK_RIGHT) player.setLocation(Math.min(panel.getWidth() - 20, player.getX() + 10), player.getY());
                    if (key == KeyEvent.VK_SPACE) shootGreenBtn.doClick();
                    if (key == KeyEvent.VK_ENTER) shootOrangeBtn.doClick();
                }
            });

            frame.setContentPane(panel);
            frame.setVisible(true);
        });
    }

    private static double distance(JLabel a, JLabel b) {
        int dx = a.getX() - b.getX();
        int dy = a.getY() - b.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
}