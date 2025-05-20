package latice.game;

public class Arbitre {
    private final Board board;
    private boolean isFirstMoveDone;

    public Arbitre(Board board) {
        this.board = board;
        this.isFirstMoveDone = false;
    }

    public boolean isMoveValid(Tile tile, int row, int col) {
        // Vérifie que la case est vide
        BoardCell cell = board.getCell(row, col);
        if (cell.getTile() != null) {
            return false;
        }

        if (!isFirstMoveDone) {
            // La première tuile doit être sur la moonstone (4, 4)
            if (row == 4 && col == 4) {
                isFirstMoveDone = true;
                return true;
            } else {
                return false;
            }
        }

        // Vérifie les voisins
        boolean hasAdjacent = false;
        boolean hasMatchingNeighbor = false;

        int[][] directions = {
            {-1, 0}, // haut
            {1, 0},  // bas
            {0, -1}, // gauche
            {0, 1}   // droite
        };

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            if (isInBounds(newRow, newCol)) {
                Tile neighbor = board.getCell(newRow, newCol).getTile();
                if (neighbor != null) {
                    hasAdjacent = true;
                    if (neighbor.getColor() == tile.getColor() ||
                        neighbor.getShape() == tile.getShape()) {
                        hasMatchingNeighbor = true;
                    }
                }
            }
        }

        return hasAdjacent && hasMatchingNeighbor;
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < 9 && col >= 0 && col < 9;
    }
}
