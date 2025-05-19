package latice.game;

public class Board {
    private final BoardCell[][] grid;

    public Board() {
        grid = new BoardCell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                SpecialType type = SpecialType.NORMAL;
                if (i == 4 && j == 4) {
                    type = SpecialType.MOONSTONE;
                } else if ((i == 2 && j == 2) || (i == 2 && j == 6) || (i == 6 && j == 2) || (i == 6 && j == 6)) {
                    type = SpecialType.SUNSTONE;
                }
                grid[i][j] = new BoardCell(type);
            }
        }
    }

    public BoardCell getCell(int row, int col) {
        if (row >= 0 && row < 9 && col >= 0 && col < 9) {
            return grid[row][col];
        } else {
            throw new IndexOutOfBoundsException("Coordonnées hors limites : (" + row + ", " + col + ")");
        }
    }

    public void displayBoard() {
        String[][] symbols = new String[9][9];

        // Remplir avec "." par défaut
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                symbols[row][col] = ".";
            }
        }

        // Placer les sunstones ☀
        int[][] sunstoneCoords = {
            {0, 0}, {0, 4}, {0, 8},
            {1, 1}, {1, 7},
            {2, 2}, {2, 6},
            {4, 0}, {4, 8},
            {6, 2}, {6, 6},
            {7, 1}, {7, 7},
            {8, 0}, {8, 4}, {8, 8}
        };

        for (int[] pos : sunstoneCoords) {
            symbols[pos[0]][pos[1]] = "☀";
        }

        // Placer la moonstone 🌙
        symbols[4][4] = "🌙";

        // Afficher le plateau
        for (int row = 0; row < 9; row++) {
            StringBuilder line = new StringBuilder();

            // Indentation sur certaines lignes
            if (row == 3 || row == 5) {
                line.append("    ");
            } else if (row == 1 || row == 2 || row == 6 || row == 7) {
                line.append("  ");
            }

            for (int col = 0; col < 9; col++) {
                BoardCell cell = getCell(row, col);
                if (cell.getTile() != null) {
                    line.append(cell.getTile().toString()).append(" ");
                } else {
                    line.append(symbols[row][col]).append(" ");
                }
            }

            System.out.println(line.toString().trim());
        }
    }

}