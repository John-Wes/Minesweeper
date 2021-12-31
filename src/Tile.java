import java.awt.*;

public class Tile {
    
    private int val;
    private boolean revealed, flagged;

    /**
     * Constructor for a tile with predetermined value
     * @param val -> value of tile
     */
    public Tile(int val) {
        this.val = val;
        revealed = false;
        flagged = false;
    }

    /**
     * Get the value of the tile
     * @return value of tile
     */
    public int getVal() {
        return val;
    }

    /**
     * Set the value of the tile
     * @param val -> new value of tile
     */
    public void setVal(int val) {
        this.val = val;
    }

    /**
     * Increment the value of the tile by one
     */
    public void incrementVal() {
        val++;
    }

    /**
     * Checks if the tile is a bomb
     * @return if the tile is a bomb
     */
    public boolean isBomb() {
        return val == -1;
    }

    /**
     * Change revealed status to true
     */
    public void reveal() {
        revealed = true;
    }

    /**
     * Checks if the tile is revealed
     * @return if the tile is revealed
     */
    public boolean isRevealed() {
        return revealed;
    }

    /**
     * Place or removed flag
     */
    public void toggleFlagged() {
        flagged = !flagged;
    }

    /**
     * Get flagged status
     * @return if the tile is flagged
     */
    public boolean isFlagged() {
        return flagged;
    }

    /**
     * Draw the tile
     * @param g -> graphics
     * @param x -> x-coordinate
     * @param y -> y-coordinate
     */
    public void draw(Graphics g, int x, int y) {
        if (isFlagged()) {
            g.setColor(Color.YELLOW);
            g.fillRect(x * 40, y * 40, 40, 40);
        } else if (!revealed) {
            g.setColor(Color.GRAY);
            g.fillRect(x * 40, y * 40, 40, 40);
        } else if (val > 0) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(x * 40, y * 40, 40, 40);
            drawVal(g, x, y);
        }  else if (isBomb()) {
            g.setColor(Color.RED);
            g.fillRect(x * 40, y * 40, 40, 40);
        } else {
            g.setColor(Color.WHITE);
            g.fillRect(x * 40, y * 40, 40, 40);
        }
    }

    /**
     * Draws the value of the tile
     * @param g -> graphics
     * @param x -> x-coordinate
     * @param y -> y-coordinate
     */
    public void drawVal(Graphics g, int x, int y) {
        if (val == 1) {
            g.setColor(new Color(0, 0, 255));
        } else if (val == 2) {
            g.setColor(new Color(0, 128, 0));
        } else if (val == 3) {
            g.setColor(new Color(255, 0, 0));
        } else if (val == 4) {
            g.setColor(new Color(0, 0, 128));
        } else if (val == 5) {
            g.setColor(new Color(128, 0, 0));
        } else {
            g.setColor(Color.BLACK);
        }

        g.setFont(new Font("Serif", Font.BOLD, 22));
        g.drawString(Integer.toString(val), 40 * x + 15, 40 * y + 27);
    }

    /**
     * Override equals method
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Tile) {
            Tile tile = (Tile) other;
            return this.val == tile.getVal();
        }
        return false;
    }
}
