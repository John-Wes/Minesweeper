import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class GameController extends JPanel {
    private Board board;
    private JLabel status;

    public GameController(JLabel status) {
        board = new Board();
        this.status = status;
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setFocusable(true);
    
        /*
         * Add a mouse listener
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Point p = e.getPoint();

                if (SwingUtilities.isRightMouseButton(e)) {
                    board.flag(p.y / 40, p.x / 40);
                } else {
                    board.reveal(p.y / 40, p.x / 40);
                }

                updateStatus();
                repaint();
            }
        });
    }

    /**
     * Reset game
     */
    public void reset() {
        board.reset();
        status.setText("You have 40 flags remaining! Right click to place one!");
        repaint();

        requestFocusInWindow();
    }

    /**
     * Updates the JLabel to reflect the current state of the game
     */
    private void updateStatus() {
        if (!board.isOver()) {
            status.setText("You have " + board.getFlags() + " flags remaining! Right click to place one!");
        } else if (board.hasWon()) {
            status.setText("You Win! Press reset to start a new game.");
        } else {
            board.showBombs();
            status.setText("You Lose! Press reset to start a new game.");
        }
    }

    /**
     * Draws the game board.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        board.draw(g);
    }

    /**
     * Board size
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(640, 640);
    }
}
