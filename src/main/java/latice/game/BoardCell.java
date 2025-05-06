package latice.game;

public class BoardCell {
    private TileType tileType;
    private Tile tile;

    public BoardCell(TileType tileType) {
        this.tileType = tileType;
        this.tile = null;
    }

    public TileType getSpecialType() {
        return tileType;
    }

    public Tile getTile() {
        return tile;
    }

    public void placeTile(Tile tile) {
        this.tile = tile;
    }

    public boolean isEmpty() {
        return tile == null;
    }
}
