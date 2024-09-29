package cz.vse.sven.uiText;

import cz.vse.sven.logic.game.IGame;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * TextInterface - read player's text inputs
 */
public class TextInterface {

    private IGame hra;

    /**
     * Creates the text version of the game
     */
    public TextInterface(IGame hra) {
        this.hra = hra;
    }

    /**
     * Displays introduction text and repeatedly reads and processes text inputs from the player until the game ends
     * After the game ends it displays epilogue text and automatically closes itself 15 seconds after the game ends.
     */
    public void play() {
        System.out.println(hra.returnIntroduction() + hra.getGamePlan().getCurrentArea().longDescription());

        while (!hra.isGameEnd()) {
            String radek = prectiString();
            System.out.println(hra.processCommand(radek));
        }

        System.out.println("\n--------------------------------------------------\n\n" + hra.returnEpilogue());
        System.out.println("End of the game. The windows will automatically close in 15 seconds.");

        try {
            TimeUnit.SECONDS.sleep(15);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Reads player's text input
     */
    private String prectiString() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        return scanner.nextLine();
    }
}
