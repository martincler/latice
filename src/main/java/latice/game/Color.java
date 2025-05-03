package latice.game;

public enum Color {
    YELLOW("\u001B[33m"),
    NAVY("\u001B[34m"),
    MAGENTA("\u001B[35m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    TEAL("\u001B[36m");

    private final String ansiCode;

    Color(String ansiCode) {
        this.ansiCode = ansiCode;
    }

    public String getAnsiCode() {
        return ansiCode;
    }

    public String colorize(String text) {
        return ansiCode + text + "\u001B[0m"; // \u001B[0m = reset
    }
}