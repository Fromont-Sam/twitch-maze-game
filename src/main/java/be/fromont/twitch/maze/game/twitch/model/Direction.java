package be.fromont.twitch.maze.game.twitch.model;

public enum Direction {
    LEFT,
    UP,
    RIGHT,
    DOWN;

    public static Direction parseCommand(String command) {
        if (command.equalsIgnoreCase("!t") || command.equalsIgnoreCase("!top"))
            return Direction.UP;
        else if (command.equalsIgnoreCase("!b") || command.equalsIgnoreCase("!bot"))
            return Direction.DOWN;
        else if (command.equalsIgnoreCase("!r") || command.equalsIgnoreCase("!right"))
            return Direction.RIGHT;
        else if (command.equalsIgnoreCase("!l") || command.equalsIgnoreCase("!left"))
            return Direction.LEFT;
        return null;
    }
}
