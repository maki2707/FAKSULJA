package ui;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
public class MyHeuristicNode extends MyNode
{
    private final double gFunction;
    private final double fFunction;
    private final MyHeuristicNode parent;

    public MyHeuristicNode(String state,int cost, MyHeuristicNode parent) {
        super(state, parent, cost);

        this.parent = parent;
        this.gFunction = (this.parent != null) ? this.parent.getgFunction() + cost : cost;
        this.fFunction = this.gFunction + Solution.hDesc.get(this.getState());
    }

    public double getgFunction() {
        return gFunction;
    }

    public double getfFunction() {
        return fFunction;
    }

    public String getState(){
        return this.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, parent, fFunction, gFunction);
    }

    @Override
    public int compareTo(MyNode o)
    {
        MyHeuristicNode other = (MyHeuristicNode) o;
        if (this.getfFunction() == other.getfFunction())
        {
            return this.getState().compareTo(other.getState());
        }
        else
        {
            return Double.compare(this.getfFunction(), other.getfFunction());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MyHeuristicNode) {
            MyHeuristicNode other = (MyHeuristicNode) obj;
            return Double.compare(other.fFunction, fFunction) == 0 && Double.compare(other.gFunction, gFunction) == 0 && this.state.equals(other.state) && Objects.equals(this.parent, other.parent);
        }
        else
        {
            return false;
        }
    }

}
