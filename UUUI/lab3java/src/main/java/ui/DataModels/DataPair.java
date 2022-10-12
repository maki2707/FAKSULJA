package ui.DataModels;

import java.util.Objects;

/**
 * model of a single "cell" from csv file
 */
public class DataPair
{
    private String attribute;
    private String value;

    public DataPair(String attribute, String value)
    {
        this.attribute = attribute;
        this.value = value;
    }

    public String getAttribute()
    {
        return attribute;
    }

    public void setAttribute(String attribute)
    {
        this.attribute = attribute;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataPair dataPair = (DataPair) o;
        return attribute.equals(dataPair.attribute) && value.equals(dataPair.value);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(attribute, value);
    }


    @Override
    public String toString() {
        return "DataPair{" +
                "attribute='" + attribute + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
