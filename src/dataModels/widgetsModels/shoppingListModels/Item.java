package dataModels.widgetsModels.shoppingListModels;

public class Item
{
    private String key;
    private String item;

    Item(String key, String item)
    {
        this.key = key;
        this.item = item;
    }

    public String getKey()
    {
        return key;
    }

    public String getItem()
    {
        return item;
    }
}
