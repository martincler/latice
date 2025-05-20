package latice.application;

import latice.game.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
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
        Player player1 = new Player("Joueur1", pool1);
        Player player2 = new Player("Joueur2", pool2);

        // Plateau et arbitre
        Board board = new Board();
        Arbitre arbitre = new Arbitre(board);

        Scanner scanner = new Scanner(System.in);

        // Choix du joueur aléatoire
        Player currentPlayer = Math.random() < 0.5 ? player1 : player2;
        Player otherPlayer = currentPlayer == player1 ? player2 : player1;

        System.out.println("Le joueur qui commence est : " + currentPlayer.getName());

        boolean gameOver = false;

        while (!gameOver) {
            System.out.println("\nTour de " + currentPlayer.getName());

            // Affichage du plateau
            board.displayBoard();

            // Affichage du rack
            List<Tile> rackTiles = currentPlayer.getRack().getTiles();
            if (rackTiles.isEmpty()) {
                System.out.println(currentPlayer.getName() + " n’a plus de tuiles !");
            } else {
                System.out.println("\nVotre rack :");
                for (int i = 0; i < rackTiles.size(); i++) {
                    System.out.println((i + 1) + " - " + rackTiles.get(i));
                }

                boolean validMove = false;
                while (!validMove) {
                    System.out.print("\nNuméro de la tuile à jouer : ");
                    int tileIndex = scanner.nextInt() - 1;

                    if (tileIndex < 0 || tileIndex >= rackTiles.size()) {
                        System.out.println("Numéro invalide.");
                        continue;
                    }

                    Tile selectedTile = rackTiles.get(tileIndex);

                    System.out.print("Colonne (0-8) : ");
                    int col = scanner.nextInt();

                    System.out.print("Ligne (0-8) : ");
                    int row = scanner.nextInt();

                    if (arbitre.isMoveValid(selectedTile, row, col)) {
                        board.getCell(row, col).setTile(selectedTile);
                        currentPlayer.getRack().removeTile(selectedTile);

                        validMove = true;
                    } else {
                        System.out.println("Placement invalide. Réessayez.");
                    }
                }
            }

            // Vérifie fin de partie
            boolean player1Empty = player1.getRack().getTiles().isEmpty();
            boolean player2Empty = player2.getRack().getTiles().isEmpty();
            if (player1Empty && player2Empty) {
                gameOver = true;
                System.out.println("\nFin de la partie !");
                System.out.println("Plus de tuiles à jouer.");
            } else {
                // Tour suivant
                Player temp = currentPlayer;
                currentPlayer = otherPlayer;
                otherPlayer = temp;
            }
        }

        scanner.close();
    }
}

