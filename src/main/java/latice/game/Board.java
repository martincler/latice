package latice.game;

public class Board {
    private final int SIZE = 9;
    private final BoardCell[][] grid;

    public Board() {
        grid = new BoardCell[SIZE][SIZE];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                TileType type = TileType.STONE;
                if (row == 4 && col == 4) {
                    type = TileType.MOONSTONE;
                } else if (isSunstonePosition(row, col)) {
                    type = TileType.SUNSTONE;
                }
                grid[row][col] = new BoardCell(type);
            }
        }
    }

    private boolean isSunstonePosition(int row, int col) {
        
        int[][] positions = {
            {0,0},{0,4},{0,8},
            {4,0},{4,8},
            {8,0},{8,4},{8,8},
            {2,2},{2,6},{6,2},{6,6},
            {1,1},{1,7},{7,1},{7,7}
        };
        for (int[] pos : positions) {
            if (pos[0] == row && pos[1] == col) return true;
        }
        return false;
    }

    public void displayBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                BoardCell cell = grid[row][col];
                String symbol;

                if (!cell.isEmpty()) {
                    symbol = ".";
                } else {
                    symbol = cell.getSpecialType().getSymbol();
                }

                System.out.print(symbol + " ");
            }
            System.out.println();
        }
    }

}
