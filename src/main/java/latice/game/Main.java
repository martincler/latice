package latice.game;



public class Main {
    public static void main(String[] args) {
        // Test de TileSet
        TileSet tileSet = new TileSet();
        System.out.println("Tuiles générées dans TileSet :");
        for (Tile tile : tileSet.getTiles()) {
            System.out.println(tile);
        }
        System.out.println("Nombre total de tuiles : " + tileSet.getTiles().size());

        // Test de Pool
        Pool pool = new Pool();
        System.out.println("\nTest de la pioche (Pool) :");
        int count = 0;
        while (!pool.isEmpty()) {
            Tile drawn = pool.drawnTile();
            System.out.println("Tuile piochée : " + drawn);
            count++;
        }
        System.out.println("Nombre de tuiles piochées : " + count);
        System.out.println("Tuiles restantes : " + pool.remainingTiles());
    }
}