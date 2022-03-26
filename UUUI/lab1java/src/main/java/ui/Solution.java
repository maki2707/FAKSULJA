package ui;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class Solution
{
    private static String algorithm;
    private static String ssPath;
    private static boolean checkConsistentFlag;
    private static boolean checkOptimisticFlag;
    public static String hPath;
    private static HashMap<String, HashMap<String, Integer>> succ;
    public static Map<String, Double> hDesc;

    public static void main(String[] args) throws FileNotFoundException {
        for (int i = 0, length = args.length; i < length; i++)
        {
            switch (args[i])
            {
                case "--alg":
                    algorithm = args[i+1];
                    break;
                case "--ss":
                    ssPath = args[i+1];
                    break;
                case "--check-consistent":
                    checkConsistentFlag = true;
                    break;
                case "--h":
                    hPath = args[i+1];
                    break;
                case "--check-optimistic":
                    checkOptimisticFlag = true;
                    break;
            }
        }
        if(algorithm != null)
        {
            if(algorithm.equals("bfs"))
            {
                succ = Parsers.readData(Paths.get(ssPath).toAbsolutePath().normalize());
                BfsAlgorithm.BFS_execute(Parsers.getInitialState(),succ,Parsers.getGoalStates());
            }
            else if(algorithm.equals("ucs"))
            {
                succ = Parsers.readData(Paths.get(ssPath).toAbsolutePath().normalize());
                UcsAlgorithm.UCS_execute(Parsers.getInitialState(),succ,Parsers.getGoalStates(),false);
            }
            else if(algorithm.equals("astar"))
            {
                hDesc = Parsers.readHeuristicDescData(Paths.get(hPath).toAbsolutePath().normalize());
                A_starAlgorithm.Astar_go(ssPath,hPath);
            }
        }
        else
        {
            if(checkConsistentFlag)
            {
                CheckHeuristics.checkIfConsistent(Parsers.readData(Path.of(ssPath)), Paths.get(hPath).toAbsolutePath().normalize());
            }
            else if(checkOptimisticFlag)
            {
                CheckHeuristics.checkIfOptimistic(Parsers.readData(Path.of(ssPath)), Paths.get(hPath).toAbsolutePath().normalize(),Parsers.getGoalStates());
            }
        }
    }
}
