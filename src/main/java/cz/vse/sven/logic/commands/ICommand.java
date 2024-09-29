package cz.vse.sven.logic.commands;

/**
 * Command interface
 */
public interface ICommand {

    /**
     * Executes the command
     * @param parameters depends on the command
     */
    String executeCommand(String... parameters);

    /**
     * Returns the name of the command
     *
     * @return name of the command
     */
    String getName();
}
