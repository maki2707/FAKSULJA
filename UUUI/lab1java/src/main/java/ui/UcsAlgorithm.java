package ui;

import java.util.*;

public class UcsAlgorithm
{
    public static List<String> goalStates;
    public static Double resultCost;

    public static double UCS_checkOptimistic(String initialState, HashMap<String, HashMap<String, Integer>> succ, List<String> goalStates)
    {
        UCS_execute(initialState,succ,goalStates,true);
        return resultCost;
    }

    public static void UCS_execute(String initialState, HashMap<String, HashMap<String, Integer>> succ, List<String> goalStates, boolean flagOptimistic)
    {
        PriorityQueue<MyNode> open = new PriorityQueue<>();
        HashSet<String> closed = new HashSet<>();
        open.add(new MyNode(initialState, null, 0));
        while(!open.isEmpty())
        {
            MyNode currentNode = open.remove();
            closed.add(currentNode.getState());

            if(goalStates.contains(currentNode.getState()))
            {
                if(!flagOptimistic)
                {
                    Util.FoundSolution(Util.getPath(currentNode), closed.size(), "UCS");
                    setResultCostCost(currentNode);
                }
                else
                {
                    setResultCostCost(currentNode);
                }
                return;
            }

            for(String nodeState : succ.get(currentNode.getState()).keySet())
            {
                if(!closed.contains(nodeState))
                {
                    MyNode childNode = new MyNode(nodeState, currentNode, succ.get(currentNode.getState()).get(nodeState));
                    open.add(childNode);
                }
            }
        }
    }

    private static void setResultCostCost(MyNode node)
    {
        resultCost = (double) node.getCost();
    }

    public static List<String> getGoalStates(){
        return goalStates;
    }
}
