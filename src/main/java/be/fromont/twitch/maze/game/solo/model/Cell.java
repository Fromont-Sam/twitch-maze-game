package be.fromont.twitch.maze.game.solo.model;

public class Cell {

    private Boolean leftOpen;
    private Boolean botOpen;

    public Cell() {
        this.leftOpen = false;
        this.botOpen = false;
    }

    public Cell(Cell cell)
    {
        this.leftOpen = cell.leftOpen;
        this.botOpen = cell.botOpen;
    }

    public Boolean getLeftOpen() {
        return leftOpen;
    }

    public void setLeftOpen(Boolean leftOpen) {
        this.leftOpen = leftOpen;
    }

    public Boolean getBotOpen() {
        return botOpen;
    }

    public void setBotOpen(Boolean botOpen) {
        this.botOpen = botOpen;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (!this.getLeftOpen()) sb.append("|");
        if (!this.getBotOpen()) sb.append("_");
        return sb.toString();
    }
}
