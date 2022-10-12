package ui.DataModels;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;

/**
 * model of the whole data set that is being used
 */
public class DataSet
{
    private Set<String> attributes;
    private String goalAttribute;
    private final Map<String, Set<String>> attributeValues;
    private final Set<String> goalAttributeValues = new TreeSet<>();
    private List<String> attributesList = new ArrayList<>();
    private final List<List<DataPair>> allData = new ArrayList<>();

    public DataSet(Path path) throws FileNotFoundException
    {
        File f = new File(String.valueOf(path));
        Scanner sc = new Scanner(f);
        List<String> allLines = new ArrayList<>();

        while (sc.hasNextLine())
        {
            allLines.add(sc.nextLine());
        }

        this.attributeValues = new HashMap<>();

        parseAttributes(allLines.get(0));
        allLines.remove(0);
        parseAttributeValues(allLines);
        setAllData(allLines);
    }

    private void setAllData(List<String> lines)
    {
        for (String line : lines)
        {
            String[] valuesArray = line.split(",");
            List<DataPair> list = new ArrayList<>();
            for (int i = 0; i < valuesArray.length - 1; i++)
            {
                DataPair dp = new DataPair(attributesList.get(i), valuesArray[i]);
                list.add(dp);
            }
            list.add(new DataPair(goalAttribute, valuesArray[valuesArray.length - 1]));
            this.allData.add(list);
        }
    }

    private void parseAttributeValues(List<String> lines)
    {
        for (String line : lines)
        {
            String[] valuesArray = line.split(",");
            this.goalAttributeValues.add(valuesArray[valuesArray.length - 1]);
            valuesArray = Arrays.copyOf(valuesArray, valuesArray.length - 1);

            for (int i = 0; i < valuesArray.length; i++)
            {
                attributeValues.get(attributesList.get(i)).add(valuesArray[i]);
            }
        }

    }

    private void parseAttributes(String line)
    {
        String[] attributeArray = line.split(",");
        this.goalAttribute = attributeArray[attributeArray.length - 1];

        attributeArray = Arrays.copyOf(attributeArray, attributeArray.length - 1);


        this.attributes = new TreeSet<>(Arrays.asList(attributeArray));
        this.attributesList = Arrays.asList(attributeArray);

        for (String s : attributeArray)
        {
            attributeValues.put(s, new TreeSet<>());
        }
    }

    public Set<String> getAttributes()
    {
        return attributes;
    }

    public String getGoalAttribute()
    {
        return goalAttribute;
    }

    public Map<String, Set<String>> getAttributeValues()
    {
        return attributeValues;
    }

    public Set<String> getGoalAttributeValues()
    {
        return goalAttributeValues;
    }

    public List<List<DataPair>> getAllData()
    {
        return allData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataSet dataSet = (DataSet) o;
        return attributes.equals(dataSet.attributes) && goalAttribute.equals(dataSet.goalAttribute) && attributeValues.equals(dataSet.attributeValues) && goalAttributeValues.equals(dataSet.goalAttributeValues);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributes, goalAttribute, attributeValues, goalAttributeValues);
    }


}


