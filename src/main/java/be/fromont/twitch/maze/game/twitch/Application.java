package be.fromont.twitch.maze.game.twitch;

import be.fromont.twitch.maze.game.twitch.common.threads.ThreadBot;
import be.fromont.twitch.maze.game.twitch.common.threads.ThreadTimer;
import be.fromont.twitch.maze.game.twitch.gui.GameFrame;
import be.fromont.twitch.maze.game.twitch.gui.GridPanel;
import be.fromont.twitch.maze.game.twitch.model.Direction;
import org.apache.log4j.BasicConfigurator;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

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

            //launch listener
            ThreadBot bot = new ThreadBot();
            bot.start();

            //launch timer
            ThreadTimer timer = new ThreadTimer();
            timer.start();

        } catch (Exception e) {
            LOG.error("Application crashed because of an exception.", e);
            System.exit(1);
        }
    }

    public static class Listener extends ListenerAdapter {
        @Override
        public void onGenericMessage(GenericMessageEvent event) throws IOException {
            GridPanel.getInstance().movePlayer(Direction.parseCommand(event.getMessage()));
        }
    }

}
