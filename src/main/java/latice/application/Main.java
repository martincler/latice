package latice.application;

import latice.game.Board;
import latice.game.Player;
import latice.game.Pool;
import latice.game.Tile;
import latice.game.TileSet;
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

        // Sélection aléatoire du joueur qui commence
        Player currentPlayer = Math.random() < 0.5 ? player1 : player2;

        // Création du plateau
        Board board = new Board();

        System.out.println("Le joueur qui commence est : " + currentPlayer.getName());

        // Affichage du rack et du plateau
        currentPlayer.showRack();
        System.out.println("\nPlateau de Latice :");
        board.displayBoard();

        //Première tuile : obligatoire sur la moonstone (4,4)
        @SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
        System.out.println("\nVous devez jouer votre première tuile sur la case centrale (ligne 4, colonne 4)");

        System.out.print("Choisissez le numéro de la tuile à jouer (0 à 4) : ");
        int tileIndex = scanner.nextInt();
        Tile selectedTile = currentPlayer.getRack().getTiles().get(tileIndex);

        // Placement sur la moonstone
        board.getCell(4, 4).setTile(selectedTile);
        currentPlayer.getRack().removeTile(selectedTile);

        System.out.println("\nTuile placée sur la case centrale (4, 4) !");
        System.out.println("\nNouveau plateau :");
        board.displayBoard();

        System.out.println("\nNouveau rack de " + currentPlayer.getName() + " :");
        currentPlayer.showRack();

    }
}
