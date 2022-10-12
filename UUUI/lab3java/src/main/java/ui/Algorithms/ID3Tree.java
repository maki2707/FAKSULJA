package ui.Algorithms;

import ui.DataModels.DataPair;
import ui.DataModels.DataSet;
import ui.Nodes.LeafNode;
import ui.Nodes.Node;

import java.util.*;

public class ID3Tree {
    private final Integer depthLimit;
    private Node root;
    private DataSet trainDataSet;


    public ID3Tree() {
        this.depthLimit = null;
    }

    public ID3Tree(Integer depthLimit) {
        this.depthLimit = depthLimit;
    }

    /**
     * fit method that trains the tree
     * @param dataSet
     */
    public void fit(DataSet dataSet) {
        trainDataSet = dataSet;
        root = id3_exe(dataSet.getAllData(),dataSet.getAllData(), dataSet.getAttributes(), trainDataSet.getGoalAttribute(), this.depthLimit);
        root.print_exe(root);
    }

    /**
     * method that make the decision tree, return node, follows the algorithm from the ppt
     * @param data
     * @param parentData
     * @param attributes > current attributes
     * @param y > goal attribute
     * @param depthL
     * @return
     */
    private Node id3_exe(List<List<DataPair>> data, List<List<DataPair>> parentData, Set<String> attributes, String y, Integer depthL  )
    {
        if( depthL!= null)
        {
            if(depthL == 0)
            {
                String returnValue = null;
                if(data == null || data.isEmpty())
                {
                    returnValue = getMostCommonValue(parentData, y);
                }
                else
                {
                    returnValue = getMostCommonValue(data,y);
                }
                return new LeafNode(returnValue);
            }
        }

        if (data.isEmpty())
        {
            return new LeafNode(getMostCommonValue(parentData, y));
        }

        String newMCV = getMostCommonValue(data,y);
        List<List<DataPair>> newData = filterData(data,newMCV,y);

        if(newData.size() == data.size() || attributes.isEmpty())
        {
            return new LeafNode(newMCV);
        }

        String newMDA = mostDiscriminativeAttribute(data, attributes);
        Node newNode = new Node(newMDA);
        Set<String> newAttributes = new HashSet<>(attributes);
        newAttributes.remove(newMDA);

        for(String a : trainDataSet.getAttributeValues().get(newMDA))
        {
           List<List<DataPair>> nData = filterData(data, a, newMDA);
           Integer newDepth = computeDepth(depthL);

           Node n = id3_exe(nData, data, newAttributes, y, newDepth );
           newNode.addToChildren(a, n);
        }
        return newNode;
    }

    /**
     * computes depth if depth exists
     * @param d
     * @return
     */
    private Integer computeDepth(Integer d)
    {
        if(d != null)
        {
            return d-1;
        }
        else
        {
            return null;
        }
    }

    /**
     * util method for filtering data
     * @param oldData
     * @param target
     * @param attribute
     * @return
     */
    private List<List<DataPair>> filterData( List<List<DataPair>> oldData, String target, String attribute)
    {
        List<List<DataPair>> newD = new ArrayList<>();
        List<List<DataPair>> tempData = new ArrayList<>(oldData);
        for(List<DataPair> list : tempData)
        {
            List<DataPair> partList = new ArrayList<>(list);
            boolean f = false;
            for(DataPair dp : list)
            {
                if(dp.getAttribute().equals(attribute))
                {
                    if(dp.getValue().equals(target))
                    {
                        f = true;
                    }
                }
            }
            if(f)
            {
                newD.add(partList);
            }
        }
        return newD;
    }

    /**
     * method that returnS the most discriminative attribute from the given data
     * @param data
     * @param attributes
     * @return
     */
    private String mostDiscriminativeAttribute(List<List<DataPair>> data, Set<String> attributes)
    {
        String returnVal = "";
        double maxIG = 0;

        for(String attribute : attributes)
        {
            double tempIG = getInformationGain(data, attribute);
            if(returnVal.equals("") || tempIG > maxIG || (tempIG == maxIG && returnVal.compareTo(attribute) > 0))
            {
                returnVal = attribute;
                maxIG = tempIG;
            }
            System.out.print("IG(" + attribute + ")=");
            String.format("%.3g", tempIG);
            System.out.print(" ");
        }
        System.out.println();
        return returnVal;
    }

    /**
     * method that calculates IG
     * @param data
     * @param attribute
     * @return
     */
    private double getInformationGain(List<List<DataPair>> data, String attribute)
    {
        double resultIG = getEntropy(data, trainDataSet.getGoalAttribute());
        List<List<DataPair>> newData = null;
        double n = data.size();
        for(String value : trainDataSet.getAttributeValues().get(attribute))
        {
            newData = filterData(data, value, attribute);

            double tempEntropy = getEntropy(newData, trainDataSet.getGoalAttribute());

            resultIG = resultIG - (newData.size() / n * tempEntropy);
            newData = null;
        }

        return resultIG;
    }

