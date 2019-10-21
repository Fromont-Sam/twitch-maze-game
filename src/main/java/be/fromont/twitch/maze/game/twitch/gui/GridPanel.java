package be.fromont.twitch.maze.game.twitch.gui;

import be.fromont.twitch.maze.game.twitch.common.threads.ThreadVictory;
import be.fromont.twitch.maze.game.twitch.model.Cell;
import be.fromont.twitch.maze.game.twitch.model.Direction;
import be.fromont.twitch.maze.game.twitch.model.Maze;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GridPanel extends JPanel implements KeyListener {

    private static final Logger LOG = LogManager.getLogger(GridPanel.class);

    private static GridPanel instance;

    private int thickness;
    private int x0, y0;
    private int cellSize;
    private boolean waitingVictory;
    private Maze maze;
    private BufferedImage background;
    private BufferedImage character;

    private GridPanel() {
        super(new BorderLayout());
        loadProperties();
    }

    public static GridPanel getInstance() {
        if (instance == null) {
            LOG.info("Grid panel creation");
            instance = new GridPanel();
        }
        return instance;
    }

    public void restart() {
        maze = new Maze(maze.getLength());
        waitingVictory = false;
        repaint();
    }

    private void loadProperties() {
        try {
            Properties gameProp = new Properties();
            InputStream input = GameFrame.class.getClassLoader().getResourceAsStream("game.properties");
            gameProp.load(input);

            int margin = Integer.valueOf(gameProp.getProperty("margin"));
            //Frame settings
            int width = Integer.valueOf(gameProp.getProperty("width"));
            int height = Integer.valueOf(gameProp.getProperty("height"));
            waitingVictory = false;

            //Grid settings
            int length = Integer.valueOf(gameProp.getProperty("length"));
            int thickness = Integer.valueOf(gameProp.getProperty("thickness"));
            this.maze = new Maze(length);
            this.thickness = thickness;
            this.x0 = (width - (height - 2 * margin - (length + 1) * thickness)) / 2;
            this.y0 = margin;
            this.cellSize = (height - 2 * margin - (length + 1) * thickness) / length;
            this.setBackground(Color.BLACK);
            this.addKeyListener(this);
            this.setFocusable(true);

        } catch (IOException e) {
            LOG.error("An error occurred while trying to load game settings : {}", e);
        } catch (NumberFormatException e) {
            LOG.error("An error occurred while trying to load game settings : {}", e);
        }
    }

    private BufferedImage getCharacter() {
        if (character == null) {
            try {
                character = ImageIO.read(new File("gui/square.png"));
            } catch (IOException ex) {
                LOG.error("An error occurred while trying to load game settings : {}", ex);
            }
        }
        return character;
    }

    private BufferedImage getBackgroundImage() {
        if (background == null) {
            try {
                background = ImageIO.read(new File("gui/background.png"));
            } catch (IOException ex) {
                LOG.error("An error occurred while trying to load game settings : {}", ex);
            }
        }
        return background;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        BasicStroke stroke = new BasicStroke(thickness);
        g2.drawImage(getBackgroundImage(), 0, 0, this);
        g2.setStroke(stroke);

        //exit
        g2.setColor(new Color(105, 105, 105));
        g2.fillRect(x0 + (maze.getLength() - 1) * cellSize, y0 + (maze.getLength() - 1) * cellSize, cellSize, cellSize);

        //maze
        g2.setColor(Color.WHITE);
        g2.drawLine(x0, y0, x0 + maze.getLength() * cellSize, y0);
        g2.drawLine(x0, y0, x0, y0 + maze.getLength() * cellSize);
        g2.drawLine(x0, y0 + maze.getLength() * cellSize, x0 + maze.getLength() * cellSize, y0 + maze.getLength() * cellSize);
        g2.drawLine(x0 + maze.getLength() * cellSize, y0, x0 + maze.getLength() * cellSize, y0 + maze.getLength() * cellSize);
        for (int x = 0; x < maze.getLength(); x++) {
            for (int y = 0; y < maze.getLength(); y++) {
                Cell cell = maze.getCell(y, x);
                if (!cell.getLeftOpen()) g2.drawLine(getRealX(x), getRealY(y), getRealX(x), getRealY(y) + cellSize);
                if (!cell.getBotOpen())
                    g2.drawLine(getRealX(x), getRealY(y) + cellSize, getRealX(x) + cellSize, getRealY(y) + cellSize);
            }
        }
        g2.drawImage(getCharacter(), x0 + (maze.getPlayerX() * cellSize), y0 + (maze.getPlayerY() * cellSize), cellSize, cellSize, this);
    }

    private int getRealX(int x) {
        return x0 + x * cellSize;
    }

    private int getRealY(int y) {
        return y0 + y * cellSize;
    }

    public boolean isVictory() {
        return maze.isVictory();
    }

    public void movePlayer(Direction direction) {
        if (direction != null) {
            maze.move(direction);
            if (maze.isVictory() && !waitingVictory) {
                waitingVictory = true;
                ThreadVictory threadVictory = new ThreadVictory();
                threadVictory.start();
            } else {
                repaint();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent evt) {
        int keyCode = evt.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            movePlayer(Direction.LEFT);
            repaint();
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            movePlayer(Direction.RIGHT);
            repaint();
        } else if (keyCode == KeyEvent.VK_UP) {
            movePlayer(Direction.UP);
            repaint();
        } else if (keyCode == KeyEvent.VK_DOWN) {
            movePlayer(Direction.DOWN);
            repaint();
        } else if (keyCode == KeyEvent.VK_R) {
            this.maze = new Maze(maze.getLength());
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
