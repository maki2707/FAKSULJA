package ui;

import ui.Algorithms.ID3Tree;
import ui.DataModels.DataSet;

import java.io.FileNotFoundException;
import java.nio.file.Path;

public class Solution
{
    private static Integer depthLimit = null;
    public static void main(String[] args) throws FileNotFoundException
    {
        String trainCSVFile = args[0];
        String testCSVFile = args[1];
        if(args.length == 3)
        {
            depthLimit = Integer.valueOf(args[2]);
        }
        DataSet trainDataSet = new DataSet(Path.of(trainCSVFile));
        DataSet testDataSet = new DataSet(Path.of(testCSVFile));

        ID3Tree tree = new ID3Tree(depthLimit);
        tree.fit(trainDataSet);
        tree.predict(testDataSet);
    }
}
