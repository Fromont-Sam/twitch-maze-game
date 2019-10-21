package be.fromont.twitch.maze.game.solo;

import be.fromont.twitch.maze.game.solo.common.threads.ThreadTimer;
import be.fromont.twitch.maze.game.solo.gui.GameFrame;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {
        launchThreads();
        GameFrame.getInstance().run();
    }

    public static void launchThreads() {
        try {
            BasicConfigurator.configure();
            LOG.info("Application started.");
            //load configuration for logs

            //launch timer
            ThreadTimer timer = new ThreadTimer();
            timer.start();

        } catch (Exception e) {
            LOG.error("Application crashed because of an exception.", e);
            System.exit(1);
        }
    }

}
