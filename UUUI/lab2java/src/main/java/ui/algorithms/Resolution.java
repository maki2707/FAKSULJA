package ui.algorithms;

import ui.models.Clause;
import ui.models.ClauseInput;
import ui.models.ClausePair;
import ui.models.Literal;

import java.util.*;

public class Resolution
{
    public static Boolean NIL;
    public static int index;

    /**
     * core method for the algorithm from the slides
     * @param clauses
     * @return
     */
    public static Boolean plResolution(ClauseInput clauses)
    {
        Clause goalClause = clauses.getGoalClause();
        List<Clause> allClauses = clauses.getClauses();
        List<Clause> SoS = new ArrayList<>();
        List<Clause> derivedClauses = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        NIL = false;

        allClauses = removeRedundantFromInitial(allClauses);
        allClauses.removeIf(Resolution::checkForTautology);

        index = 1;

        for(Clause c : allClauses)
        {
            System.out.println(index+ ". " + c);
            index++;
        }

        List<Clause> negatedGoalClause = negateClause(goalClause,index);
        for(Clause c : negatedGoalClause)
        {
            System.out.println(c.getIndex()+". " + c);
            SoS.add(c);
            index++;
        }

        SoS.removeIf(Resolution::checkForTautology);


        System.out.println("=============");

        while(true)
        {
            SoS.removeIf(Resolution::checkForTautology);
            allClauses.removeIf(Resolution::checkForTautology);

            List<ClausePair> clausePairList = selectClauses(allClauses, SoS);
            if (clausePairList.isEmpty()) {
                System.out.println("[CONCLUSION]: " + goalClause + " is unknown");
                return false;
            }

            for(ClausePair pair : clausePairList)
            {
                Clause c1 = pair.getFirst();
                Clause c2 = pair.getSecond();

                List<Clause> resolvents = plResolve(c1,c2);

                if (NIL)
                {
                    List<Literal> nilLiterals = new ArrayList<>();
                    nilLiterals.addAll(c1.getLiterals());
                    nilLiterals.addAll(c2.getLiterals());
                    Clause nil = new Clause(nilLiterals,50,c1,c2);
                    printTheRest(c1,c2);
                    System.out.println("[CONCLUSION]: " + goalClause + " is true");
                    System.out.println();
                    printBeautify(nil);
                    return true;

                }

                printCurrentResolvents(c1,c2,resolvents);
                resolvents.removeIf(Resolution::checkForTautology);

                for (Clause resolvent : resolvents)
                {
                    SoS.removeIf(c -> c.isSubsumedBy(resolvent));
                    allClauses.removeIf(c -> c.isSubsumedBy(resolvent));
                }
                SoS.addAll(resolvents);
                allClauses.addAll(resolvents);
            }
       }
    }

    /**
     * prints out the current resolvents
     * @param c1
     * @param c2
     * @param resolvents
     */
    private static void printCurrentResolvents(Clause c1, Clause c2, List<Clause> resolvents)
    {
        StringBuilder sb = new StringBuilder();
        for(Clause res : resolvents)
        {
            sb.append(res.getIndex()).append(". ").append(res).append(" (").append(c1.getIndex()).append(", ").append(c2.getIndex()).append(")\n");
        }
        System.out.print(sb);
    }

    /**
     * final output after NIL
     * @param c1
     * @param c2
     */
    private static void printTheRest(Clause c1, Clause c2)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(index).append(". NIL (").append(c2.getIndex()).append(", ").append(c1.getIndex()).append(")\n");
        System.out.print(sb);
        System.out.println("=============");
    }

    /**
     * method that resolves 2 given clauses
     * @param c1
     * @param c2
     * @return
     */
    private static List<Clause> plResolve(Clause c1, Clause c2)
    {
        HashSet<Clause>  resolvents = new HashSet<>();

        List<Literal> literalsC1 = c1.getLiterals();
        List<Literal> literalsC2 = c2.getLiterals();

        HashSet<Literal> literalsToRetain = new HashSet<>();
        List<Literal> literalsToDelete = new ArrayList<>();
        List<Literal> literalsForClause = new ArrayList<>();


        for (Literal l1 : literalsC1)
        {
            for (Literal l2 : literalsC2)
            {
                if (l1.equals(l2.getNegative()))
                {
                    literalsToDelete.add(l1);
                }
            }
        }

        for (Literal l : literalsToDelete)
        {
            literalsToRetain.addAll(literalsC1);
            literalsToRetain.addAll(literalsC2);
            literalsToRetain.remove(l);
            literalsToRetain.remove(l.getNegative());

            if(!literalsToRetain.isEmpty())
            {
                literalsForClause.addAll(literalsToRetain);
                if(!checkForTautology(new Clause(literalsForClause,index,c1,c2)))
                {
                    if(literalsForClause.isEmpty())
                    {
                        resolvents.add(new Clause(literalsForClause,index,c1,c2));
                    }
                    else
                    {
                        resolvents.add(new Clause(literalsForClause,index,c1,c2));
                    }

                    index = index + 1;
                    literalsToRetain.clear();
                }
                literalsToRetain.clear();
            }
        }
        resolvents.removeIf(Resolution::checkForTautology);

        if(resolvents.isEmpty())
        {
            NIL = true;
        }

        return new ArrayList<>(resolvents);
    }

    /**
     * method that select pairs of clauses for resolving
     * @param allClauses
     * @param sos
     * @return
     */
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
                        if(!pairList.contains(new ClausePair(clause, sosClause)))
                        {
                            pairList.add(new ClausePair(clause, sosClause));
                        }

                    }
                }
            }
            for (Clause sosClause2 : sos)
            {
                for(Literal l : sosClause2.getLiterals())
                {
                    if(sosClause.checkIfContainsLiteral(l.getNegative()))
                    {
                        if(!pairList.contains(new ClausePair(sosClause2, sosClause)))
                        {
                            pairList.add(new ClausePair(sosClause2, sosClause));
                        }
                    }
                }
            }
        }
        return pairList;
    }

    /**
     * method for negating a claues, used for negation of goal clauses
     * @param in
     * @param i
     * @return
     */
    public static List<Clause> negateClause(Clause in, int i)
    {
        List<Clause> newClauses = new ArrayList<>();

        for(Literal l : in.getLiterals())
        {
            List<Literal> newLiterals = new ArrayList<>();
            newLiterals.add(l.getNegative());
            newClauses.add(new Clause(newLiterals,i,in.getParent1(),in.getParent2()));
            i++;
        }
        return newClauses;
    }

    /**
     * util method that check for tautology in clause
     * @param c
     * @return
     */
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

    /**
     * method that removes redundany from a list of clauses
     * @param clauseList
     * @return
     */
    public static List<Clause> removeRedundantFromInitial(List<Clause> clauseList)
    {
        /*decided to use iterator beacuse when I tried using for loop it threw
          ConcurrentModificationException */

        Iterator<Clause> iterator = clauseList.iterator();
        while (iterator.hasNext())
        {
            Clause clause = iterator.next();
            for (Clause c : clauseList)
            {
                if (clause.getLiterals().containsAll(c.getLiterals()) && !(clause.equals(c)))
                {
                    iterator.remove();
                    break;
                }
            }
        }
        return clauseList;
    }




}
