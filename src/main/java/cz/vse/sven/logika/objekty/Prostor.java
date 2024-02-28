package cz.vse.sven.logika.objekty;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Trida Prostor - popisuje jednotlivé prostory (místnosti) hry
 * <p>
 * Tato třída je součástí jednoduché textové hry.
 * <p>
 * "Prostor" reprezentuje jedno místo (místnost, prostor, ..) ve scénáři hry.
 * Prostor může mít sousední prostory připojené přes východy. Pro každý východ
 * si prostor ukládá odkaz na sousedící prostor.
 *
 * @author Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Tomáš Kotouč
 * @version únor 2024
 */
public class Prostor {

    private String nazev;
    private String popis;
    private Set<Prostor> vychody;    // obsahuje sousedni mistnosti
    private Map<String, Vec> seznamVeci;
    private Map<String, Postava> seznamPostav;

    /**
     * Vytvoření prostoru se zadaným popisem, např. "kuchyň", "hala", "trávník
     * před domem"
     *
     * @param nazev nazev prostoru, jednoznačný identifikátor, jedno slovo nebo
     *              víceslovný název bez mezer.
     * @param popis Popis prostoru.
     */
    public Prostor(String nazev, String popis) {
        this.nazev = nazev;
        this.popis = popis;
        vychody = new HashSet<>();
        seznamVeci = new HashMap<>();
        seznamPostav = new HashMap<>();
    }

    /**
     * Definuje východ z prostoru (sousední/vedlejsi prostor). Vzhledem k tomu,
     * že je použit Set pro uložení východů, může být sousední prostor uveden
     * pouze jednou (tj. nelze mít dvoje dveře do stejné sousední místnosti).
     * Druhé zadání stejného prostoru tiše přepíše předchozí zadání (neobjeví se
     * žádné chybové hlášení). Lze zadat též cestu ze do sebe sama.
     *
     * @param vedlejsi prostor, který sousedi s aktualnim prostorem.
     */
    public void setVychod(Prostor vedlejsi) {
        vychody.add(vedlejsi);
    }

    /**
     * Metoda equals pro porovnání dvou prostorů. Překrývá se metoda equals ze
     * třídy Object. Dva prostory jsou shodné, pokud mají stejný název. Tato
     * metoda je důležitá z hlediska správného fungování seznamu východů (Set).
     * <p>
     * Bližší popis metody equals je u třídy Object.
     *
     * @param o object, který se má porovnávat s aktuálním
     * @return hodnotu true, pokud má zadaný prostor stejný název, jinak false
     */
    @Override
    public boolean equals(Object o) {
        // porovnáváme zda se nejedná o dva odkazy na stejnou instanci

        if (this == o) {
            return true;
        }
        // porovnáváme jakého typu je parametr
        if (!(o instanceof Prostor)) {
            return false; // pokud parametr není typu Prostor, vrátíme false
        }
        // přetypujeme parametr na typ Prostor
        Prostor druhy = (Prostor) o;

        //metoda equals třídy java.util.Objects porovná hodnoty obou názvů.
        //Vrátí true pro stejné názvy a i v případě, že jsou oba názvy null,
        //jinak vrátí false.

        return (java.util.Objects.equals(this.nazev, druhy.nazev));
    }

    /**
     * metoda hashCode vraci ciselny identifikator instance, ktery se pouziva
     * pro optimalizaci ukladani v dynamickych datovych strukturach. Pri
     * prekryti metody equals je potreba prekryt i metodu hashCode. Podrobny
     * popis pravidel pro vytvareni metody hashCode je u metody hashCode ve
     * tride Object
     */
    @Override
    public int hashCode() {
        int vysledek = 3;
        int hashNazvu = java.util.Objects.hashCode(this.nazev);
        vysledek = 37 * vysledek + hashNazvu;
        return vysledek;
    }

    /**
     * Vrací název prostoru (byl zadán při vytváření prostoru jako parametr
     * konstruktoru)
     *
     * @return název prostoru
     */
    public String getNazev() {
        return nazev;
    }

    /**
     * Vrací "dlouhý" popis prostoru
     *
     * @return Dlouhý popis prostoru
     */
    public String dlouhyPopis() {
        return "\n\nNacházíš se " + popis + "\n\n"
                + popisVychodu() + "\n"
                + seznamVeci() + "\n"
                + seznamPostav() + "\n";
    }

    /**
     * Vrací textový řetězec, který popisuje sousední východy, například:
     * "vychody: hala ".
     *
     * @return Popis východů - názvů sousedních prostorů
     */
    public String popisVychodu() {
        String vracenyText = "východy: ";
        for (Prostor sousedni : vychody) {
            vracenyText += sousedni.getNazev() + "  ";
        }
        return vracenyText;
    }

    /**
     * Vrací prostor, který sousedí s aktuálním prostorem a jehož název je zadán
     * jako parametr. Pokud prostor s udaným jménem nesousedí s aktuálním
     * prostorem, vrací se hodnota null.
     *
     * @param nazevSouseda Jméno sousedního prostoru (východu)
     * @return Prostor, který se nachází za příslušným východem, nebo hodnota
     * null, pokud prostor zadaného jména není sousedem.
     */

