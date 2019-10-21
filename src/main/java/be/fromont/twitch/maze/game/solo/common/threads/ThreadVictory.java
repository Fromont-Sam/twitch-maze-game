package be.fromont.twitch.maze.game.solo.common.threads;

import be.fromont.twitch.maze.game.solo.gui.GridPanel;
import be.fromont.twitch.maze.game.solo.gui.StatsPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadVictory extends Thread {

    private static final Logger LOG = LogManager.getLogger(ThreadVictory.class);

    private volatile boolean isRunning = false;

    @Override
    public synchronized void start() {
        if (!isRunning) {
            super.start();
        }
    }

    @Override
    public void run() {
        //init
        isRunning = true;
        StatsPanel.getInstance().displayVictory();

        //process
        LOG.info("Victory ! Waiting for the new game");
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        GridPanel.getInstance().restart();

        //end
        StatsPanel.getInstance().displayVictory();
        isRunning = false;
    }
}
