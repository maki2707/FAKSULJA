package ui;

import java.util.*;


public class MyNode implements Comparable<MyNode>
{
    public String state;
    public MyNode parent;
    public int depth;
    public int cost;

    public MyNode(String state, MyNode parent, int cost) {
        this.state = state;
        this.parent = parent;
        this.depth = parent == null ? 0 : this.parent.depth+1;
        this.cost = parent == null ? cost : this.parent.cost + cost;
    }

    public String getState() {
        return state;
    }

    public MyNode getParent() {
        return parent;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public int compareTo(MyNode o)
    {
        if(this.getCost() == o.getCost())
        {
            return this.getState().compareTo(o.getState());
        }
        else
        {
            return Integer.compare(this.cost, o.cost);
        }
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(state, parent);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        else if (o == null)
        {
            return false;
        }
        MyNode n = (MyNode) o;
        boolean res = Objects.equals(parent, n.parent)&& state.equals(n.state);
        return res;
    }
}
