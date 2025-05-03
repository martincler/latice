package latice.game;

public enum Shape {
    FEATHER("🪶"),
    BIRD("🐦"),
    TURTLE("🐢"),
    FLOWER("🌸"),
    GECKO("🦎"),
    DOLPHIN("🐬");

    private final String symbol;

    Shape(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
   
}