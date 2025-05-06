package latice.game;

public enum TileType {
	STONE("."),
	MOONSTONE("☽"),
	SUNSTONE("☀");
	
	private final String symbol;
	
	TileType(String symbol) {
		this.symbol=symbol;
	}
	
	public String getSymbol() {
		return symbol;
	}
}
