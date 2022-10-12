package ui.Nodes;

import java.util.HashMap;
import java.util.Map;

/**
 * model of the node used in tree
 */
public class Node
{
    public String attribute;
    public String value;
    public Map<String, Node> children;

    public Node(String attribute)
    {
        this.attribute = attribute;
        this.value = null;
        this.children = new HashMap<>();
    }

    public Node()
    {

    }

    public String getAttribute()
    {
        return attribute;
    }

    public void setAttribute(String attribute)
    {
        this.attribute = attribute;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public Map<String, Node> getChildren()
    {
        return children;
    }

    public void setChildren(Map<String, Node> children)
    {
        this.children = children;
    }

    public void addToChildren(String key, Node node)
    {
        this.children.put(key, node);
    }

    public void print_exe(Node node)
    {
        System.out.println("[BRANCHES]: ");
        printBranches(node,"",0);
    }

    private void printBranches(Node node, String old, int d)
    {
        d = d + 1;

        if(node.getAttribute() == null)
        {
            System.out.println(old + node.getValue());
            return;
        }

        for(Map.Entry<String, Node> childNode : node.getChildren().entrySet())
        {
            StringBuilder oldNew = new StringBuilder();
            oldNew.append(old).append(d).append(":").append(node.getAttribute()).append("=").append(childNode.getKey()).append(" ");
            printBranches(childNode.getValue(), oldNew.toString(), d);
        }

    }
}
