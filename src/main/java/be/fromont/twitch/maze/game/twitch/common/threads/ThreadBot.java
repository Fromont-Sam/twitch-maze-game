package be.fromont.twitch.maze.game.twitch.common.threads;

import be.fromont.twitch.maze.game.twitch.common.Bot;
import org.pircbotx.exception.IrcException;

import java.io.IOException;

public class ThreadBot extends Thread {

    @Override
    public void run() {
        Bot bot = Bot.getInstance();
        try {
            bot.startBot();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IrcException e) {
            e.printStackTrace();
        }
    }

}
