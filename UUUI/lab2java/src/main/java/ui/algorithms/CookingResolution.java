package ui.algorithms;

import ui.models.Clause;
import ui.models.ClauseInput;
import ui.models.Command;
import ui.parsers.RegularParser;
import ui.parsers.UserInputParser;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class CookingResolution
{
    public static List<Clause> cookClauses;
    public static List<Command> commandList;

    /**
     * initializes the cook book
     * @param regularClausesFile
     * @param userInputFile
     * @throws FileNotFoundException
     */
    public static void setUpCookBook(String regularClausesFile, String userInputFile) throws FileNotFoundException
    {
        ClauseInput starter = RegularParser.parseResolution(regularClausesFile);
        cookClauses = starter.getClauses();
        commandList = UserInputParser.parseUserInput(userInputFile);
        cook(starter);
    }

    /**
     * method that deals with users commands
     * @param starter
     */
    public static void cook(ClauseInput starter)
    {
        System.out.println("Constructed with knowledge:");
        for(Clause c : cookClauses)
        {
            System.out.println(c);
        }
        System.out.println();
        for(Command command : commandList)
        {
            StringBuilder sb = new StringBuilder();

            sb.append("User's command: ");
            sb.append(command.getTargetClause());
            sb.append(" " + command.getType());
            System.out.println(sb);

            if(command.getType().equals("?"))
            {
                List<Clause> cookClauses_copy = new ArrayList<>(cookClauses);
                cookClauses_copy.add(starter.getGoalClause());

                ClauseInput starter_copy = new ClauseInput(cookClauses_copy,command.getTargetClause());

                Clause newTarget = command.getTargetClause();
                newTarget.setIndex(cookClauses_copy.size());

                starter_copy.setGoalClause(newTarget);

                System.out.println(starter_copy.getClauses());
                System.out.println(starter.getClauses());
                Resolution.plResolution(starter_copy);
            }
            else if(command.getType().equals("+"))
            {
                cookClauses.add(command.getTargetClause());
                starter.setClauses(cookClauses);
                System.out.println("Added " + command.getTargetClause());
            }
            else if(command.getType().equals("-"))
            {
                cookClauses.remove(command.getTargetClause());
                starter.setClauses(cookClauses);
                System.out.println("Removed " + command.getTargetClause());

            }
        }


    }


}
