package ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Util
{
    /**                         prints the required fields and the result of the algorithm that was executed
     *
     * @param path              list with states that are part of the solution path
     * @param visitedStates     number of visited states during the algorithm
     */
    public static void FoundSolution(List<String> path, int visitedStates, String algType )
    {
        System.out.println("# " +algType);
        System.out.println("[FOUND_SOLUTION]: yes");
        System.out.println("[STATES_VISITED]: " + visitedStates);
        System.out.println("[PATH_LENGTH]: " + (path.size()-1));
        System.out.println("[TOTAL_COST]: " + path.remove(path.size()-1));
        StringBuilder sb = new StringBuilder();
        for(String s : path)
        {
            sb.append(s);
            sb.append(" => ");
        }
        sb.setLength(sb.length() - 4);
        System.out.println("[PATH]: " + sb);
    }

    /**
     *
     * @param node      the result node
     * @return          method returns list of states that form the shortest path
     */
    public static List<String> getPath(MyNode node)
    {
        List<String> path = new ArrayList<>();
        int totalCost = node.getCost();
        MyNode active = node;

        while(active != null)
        {
            path.add(active.getState());
            active = active.getParent();
        }
        double tc = totalCost;
        Collections.reverse(path);
        path.add(String.valueOf(tc));
        return path;
    }



}
