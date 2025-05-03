package latice.game;

public class Player {
	private final String name;
	private final Rack rack;
	
	public Player (String name, Pool pool) {
		this.name = name;
		this.rack = new Rack(pool);
	}
	
	public String getName() {
		return name;
	}
	
	public Rack getRack() {
		return rack;
	}
}