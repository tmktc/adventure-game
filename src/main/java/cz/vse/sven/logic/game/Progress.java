package cz.vse.sven.logic.game;

/**
 * Progress - keeps track of progress value (how far the player is into the story)
 */
public class Progress {

    private int progress;

    /**
     * Constructor
     */
    public Progress() {
        progress = 0;
    }

    /**
     * Returns progress value
     *
     * @return progress value
     */
    public int getProgress() {
        return progress;
    }

    /**
     * Sets the progress value (used in tests)
     *
     * @param number value to be set
     */
    public void setProgress(int number) {
        this.progress = number;
    }

    /**
     * Increases progress value by 1
     */
    public void addProgress() {
        progress++;
    }


}
