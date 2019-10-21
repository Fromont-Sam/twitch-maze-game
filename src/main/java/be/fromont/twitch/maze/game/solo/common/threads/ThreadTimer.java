package be.fromont.twitch.maze.game.solo.common.threads;

import be.fromont.twitch.maze.game.solo.gui.GridPanel;
import be.fromont.twitch.maze.game.solo.gui.StatsPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadTimer extends Thread {

    private static final Logger LOG = LogManager.getLogger(ThreadTimer.class);

    private volatile boolean flag = true;

    public void stopRunning() {
        flag = false;
    }

    @Override
    public void run() {
        while (flag) {
            if (!GridPanel.getInstance().isVictory()) {
                StatsPanel.getInstance().addTime();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    LOG.error("Error while editing uptime clock.", e);
                }
            }
        }
    }
}

