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
        for (int i = 0; i < 5 && !pool.isEmpty(); i++) {
            tiles.add(pool.drawTile());
        }
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void showRack() {
        for (Tile tile : tiles) {
            System.out.println(tile);
        }
    }
}
