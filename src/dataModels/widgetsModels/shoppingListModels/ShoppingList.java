package dataModels.widgetsModels.shoppingListModels;

import java.util.LinkedList;

/**
 * @author Pucci on 02/12/2016.
 */
public class ShoppingList
{
    private String color;
    private LinkedList<Item> itemLinkedList = new LinkedList<>();

    public void setItemList(String key, String item)
    {
        this.itemLinkedList.add(new Item(key, item));
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public LinkedList<Item> getItemLinkedList()
    {
        return itemLinkedList;
    }
}