package latice.application;

import latice.console.Console;
import latice.game.PlayerBag;
import latice.game.Pool;
import latice.game.Tile;
import latice.game.TileSet;

public class Main {
    public static void main(String[] args) {
        // Test de TileSet
        TileSet tileSet = new TileSet();
        Console.message("Tuiles générées dans TileSet :");
        for (Tile tile : tileSet.getTiles()) {
            System.out.println(tile);
        }
        Console.message("Nombre total de tuiles : " + tileSet.getTiles().size());

        // Test de Pool
        Pool pool = new Pool();
        Console.message("\nTest de la pioche (Pool) :");
        int count = 0;
        while (!pool.isEmpty()) {
            Tile drawn = pool.drawnTile();
            Console.message("Tuile piochée : " + drawn);
            count++;
        }
        Console.message("Nombre de tuiles piochées : " + count);
        Console.message("Tuiles restantes : " + pool.remainingTiles());
        
        PlayerBag playerBag1 = new PlayerBag();
        PlayerBag playerBag2 = new PlayerBag();
        
        Pool pool2 = new Pool();
        
        boolean toPlayer1 = true; 
        while (!pool.isEmpty()) {
        	Tile drawn = pool.drawnTile();
        	if (toPlayer1) {
        		playerBag1.addTile(drawn);
        }	else {
        	playerBag2.addTile(drawn);
        }
        	toPlayer1 = !toPlayer1;
    }
        Console.message("Tuiles du joueur 1 : ");
        for (Tile tile : playerBag1.getTiles()) {
        	System.out.println(tile);
        }
        
        Console.message("Tuiles du joueur 2 : ");
        for (Tile tile : playerBag2.getTiles()) {
        	System.out.println(tile);
        }
    }
    
}