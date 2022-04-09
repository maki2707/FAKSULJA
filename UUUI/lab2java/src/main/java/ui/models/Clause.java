package ui.models;

import java.util.*;
import java.util.stream.Collectors;

public class Clause extends ClausePair {
    private List<Literal> literals;
    private int index;


    public Clause(List<Literal> literals, int index) {
        this.literals = literals.stream().distinct().collect(Collectors.toList());
        this.literals.sort(Comparator.naturalOrder());

        this.index = index;
    }

    public List<Literal> getLiterals() {
        return literals;
    }

    public void setLiterals(List<Literal> literals) {
        this.literals = literals;
    }

    public boolean checkIfContainsLiteral(Literal l) {
        return this.literals.contains(l);
    }

    public boolean isSubsumedBy(Clause other) {
        return literals.containsAll(other.literals);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clause clause = (Clause) o;
        return Objects.equals(literals, clause.literals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(literals);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Literal l : getLiterals())
        {
            sb.append(l);
            sb.append(" v ");
        }

        return sb.toString().substring(0,sb.length()-3);
    }

}
