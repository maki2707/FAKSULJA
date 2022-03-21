import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;



public class UcsAlgorithm
{
    public static String initialState;
    public static List<String> goalStates;

    public static void main(String[] args) throws FileNotFoundException
    {
        HashMap<String, HashMap<String, Integer>> succ = readData("data.txt");

        UCS_execute(initialState, succ,goalStates);
    }

    private static void UCS_execute(String initialState, HashMap<String, HashMap<String, Integer>> succ, List<String> goalStates)
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

                FoundSolution(getPath(currentNode), closed.size());
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

        for(String s : allLines)
        {
            String key = s.substring(0, s.indexOf(':'));
            String[] nbs = s.split(":")[1].trim().split(" ");
            HashMap<String, Integer> tempmap = new HashMap<String, Integer>();
            for(String n : nbs)
            {
                tempmap.put(n.split(",")[0],Integer.valueOf(n.split(",")[1]));
            }
            neighbours.put(key, tempmap);
        }
        return neighbours;
    }

    private static void FoundSolution(List<String> path, int visitedStates )
    {
        System.out.println("[FOUND_SOLUTION]: yes");
        System.out.println("[STATES_VISITED]: " + visitedStates);
        System.out.println("[PATH_LENGTH]: " + (path.size()-1));
        System.out.println("[TOTAL_COST]: " + path.remove(path.size()-1) );
        StringBuilder sb = new StringBuilder();
        for(String s : path)
        {
            sb.append(s);
            sb.append(" => ");
        }
        sb.setLength(sb.length() - 4);
        System.out.println("[PATH]: " + sb);
    }

    private static List<String> getPath(MyNode node)
    {
        List<String> path = new ArrayList<>();
        int totalCost = node.getCost();
        MyNode active = node;

        while(active != null)
        {
            path.add(active.getState());
            active = active.getParent();
        }
        Collections.reverse(path);
        path.add(String.valueOf(totalCost));
        return path;
    }

}
