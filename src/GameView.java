import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class GameView implements Runnable {
    public void run() {

        final JFrame frame = new JFrame("Minesweeper");
        frame.setLocation(450, 50);

        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        final GameController board = new GameController(status);
        frame.add(board, BorderLayout.CENTER);

        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.reset();
            }
        });
        control_panel.add(reset);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater((new GameView()));
    }
}