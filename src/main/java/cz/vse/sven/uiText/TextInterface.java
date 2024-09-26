package cz.vse.sven.uiText;

import cz.vse.sven.logic.game.IGame;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Class TextoveRozhrani
 * <p>
 * Toto je uživatelského rozhraní aplikace Adventura
 * Tato třída vytváří instanci třídy Hra, která představuje logiku aplikace.
 * Čte vstup zadaný uživatelem a předává tento řetězec logice a vypisuje odpověď logiky na konzoli.
 *
 * @author Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Tomáš Kotouč
 * @version březen 2024
 */
public class TextInterface {

    private IGame hra;

    /**
     * Vytváří hru.
     */
    public TextInterface(IGame hra) {
        this.hra = hra;
    }

    /**
     * Hlavní metoda hry. Vypíše úvodní text a pak opakuje čtení a zpracování
     * příkazu od hráče do konce hry (dokud metoda konecHry() z logiky nevrátí
     * hodnotu true). Nakonec vypíše text epilogu a po 15 sekundách se automaticky zavře.
     */
    public void hraj() {
        System.out.println(hra.returnIntroduction() + hra.getGamePlan().getCurrentArea().longDescription());

        // základní cyklus programu - opakovaně se čtou příkazy a poté
        // se provádějí do konce hry.

        while (!hra.gameEnd()) {
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
     * Metoda přečte příkaz z příkazového řádku
     *
     * @return Vrací přečtený příkaz jako instanci třídy String
     */
    private String prectiString() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        return scanner.nextLine();
    }
}
