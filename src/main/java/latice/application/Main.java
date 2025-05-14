package latice.application;

import latice.game.Board;
import latice.game.Player;
import latice.game.Pool;
import latice.game.Tile;
import latice.game.TileSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Création des 72 tuiles
        TileSet tileSet = new TileSet();
        List<Tile> allTiles = tileSet.getTiles();
        Collections.shuffle(allTiles); // mélange global

        // Répartition équitable des 72 tuiles en deux pools (36 chacune)
        List<Tile> tilesForPlayer1 = new ArrayList<>(allTiles.subList(0, 36));
        List<Tile> tilesForPlayer2 = new ArrayList<>(allTiles.subList(36, 72));

        // Création des pools
        Pool pool1 = new Pool(tilesForPlayer1);
        Pool pool2 = new Pool(tilesForPlayer2);

        // Création des joueurs avec leurs pools respectives
        Player player1 = new Player("Joueur1", pool1);
        Player player2 = new Player("Joueur2", pool2);
        
        // Choisir un joueur aléatoire pour commencer
        Player currentPlayer = Math.random() < 0.5 ? player1 : player2;

        // Afficher le joueur qui commence et son rack
        System.out.println("Le joueur qui commence est : " + currentPlayer.getName());
        currentPlayer.showRack();

        // Affichage du nombre de tuiles restantes dans chaque pioche
        System.out.println("Tuiles restantes dans la pioche de " + player1.getName() + " : " + pool1.remainingTiles());
        System.out.println("Tuiles restantes dans la pioche de " + player2.getName() + " : " + pool2.remainingTiles());
        //Affichage du plateau de jeu
        System.out.println();
        System.out.println("Plateau de Latice :");
        System.out.println();
        Board board = new Board();
        board.displayBoard();
        }
    }
