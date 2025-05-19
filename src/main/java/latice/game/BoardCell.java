package latice.game;

public class BoardCell {
    private Tile tile;
    private final SpecialType specialType;

    public BoardCell(SpecialType specialType) {
        this.specialType = specialType;
        this.tile = null;
    }

    public boolean isEmpty() {
        return tile == null;
    }

    public Tile getTile() {
        return tile;
    }
    
    public void setTile(Tile tile) {
    	    this.tile=tile;
    }

    public void placeTile(Tile tile) {
        this.tile = tile;
    }

    public SpecialType getSpecialType() {
        return specialType;
    }

    @Override
    public String toString() {
        if (tile != null) {
            return tile.toString();
        } else {
            return specialType.getSymbol();
        }
    }
}
