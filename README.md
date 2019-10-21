# Maze game using twitch chat implementation

![Screenshot](https://github.com/Fromont-Sam/twitch-maze-game/blob/master/doc/game.PNG?raw=true)

I made this game only to train the twitch implementation and the java swing GUI library. It generates a random maze that you need to resolve to win the game. Commands are really simple you just need to use your directional arrows and R if you want to generate another maze.

## This project is split in two part. 

- The first part is the solo implementation contained in the solo subdirectory. This implementation allows you to play like any other game without needing to use a twitch chat
- The second part is the twitch chat implementation in the twitch subdirectory. If you enter your user and private key in the config file, you'll be able to launch a game that's connected to your chat. From that moment, you viewers will play the maze using chat command (!left, !top, !right, !bot)

## The config file 
To use the twitch API you just need to add a .properties file with the exact name "config.properties" in the resources file of your project. Then add :
- auth={insert-your-key}
- channel=#{insert-you-channel-name} 

![Screenshot](https://github.com/Fromont-Sam/twitch-maze-game/blob/master/doc/config.png?raw=true)
