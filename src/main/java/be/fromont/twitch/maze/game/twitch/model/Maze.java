package be.fromont.twitch.maze.game.twitch.model;

import be.fromont.twitch.maze.game.twitch.common.Engine;
import be.fromont.twitch.maze.game.twitch.gui.StatsPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Maze {

    private static final Logger LOG = LogManager.getLogger(Maze.class);

    private List<List<Cell>> grid;
    private int length;
    private int playerX, playerY;
    private boolean isVictory;

    public Maze(int length) {
        LOG.info("Maze creation");
        this.length = length;
        this.playerX = 0;
        this.playerY = 0;
        this.isVictory = false;
        grid = Engine.getInstance().generateMaze(length);
    }

    public Cell getCell(int x, int y) {
        return new Cell(grid.get(x).get(y));
    }

    public void move(Direction direction) {
        if (!isVictory) {
            if (direction == Direction.LEFT && (playerX - 1) >= 0 && grid.get(playerY).get(playerX).getLeftOpen()) {
                playerX -= 1;
                StatsPanel.getInstance().addMovesNbr();
            } else if (direction == Direction.RIGHT && (playerX + 1) < length && grid.get(playerY).get(playerX + 1).getLeftOpen()) {
                playerX += 1;
                StatsPanel.getInstance().addMovesNbr();
            } else if (direction == Direction.UP && (playerY - 1) >= 0 && grid.get(playerY - 1).get(playerX).getBotOpen()) {
                playerY -= 1;
                StatsPanel.getInstance().addMovesNbr();
            } else if (direction == Direction.DOWN && (playerY + 1) < length && grid.get(playerY).get(playerX).getBotOpen()) {
                playerY += 1;
                StatsPanel.getInstance().addMovesNbr();
            }
            if (playerX == length - 1 && playerY == length - 1) {
                isVictory = true;
            }
        }
    }

    public int getLength() {
        return length;
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public boolean isVictory() {
        return isVictory;
    }
}
