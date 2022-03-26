package ui;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class CheckHeuristics
{
    /*check if consistent*/
    public static Map<String, Double> hDescMap = new HashMap<>();

    public static void checkIfConsistent(HashMap<String, HashMap<String, Integer>> succ, Path hpath) throws FileNotFoundException
    {
        hDescMap = Parsers.readHeuristicDescData(hpath);
        AtomicBoolean pass = new AtomicBoolean(true); /*has to be AtomicBoolean because of using lambda expression*/

        for (Map.Entry<String, HashMap<String, Integer>> func : succ.entrySet())
        {
            func.getValue().forEach(((String neighbour, Integer cost) -> {
                if(hDescMap.get(func.getKey())<= hDescMap.get(neighbour) + cost)
                {
                    System.out.print("[CONDITION]: [OK] ");
                }
                else
                {
                    System.out.print("[CONDITION]: [ERR] ");
                    pass.set(false);
                }
                StringBuilder part2 = new StringBuilder();
                part2.append("h(" + func.getKey()  + ") <= h(" + neighbour + ") + c: ");
                part2.append(Double.valueOf(hDescMap.get(func.getKey())));
                part2.append(" <= ");
                part2.append(hDescMap.get(neighbour) + " + " + cost + ".0");
                System.out.println(part2);

            }));
        }
        if(pass.get())
        {
            System.out.println("[CONCLUSION]: Heuristic is consistent.");
        }
        else
        {
            System.out.println(("[CONCLUSION]: Heuristic is not consistent."));
        }
    }
    public static void checkIfOptimistic(HashMap<String, HashMap<String, Integer>> succ, Path hpath, List<String> goalStates) throws FileNotFoundException
    {
        hDescMap = Parsers.readHeuristicDescData(hpath);
        boolean pass = true;

        List<String> allStates = new ArrayList<>(hDescMap.keySet());
        for (String s : allStates)
        {
            StringBuilder sb = new StringBuilder();
            double cost = UcsAlgorithm.UCS_checkOptimistic(s, succ, goalStates);
            if(hDescMap.get(s) <= cost)
            {
                sb.append("[CONDITION]: [OK] ");
            }
            else
            {
                sb.append("[CONDITION]: [ERR] ");
                pass = false;
            }

            sb.append("h("+s+") <= h*: ");
            sb.append(hDescMap.get(s) + " <= " + cost);
            /*[OK] h(Umag) <= h*: 31.0 <= 54.0*/
            System.out.println(sb);
        }
        if(pass)
        {
            System.out.println("[CONCLUSION]: Heuristic is optimistic.");
        }
        else
        {
            System.out.println(("[CONCLUSION]: Heuristic is not optimistic."));
        }
    }
}
