package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.GamePlan;
import cz.vse.sven.logic.game.Game;
import cz.vse.sven.logic.game.Money;
import cz.vse.sven.logic.game.Progress;
import cz.vse.sven.logic.objects.Backpack;
import cz.vse.sven.logic.objects.Area;
import cz.vse.sven.logic.objects.Item;

/**
 * Třída PrikazPromluv - implementuje pro hru příkaz promluv.
 * příkaz promluví s vybranou postavou
 *
 * @author Tomáš Kotouč
 * @version březen 2024
 */
public class CommandTalk implements ICommand {

    public static final String NAME = "talk";
    private GamePlan plan;
    private Backpack backpack;
    private Money money;
    private Progress progress;
    private Game game;

    /**
     * Konstruktor
     *
     * @param plan     ve kterém se bude mluvit
     * @param backpack    který má hráč u sebe
     * @param money   které má hráč u sebe
     * @param progress hry
     */
    public CommandTalk(GamePlan plan, Backpack backpack, Money money, Progress progress, Game game) {
        this.plan = plan;
        this.backpack = backpack;
        this.money = money;
        this.progress = progress;
        this.game = game;
    }

    /**
     * Metoda nejdříve zjistí, jestli se daná postava nachází v aktuálním prostoru
     * a pak odkáže na metodu dialogu s danou postavou
     *
     * @param parameters - jméno postavy, se kterou chceme promluvit.
     * @return metoda dialogu s danou postavou
     */
    @Override
    public String executeCommand(String... parameters) {
        if (parameters.length == 0) {
            return "You must name the NPC you want to talk to.";
        }

        String name = parameters[0];
        Area currentArea = plan.getCurrentArea();

        if (currentArea.containsNPC(name)) {
            return switch (name) {
                case "pepa" -> dialogPepa();
                case "kim" -> dialogKim();
                case "suspect" -> dialogSuspect();
                case "shopAssistant" -> dialogShopAssistant();
                case "pawnbroker" -> dialogPawnbroker();
                default -> "default";
            };
        } else {
            return "There is no such NPC in here.";
        }
    }

    /**
     * Metoda vrací podle hodnoty progressu a jiných podmínek korespondující dialog s Pepou,
     *
     * @return dialog s Pepou
     */
    private String dialogPepa() {
        if ((progress.getProgress() == 6) && (backpack.containsItem("dogFood")) && ((backpack.containsItem("bagels")) || backpack.containsItem("glutenFreeBread"))) {
            plan.setWin(true);
            game.setGameEnd();
            return "";
        } else if ((progress.getProgress() == 7) && backpack.containsItem("snus") && (backpack.containsItem("dogFood")) && (backpack.containsItem("bagels"))) {
            plan.setPerfectWin(true);
            game.setGameEnd();
            return "";
        } else if (progress.getProgress() >= 6 && (!(backpack.containsItem("dogFood")) || (!(backpack.containsItem("bagels")) && !(backpack.containsItem("glutenFreeBread"))))) {
            plan.setLoss(true);
            game.setGameEnd();
            return "";
        }

        return "\nSven: \n\"Hi, Pepa.\"\n";
    }

    /**
     * Metoda vrací podle hodnoty progressu a jiných podmínek korespondující dialog s Kimem,
     *
     * @return dialog s Kimem
     */
    private String dialogKim() {
        if (progress.getProgress() == 0) {
            return "\nKim: \n\"Hey Sven, too bad the Soup kitchen is closed today, right? I was looking forward to my favorite gluten-free noodles." +
                    "\nI am sorry that Pepa will not get to eat today, but I might have a solution for you." +
                    "\nI myself have no money, but when I was on my way here, I saw an old clock in a junkyard." +
                    "\nGo find it and try to sell in in a Pawnshop, maybe you will get enough money to buy food for Pepa and yourself.\"\n";
        } else if (progress.getProgress() == 3) {
            progress.addProgress();
            return "\nKim: \n\"I will gladly help you, lead me to him.\"\n";
        } else if (progress.getProgress() == 4) {
            return "\nKim: \n\"Are we there yet?\"\n";
        } else if (progress.getProgress() == 6 && backpack.containsItem("glutenFreeBread")) {
            progress.addProgress();
            return "\nSven: \n\"Thank you once again for your help Kim, here is a little surprise.\"\n" +
                    backpack.removeItem("glutenFreeBread") + "\n";
        }

        return "\nKim: \n\"Hi, Sven.\"\n";
    }

