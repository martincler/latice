package latice.game;

import java.util.ArrayList;
import java.util.List;

public class Rack {
    private final List<Tile> tiles;

    public Rack(Pool pool) {
        tiles = new ArrayList<>();
        drawInitialTiles(pool);
    }

    private void drawInitialTiles(Pool pool) {
        for (Integer drawCount = 0; drawCount < 5 && !pool.isEmpty(); drawCount++) {
            tiles.add(pool.drawTile());
        }
    }

    public List<Tile> getTilesFromPlayerRack() {
        return tiles;
    }
    
    public void removeTile(Tile tile) {
    	    tiles.remove(tile);
    }

    public void showRack() {
        for (Tile tile : tiles) {
            System.out.println(tile);
        }
    }
}
