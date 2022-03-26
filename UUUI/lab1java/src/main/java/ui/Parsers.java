package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;

public class Parsers {
    public static String initialState;
    public static List<String> goalStates;

    /** parser for bfs,ucs,astar
     *
     * @param path                      path to file with required data
     * @return                          returns a map with parsed data
     * @throws FileNotFoundException    in case a file couldn't be found
     */
    public static HashMap<String, HashMap<String, Integer>> readData (Path path) throws FileNotFoundException
    {
        HashMap<String, HashMap<String, Integer>> neighbours = new HashMap<>();
        File f = new File(String.valueOf(path));
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
        goalStates= Arrays.asList(allLines.get(1).split(" "));

        allLines.remove(0);
        allLines.remove(0);

        for(String s : allLines)
        {
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

    /**
     *
     * @param path                      path to file with required data
     * @return                          return a map with parsed heuristic data
     * @throws FileNotFoundException    in case a file couldn't be found
     */
    public static Map<String, Double> readHeuristicDescData (Path path) throws FileNotFoundException
    {
        Map<String, Double> hDMap = new HashMap<>();
        File f = new File(String.valueOf(path));
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
        for(String s : allLines)
        {
            String[] parts = s.strip().split(":");
            hDMap.put(parts[0], Double.valueOf(parts[1].strip()));
        }
        return hDMap;
    }

    public static String getInitialState(){
        return initialState;
    }

    public static List<String> getGoalStates() {
        return goalStates;
    }
}
