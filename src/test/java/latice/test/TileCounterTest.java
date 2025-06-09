package latice.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import latice.game.Pool;
import latice.game.Rack;
import latice.game.Tile;
import latice.game.TileSet;

public class TileCounterTest {
	
	private static List<Tile> createTiles() {
		// Création des 72 tuiles
        TileSet tileSet = new TileSet();
        List<Tile> allTiles = tileSet.getTiles();
        Collections.shuffle(allTiles);
		return allTiles;
	}
	@Test
	void test_Player_Pool_Size() {
			List<Tile> allTiles = createTiles();
		   List<Tile> tileForPlayer = new ArrayList<>(allTiles.subList(0, 36));
		   Pool pool = new Pool(tileForPlayer);
		   
		   assertEquals(36 ,tileForPlayer.size()); 
		   
	   }
	@Test
	void test_Player_Rack_Size() {
		List<Tile> allTiles = createTiles();
		List<Tile> tileForPlayer = new ArrayList<>(allTiles.subList(0, 36));
		Pool pool = new Pool(tileForPlayer);
		Rack playerRack = new Rack(pool);
		assertEquals(5 ,playerRack.getTilesFromPlayerRack().size());
	}
}
