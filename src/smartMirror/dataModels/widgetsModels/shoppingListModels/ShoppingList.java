package smartMirror.dataModels.widgetsModels.shoppingListModels;

import java.util.LinkedList;

/**
 * @author Pucci on 02/12/2016.
 */
public class ShoppingList
{
    private LinkedList<Item> itemLinkedList = new LinkedList<>();

    public void setItemList(String key, String item)
    {
        this.itemLinkedList.add(new Item(key, item));
    }

    public LinkedList<Item> getItemLinkedList()
    {
        return itemLinkedList;
    }
}