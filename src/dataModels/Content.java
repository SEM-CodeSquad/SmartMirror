package dataModels;

public class Content
{
    private String key;
    private String value;

    public Content(String name, String value)
    {
        this.key = name;
        this.value = value;
    }

    public String getKey()
    {
        return key;
    }

    public String getValue()
    {
        return value;
    }
}
