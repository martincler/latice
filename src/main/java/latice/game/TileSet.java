package latice.game;

import java.util.Array.List;
import java.util.Collections;
import java.util.List;

public class TileSet {
	private final List<Tiles> tiles;
	
	public TileSet() {
		tiles = new ArrayList<>();
		generateTiles();
	}
	
	private void generateTiles() {
		for Color color : Color.values()) {
			for Shape shape : Shape.values()) {
				tiles.add(new Tile(color, shape));
				tiles.add(new Tile(color, shape));
			}
         }	
		Collections.shuffle(tiles);
	}
	public List<Tiles> getTiles() {
		return tiles;
	}
}