package dataModels.widgetsModels.postItsModels;

public class PostItAction
{
    private String postItId;
    private String action;
    private String modification;

    public PostItAction(String postItId, String action, String modification)
    {
        this.postItId = postItId;
        this.action = action;
        this.modification = modification;
    }

    public boolean isActionDelete()
    {
       return this.action.equals("Delete");
    }

    public boolean isActionModify()
    {
        return this.action.equals("Modify");
    }

    public String getPostItId()
    {
        return postItId;
    }

    public String getModification()
    {
        return modification;
    }
}
