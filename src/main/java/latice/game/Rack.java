package latice.game;

import java.util.ArrayList;
import java.util.List;

public class Rack {
	private final List<Tile> tiles;
	
	public Rack(Pool pool) {
		tiles = new ArrayList<>();
		refill(pool);
	}
	
	public void refill(Pool pool) {
		while (tiles.size()<5 && !pool.isEmpty()) {
			Tile drawn = pool.drawnTile();
			if (drawn != null) {
				tiles.add(drawn);
			}
		}
	}
	
	public List<Tile> getTiles() {
		return tiles;
	}
	
	public void removeTile (Tile tile) {
		tiles.remove(tile);
	}
}