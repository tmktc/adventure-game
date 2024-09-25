package cz.vse.sven.logic.commands;

import java.util.*;

/**
 * Class SeznamPrikazu - eviduje seznam přípustných příkazů adventury.
 * Používá se pro rozpoznávání příkazů
 * a vrácení odkazu na třídu implementující konkrétní příkaz.
 * Každý nový příkaz (instance implementující rozhraní Prikaz) se
 * musí do seznamu přidat metodou vlozPrikaz.
 * <p>
 * Tato třída je součástí jednoduché textové hry.
 *
 * @author Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova
 * @version pro školní rok 2016/2017
 */
public class ListOfCommands {
    private Map<String, ICommand> CommandMap;

    /**
     * Konstruktor
     */
    public ListOfCommands() {
        CommandMap = new HashMap<>();
    }

    /**
     * Vkládá nový příkaz.
     *
     * @param command Instance třídy implementující rozhraní IPrikaz
     */
    public void insertCommand(ICommand command) {
        CommandMap.put(command.getName(), command);
    }

    /**
     * Vrací odkaz na instanci třídy implementující rozhraní IPrikaz,
     * která provádí příkaz uvedený jako parametr.
     *
     * @param string klíčové slovo příkazu, který chce hráč zavolat
     * @return instance třídy, která provede požadovaný příkaz
     */
    public ICommand returnCommand(String string) {
        return CommandMap.getOrDefault(string, null);
    }

    /**
     * Kontroluje, zda zadaný řetězec je přípustný příkaz.
     *
     * @param string Řetězec, který se má otestovat, zda je přípustný příkaz
     * @return Vrací hodnotu true, pokud je zadaný
     * řetězec přípustný příkaz
     */
    public boolean isValidCommand(String string) {
        return CommandMap.containsKey(string);
    }

    /**
     * Vrací abecedně seřazený seznam přípustných příkazů, jednotlivé příkazy jsou odděleny mezerou.
     *
     * @return Řetězec, který obsahuje seznam přípustných příkazů
     */
    public String returnCommandNames() {
        List<String> commands = new ArrayList<>(CommandMap.keySet());
        Collections.sort(commands);

        String list = "";
        for (String commandWord : commands) {
            list += commandWord + " ";
        }
        return list;

    }
}
