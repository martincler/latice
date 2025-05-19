package latice.game;

public enum SpecialType {
    NORMAL("."), SUNSTONE("☀"), MOONSTONE("🌙");

    private final String symbol;

    SpecialType(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
