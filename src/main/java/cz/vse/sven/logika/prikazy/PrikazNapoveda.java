package cz.vse.sven.logika.prikazy;

/**
 * Třída PrikazNapoveda implementuje pro hru příkaz napoveda.
 * Tato třída je součástí jednoduché textové hry.
 *
 * @author Jarmila Pavlickova, Luboš Pavlíček
 * @version pro školní rok 2016/2017
 */
public class PrikazNapoveda implements IPrikaz {

    private static final String NAZEV = "napoveda";
    private SeznamPrikazu platnePrikazy;

    /**
     * Konstruktor třídy
     *
     * @param platnePrikazy seznam příkazů,
     *                      které je možné ve hře použít,
     *                      aby je nápověda mohla zobrazit uživateli.
     */
    public PrikazNapoveda(SeznamPrikazu platnePrikazy) {
        this.platnePrikazy = platnePrikazy;
    }

    /**
     * Vrací seznam dostupných příkazů
     *
     * @return napoveda ke hre
     */
    @Override
    public String provedPrikaz(String... parametry) {
        return "Hra má čtyři možné konce. Dva z nich jsou špatné, jeden dobrý a jeden perfektní.\n" +
                "Průběh hry se vždy posune dál díky promluvení s nějakou postavou. Dávejte pozor, co postavy říkají.\n\n" +
                "Možné příkazy: " + platnePrikazy.vratNazvyPrikazu();
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
