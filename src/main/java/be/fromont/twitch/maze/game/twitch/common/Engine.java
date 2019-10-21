package be.fromont.twitch.maze.game.twitch.common;

import be.fromont.twitch.maze.game.twitch.gui.StatsPanel;
import be.fromont.twitch.maze.game.twitch.model.Cell;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Engine {

    private static final Logger LOG = LogManager.getLogger(Engine.class);

    private static final int HORIZONTAL = 1;
    private static final int VERTICAL = 2;
    private static final int S = 1;
    private static final int E = 2;
    private static Engine instance;
    private Random rand;
    private int[][] maze;

    private Engine() {
        rand = new Random();
    }

    public static Engine getInstance() {
        if (instance == null) {
            instance = new Engine();
        }
        return instance;
    }

    public List<List<Cell>> generateMaze(int length) {
        //change seed
        long seed = rand.nextLong();
        LOG.info("Seed is : {}", seed);
        StatsPanel.getInstance().setSeed(seed);
        StatsPanel.getInstance().resetMovesNbr();
        StatsPanel.getInstance().resetTimer();
        rand.setSeed(seed);

        //Generate maze as a cell list
        maze = new int[length][length];
        generateMazeArray(maze, 0, 0, length, length, 2);
        List<String> array = getMazeStringArray();
        List<List<Cell>> grid = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            List<Cell> row = new ArrayList<>();
            for (int j = 0; j < array.get(i).length() - 1; j = j + 2) {
                Cell cell = new Cell();
                cell.setLeftOpen(array.get(i).charAt(j) != '|');
                cell.setBotOpen(array.get(i).charAt(j + 1) != '_');
                row.add(cell);
            }
            grid.add(row);
        }
        return grid;
    }

    private void generateMazeArray(int[][] grid, int x, int y, int width, int height, int orientation) {
        if (width < 2 || height < 2) {
            return;
        }
        boolean horizontal = orientation == HORIZONTAL;

        int wx = x + (horizontal ? 0 : rand.nextInt(width == 2 ? 1 : width - 2));
        int wy = y + (horizontal ? rand.nextInt(height == 2 ? 1 : height - 2) : 0);

        int px = wx + (horizontal ? rand.nextInt(width) : 0);
        int py = wy + (horizontal ? 0 : rand.nextInt(height));

        int dx = horizontal ? 1 : 0;
        int dy = horizontal ? 0 : 1;

        int length = horizontal ? width : height;

        int dir = horizontal ? S : E;

        for (int i = 0; i < length; i++) {
            if (wx != px || wy != py) {
                grid[wy][wx] |= dir;
            }
            wx += dx;
            wy += dy;
        }

        int nx = x;
        int ny = y;
        int w = horizontal ? width : wx - x + 1;
        int h = horizontal ? wy - y + 1 : height;
        generateMazeArray(grid, nx, ny, w, h, chooseOrientation(w, h));

        nx = horizontal ? x : wx + 1;
        ny = horizontal ? wy + 1 : y;
        w = horizontal ? width : x + width - wx - 1;
        h = horizontal ? y + height - wy - 1 : height;
        generateMazeArray(grid, nx, ny, w, h, chooseOrientation(w, h));
    }

    private String displayMazeArray() {
        StringBuilder res = new StringBuilder();
        boolean bottom, south, south2, east;
        res.append(StringUtils.repeat("_", maze.length * 2));
        for (int i = 0; i < maze.length; i++) {
            res.append("\n|");
            for (int j = 0; j < maze[i].length; j++) {
                int cell = maze[i][j];
                bottom = i + 1 >= maze[i].length;
                south = ((cell & S) != 0 || bottom);
                south2 = (j + 1 < maze[i].length && (maze[i][j + 1] & S) != 0 || bottom);
                east = ((cell & E) != 0 || j + 1 >= maze[i].length);

                res.append(south ? "_" : " ");
                res.append(east ? "|" : ((south && south2) ? "_" : " "));
            }
        }
        return res.toString();
    }

    private List<String> getMazeStringArray() {
        List<String> gridStringArray = new ArrayList<>();
        boolean bottom, south, south2, east;
        for (int i = 0; i < maze.length; i++) {
            StringBuilder res = new StringBuilder();
            res.append("|");
            for (int j = 0; j < maze[i].length; j++) {
                int cell = maze[i][j];
                bottom = i + 1 >= maze[i].length;
                south = ((cell & S) != 0 || bottom);
                south2 = (j + 1 < maze[i].length && (maze[i][j + 1] & S) != 0 || bottom);
                east = ((cell & E) != 0 || j + 1 >= maze[i].length);

                res.append(south ? "_" : " ");
                res.append(east ? "|" : ((south && south2) ? "_" : " "));
            }
            gridStringArray.add(res.toString());
        }
        return gridStringArray;
    }


    private int chooseOrientation(int w, int h) {
        if (w < h) {
            return HORIZONTAL;
        } else if (h < w) {
            return VERTICAL;
        } else {
            return rand.nextInt(2) + 1;
        }
    }

}
