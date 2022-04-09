package ui.models;

import java.util.List;

public class ClauseInput
{
    private List<Clause> clauses;
    private Clause goalClause;

    public ClauseInput() { }

    public ClauseInput(List<Clause> clauses, Clause goalClause) {
        this.clauses = clauses;
        this.goalClause = goalClause;
    }

    public List<Clause> getClauses() {
        return clauses;
    }

    public void setClauses(List<Clause> clauses) {
        this.clauses = clauses;
    }

    public Clause getGoalClause() {
        return goalClause;
    }

    public void setGoalClause(Clause goalClause) {
        this.goalClause = goalClause;
    }
}
