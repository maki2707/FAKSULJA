package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class A_starAlgorithm {
    public static String initialState;
    public static List<String> goalStates;


    public static void Astar_go(String path, String hPath) throws FileNotFoundException
    {
        HashMap<String, HashMap<String, Integer>> succ = readData(path);
        A_star_execute(initialState, succ,goalStates,hPath);
    }

    public static void A_star_execute(String initialState, HashMap<String, HashMap<String, Integer>> succ, List<String> goalStates, String hPath)
    {
        PriorityQueue<MyHeuristicNode> open = new PriorityQueue<>();
        HashMap<String, MyHeuristicNode> openMap = new HashMap<>();
        HashMap<String, MyHeuristicNode> closed = new HashMap<>();

        open.add(new MyHeuristicNode(initialState,0,null));
        openMap.put(initialState, open.peek());

        while (!open.isEmpty())
        {
            MyHeuristicNode currentNode = open.remove();
            closed.put(currentNode.getState(),currentNode);
            if(goalStates.contains(currentNode.getState()))
            {
                String a = "A_STAR " + hPath;
                Util.FoundSolution(Util.getPath(currentNode), closed.size(), a);
                return;
            }

            for(String nodeState : succ.get(currentNode.getState()).keySet())
            {
                if(openMap.containsKey((nodeState)))
                {
                    MyHeuristicNode mNode = openMap.get(nodeState);
                    double newCost = currentNode.getCost() + succ.get(currentNode.getState()).get(nodeState);
                    if(mNode.getCost() < newCost)
                    {
                        continue;
                    }
                    else
                    {
                        open.remove(mNode);
                        openMap.remove(mNode.getState());
                    }
                }

                else if (closed.containsKey(nodeState))
                {
                    MyHeuristicNode mNode = closed.get(nodeState);
                    double newCost = currentNode.getCost() + succ.get(currentNode.getState()).get(nodeState);
                    if(mNode.getCost()<newCost)
                    {
                        continue;
                    }
                    else
                    {
                        closed.remove(mNode.getState());
                    }
                }
                int cost = succ.get(currentNode.getState()).get(nodeState);
                open.add(new MyHeuristicNode(nodeState, cost, currentNode));
                openMap.put(nodeState,new MyHeuristicNode(nodeState, cost, currentNode));
            }
        }
    }

    public static HashMap<String, HashMap<String, Integer>> readData (String path) throws FileNotFoundException
    {
        HashMap<String, HashMap<String, Integer>> neighbours = new HashMap<>();
        File f = new File(path);
        Scanner sc = new Scanner(f);
        List<String> allLines = new ArrayList<>();

        while(sc.hasNextLine())
        {
            String cLine = sc.nextLine();
            if(!cLine.startsWith("#"))
            {
                allLines.add(cLine);
            }
        }

        initialState = allLines.get(0);
        goalStates=Arrays.asList(allLines.get(1).split(" "));

        allLines.remove(0);
        allLines.remove(0);

        for(String s : allLines) {
            String key = s.substring(0, s.indexOf(':'));
            String[] nbs = {};
            if (s.split(":").length > 1)
            {
                nbs = s.split(":")[1].trim().split(" ");
            }
            HashMap<String, Integer> tempmap = new HashMap<>();
            for(String n : nbs)
            {
                tempmap.put(n.split(",")[0],Integer.valueOf(n.split(",")[1]));
            }
            neighbours.put(key, tempmap);
        }
        return neighbours;
    }
}
