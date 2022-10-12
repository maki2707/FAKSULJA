package ui.models;

import java.util.Objects;

/**
 * class which represents a model for a single literal
 */
public class Literal implements Comparable<Literal>
{
    private String name;
    private boolean status;

    public Literal(String name, boolean status)
    {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Literal getNegative(){
        return new Literal(this.name, ! this.status);
    }

    @Override
    public String toString() {
        return (status ? "" : "~") + this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Literal literal = (Literal) o;
        return status == literal.status && name.equals(literal.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, status);
    }

    public int compareTo(Literal o){
        if(o.getName().equals(this.getName())){
            return Boolean.compare(o.getStatus(),getStatus());
        }
        else{
            return getName().compareTo(o.getName());
        }
    }
}
