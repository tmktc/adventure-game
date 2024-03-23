package cz.vse.sven.logika.hra;

/**
 * Třída Progress - slouží k manipulaci s hodnotou progress
 * hodnota progress ukazuje v jaké částí příběhu se hráč práve nachází
 *
 * @author Tomáš Kotouč
 * @version prosinec 2023
 */
public class Progress {

    private int progress;

    /**
     * Konstruktor - nastaví původní hodnotu na 0
     */
    public Progress() {
        progress = 0;
    }

    /**
     * Metoda zjistí hodnotu progressu
     *
     * @return hodnota progressu
     */
    public int getProgress() {
        return progress;
    }

    /**
     * Metoda nastaví hodnotu progressu - využito v testech
     *
     * @param cislo hodonta na kterou se má progress nastavit
     */
    public void setProgress(int cislo) {
        this.progress = cislo;
    }

    /**
     * Metoda navýší hodnotu progressu
     */
    public void addProgress() {
        progress++;
    }


}
