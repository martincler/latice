package latice.application;

import latice.game.*;
import java.util.*;

public class Main {
	static Integer MAX_TABLE_SIZE = 9;
	
    public static void main(String[] args) {

        // Création des 72 tuiles
        TileSet tileSet = new TileSet();
        List<Tile> allTiles = tileSet.getTiles();
        Collections.shuffle(allTiles);

        // Répartition équitable des 72 tuiles en deux pools (36 chacune)
        List<Tile> tilesForPlayer1 = new ArrayList<>(allTiles.subList(0, 36));
        List<Tile> tilesForPlayer2 = new ArrayList<>(allTiles.subList(36, 72));

        // Création des pools
        Pool pool1 = new Pool(tilesForPlayer1);
        Pool pool2 = new Pool(tilesForPlayer2);

        // Création des joueurs
        Player player1 = new Player("Paul", pool1);
        Player player2 = new Player("Jordan", pool2);

        // Plateau et arbitre
        Board board = new Board();
        Arbitre arbitre = new Arbitre(board);

        // Initialisation des scores
        Map<Player, Integer> scores = new HashMap<>();
        scores.put(player1, 0);
        scores.put(player2, 0);

        Scanner scanner = new Scanner(System.in);

        // Choix du joueur aléatoire qui commence
        Player currentPlayer = Math.random() < 0.5 ? player1 : player2;
        Player otherPlayer = currentPlayer == player1 ? player2 : player1;

        System.out.println("Le joueur qui commence est : " + currentPlayer.getName());

        Boolean gameOver = false;

        while (!gameOver) {
            System.out.println("\nTour de " + currentPlayer.getName());

            // Affichage du plateau
            board.displayBoard();

            // Affichage du rack
            List<Tile> rackTiles = currentPlayer.getRack().getTiles();
            
            // message si le joueur n'a plus de tuiles 
            if (rackTiles.isEmpty()) {
                System.out.println(currentPlayer.getName() + " n’a plus de tuiles !");
           //sinon on lui affiche son rack
            } else {
                System.out.println("\nVotre rack :");
                for (Integer iterateTileInRack = 0; iterateTileInRack < rackTiles.size(); iterateTileInRack++) {
                    System.out.println((iterateTileInRack + 1) + " - " + rackTiles.get(iterateTileInRack));
                }

                Boolean validMove = false;
                while (!validMove) {
                    System.out.print("\nNuméro de la tuile à jouer : ");
                    Integer tileIndex = scanner.nextInt() - 1;

                    if (tileIndex < 0 || tileIndex >= rackTiles.size()) {
                        System.out.println("Numéro invalide.");
                        continue;
                    }

                    Tile selectedTile = rackTiles.get(tileIndex);

                    System.out.print("Colonne (0-8) : ");
                    Integer col = scanner.nextInt();

                    System.out.print("Ligne (0-8) : ");
                    Integer row = scanner.nextInt();

                    if (row < 0 || row > 8 || col < 0 || col > 8) {
                        System.out.println("Coordonnées hors limites.");
                        continue;
                    }

                    if (arbitre.isMoveValid(selectedTile, row, col)) {
                        board.getCell(row, col).setTile(selectedTile);
                        currentPlayer.getRack().removeTile(selectedTile);

                        // Calcul des points pour le coup joué
                        Integer pointsGagnes = calculateScore(board, row, col);

                        // Bonus +1 point si case sunstone
                        if (board.getCell(row, col).getSpecialType() == SpecialType.SUNSTONE) {
                            pointsGagnes++;
                        }

                        // Ajout des points au score du joueur
                        scores.put(currentPlayer, scores.get(currentPlayer) + pointsGagnes);

                        System.out.println(currentPlayer.getName() + " gagne " + pointsGagnes + " points.");

                        // Pioche d’une nouvelle tuile si possible
                        drawRandomTileForPlayer(currentPlayer);

                        validMove = true;
                    } else {
                        System.out.println("Placement invalide. Réessayez.");
                    }
                }
            }

            // Affichage du tableau des scores
            System.out.println("\nScores actuels :");
            for (Map.Entry<Player, Integer> entry : scores.entrySet()) {
                System.out.println(entry.getKey().getName() + " : " + entry.getValue() + " points");
            }

            // Vérification fin de partie : si les deux racks sont vides
            Boolean player1Empty = player1.getRack().getTiles().isEmpty();
            Boolean player2Empty = player2.getRack().getTiles().isEmpty();
            if (player1Empty && player2Empty) {
                gameOver = true;
                System.out.println("\nFin de la partie !");
                System.out.println("Plus de tuiles à jouer.");

                // Annonce du vainqueur
                if (scores.get(player1) > scores.get(player2)) {
                    System.out.println("Le vainqueur est " + player1.getName() + " avec " + scores.get(player1) + " points !");
                } else if (scores.get(player1) < scores.get(player2)) {
                    System.out.println("Le vainqueur est " + player2.getName() + " avec " + scores.get(player2) + " points !");
                } else {
                    System.out.println("Match nul entre " + player1.getName() + " et " + player2.getName() + " avec " + scores.get(player1) + " points !");
                }
            } else {
                // Passage au joueur suivant
                Player temp = currentPlayer;
                currentPlayer = otherPlayer;
                otherPlayer = temp;
            }
        }

        scanner.close();
    }

    private static void drawRandomTileForPlayer(Player player) {
        Pool pool = player.getPool();
        if (!pool.isEmpty()) {
            Tile nouvelleTuile = pool.drawTile();
            if (nouvelleTuile != null) {
                player.getRack().getTiles().add(nouvelleTuile);
                System.out.println(player.getName() + " pioche une nouvelle tuile : " + nouvelleTuile);
            }
        }
    }

    public static int calculateScore(Board board, Integer row, Integer col) {
        Integer score = 0;
        Tile placedTile = board.getCell(row, col).getTile();

        Integer[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

        for (Integer[] dir : directions) {
            Integer adjRow = row + dir[0];
            Integer adjCol = col + dir[1];

            
			if (adjRow >= 0 && adjRow < MAX_TABLE_SIZE && adjCol >= 0 && adjCol < MAX_TABLE_SIZE) {
                Tile adjTile = board.getCell(adjRow, adjCol).getTile();
                if (adjTile != null) {
                    if (adjTile.getColor() == placedTile.getColor() || adjTile.getShape() == placedTile.getShape()) {
                        score++;
                    }
                }
            }
        }

        return score;
    }
}
