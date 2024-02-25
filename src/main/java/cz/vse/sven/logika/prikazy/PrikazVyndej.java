package cz.vse.sven.logika.prikazy;

import cz.vse.sven.logika.hra.HerniPlan;
import cz.vse.sven.logika.objekty.Batoh;
import cz.vse.sven.logika.objekty.Prostor;
import cz.vse.sven.logika.objekty.Vec;


/**
 * Třída PrikazPoloz - implementuje pro hru příkaz vyndej
 * příkaz vyndá věc z batohu a položí ji na zem
 *
 * @author Tomáš Kotouč
 * @version únor 2024
 */

public class PrikazVyndej implements IPrikaz {

    public static final String NAZEV = "vyndej";
    private HerniPlan plan;
    private Batoh batoh;

    /**
     * Konstruktor
     *
     * @param plan  ve kterém se bude pokládat
     * @param batoh ze kterého se má daná věc vyndat
     */
    public PrikazVyndej(HerniPlan plan, Batoh batoh) {
        this.plan = plan;
        this.batoh = batoh;
    }

    /**
     * Metoda nejdříve zkontroluje, zda se věc v batohu nachází,
     * pak zkontroluje, jestli se hráč nenachází v lidlu nebo trafice,
     * pak ji odstraní z batohu a vloží ji do aktuálního prostoru
     *
     * @param parametry počet parametrů závisí na konkrétním příkazu.
     * @return zpráva, zda se vyndání zdařilo nebo ne
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return "Nezadali jste název věci, kterou chcete vyndat z batohu";
        }

        String jmenoVeci = parametry[0];
        Prostor aktualniProstor = plan.getAktualniProstor();
        if (batoh.obsahujeVec(jmenoVeci)) {

            if (!(plan.getAktualniProstor().getNazev().equals("lidl") || plan.getAktualniProstor().getNazev().equals("trafika"))) {
                Vec vec = batoh.vyberVec(jmenoVeci);
                batoh.odstranVec(jmenoVeci);
                aktualniProstor.addVec(vec);
                return jmenoVeci + " nyní leží na zemi";
            }
            return "V tomto prostoru nelze odkládat věci";


        }
        return "Takovou věc u sebe nemáte";
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
