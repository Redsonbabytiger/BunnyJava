import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class BunnyGameApplet extends JApplet {
    private JLabel bunnyLabel;
    private int[] pos = {50, 50};
    private int bunnyWidth = 100, bunnyHeight = 100, step = 20;

    public void init() {
        setLayout(null);

        // Load bunny image
        ImageIcon bunnyIcon = null;
        try {
            URL url = new URL("https://thumbs.dreamstime.com/b/rabbit-hand-drawn-contour-line-drawing-black-white-image-easter-bunny-postcards-printing-fabric-cute-animal-doodles-171134457.jpg");
            Image img = new ImageIcon(url).getImage();
            Image scaledImg = img.getScaledInstance(bunnyWidth, bunnyHeight, Image.SCALE_SMOOTH);
            bunnyIcon = new ImageIcon(scaledImg);
        } catch (Exception e) {
            e.printStackTrace();
        }

        bunnyLabel = new JLabel(bunnyIcon);
        bunnyLabel.setBounds(pos[0], pos[1], bunnyWidth, bunnyHeight);
        add(bunnyLabel);

        JButton upBtn = new JButton("↑");
        JButton downBtn = new JButton("↓");
        JButton leftBtn = new JButton("←");
        JButton rightBtn = new JButton("→");

        upBtn.setBounds(50, 300, 50, 30);
        downBtn.setBounds(50, 340, 50, 30);
        leftBtn.setBounds(10, 340, 50, 30);
        rightBtn.setBounds(90, 340, 50, 30);

        upBtn.addActionListener(e -> bunnyLabel.setLocation(pos[0], Math.max(0, pos[1] - step)));
        downBtn.addActionListener(e -> bunnyLabel.setLocation(pos[0], Math.min(getHeight() - bunnyHeight, pos[1] + step)));
        leftBtn.addActionListener(e -> bunnyLabel.setLocation(Math.max(0, pos[0] - step), pos[1]));
        rightBtn.addActionListener(e -> bunnyLabel.setLocation(Math.min(getWidth() - bunnyWidth, pos[0] + step), pos[1]));

        add(upBtn);
        add(downBtn);
        add(leftBtn);
        add(rightBtn);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_UP) pos[1] = Math.max(0, pos[1] - step);
                if (key == KeyEvent.VK_DOWN) pos[1] = Math.min(getHeight() - bunnyHeight, pos[1] + step);
                if (key == KeyEvent.VK_LEFT) pos[0] = Math.max(0, pos[0] - step);
                if (key == KeyEvent.VK_RIGHT) pos[0] = Math.min(getWidth() - bunnyWidth, pos[0] + step);
                bunnyLabel.setLocation(pos[0], pos[1]);
            }
        });
        setFocusable(true);
    }
}