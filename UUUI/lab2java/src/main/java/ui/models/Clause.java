package ui.models;

import java.util.*;
import java.util.stream.Collectors;

/**
 * class which represents a model for a single clause
 */
public class Clause extends ClausePair {
    private List<Literal> literals;
    private int index;
    private Clause parent1;
    private Clause parent2;

    public Clause(List<Literal> literals, int index, Clause parent1, Clause parent2) {
        this.literals = literals.stream().distinct().collect(Collectors.toList());
        this.literals.sort(Comparator.naturalOrder());

        this.index = index;
        this.parent1 = parent1;
        this.parent2 = parent2;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public List<Literal> getLiterals() {
        return literals;
    }
    public List<Clause> getParents() {
        List<Clause> parents = new ArrayList<>();
        parents.add(this.getParent1());
        parents.add(this.getParent2());

        return parents;
    }


    public boolean checkIfContainsLiteral(Literal l) {
        return literals.contains(l);
    }

    public boolean isSubsumedBy(Clause other) {
        return literals.containsAll(other.getLiterals());
    }

    public int getIndex() {
        return index;
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

    public Clause getParent1() {
        return parent1;
    }

    public Clause getParent2() {
        return parent2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Clause clause = (Clause) o;
        return literals.equals(clause.literals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), literals, index, parent1, parent2);
    }
}
