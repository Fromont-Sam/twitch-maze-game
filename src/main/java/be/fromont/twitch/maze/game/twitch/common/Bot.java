package be.fromont.twitch.maze.game.twitch.common;

import be.fromont.twitch.maze.game.twitch.Application;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Bot extends PircBotX {

    private static Bot instance;

    private Bot(Configuration configuration) {
        super(configuration);
    }

    public static Bot getInstance() {
        if (instance == null) {
            try (InputStream input = Bot.class.getClassLoader().getResourceAsStream("config.properties")) {
                //Properties init
                Properties prop = new Properties();
                // load a properties file
                prop.load(input);

                //Configure what we want our bot to do
                Configuration configuration = new Configuration.Builder()
                        .setName("Aly") //Set the nick of the bot. CHANGE IN YOUR CODE
                        .addServer("irc.chat.twitch.tv", 6667) //Join the freenode network
                        .setServerPassword(prop.getProperty("auth"))
                        .addAutoJoinChannel(prop.getProperty("channel")) //Join the official #pircbotx channel
                        .addListener(new Application.Listener()) //Add our listener that will be called on Events
                        .buildConfiguration();

                //Create our bot with the configuration
                instance = new Bot(configuration);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

}
