package cz.vse.sven.logika.prikazy;

import cz.vse.sven.logika.hra.Progress;
import cz.vse.sven.logika.objekty.HerniPlan;
import cz.vse.sven.logika.objekty.Postava;
import cz.vse.sven.logika.objekty.Prostor;

/**
 * Třída PrikazJdi implementuje pro hru příkaz jdi.
 * příkaz umožňuje hráči pohybovat se mezi prostory
 *
 * @author Jarmila Pavlickova, Luboš Pavlíček, Tomáš Kotouč
 * @version prosinec 2023
 */
public class PrikazJdi implements IPrikaz {

    public static final String NAZEV = "jdi";
    private HerniPlan plan;
    private Progress progress;

    /**
     * Konstruktor třídy
     *
     * @param plan     herní plán, ve kterém se bude ve hře "chodit"
     * @param progress hry
     */
    public PrikazJdi(HerniPlan plan, Progress progress) {
        this.plan = plan;
        this.progress = progress;
    }

    /**
     * Provádí příkaz "jdi". Zkouší se vyjít do zadaného prostoru. Pokud prostor
     * existuje, vstoupí se do nového prostoru. Pokud zadaný sousední prostor
     * (východ) není, vypíše se chybové hlášení.
     *
     * @param parametry - jako  parametr obsahuje jméno prostoru (východu),
     *                  do kterého se má jít.
     * @return zpráva, kterou vypíše hra hráči
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return "Nezadali jste místo, kam chcete jít";
        }

        String smer = parametry[0];

        Prostor sousedniProstor = plan.getAktualniProstor().vratSousedniProstor(smer);
        Prostor aktualniProstor = plan.getAktualniProstor();

        if (sousedniProstor == null) {
            return "Tam se odsud jít nedá";
        } else {
            plan.setAktualniProstor(sousedniProstor);
            //Kim nás musí po určitý čas následovat, takže vždy z předchozího prostoru Kima odebereme a do dalšího přidáme
            if (progress.getProgress() == 4) {
                aktualniProstor.removePostava("Kim");
                sousedniProstor.addPostava(new Postava("Kim"));
            }
            if (progress.getProgress() > 4) {
                if (sousedniProstor.getNazev().equals("jidelna")) {
                    sousedniProstor.addPostava(new Postava("Kim"));
                }
                if (!(aktualniProstor.getNazev().equals("jidelna"))) {
                    aktualniProstor.removePostava("Kim");
                }
            }
            return sousedniProstor.dlouhyPopis();
        }
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
