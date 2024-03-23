package cz.vse.sven.logika.prikazy;

import cz.vse.sven.logika.hra.Hra;
import cz.vse.sven.logika.hra.Progress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testovací třída PrikazJdiTest slouží k otestování třídy PrikazJdi
 *
 * @author Tomáš Kotouč
 * @version únor 2024
 */
public class PrikazJdiTest {

    private Hra hra;
    private Progress progress;

    @BeforeEach
    public void setUp() {
        hra = new Hra();
        progress = new Progress();
    }

    /**
     * Otestuje, zda se příkaz správně vypořádá se zadaným parametrem
     */
    @Test
    public void provedPrikaz() {
        PrikazJdi prikazJdi = new PrikazJdi(hra.getHerniPlan(), progress);

        // Parametr chybí
        assertEquals("Nezadali jste místo, kam chcete jít", prikazJdi.provedPrikaz());

        // Není platný sousední prostor
        assertEquals("Tam se odsud jít nedá", prikazJdi.provedPrikaz("smetiste"));

        String spravnyText =
                "\n\nNacházíš se u jídelny, která má právě zavřeno, vedle ní postává váš kamarád Kim.\n" +
                        "\n" +
                        "východy: lidl  smetiste  trafika  domov  \n" +
                        "věci: \n" +
                        "postavy: Kim  \n";

        // Platný sousední prostor
        assertEquals(spravnyText, prikazJdi.provedPrikaz("jidelna"));
    }
}