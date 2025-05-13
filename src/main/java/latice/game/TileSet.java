package latice.game;

import java.util.ArrayList;
import java.util.List;

public class TileSet {
    private List<Tile> tiles;

    public TileSet() {
        tiles = new ArrayList<>();
        for (Color color : Color.values()) {
            for (Shape shape : Shape.values()) {
                tiles.add(new Tile(color, shape));
                tiles.add(new Tile(color, shape)); 
            }
        }
    }

    public List<Tile> getTiles() {
        return tiles;
    }
}
