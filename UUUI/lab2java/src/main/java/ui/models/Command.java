package ui.models;

import java.util.Objects;

/**
 * model for a command, makes it easier to work with when dealing with "cooking" tasks
 */
public class Command
{
    private  String type;
    private  Clause targetClause;

    public Command(String type, Clause targetClause)
    {
        this.type = type;
        this.targetClause = targetClause;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Clause getTargetClause() {
        return targetClause;
    }

    public void setTargetClause(Clause targetClause) {
        this.targetClause = targetClause;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return type.equals(command.type) && targetClause.equals(command.targetClause);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, targetClause);
    }
}