    public Prostor vratSousedniProstor(String nazevSouseda) {
        List<Prostor> hledaneProstory =
                vychody.stream()
                        .filter(sousedni -> sousedni.getNazev().equals(nazevSouseda))
                        .collect(Collectors.toList());
        if (hledaneProstory.isEmpty()) {
            return null;
        } else {
            return hledaneProstory.get(0);
        }
    }

    /**
     * Vrací kolekci obsahující prostory, se kterými tento prostor sousedí.
     * Takto získaný seznam sousedních prostor nelze upravovat (přidávat,
     * odebírat východy) protože z hlediska správného návrhu je to plně
     * záležitostí třídy Prostor.
     *
     * @return Nemodifikovatelná kolekce prostorů (východů), se kterými tento
     * prostor sousedí.
     */
    public Collection<Prostor> getVychody() {
        return Collections.unmodifiableCollection(vychody);
    }

    /**
     * Metoda vrátí kolekci věcí nacházejících se v prostoru
     *
     * @return kolekce věcí nacházejících se v prostoru
     */
    public Collection<Vec> getSeznamVeci() {
        return Collections.unmodifiableCollection(seznamVeci.values());
    }

    /**
     * Metoda vrátí kolekci postav nacházejích se v prostoru
     *
     * @return kolekce postav nacházejících se v prostoru
     */
    public Collection<Postava> getSeznamPostav() {
        return Collections.unmodifiableCollection(seznamPostav.values());
    }

    /**
     * Přidá věc do prostoru
     *
     * @param vec k přidání
     */
    public void addVec(Vec vec) {
        seznamVeci.put(vec.getJmeno(), vec);
    }

    /**
     * Odebere věc z prostoru
     *
     * @param vec k odebrání
     */
    public void removeVec(String vec) {
        seznamVeci.remove(vec);
    }

    /**
     * Zkontroluje, zda prostor obsahuje danou věc
     *
     * @param jmenoVeci ke zkontrolování
     * @return true/false
     */
    public boolean obsahujeVec(String jmenoVeci) {
        return seznamVeci.containsKey(jmenoVeci);
    }

    /**
     * Zkontroluje, zda daná věc nacházejíci se v prostoru je sebratelná
     *
     * @param jmenoVeci ke zkontrolovaní
     * @return Pokud je sebratelná, tak věc vrátí, pokud ne, vrátí null
     */
    public Vec jeVecSebratelna(String jmenoVeci) {
        Vec vec;
        if (seznamVeci.containsKey(jmenoVeci)) {
            vec = seznamVeci.get(jmenoVeci);
            if (vec.isSebratelna()) {
                return vec;
            }
            return null;
        }
        return null;
    }

    /**
     * Zkontroluje, zda daná věc nacházejíci se v prostoru je koupitelná
     *
     * @param jmenoVeci ke zkontrolovaní
     * @return Pokud je koupitelná, tak věc vrátí, pokud ne, vrátí null
     */
    public Vec jeVecKoupitelna(String jmenoVeci) {
        Vec vec;
        if (seznamVeci.containsKey(jmenoVeci)) {
            vec = seznamVeci.get(jmenoVeci);
            if (vec.isKoupitelna()) {
                return vec;
            }
            return null;
        }
        return null;
    }

    /**
     * Vrátí abecedně seřazený seznam věcí v prostoru
     *
     * @return seznam věcí
     */
    public String seznamVeci() {
        List<String> veci = new ArrayList<>(seznamVeci.keySet());
        Collections.sort(veci);

        String nazvy = "věci: ";
        for (String jmenoVeci : veci) {
            nazvy += jmenoVeci + "  ";
        }
        return nazvy;
    }

    /**
     * Vloží postavu do prostoru v případě, že se postava v tomot prostoru ještě nenachází
     *
     * @param postava k vložení
     */
    public void addPostava(Postava postava) {
        if (!(seznamPostav.containsKey(postava.getJmeno()))) {
            seznamPostav.put(postava.getJmeno(), postava);
        }
    }

    /**
     * Odebere věc z prostoru
     *
     * @param jmenoPostavy k odebrání
     */
    public void removePostava(String jmenoPostavy) {
        seznamPostav.remove(jmenoPostavy);
    }

    /**
     * zjistí, zda se postava v prostoru nachází
     *
     * @param jmeno ke kontrole
     * @return true - postava se v prostoru nachazi, false - nenachazi
     */
    public boolean obsahujePostavu(String jmeno) {
        return seznamPostav.containsKey(jmeno);
    }

    /**
     * Vrátí abecedně seřazený seznam postav v prostoru
     *
     * @return seznam postav
     */
    public String seznamPostav() {
        List<String> postavy = new ArrayList<>(seznamPostav.keySet());
        Collections.sort(postavy);

        String nazvy = "postavy: ";
        for (String jmenoPostavy : postavy) {
            nazvy += jmenoPostavy + "  ";
        }
        return nazvy;
    }
}