    /**
     * method that calculates entropy
     * @param data
     * @param g
     * @return
     */
    private double getEntropy(List<List<DataPair>> data, String g)
    {
        double entropy = 0;
        Map<String,Integer> tempMap = new HashMap<>();
        for(List<DataPair> list : data)
        {
            for(DataPair dp : list)
            {
                if(dp.getAttribute().equals(g))
                {
                    tempMap.compute(dp.getValue(), (key, val) -> val != null ? val + 1 : 1);
                }
            }
        }
        for(Integer n : tempMap.values())
        {
            double temp = (double) n/data.size();
            entropy = entropy - ((temp) * (double)(Math.log(temp) / Math.log(2)));
        }
        return entropy;
    }

    /**
     * method that returns most common value from given data
     * @param allData
     * @param t
     * @return
     */
    public String getMostCommonValue(List<List<DataPair>> allData, String t)
    {
        String returnVal = "";
        Map<String,Integer> tempMap = new HashMap<>();
        for(List<DataPair> list : allData)
        {
            for(DataPair dp : list)
            {
                if(dp.getAttribute().equals(t))
                {
                    tempMap.compute(dp.getValue(), (key, val) -> val == null ? 1 : val + 1);
                }
            }
        }

        int maxCount = 0;
        for(Map.Entry<String, Integer> entry : tempMap.entrySet())
        {
            if(entry.getValue() > maxCount || returnVal.equals("") || (entry.getValue() == maxCount && returnVal.compareTo(entry.getKey()) > 0))
            {
                maxCount = entry.getValue();
                returnVal = entry.getKey();
            }
        }
        return returnVal;
    }

    /**
     *
     * @param data
     */
    public void predict(DataSet data)
    {
        if(root == null)
        {
            return;
        }

        Set<String> allDistinctTargetValues = new TreeSet<>(data.getGoalAttributeValues());
        allDistinctTargetValues.addAll(trainDataSet.getGoalAttributeValues());
        List<String> targets = new ArrayList<>(allDistinctTargetValues);

        int[][] confMatrix = new int[targets.size()][targets.size()];
        int correctPredictions = 0;
        StringBuilder sb = new StringBuilder();
        int dataSize = data.getAllData().size();

        for(List<DataPair> list : data.getAllData())
        {
            String currentPrediction = makePrediction(root,list, trainDataSet);
            sb.append(currentPrediction + " ");
            String realValue = null;

            for(DataPair dp : list)
            {
                if(dp.getAttribute().equals(trainDataSet.getGoalAttribute()))
                {
                    realValue = dp.getValue();
                }
            }
            assert realValue != null;
            if(realValue.equals(currentPrediction))
            {
                correctPredictions = correctPredictions + 1;
            }

            confMatrix[targets.indexOf(realValue)][targets.indexOf(currentPrediction)]++;
        }

        printPredictions(sb);
        printAccuracy(correctPredictions,dataSize);
        printConfusionMatrix(confMatrix, targets.size());
    }


    /**
     * method returns prediction for given row
     * @param node
     * @param list
     * @param trainDataSet
     * @return
     */
    private String makePrediction(Node node, List<DataPair> list, DataSet trainDataSet)
    {
        if(node.getAttribute() == null)
        {
            return node.getValue();
        }
        String value = null;
        for(DataPair dp : list)
        {
            if(dp.getAttribute().equals(node.getAttribute()))
            {
                value = dp.getValue();
            }
        }

        Node newNode = node.getChildren().get(value);
        if(newNode == null)
        {
            return getMostCommonValue(trainDataSet.getAllData(), trainDataSet.getGoalAttribute());
        }
        return makePrediction(newNode, list, trainDataSet);
    }

    /**
     * util method for printing predictions
     * @param sb
     */
    private void printPredictions(StringBuilder sb)
    {
        System.out.print("[PREDICTIONS]: ");
        System.out.println(sb.deleteCharAt(sb.length()-1).toString());
    }

    /**
     * util method for printing accuracy
     * @param correctPredictions
     * @param datasize
     */
    private void printAccuracy(int correctPredictions, double datasize)
    {
        System.out.print("[ACCURACY]: ");
        System.out.format("%.5f", (double)correctPredictions / datasize);
        System.out.println();
    }

    /**
     * util method for printing confusion matrix
     * @param matrix
     * @param s
     */
    public void printConfusionMatrix(int[][] matrix, int s)
    {
        System.out.println("[CONFUSION_MATRIX]: ");
        StringBuilder sbMat = new StringBuilder();

        for(int i =0; i < s; i++ )
            for(int j =0; j < s; j++ )
            {
                sbMat.append(matrix[i][j]);
                if(j == s - 1)
                {
                    sbMat.append("\n");
                }
                else
                {
                    sbMat.append(" ");
                }
            }
        System.out.print(sbMat);
    }
}
