package cz.vse.sven.logika.hra;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HraTest {

    private Hra hra;

    @BeforeEach
    public void setUp() {
        hra = new Hra();
    }

    /**
     * Otestuje základní průběh hry
     */
    @Test
    public void zakladniPrubehHry() {
        assertEquals("domov", hra.getHerniPlan().getAktualniProstor().getNazev());
        hra.zpracujPrikaz("jdi jidelna");
        assertEquals(false, hra.konecHry());
        assertEquals("jidelna", hra.getHerniPlan().getAktualniProstor().getNazev());
        hra.zpracujPrikaz("jdi smetiste");
        assertEquals(false, hra.konecHry());
        assertEquals("smetiste", hra.getHerniPlan().getAktualniProstor().getNazev());
        hra.zpracujPrikaz("konec");
        assertEquals(true, hra.konecHry());
    }

    /**
     * Otestuje scénář hry, kdy prohrajeme, protože nám uteče lupič
     */
    @Test
    public void prubehHryProhra1() {
        hra.zpracujPrikaz("jdi jidelna");
        hra.zpracujPrikaz("jdi smetiste");
        hra.zpracujPrikaz("seber StareHodiny");
        hra.zpracujPrikaz("jdi zastavarna");
        hra.zpracujPrikaz("promluv Zastavarnik");
        hra.zpracujPrikaz("jdi sekac");
        hra.zpracujPrikaz("promluv Prodavac");
        hra.zpracujPrikaz("jdi pracak");
        hra.zpracujPrikaz("promluv Podezrely");
        hra.zpracujPrikaz("promluv Podezrely");
        assertEquals(true, hra.getHerniPlan().isProhra());
    }

    /**
     * Otestuje scénář, kdy prohrajeme, protože jsme nestihli koupit jídlo
     */
    @Test
    public void prubehHryProhra2() {
        hra.zpracujPrikaz("jdi jidelna");
        hra.zpracujPrikaz("jdi smetiste");
        hra.zpracujPrikaz("seber StareHodiny");
        hra.zpracujPrikaz("jdi zastavarna");
        hra.zpracujPrikaz("promluv Zastavarnik");
        hra.zpracujPrikaz("jdi sekac");
        hra.zpracujPrikaz("promluv Prodavac");
        hra.zpracujPrikaz("jdi pracak");
        hra.zpracujPrikaz("promluv Podezrely");
        hra.zpracujPrikaz("jdi smetiste");
        hra.zpracujPrikaz("jdi jidelna");
        hra.zpracujPrikaz("promluv Kim");
        hra.zpracujPrikaz("jdi smetiste");
        hra.zpracujPrikaz("jdi pracak");
        hra.zpracujPrikaz("promluv Podezrely");
        hra.zpracujPrikaz("seber CervenaBunda");
        hra.zpracujPrikaz("seber ZelenaCepice");
        hra.zpracujPrikaz("jdi sekac");
        hra.zpracujPrikaz("promluv Prodavac");
        hra.zpracujPrikaz("jdi smetiste");
        hra.zpracujPrikaz("jdi jidelna");
        hra.zpracujPrikaz("jdi domov");
        hra.zpracujPrikaz("promluv Pepa");
        assertEquals(true, hra.getHerniPlan().isProhra());
    }

    /**
     * Otestujeme scénář výhry
     */
    @Test
    public void prubehHryVyhra() {
        hra.zpracujPrikaz("jdi jidelna");
        hra.zpracujPrikaz("jdi smetiste");
        hra.zpracujPrikaz("seber StareHodiny");
        hra.zpracujPrikaz("jdi zastavarna");
        hra.zpracujPrikaz("promluv Zastavarnik");
        hra.zpracujPrikaz("jdi sekac");
        hra.zpracujPrikaz("promluv Prodavac");
        hra.zpracujPrikaz("jdi pracak");
        hra.zpracujPrikaz("promluv Podezrely");
        hra.zpracujPrikaz("jdi smetiste");
        hra.zpracujPrikaz("jdi jidelna");
        hra.zpracujPrikaz("promluv Kim");
        hra.zpracujPrikaz("jdi smetiste");
        hra.zpracujPrikaz("jdi pracak");
        hra.zpracujPrikaz("promluv Podezrely");
        hra.zpracujPrikaz("seber CervenaBunda");
        hra.zpracujPrikaz("seber ZelenaCepice");
        hra.zpracujPrikaz("jdi sekac");
        hra.zpracujPrikaz("promluv Prodavac");
        hra.zpracujPrikaz("jdi smetiste");
        hra.zpracujPrikaz("jdi jidelna");
        hra.zpracujPrikaz("jdi lidl");
        hra.zpracujPrikaz("kup Rohliky");
        hra.zpracujPrikaz("kup PsiGranule");
        hra.zpracujPrikaz("jdi jidelna");
        hra.zpracujPrikaz("jdi domov");
        hra.zpracujPrikaz("promluv Pepa");
        assertEquals(true, hra.getHerniPlan().isVyhra());
    }

    /**
     * Otestuje scénář perfektní výhry
     */
    @Test
    public void prubehHryPerfektniVyhra() {
        hra.zpracujPrikaz("jdi jidelna");
        hra.zpracujPrikaz("jdi smetiste");
        hra.zpracujPrikaz("seber StareHodiny");
        hra.zpracujPrikaz("seber LahevOdSvijan");
        hra.zpracujPrikaz("seber LahevOdPlzne");
        hra.zpracujPrikaz("seber LahevOdBranika");
        hra.zpracujPrikaz("jdi zastavarna");
        hra.zpracujPrikaz("promluv Zastavarnik");
        hra.zpracujPrikaz("jdi sekac");
        hra.zpracujPrikaz("promluv Prodavac");
        hra.zpracujPrikaz("jdi pracak");
        hra.zpracujPrikaz("promluv Podezrely");
        hra.zpracujPrikaz("jdi smetiste");
        hra.zpracujPrikaz("jdi jidelna");
        hra.zpracujPrikaz("promluv Kim");
        hra.zpracujPrikaz("jdi smetiste");
        hra.zpracujPrikaz("jdi pracak");
        hra.zpracujPrikaz("promluv Podezrely");
        hra.zpracujPrikaz("seber CervenaBunda");
        hra.zpracujPrikaz("seber ZelenaCepice");
        hra.zpracujPrikaz("jdi sekac");
        hra.zpracujPrikaz("promluv Prodavac");
        hra.zpracujPrikaz("jdi smetiste");
        hra.zpracujPrikaz("jdi jidelna");
        hra.zpracujPrikaz("jdi lidl");
        hra.zpracujPrikaz("vymen LahevOdSvijan");
        hra.zpracujPrikaz("vymen LahevOdPlzne");
        hra.zpracujPrikaz("vymen LahevOdBranika");
        hra.zpracujPrikaz("kup Rohliky");
        hra.zpracujPrikaz("kup PsiGranule");
        hra.zpracujPrikaz("kup BezlepkovyChleba");
        hra.zpracujPrikaz("jdi trafika");
        hra.zpracujPrikaz("kup Snus");
        hra.zpracujPrikaz("jdi jidelna");
        hra.zpracujPrikaz("promluv Kim");
        hra.zpracujPrikaz("jdi domov");
        hra.zpracujPrikaz("promluv Pepa");
        assertEquals(true, hra.getHerniPlan().isPerfektniVyhra());
    }

    /**
     * Otestuje různé konce hry
     */
    @Test
    public void konecHryProhra() {
        hra.getHerniPlan().setProhra(true);
        String spravnyText = "Nestihli jste koupit pro jídlo pro sebe a pro Pepu" +
                "\nProhra, hodně štěstí příště.\n";
        assertEquals(spravnyText, hra.vratEpilog());


        hra.getProgressInstance().setProgress(3);
        String spravnyText2 = "Sven byl sám na lupiče krátký, lupičovi se podařilo s oblečením utéct." +
                "\nProhra, hodně štěstí příště.\n";
        assertEquals(spravnyText2, hra.vratEpilog());
    }

    @Test
    public void konecHryVyhra() {
        hra.getHerniPlan().setVyhra(true);
        String spravnyText = "Obstarali jste jídlo pro sebe a pro Pepu.\n" +
                "Kim ale dneska bude o hladu - příště by jste to mohli napravit.\n" +
                "Výhra, dobrá práce.\n";
        assertEquals(spravnyText, hra.vratEpilog());

    }

    @Test
    public void konecHryPerfektniVyhra() {
        hra.getHerniPlan().setPerfektniVyhra(true);
        String spravnyText = "Obstarali jste pro všechny jídlo a Sven si koupil snus.\n" +
                "Perfektni výhra, gratuluji.\n";
        assertEquals(spravnyText, hra.vratEpilog());
    }
}