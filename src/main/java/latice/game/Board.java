package latice.game;

public class Board {
    private final BoardCell[][] grid;
    private static Integer MAX_TABLE_SIZE = 9;
   
    // parcourir le tableau en 2d
    public Board() {
        
		grid = new BoardCell[MAX_TABLE_SIZE][MAX_TABLE_SIZE];
		// On place les images des cases
        setupTable();
    }

	
    
    public BoardCell getCell(Integer row, Integer col) {
        if (row >= 0 && row < MAX_TABLE_SIZE && col >= 0 && col < MAX_TABLE_SIZE) {
            return grid[row][col];
        } else {
            throw new IndexOutOfBoundsException("Coordonnées hors limites : (" + row + ", " + col + ")");
        }
    }

    public void displayBoard() {
        String[][] symbols = new String[MAX_TABLE_SIZE][MAX_TABLE_SIZE];
        //Toutes les cases placer visuelemment 
        fillWithDot(symbols);   //case normal
        placeSunStone(symbols); //sunstone
        placeMoonStone(symbols); //moonstone
        // Afficher le plateau
        for (Integer row = 0; row < MAX_TABLE_SIZE; row++) {
            StringBuilder line = new StringBuilder();

            // Indentation sur certaines lignes
            if (row == 3 || row == 5) {
                line.append("    ");
            } else if (row == 1 || row == 2 || row == 6 || row == 7) {
                line.append("  ");
            }

            for (Integer iterate_Column = 0; iterate_Column < MAX_TABLE_SIZE; iterate_Column++) {
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

 // fonctions

	private void placeMoonStone(String[][] symbols) {
		// Placer la moonstone 🌙
		symbols[4][4] = "🌙";
	}



	private void placeSunStone(String[][] symbols) {
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
	}

    

   	//fonction fillWith dot 
	private void fillWithDot(String[][] symbols) {
		// Remplir avec "." par défaut
        for (Integer row = 0; row < MAX_TABLE_SIZE; row++) {
            for (Integer col = 0; col < MAX_TABLE_SIZE; col++) {
                symbols[row][col] = ".";
            }
        }
	}
	private void setupTable() {
		for (Integer iterateRow = 0; iterateRow < MAX_TABLE_SIZE; iterateRow++) {
            for (Integer iterateColumn = 0; iterateColumn < MAX_TABLE_SIZE; iterateColumn++) {
                SpecialType type = SpecialType.NORMAL;
                // on défini les cases suivante comme étant spécial 
                if (iterateRow == 4 && iterateColumn == 4) {
                    type = SpecialType.MOONSTONE;
                
                } else if ((iterateRow == 2 && iterateColumn == 2) || (iterateRow == 2 && iterateColumn == 6) || (iterateRow == 6 && iterateColumn == 2) || (iterateRow == 6 && iterateColumn == 6)) {
                    type = SpecialType.SUNSTONE;
                }
                
                grid[iterateRow][iterateColumn] = new BoardCell(type);
            }
        }
	}

}