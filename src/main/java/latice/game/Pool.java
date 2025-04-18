package latice.game;

import java.util.LinkedList;
import java.util.Queue;

public class Pool {
	private final Queue<Tile> tilePool;
	
	public Pool() {
		TileSet tileSet = new TileSet();
		tilePool = new LinkedList<>(tileSet.getTiles());
	}
	
	public boolean isEmpty() {
		return tile.Pool.isEmpty();
	}
	
	public Tile drawnTile() {
		return tilePool.poll();
	}
	
	public int remainingTiles() {
		return tilePool.size();
	}
}