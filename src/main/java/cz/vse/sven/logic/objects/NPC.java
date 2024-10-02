package cz.vse.sven.logic.objects;

import java.util.Objects;

/**
 * NPC
 */
public record NPC(String name, String fullName) {

    /**
     * Constructor
     */
    public NPC {
    }

    /**
     * Name getter
     */
    @Override
    public String name() {
        return name;
    }

    /**
     * Full name getter
     */
    @Override
    public String fullName() {
        return fullName;
    }

    /**
     * equals method - two NPCs are the same, if they have the same name
     *
     * @param NPC to be compared
     * @return true if the two NPCs are the same, false if not
     */
    @Override
    public boolean equals(Object NPC) {
        if (this == NPC) return true;
        if (NPC == null || getClass() != NPC.getClass()) return false;
        NPC n = (NPC) NPC;
        return Objects.equals(name, n.name);
    }

    /**
     * hashCode method
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * toString method
     *
     * @return NPC name
     */
    @Override
    public String toString() {
        return name();
    }
}