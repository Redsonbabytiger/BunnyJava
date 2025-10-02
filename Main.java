import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Java Game Launcher");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 300);
            frame.setLocationRelativeTo(null);

            JPanel panel = new JPanel();
            panel.setLayout(null);

            JLabel title = new JLabel("Choose a Game");
            title.setFont(new Font("Arial", Font.BOLD, 20));
            title.setBounds(160, 10, 200, 30);
            panel.add(title);

            // Launch Original BunnyGame
            JButton bunnyGameBtn = new JButton("BunnyGame");
            bunnyGameBtn.setBounds(50, 80, 150, 40);
            bunnyGameBtn.addActionListener(e -> BunnyGame.start());
            panel.add(bunnyGameBtn);

            // Launch BunnyGameLevel2
            JButton bunnyLevel2Btn = new JButton("Bunny Level 2");
            bunnyLevel2Btn.setBounds(250, 80, 150, 40);
            bunnyLevel2Btn.addActionListener(e -> BunnyGameLevel2.start());
            panel.add(bunnyLevel2Btn);

            // Launch Box Shooter Game
            JButton boxShooterBtn = new JButton("Box Shooter");
            boxShooterBtn.setBounds(150, 160, 200, 40);
            boxShooterBtn.addActionListener(e -> BunnyGameLevel2BoxShooter.start());
            panel.add(boxShooterBtn);

            JLabel controlsLabel = new JLabel("<html>Controls vary by game:<br>" +
                    "Arrow Keys = Move<br>" +
                    "Space / Enter = Shoot (Box Shooter)<br>" +
                    "Buttons can also be clicked for movement/shooting in all games</html>");
            controlsLabel.setBounds(50, 220, 400, 50);
            panel.add(controlsLabel);

            frame.setContentPane(panel);
            frame.setVisible(true);
        });
    }
}