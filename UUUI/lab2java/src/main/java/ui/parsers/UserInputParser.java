package ui.parsers;

import ui.models.Clause;
import ui.models.Command;
import ui.models.Literal;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInputParser
{
    /**
     * parses file that are "users input" type
     * @param path
     * @return
     * @throws FileNotFoundException
     */
    public static List<Command> parseUserInput(String path) throws FileNotFoundException
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
        List<Command> commandList = new ArrayList<>();
        for (String line : allLines)
        {
            List<Literal> l = new ArrayList<>();
            Literal lit;
            int i = line.lastIndexOf(" ");
            for(String s: line.substring(0, i).split(" v "))
            {
                if(s.startsWith("~"))
                {
                    lit = new Literal(s.substring(1).toLowerCase(), false);
                }
                else
                {
                    lit = new Literal(s.toLowerCase(), true);
                }
                l.add(lit);
            }

            commandList.add(new Command(line.substring(i+1), new Clause(l, 0, null, null)));
        }
        return commandList;
    }
}
