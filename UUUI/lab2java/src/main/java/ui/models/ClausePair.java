package ui.models;

import java.util.Objects;

/**
 * just a simple model for a pair of 2 clauses, for handling selectClauses method
 */
public class ClausePair
{
    private Clause first;
    private Clause second;

    public ClausePair(Clause first, Clause second) {
        this.first = first;
        this.second = second;
    }

    public ClausePair() {
    }

    public Clause getFirst() {
        return first;
    }

    public void setFirst(Clause first) {
        this.first = first;
    }

    public Clause getSecond() {
        return second;
    }

    public void setSecond(Clause second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClausePair that = (ClausePair) o;
        return Objects.equals(first, that.first) && Objects.equals(second, that.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
