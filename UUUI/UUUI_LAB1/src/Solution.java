import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Deque;

public class Solution
{
    public static void main(String[] args) throws FileNotFoundException
    {
        MyNodes test = readData("data.txt");
        BFS(test);
    }

    public static MyNodes readData (String path) throws FileNotFoundException
    {
        MyNodes myNodes = new MyNodes();
        HashMap<String, HashMap<String, Double>> neighbours = new HashMap<>();
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

        myNodes.setInitialState(allLines.get(0));
        myNodes.setGoalStates(Arrays.asList(allLines.get(1).split(" ")));

        allLines.remove(0);
        allLines.remove(0);

        for(String s : allLines)
        {
            String key = s.substring(0, s.indexOf(':'));
            String[] nbs = s.split(":")[1].trim().split(" ");
            HashMap<String, Double> tempmap = new HashMap<String, Double>();
            for(String n : nbs)
            {
                tempmap.put(n.split(",")[0],Double.valueOf(n.split(",")[1]));
            }
            neighbours.put(key, tempmap);
        }

        myNodes.setNeighbours(neighbours);
        return myNodes;
    }

    public static void BFS(MyNodes myNodes)
    {
        int visitedStates = 0;
        List<String> closed = new ArrayList<>();
        Deque<String> open = new LinkedList<>();
        HashMap<String, String> backTrackMap = new HashMap<>();
        open.add(myNodes.getInitialState());

        while(!open.isEmpty())
        {
            String currentState = open.removeFirst();
            closed.add(currentState);
            visitedStates = visitedStates + 1;

            if(myNodes.getGoalStates().contains(currentState))
            {
                foundSolution(myNodes, backTrackMap, currentState, visitedStates);
                return;
            }
            List<String> keyNodes = new ArrayList<>(myNodes.getNeighbours().get(currentState).keySet());
            Collections.sort(keyNodes);
            for(String s : keyNodes)
            {
                if(!closed.contains(s))
                {
                    open.add(s);
                    backTrackMap.put(s, currentState);
                }
            }
        }
        return;
    }

    public static List<String> getPath (MyNodes nodes, HashMap<String,String> backTrackMap, String wantedState)
    {
        String activeState = wantedState;
        List<String> pathToGoal = new ArrayList<>();
        double totalCost = 0;

        while(!activeState.equals(nodes.getInitialState()))
        {
            pathToGoal.add(activeState);
            double cost = nodes.getNeighbours().get(activeState).get(backTrackMap.get(activeState));
            activeState = backTrackMap.get(activeState);
            totalCost += cost;
        }

        pathToGoal.add(nodes.getInitialState());
        pathToGoal.add(String.valueOf(totalCost));
        return pathToGoal;
    }

    public static void foundSolution(MyNodes myNodes, HashMap<String, String> backTrackMap, String currentState, int visitedStates)
    {
        List<String> resultPath = getPath(myNodes, backTrackMap,currentState);
        String totCost = resultPath.get(resultPath.size()-1);
        resultPath.remove(resultPath.size()-1);
        Collections.reverse(resultPath);
        StringBuilder sb = new StringBuilder();

        System.out.println("[FOUND_SOLUTION]: yes");
        System.out.println("[STATES_VISITED]: " + visitedStates);
        System.out.println("[PATH_LENGTH]: " + resultPath.size());
        System.out.println("[TOTAL_COST]: " + totCost);

        for(String s : resultPath)
        {
            sb.append(s);
            sb.append(" => ");
        }
        sb.setLength(sb.length() - 4);
        System.out.println("[PATH]: " + sb);
    }
}
