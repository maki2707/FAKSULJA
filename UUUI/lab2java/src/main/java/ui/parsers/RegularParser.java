package ui.parsers;

import ui.models.Clause;
import ui.models.ClauseInput;
import ui.models.Literal;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class RegularParser
{
    public static ClauseInput starter = new ClauseInput();
    public static List<Clause> allClauses = new ArrayList<>();

    public static ClauseInput parseResolution(String path) throws FileNotFoundException
    {
        File f = new File(path);
        Scanner sc = new Scanner(f);
        List<String> allLines = new ArrayList<>();

        while(sc.hasNextLine())
        {
            String cLine = sc.nextLine();
            if(!cLine.startsWith("#"))
            {
                allLines.add(cLine.trim());
            }
        }

        int index = 1;
        for (String line : allLines)
        {
            List<String> toBeLiterals = Arrays.asList(line.toLowerCase(Locale.ROOT).split(" v "));
            List<Literal> literals = new ArrayList<>();
            for(String toBeL : toBeLiterals)
            {
                Literal l;
                if(toBeL.startsWith("~"))
                {
                    l = new Literal(toBeL.substring(1).toLowerCase(Locale.ROOT), false);
                }
                else
                {
                    l = new Literal(toBeL.toLowerCase(Locale.ROOT), true);
                }
                literals.add(l);
            }
            allClauses.add(new Clause(literals,index,null,null));
            index++;
        }
        starter.setGoalClause(allClauses.get(allClauses.size()-1));
        allClauses.remove(allClauses.size()-1);
        starter.setClauses(allClauses);
        return starter;
    }

}
