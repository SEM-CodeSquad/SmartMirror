package smartMirror.dataHandlers.widgetsDataHandlers.feed;

import java.util.LinkedList;

/**
 * @author Geoffrey Chen @copyright on 07/12/2016.
 */
class RSSFeed
{
    private final LinkedList<RSSMessage> entries = new LinkedList<>();
    private final String title;

    /**
     * Constructor
     *
     * @param title news title
     */
    RSSFeed(String title)
    {
        this.title = title;
    }

    /**
     * List Getter
     *
     * @return entries
     */

    LinkedList getList()
    {
        return entries;
    }

    /**
     * toString method
     *
     * @return string
     */
    @Override
    public String toString()
    {
        return "News from: [ " + title + " ]";
    }

}
