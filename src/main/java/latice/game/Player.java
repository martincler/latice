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

    public Rack getRack() {
        return rack;
    }
    
    public Pool getPool() {
    	    return pool;
    }

    public void showRack() {
        System.out.println("Main de " + name + " :");
        rack.showRack();
    }
}

