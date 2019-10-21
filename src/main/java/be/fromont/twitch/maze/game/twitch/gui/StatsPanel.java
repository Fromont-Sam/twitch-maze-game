package be.fromont.twitch.maze.game.twitch.gui;

import javax.swing.*;
import java.awt.*;

public class StatsPanel extends JPanel {

    private static StatsPanel instance;

    private JPanel winPanel;
    private JPanel subPanel;
    private JLabel winLabel;
    private JLabel winNoteLabel;
    private JLabel movesNbr;
    private JLabel time;
    private JLabel seed;
    private int movesCount, seconds;

    private StatsPanel() {
        super();
        movesCount = 0;
        seconds = 0;
        setLayout(new BorderLayout());
        setOpaque(false);
        add(getSubPanel(), BorderLayout.PAGE_END);
        add(getWinPanel(), BorderLayout.EAST);
        getWinPanel().setVisible(false);
    }

    public static StatsPanel getInstance() {
        if (instance == null) {
            instance = new StatsPanel();
        }
        return instance;
    }

    //Data
    public void displayVictory() {
        getWinPanel().setVisible(!getWinPanel().isVisible());
    }

    public void resetTimer() {
        seconds = 0;
        getTime().setText("Uptime : " + String.format("%02dh%02dm%02ds", seconds / 3600, (seconds % 3600) / 60, seconds % 60));
    }

    public void resetMovesNbr() {
        movesCount = 0;
        getMovesNbr().setText("Movements : " + movesCount);
    }

    public void setSeed(long seed) {
        getSeed().setText("Seed : " + seed);
    }

    public void addMovesNbr() {
        movesCount++;
        getMovesNbr().setText("Movements : " + movesCount);
    }

    public void addTime() {
        seconds++;
        getTime().setText("Uptime : " + String.format("%02dh%02dm%02ds", seconds / 3600, (seconds % 3600) / 60, seconds % 60));
    }

    //GUI
    private JLabel getWinLabel() {
        if (winLabel == null) {
            winLabel = new JLabel("You finished the Maze !");
            Font font = winLabel.getFont();
            winLabel.setFont(new Font(font.getName(), Font.BOLD, 32));
            winLabel.setForeground(new Color(0, 100, 0));
        }
        return winLabel;
    }

    private JLabel getWinNoteLabel() {
        if (winNoteLabel == null) {
            winNoteLabel = new JLabel("Next Maze will start soon...");
            Font font = movesNbr.getFont();
            winNoteLabel.setFont(new Font(font.getName(), Font.BOLD, 24));
            winNoteLabel.setForeground(Color.WHITE);
        }
        return winNoteLabel;
    }

    private JLabel getMovesNbr() {
        if (movesNbr == null) {
            movesNbr = new JLabel("Movements : 0");
            Font font = movesNbr.getFont();
            movesNbr.setFont(new Font(font.getName(), Font.BOLD, 24));
            movesNbr.setForeground(Color.WHITE);
        }
        return movesNbr;
    }

    private JLabel getTime() {
        if (time == null) {
            time = new JLabel("Uptime : 00h00m00s");
            Font font = time.getFont();
            time.setFont(new Font(font.getName(), Font.BOLD, 24));
            time.setForeground(Color.WHITE);
        }
        return time;
    }

    private JLabel getSeed() {
        if (seed == null) {
            seed = new JLabel("Seed : 0");
            Font font = seed.getFont();
            seed.setFont(new Font(font.getName(), Font.BOLD, 24));
            seed.setForeground(Color.WHITE);

        }
        return seed;
    }

    private JPanel getWinPanel() {
        if (winPanel == null) {
            winPanel = new JPanel();
            winPanel.setLayout(new BoxLayout(winPanel, BoxLayout.PAGE_AXIS));
            winPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            winPanel.setOpaque(false);
            winPanel.add(getWinLabel());
            winPanel.add(getWinNoteLabel());
        }
        return winPanel;
    }

    private JPanel getSubPanel() {
        if (subPanel == null) {
            subPanel = new JPanel();
            subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
            subPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            subPanel.setOpaque(false);
            subPanel.add(getMovesNbr());
            subPanel.add(getTime());
            subPanel.add(getSeed());
        }
        return subPanel;
    }
}
