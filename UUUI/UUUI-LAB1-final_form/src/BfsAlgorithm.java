import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;



public class BfsAlgorithm
{
    public static String initialState;
    public static List<String> goalStates;


    public static void main(String[] args) throws FileNotFoundException
    {
        HashMap<String, HashMap<String, Integer>> succ = readData("data.txt");

        BFS_execute(initialState, succ,goalStates);
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

                FoundSolution(getPath(currentNode), closed.size());
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

    private static void FoundSolution(List<String> path, int visitedStates )
    {
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

    private static List<String> getPath(MyNode node)
    {
        List<String> path = new ArrayList<>();
        int totalCost = 0;
        MyNode active = node;

        while(active != null)
        {
            path.add(active.getState());
            totalCost = totalCost + active.getCost();
            active = active.getParent();
        }
        Collections.reverse(path);
        path.add(String.valueOf(totalCost));
        return path;
    }
}
