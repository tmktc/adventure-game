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

    public static final String NAZEV = "promluv";
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
     * @param parametery - jméno postavy, se kterou chceme promluvit.
     * @return metoda dialogu s danou postavou
     */
    @Override
    public String provedPrikaz(String... parametery) {
        if (parametery.length == 0) {
            return "Nezadali jste název postavy, se kterou chcete promluvit.";
        }

        String jmeno = parametery[0];
        Area aktualniArea = plan.getAktualniProstor();

        if (aktualniArea.obsahujePostavu(jmeno)) {
            return switch (jmeno) {
                case "Pepa" -> dialogPepa();
                case "Kim" -> dialogKim();
                case "Podezrely" -> dialogPodezrely();
                case "Prodavac" -> dialogProdavac();
                case "Zastavarnik" -> dialogZastavarnik();
                default -> "default";
            };
        } else {
            return "Tato postava tu není.";
        }
    }

    /**
     * Metoda vrací podle hodnoty progressu a jiných podmínek korespondující dialog s Pepou,
     *
     * @return dialog s Pepou
     */
    private String dialogPepa() {
        if ((progress.getProgress() == 6) && (backpack.obsahujeVec("PsiGranule")) && ((backpack.obsahujeVec("Rohliky")) || backpack.obsahujeVec("BezlepkovyChleba"))) {
            plan.setVyhra(true);
            game.setKonecHry();
            return "";
        } else if ((progress.getProgress() == 7) && backpack.obsahujeVec("Snus") && (backpack.obsahujeVec("PsiGranule")) && (backpack.obsahujeVec("Rohliky"))) {
            plan.setPerfektniVyhra(true);
            game.setKonecHry();
            return "";
        } else if (progress.getProgress() >= 6 && (!(backpack.obsahujeVec("PsiGranule")) || (!(backpack.obsahujeVec("Rohliky")) && !(backpack.obsahujeVec("BezlepkovyChleba"))))) {
            plan.setProhra(true);
            game.setKonecHry();
            return "";
        }

        return "\nSven: \n\"Ahoj Pepo\"\n";
    }

    /**
     * Metoda vrací podle hodnoty progressu a jiných podmínek korespondující dialog s Kimem,
     *
     * @return dialog s Kimem
     */
    private String dialogKim() {
        if (progress.getProgress() == 0) {
            return "\nKim: \n\"Čau Svene, škoda, že maj dneska zavřeno, co? Já se tak těšil na moje oblíbené bezlepkové nudle." +
                    "\nMrzí mě, že je Pepa o hladu, ale možná mám pro tebe řešení." +
                    "\nSice u sebe nemám ani cent, ale když jsem šel k jídelně, tak jsem na smetišti zahlídl nějaké staré hodiny." +
                    "\nJdi je najít a zkus je prodat v zastavárně, třeba za ně dostaneš dostatek peněz na jídlo pro Pepu i tebe.\"\n";
        } else if (progress.getProgress() == 3) {
            progress.addProgress();
            return "\nKim: \n\"Rád ti pomůžu, veď mě k tomu grázlovi.\"\n";
        } else if (progress.getProgress() == 4) {
            return "\nKim: \n\"Už tam budem?\"\n";
        } else if (progress.getProgress() == 6 && backpack.obsahujeVec("BezlepkovyChleba")) {
            progress.addProgress();
            return "\nSven: \n\"Ještě jednou díky za pomoc Kime, tady máš ode mě překvapení.\"\n" +
                    backpack.odstranVec("BezlepkovyChleba") + "\n";
        }

        return "\nKim: \n\"Čau Svene\"\n";
    }

    /**
     * Metoda vrací podle hodnoty progressu a jiných podmínek korespondující dialog s Podezřelým,
     *
     * @return dialog s Podezřelým
     */
    private String dialogPodezrely() {
        if (progress.getProgress() == 2) {
            progress.addProgress();
            return "\nSven: \n\"Sám ho nepřemůžu, musím si vzít na pomoc Kima, ten by měl být stále u jídelny.\n" +
                    "Ten chudák tam asi pořád čeká na ty jeho oblíbené bezlepkové nudle.\"\n";
        } else if (progress.getProgress() == 3) {
            plan.getAktualniProstor().removePostava("Podezrely");
            plan.setProhra(true);
            game.setKonecHry();
            return "";
        } else if (progress.getProgress() == 4) {
            progress.addProgress();

            plan.getAktualniProstor().addVec(new Item("CervenaBunda", "Červená bunda", true, false, false, 0));
            plan.getAktualniProstor().addVec(new Item("ZelenaCepice", "Zelená čepice", true, false, false, 0));

            plan.getAktualniProstor().removePostava("Podezrely");
            plan.getAktualniProstor().removePostava("Kim");

            return "\nLupiče jste s Kimem přemohli tak, že hanbou utekl. Červená bunda a Zelená čepice upadly na zem.\n" +
                    "Kim se s vámi rozloučil a odešel zpátky k jídelně.\n";
        }

        return "\nPodezřele vypadající pán: \n\"Všímej si svého.\"\n";
    }

    /**
     * Metoda vrací podle hodnoty progressu a jiných podmínek korespondující dialog s Prodavačem,
     *
     * @return dialog s Prodavačem
     */
    private String dialogProdavac() {
        if (progress.getProgress() == 1) {
            progress.addProgress();
            return "\nProdavač: \n\"Před chvílí sem vtrhl nějaký grázl a sebral mi oblečení.\n" +
                    "Jestli mi to oblečení dokážes přinést zpátky, dostaneš pěknou odměnu.\n" +
                    "Byla to červená bunda a zelená čepice.\n" +
                    "Utíkal směrem k pracáku. Pokud si pospíšíš, možná ho tam ještě zastihneš.\"\n";
        } else if (progress.getProgress() == 5) {
            if (backpack.obsahujeVec("CervenaBunda") && backpack.obsahujeVec("ZelenaCepice")) {
                progress.addProgress();
                return "\nProdavač: \n\"Já věděl, že to dokážeš. Tady máš zaslouženou odměnu.\"\n" +
                        backpack.odstranVec("CervenaBunda") + "\n" +
                        backpack.odstranVec("ZelenaCepice") + "\n" +
                        money.pridejPenize(4.5) +
                        "\n\nSven: \n\"Už bych měl mít dostatek peněz na jídlo. Doufám, že v Lidlu budou slevy.\n" +
                        "Měl bych si pospíšit, za chvíli budou zavírat.\"\n";
            }

            return "\nProdavač: \n\"Tak co, už máš pro mě to oblečení?\"\n";
        }

        return "\nSven: \n\"Není nic, co bych si v sekáči mohl koupit.\"\n";
    }

    /**
     * Metoda vrací podle hodnoty progressu a jiných podmínek korespondující dialog se Zastavárníkem,
     *
     * @return dialog se Zastavárníkem
     */
    private String dialogZastavarnik() {
        if (backpack.obsahujeVec("StareHodiny")) {
            if (progress.getProgress() == 0) {
                progress.addProgress();
                return "\nZastavárník: \n\"Za takovéhle hodiny ti dám maximálně tak 50 centů.\"\n" +
                        backpack.odstranVec("StareHodiny") + "\n" +
                        money.pridejPenize(0.5) +
                        "\n\nZastavárník: \n\"Potrebuješ víc peněz? Můj kámoš ze sekáče by pro tebe možná měl nějakej kšeft.\"\n";
            }
        }

        return "\nSven: \n\"Nemám nic, co bych zastavárníkovi mohl prodat.\"\n";
    }

    /**
     * Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     * <p>
     * @ return nazev prikazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}