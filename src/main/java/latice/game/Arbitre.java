package latice.game;

public class Arbitre {
    private final Board board;
    private Boolean isFirstMoveDone;
    private Integer MAX_TABLE_SIZE = 9;
    public Arbitre(Board board) {
        this.board = board;
        this.isFirstMoveDone = false;
    }

    public Boolean isMoveValid(Tile tile, Integer row, Integer col) {
        // Vérifie que la case est vide
        BoardCell cell = board.getCellPositionOnBoard(row, col);
        if (cell.getTileFromCell() != null) {
            return false;
        }

        if (Boolean.FALSE.equals(isFirstMoveDone)) {
            // La première tuile doit être sur la moonstone (4, 4)
            
			if (row == 4 && col == 4) {
                isFirstMoveDone = true;
                return true;
            } else {
                return false;
            }
        }

        // Vérifie les voisins
        Boolean hasAdjacent = false;
        Boolean hasMatchingNeighbor = false;

        Integer[][] directions = {
            {-1, 0}, // haut
            {1, 0},  // bas
            {0, -1}, // gauche
            {0, 1}   // droite
        };

        for (Integer[] dir : directions) {
            Integer newRow = row + dir[0];
            Integer newCol = col + dir[1];

            if (isInBounds(newRow, newCol)) {
                Tile neighbor = board.getCellPositionOnBoard(newRow, newCol).getTileFromCell();
                if (neighbor != null) {
                    hasAdjacent = true;
                    if (neighbor.getColor() == tile.getColor() ||
                        neighbor.getShape() == tile.getShape()) {
                        hasMatchingNeighbor = true;
                    }
                }
            }
        }

        return hasMatchingNeighbor;
    }

    


    private Boolean isInBounds(Integer row, Integer col) {
        
		return row >= 0 && row < MAX_TABLE_SIZE && col >= 0 && col < MAX_TABLE_SIZE;
    }
}
