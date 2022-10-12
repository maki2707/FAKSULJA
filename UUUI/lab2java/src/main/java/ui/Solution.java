package ui;

import ui.models.ClauseInput;
import ui.parsers.RegularParser;
import ui.algorithms.Resolution;
import ui.algorithms.CookingResolution;

import java.io.FileNotFoundException;

public class Solution
{

    public static void main(String[] args) throws FileNotFoundException
    {
        String algType = args[0];
        switch (algType)
        {
            case "resolution" ->
                    {
                        ClauseInput starter = RegularParser.parseResolution(args[1]);
                        Resolution.plResolution(starter);
                    }
            case "cooking" ->
                    {
                        CookingResolution.setUpCookBook(args[1], args[2]);
                    }
        }
    }
}

