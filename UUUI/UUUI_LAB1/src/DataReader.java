import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class DataReader
{

    public static void main(String[] args) throws FileNotFoundException {
        MyNodes test = readData("data.txt");
        System.out.println(test.getNeighbours());
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

        //System.out.println(allLines);

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
}
