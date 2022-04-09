package ui;

import ui.models.Clause;
import ui.models.ClauseInput;
import ui.parsers.FileParser;
import ui.resolution.Resolution;

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
                        ClauseInput starter = FileParser.parseResolution(args[1]);
                        Resolution.plResolution(starter);
                    }
        }
    }
}

