package cz.vse.sven.logic.commands;

import cz.vse.sven.logic.game.GamePlan;
import cz.vse.sven.logic.game.Progress;
import cz.vse.sven.logic.objects.NPC;
import cz.vse.sven.logic.objects.Area;

/**
 * Třída PrikazJdi implementuje pro hru příkaz jdi.
 * příkaz umožňuje hráči pohybovat se mezi prostory
 *
 * @author Jarmila Pavlickova, Luboš Pavlíček, Tomáš Kotouč
 * @version březen 2024
 */
public class CommandGo implements ICommand {

    public static final String NAME = "go";
    private GamePlan plan;
    private Progress progress;

    /**
     * Konstruktor třídy
     *
     * @param plan     herní plán, ve kterém se bude ve hře "chodit"
     * @param progress hry
     */
    public CommandGo(GamePlan plan, Progress progress) {
        this.plan = plan;
        this.progress = progress;
    }

    /**
     * Provádí příkaz "jdi". Zkouší se vyjít do zadaného prostoru. Pokud prostor
     * existuje, vstoupí se do nového prostoru. Pokud zadaný sousední prostor
     * (východ) není, vypíše se chybové hlášení.
     *
     * @param parameters - jako  parametr obsahuje jméno prostoru (východu),
     *                  do kterého se má jít.
     * @return zpráva, kterou vypíše hra hráči
     */
    @Override
    public String executeCommand(String... parameters) {
        if (parameters.length == 0) {
            return "Nezadali jste místo, kam chcete jít";
        }

        String direction = parameters[0];

        Area neighboringArea = plan.getCurrentArea().returnNeighboringArea(direction);
        Area currentArea = plan.getCurrentArea();

        if (neighboringArea == null) {
            return "Tam se odsud jít nedá";
        } else {
            plan.setCurrentArea(neighboringArea);
            //Kim nás musí po určitý čas následovat, takže vždy z předchozího prostoru Kima odebereme a do dalšího přidáme
            if (progress.getProgress() == 4) {
                currentArea.removeNPC("Kim");
                neighboringArea.addNPC(new NPC("Kim", "Kim"));
            }
            if (progress.getProgress() > 4) {
                if (neighboringArea.getName().equals("jidelna")) {
                    neighboringArea.addNPC(new NPC("Kim", "Kim"));
                }
                if (!(currentArea.getName().equals("jidelna"))) {
                    currentArea.removeNPC("Kim");
                }
            }
            return neighboringArea.longDescription();
        }
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
