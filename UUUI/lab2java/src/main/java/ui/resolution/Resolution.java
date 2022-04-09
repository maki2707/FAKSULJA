package ui.resolution;

import ui.models.Clause;
import ui.models.ClauseInput;
import ui.models.ClausePair;
import ui.models.Literal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Resolution
{
    public static boolean plResolution(ClauseInput clauses)
    {
        Clause goalClause = clauses.getGoalClause();
        List<Clause> allClauses = clauses.getClauses();
        List<Clause> SoS = new ArrayList<>();
        /*SoS.removeIf(Resolution::checkForTautology);*/
        allClauses.removeIf(Resolution::checkForTautology);
        int index = 1;
        for(Clause c : allClauses)
        {
            System.out.println(index+". " + c);
            index++;
        }
        List<Clause> negatedGoalClause = negateClause(goalClause,index);
        for(Clause c : negatedGoalClause)
        {
            System.out.println(c.getIndex()+". " + c);
            SoS.add(c);
            index++;
        }
        int z = 1;
        System.out.println("=============");
        while(z==1)
        {
            SoS.removeIf(Resolution::checkForTautology);
            allClauses.removeIf(Resolution::checkForTautology);

            List<ClausePair> clausePairList = selectClauses(allClauses, SoS);

            for(ClausePair pair : clausePairList)
            {
                Clause c1 = pair.getFirst();
                Clause c2 = pair.getSecond();
                List<Clause> resolvents = plResolve(c1,c2);
                for (Clause resolvent : resolvents)
                {
                    System.out.println(resolvent);
                }
                if (resolvents.isEmpty())
                {
                    System.out.println("mozda radi");
                    return true;
                }
                resolvents.removeIf(Resolution::checkForTautology);

                if (resolvents.isEmpty())
                {
                    System.out.println("nema rješenja");
                    z=0;
                    return false;

                }

                for (Clause resolvent : resolvents) {
                    System.out.println(resolvent);
                    allClauses.removeIf(c -> c.isSubsumedBy(resolvent));
                    SoS.removeIf(c -> c.isSubsumedBy(resolvent));
                }
                SoS.addAll(resolvents);
                allClauses.addAll(resolvents);
            }
        }
        return true;

    }

    private static List<ClausePair> selectClauses(List<Clause> allClauses, List<Clause> sos)
    {
        List<ClausePair> pairList = new ArrayList<>();
        for (Clause sosClause : sos)
        {
            for (Clause clause : allClauses)
            {
                for(Literal l : clause.getLiterals())
                {
                    if(sosClause.checkIfContainsLiteral(l.getNegative()))
                    {
                        pairList.add(new ClausePair(clause, sosClause));
                    }
                }
            }
        }
        return pairList;
    }

    private static List<Clause> plResolve(Clause c1, Clause c2)
    {
        List<Literal> literalsC1 = c1.getLiterals();
        List<Literal> literalsC2 = c2.getLiterals();
        List<Literal> literalsToDelete = new ArrayList<>();
        List<Literal> literals = new ArrayList<>();
        List<Clause> resolvents = new ArrayList<>();


        for(Literal literal1 : literalsC1)
        {
            for(Literal literal2 : literalsC2)
            {
                if(literal1.equals(literal2.getNegative()))
                {
                    literalsToDelete.add(literal1);
                }
            }
        }
        for (Literal l : literalsToDelete) {
            literals.addAll(literalsC1.stream()
                    .filter(li -> !li.equals(l))
                    .collect(Collectors.toSet()));
            literals.addAll(literalsC2.stream()
                    .filter(li -> !li.equals(l.getNegative()))
                    .collect(Collectors.toSet()));
            if (!literals.isEmpty()) {
                resolvents.add(new Clause(literals, 69));
                literals.clear();
            }
        }
        return resolvents;











    }

    public static List<Clause> negateClause(Clause in, int i)
    {
        List<Clause> newClauses = new ArrayList<>();

        for(Literal l : in.getLiterals())
        {
            List<Literal> newLiterals = new ArrayList<>();
            newLiterals.add(l.getNegative());
            newClauses.add(new Clause(newLiterals,i));
            i++;
        }
        return newClauses;
    }

    public static boolean checkForTautology(Clause c)
    {
        List<Literal> literals = c.getLiterals();
        for (Literal literal : literals)
        {
            if (literals.contains(literal.getNegative()))
            {
                return true;
            }
        }
        return false;
    }

    public static boolean checkSubsumedBy(Clause c1, Clause c2)
    {
        return c2.getLiterals().containsAll(c1.getLiterals());
    }



}
