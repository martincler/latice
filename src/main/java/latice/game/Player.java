package latice.game;

public class Player {
    private final String name;
    private final Rack rack;
    private final Pool pool;

    public Player(String name, Pool pool) {
        this.name = name;
        this.pool=pool;
        this.rack = new Rack(pool);
    }

    public String getName() {
        return name;
    }

    public Rack getPlayerRack() {
        return rack;
    }
    
    public Pool getPool() {
    	    return pool;
    }

    public void showPlayerRack() {
        System.out.println("Main de " + name + " :");
        rack.showRack();
    }
}

