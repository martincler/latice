package latice.game;

public class Tile {
	private final Color color;
	private final Shape shape;
	
	public Tile(Color color, Shape shape) {
		this.color = color;
		this.shape = shape;
	}
	
	public Color getColor() {
		return color;
	}
	
	public Shape getShape() {
		return shape;
	}
	
	@Override
	public String toString() {
		return color.colorize(shape.getSymbol());
	}
	
}