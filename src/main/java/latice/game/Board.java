package latice.game;

public class Board {
    private final BoardCell[][] grid;
    
    // parcourir le tableau en 2d
    public Board() {
        grid = new BoardCell[9][9];
        for (Integer iterateRow = 0; iterateRow < 9; iterateRow++) {
            for (Integer iterateColumn = 0; iterateColumn < 9; iterateColumn++) {
                SpecialType type = SpecialType.NORMAL;
                if (iterateRow == 4 && iterateColumn == 4) {
                    type = SpecialType.MOONSTONE;
                } else if ((iterateRow == 2 && iterateColumn == 2) || (iterateRow == 2 && iterateColumn == 6) || (iterateRow == 6 && iterateColumn == 2) || (iterateRow == 6 && iterateColumn == 6)) {
                    type = SpecialType.SUNSTONE;
                }
                grid[iterateRow][iterateColumn] = new BoardCell(type);
            }
        }
    }
    
    public BoardCell getCell(Integer row, Integer col) {
        if (row >= 0 && row < 9 && col >= 0 && col < 9) {
            return grid[row][col];
        } else {
            throw new IndexOutOfBoundsException("Coordonnées hors limites : (" + row + ", " + col + ")");
        }
    }

    public void displayBoard() {
        String[][] symbols = new String[9][9];

        // Remplir avec "." par défaut
        for (Integer row = 0; row < 9; row++) {
            for (Integer col = 0; col < 9; col++) {
                symbols[row][col] = ".";
            }
        }

        // Placer les sunstones ☀
        Integer[][] sunstoneCoords = {
            {0, 0}, {0, 4}, {0, 8},
            {1, 1}, {1, 7},
            {2, 2}, {2, 6},
            {4, 0}, {4, 8},
            {6, 2}, {6, 6},
            {7, 1}, {7, 7},
            {8, 0}, {8, 4}, {8, 8}
        };

        for (Integer[] pos : sunstoneCoords) {
            symbols[pos[0]][pos[1]] = "☀";
        }

        // Placer la moonstone 🌙
        symbols[4][4] = "🌙";

        // Afficher le plateau
        for (Integer row = 0; row < 9; row++) {
            StringBuilder line = new StringBuilder();

            // Indentation sur certaines lignes
            if (row == 3 || row == 5) {
                line.append("    ");
            } else if (row == 1 || row == 2 || row == 6 || row == 7) {
                line.append("  ");
            }

            for (Integer iterate_Column = 0; iterate_Column < 9; iterate_Column++) {
                BoardCell cell = getCell(row, iterate_Column);
                if (cell.getTile() != null) {
                    line.append(cell.getTile().toString()).append(" ");
                } else {
                    line.append(symbols[row][iterate_Column]).append(" ");
                }
            }

            System.out.println(line.toString().trim());
        }
    }

}