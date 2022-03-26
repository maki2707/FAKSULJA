package ui;

import java.util.*;

public class BfsAlgorithm
{
    public static void BFS_execute(String initialState, HashMap<String, HashMap<String, Integer>> succ, List<String> goalStates)
    {
        Deque<MyNode> open = new LinkedList<>();
        HashSet<String> closed = new HashSet<>();

        open.add(new MyNode(initialState, null, 0));
        while(!open.isEmpty())
        {
            MyNode currentNode = open.removeFirst();
            closed.add(currentNode.getState());

            if(goalStates.contains(currentNode.getState()))
            {
                Util.FoundSolution(Util.getPath(currentNode), closed.size(), "BFS");
                return;
            }

            List<MyNode> childNodesForSorting = new ArrayList<>();

            for(String nodeState : succ.get(currentNode.getState()).keySet())
            {
                if(!closed.contains(nodeState))
                {
                    MyNode childNode = new MyNode(nodeState, currentNode, succ.get(currentNode.getState()).get(nodeState));
                    childNodesForSorting.add(childNode);
                }
            }
            childNodesForSorting.sort(Comparator.comparing(MyNode::getState));
            open.addAll(childNodesForSorting);
        }
    }
}
