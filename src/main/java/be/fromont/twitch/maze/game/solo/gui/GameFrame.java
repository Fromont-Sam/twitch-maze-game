package be.fromont.twitch.maze.game.solo.gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GameFrame extends JFrame {

    private static final Logger LOG = LogManager.getLogger(GameFrame.class);

    private static GameFrame instance;

    private int width;
    private int height;
    private int mpX, mpY;

    private GameFrame() {
        super();
        loadProperties();
    }

    public static GameFrame getInstance() {
        if (instance == null) {
            LOG.info("Game frame creation");
            instance = new GameFrame();
        }
        return instance;
    }

    public void run() {
        this.setVisible(true);
    }

    private void loadProperties() {
        try {
            Properties gameProp = new Properties();
            InputStream input = GameFrame.class.getClassLoader().getResourceAsStream("game.properties");
            gameProp.load(input);

            //Frame settings
            width = Integer.valueOf(gameProp.getProperty("width"));
            height = Integer.valueOf(gameProp.getProperty("height"));

            //Set properties
            this.setTitle("The Maze");
            ImageIcon img = new ImageIcon("be/fromont/twitch/maze/game/twitch/gui/icon.png");
            this.setIconImage(img.getImage());
            this.setSize(width, height);
            this.setResizable(false);
            this.setUndecorated(false);
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setListeners();

            //Set main panel
            setContentPane(GridPanel.getInstance());
            add(StatsPanel.getInstance());
        } catch (IOException e) {
            LOG.error("An error occurred while trying to load game settings : {}", e);
        } catch (NumberFormatException e) {
            LOG.error("An error occurred while trying to load game settings : {}", e);
        }
    }

    private void setListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mpX = e.getX();
                mpY = e.getY();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                setLocation(
                        getLocation().x + e.getX() - mpX,
                        getLocation().y + e.getY() - mpY);
            }
        });
    }
}
