import java.awt.*;

public class Board {
    
    private Tile[][] board;
    private boolean firstMove, bombHit;
    private int flags, revealed;

    /**
     * Contructor for a new board
     */
    public Board() {
        board = new Tile[16][16];

        for (int row = 0; row < 16; row++) {
            for (int col = 0; col < 16; col++) {
                board[row][col] = new Tile(0);
            }
        }

        firstMove = true;
        bombHit = false;
        flags = 40;
        revealed = 0;
    }

    /**
     * Get number of flags remaining
     * @return number of flags remaining
     */
    public int getFlags() {
        return flags;
    }

    /**
     * Check if the game is over
     * @return if the game is over
     */
    public boolean isOver() {
        return bombHit || flags + revealed == 256;
    }

    /**
     * Check if the user won
     * @return if the user won
     */
    public boolean hasWon() {
        return !bombHit && flags + revealed == 256;
    }

    /**
     * Fill the board after the user has made the first move
     * @param y -> row of first move
     * @param x -> column of first move
     */
    public void fillBoard(int y, int x) {
        int placed = 0;
        while (placed < 40) {
            int row = (int) (Math.random() * 16);
            int col = (int) (Math.random() * 16);

            if ((row < y - 1 || row > y + 1 || col < x - 1 || col > x + 1) && !board[row][col].isBomb()) {
                board[row][col].setVal(-1);
                placed++;
                for (int i = Math.max(row - 1, 0); i < 16 && i <= row + 1; i++) {
                    for (int j = Math.max(col - 1, 0); j < 16 && j <= col + 1; j++) {
                        if (!board[i][j].isBomb()) {
                            board[i][j].incrementVal();
                        }
                    }
                }
            }
        }
    }

    /**
     * Toggle flag on tile
     * @param y -> row of the tile
     * @param x -> column of the tile
     */
    public void flag(int y, int x) {
        if (board[y][x].isRevealed()) {
            return;
        }
        
        if (board[y][x].isFlagged()) {
            flags++;
        } else {
            flags--;
        }

        board[y][x].toggleFlagged();
    }

    /**
     * Reveal tile
     * @param y -> row of tile
     * @param x -> column of tile
     */
    public void reveal(int y, int x) {
        if (board[y][x].isRevealed() || board[y][x].isFlagged() || bombHit) {
            return;
        } else if (firstMove) {
            fillBoard(y, x);
            firstMove = false;
        }

        revealed++;
        board[y][x].reveal();

        if (board[y][x].isBomb()) {
            bombHit = true;
        } else if (board[y][x].getVal() == 0) {
            for (int i = Math.max(y - 1, 0); i < Math.min(y + 2, 16); i++) {
                for (int j = Math.max(x - 1, 0); j < Math.min(x + 2, 16); j++) {
                    if (!board[i][j].isFlagged()) {
                        reveal(i, j);
                    }
                }
            }
        }
    }

    /**
     * Reveals all bombs
     */
    public void showBombs() {
        for (int row = 0; row < 16; row++) {
            for (int col = 0; col < 16; col++) {
                if (board[row][col].isBomb()) {
                    if (board[row][col].isFlagged()) {
                        board[row][col].toggleFlagged();
                    }
                    board[row][col].reveal();
                }
            }
        }
    }

    /**
     * Resets the board to its orignal state
     */
    public void reset() {
        for (int row = 0; row < 16; row++) {
            for (int col = 0; col < 16; col++) {
                board[row][col] = new Tile(0);
            }
        }

        firstMove = true;
        bombHit = false;
        flags = 40;
        revealed = 0;
    }

    /**
     * Draw the board
     * @param g -> graphics
     */
    public void draw(Graphics g) {
        for (int row = 0; row < 16; row++) {
            for (int col = 0; col < 16; col++) {
                board[row][col].draw(g, col, row);
            }
        }

        for (int i = 0; i <= 640; i += 40) {
            g.setColor(Color.WHITE);
            g.drawLine(i, 0, i, 640);
            g.drawLine(0, i, 640, i);
        }
    }
}
