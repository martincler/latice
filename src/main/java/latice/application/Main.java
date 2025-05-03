package latice.application;

import latice.console.Console;
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
        Console. message("\nTest de la pioche (Pool) :");
        int count = 0;
        while (!pool.isEmpty()) {
            Tile drawn = pool.drawnTile();
            Console.message("Tuile piochée : " + drawn);
            count++;
        }
        Console.message("Nombre de tuiles piochées : " + count);
        Console.message("Tuiles restantes : " + pool.remainingTiles());
    }
}