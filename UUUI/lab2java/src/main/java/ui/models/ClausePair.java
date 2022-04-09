package ui.models;

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
}
