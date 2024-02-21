package cz.vse.sven.logika.prikazy;

import cz.vse.sven.logika.hra.Hra;
import cz.vse.sven.logika.hra.Penize;
import cz.vse.sven.logika.hra.Progress;
import cz.vse.sven.logika.objekty.Batoh;
import cz.vse.sven.logika.hra.HerniPlan;
import cz.vse.sven.logika.objekty.Prostor;
import cz.vse.sven.logika.objekty.Vec;

/**
 * Třída PrikazPromluv - implementuje pro hru příkaz promluv.
 * příkaz promluví s vybranou postavou
 *
 * @author Tomáš Kotouč
 * @version prosinec 2023
 */
public class PrikazPromluv implements IPrikaz {

    private static final String NAZEV = "promluv";
    private HerniPlan plan;
    private Batoh batoh;
    private Penize penize;
    private Progress progress;
    private Hra hra;

    /**
     * Konstruktor
     *
     * @param plan     ve kterém se bude mluvit
     * @param batoh    který má hráč u sebe
     * @param penize   které má hráč u sebe
     * @param progress hry
     */
    public PrikazPromluv(HerniPlan plan, Batoh batoh, Penize penize, Progress progress, Hra hra) {
        this.plan = plan;
        this.batoh = batoh;
        this.penize = penize;
        this.progress = progress;
        this.hra = hra;
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
        Prostor aktualniProstor = plan.getAktualniProstor();

        if (aktualniProstor.obsahujePostavu(jmeno)) {
            return switch (jmeno) {
                case "Pepa" -> dialogPepa();
                case "Kim" -> dialogKim();
                case "PodezreleVypadajiciPan" -> dialogPodezrely();
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
        if ((progress.getProgress() == 6) && (batoh.obsahujeVec("PsiGranule")) && ((batoh.obsahujeVec("Rohliky")) || batoh.obsahujeVec("BezlepkovyChleba"))) {
            plan.setVyhra(true);
            hra.setKonecHry();
            return "";
        } else if ((progress.getProgress() == 7) && batoh.obsahujeVec("Snus") && (batoh.obsahujeVec("PsiGranule")) && (batoh.obsahujeVec("Rohliky"))) {
            plan.setPerfektniVyhra(true);
            hra.setKonecHry();
            return "";
        } else if (progress.getProgress() >= 6 && (!(batoh.obsahujeVec("PsiGranule")) || (!(batoh.obsahujeVec("Rohliky")) && !(batoh.obsahujeVec("BezlepkovyChleba"))))) {
            plan.setProhra(true);
            hra.setKonecHry();
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
        } else if (progress.getProgress() == 6 && batoh.obsahujeVec("BezlepkovyChleba")) {
            progress.addProgress();
            return "\nSven: \n\"Ještě jednou díky za pomoc Kime, tady máš ode mě překvapení.\"\n" +
                    batoh.odstranVec("BezlepkovyChleba") + "\n";
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
            plan.setProhra(true);
            hra.setKonecHry();
            return "";
        } else if (progress.getProgress() == 4) {
            progress.addProgress();

            plan.getAktualniProstor().addVec(new Vec("CervenaBunda", true, false, false, 0));
            plan.getAktualniProstor().addVec(new Vec("ZelenaCepice", true, false, false, 0));

            plan.getAktualniProstor().removePostava("PodezreleVypadajiciPan");

            return "\nLupiče jste s Kimem přemohli, ten hanbou utekl. CervenaBunda a ZelenaCepice nyní leží na zemi.\n" +
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
            if (batoh.obsahujeVec("CervenaBunda") && batoh.obsahujeVec("ZelenaCepice")) {
                progress.addProgress();
                return "\nProdavač: \n\"Já věděl, že to dokážeš. Tady máš zaslouženou odměnu.\"\n" +
                        batoh.odstranVec("CervenaBunda") + "\n" +
                        batoh.odstranVec("ZelenaCepice") + "\n" +
                        penize.pridejPenize(4.5) +
                        "\n\nSven: \n\"Teď už bych měl mít dostatek peněz na jídlo. Doufám, že v Lídlu budou slevy.\n" +
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
        if (batoh.obsahujeVec("StareHodiny")) {
            if (progress.getProgress() == 0) {
                progress.addProgress();
                return "\nZastavárník: \n\"Za takovéhle hodiny ti dám maximálně tak 50 centů.\"\n" +
                        batoh.odstranVec("StareHodiny") + "\n" +
                        penize.pridejPenize(0.5) +
                        "\n\nZastavárník: \n\"Potrebuješ víc peněz? Můj kámoš ze sekáče by pro tebe možná měl nějakej kšeft.\"\n";
            }
        }

        return "\nSven: \n\"Nemám nic, co bych zastavárníkovi mohl prodat.\"\n";
    }

    /**
     * Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *
     * @ return nazev prikazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}