    /**
     * Metoda vrací podle hodnoty progressu a jiných podmínek korespondující dialog s Podezřelým,
     *
     * @return dialog s Podezřelým
     */
    private String dialogSuspect() {
        if (progress.getProgress() == 2) {
            progress.addProgress();
            return "\nSven: \n\"I can not confront him alone, I need to ask Kim for help, he should still be in front of the Soup kitchen.\n" +
                    "The poor guy is probably waiting there for his gluten-free noodles.\"\n";
        } else if (progress.getProgress() == 3) {
            plan.getCurrentArea().removeNPC("suspect");
            plan.setLoss(true);
            game.setGameEnd();
            return "";
        } else if (progress.getProgress() == 4) {
            progress.addProgress();

            plan.getCurrentArea().addItem(new Item("redJacket", "Red jacket", true, false, false, 0));
            plan.getCurrentArea().addItem(new Item("greenCap", "Green cap", true, false, false, 0));

            plan.getCurrentArea().removeNPC("suspect");
            plan.getCurrentArea().removeNPC("kim");

            return "\nYou managed to beat the thief. The red jacket and green cap fell on the ground.\n" +
                    "Kim said goodbye and headed back to the Soup kitchen.\n";
        }

        return "\nSuspiciously looking person: \n\"Get lost.\"\n";
    }

    /**
     * Metoda vrací podle hodnoty progressu a jiných podmínek korespondující dialog s Prodavačem,
     *
     * @return dialog s Prodavačem
     */
    private String dialogShopAssistant() {
        if (progress.getProgress() == 1) {
            progress.addProgress();
            return "\nShop assistant: \n\"Some bastard rushed into the shop and stole clothes this morning.\n" +
                    "If you manage to get the clothes back to me, you will be rewarded.\n" +
                    "It is a red jacket and a green cap.\n" +
                    "He ran towards the Job center, he could still be there.\"\n";
        } else if (progress.getProgress() == 5) {
            if (backpack.containsItem("redJacket") && backpack.containsItem("greenCap")) {
                progress.addProgress();
                return "\nShop assistant: \n\"I knew you could do it. Here is your reward.\"\n" +
                        backpack.removeItem("redJacket") + "\n" +
                        backpack.removeItem("greenCap") + "\n" +
                        money.addMoney(4.5) +
                        "\n\nSven: \n\"I should have enough money now. I hope there will be discounts in Lidl.\n" +
                        "I should hurry up, it is almost closing time.\"\n";
            }

            return "\nShop assistant: \n\"Hey do you have the clothes for me?\"\n";
        }

        return "\nSven: \n\"I can not afford anything in this shop.\"\n";
    }

    /**
     * Metoda vrací podle hodnoty progressu a jiných podmínek korespondující dialog se Zastavárníkem,
     *
     * @return dialog se Zastavárníkem
     */
    private String dialogPawnbroker() {
        if (backpack.containsItem("oldClock")) {
            if (progress.getProgress() == 0) {
                progress.addProgress();
                return "\nPawnbroker: \n\"I will give 50 cents for this clock.\"\n" +
                        backpack.removeItem("oldClock") + "\n" +
                        money.addMoney(0.5) +
                        "\n\nPawnbroker: \n\"Do you need more money? My friend from the Thrift shop could have a job for you.\"\n";
            }
        }

        return "\nSven: \n\"I have nothing to sell or buy here.\"\n";
    }

    /**
     * Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     * <p>
     * @ return nazev prikazu
     */
    @Override
    public String getName() {
        return NAME;
    }
}
