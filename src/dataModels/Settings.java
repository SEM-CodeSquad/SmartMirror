package dataModels;

public class Settings
{
    private String key;
    private String value;

    public Settings(String name, String value)
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
