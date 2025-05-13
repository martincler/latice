package latice.game;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Pool {
    private final Queue<Tile> tilePool;

    public Pool(List<Tile> tiles) {
        Collections.shuffle(tiles); 
        tilePool = new LinkedList<>(tiles);
    }

    public Tile drawTile() {
        return tilePool.poll();
    }

    public boolean isEmpty() {
        return tilePool.isEmpty();
    }

    public int remainingTiles() {
        return tilePool.size();
    }
}