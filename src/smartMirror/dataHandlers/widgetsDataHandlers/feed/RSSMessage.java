package smartMirror.dataHandlers.widgetsDataHandlers.feed;

/**
 * @author Geofrrey Chen
 *         Class that stores the information within the RSS message
 *         in this case, the title of the news
 */
class RSSMessage
{

    private String title;

    /**
     * item title setter
     *
     * @param title t
     */
    void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * toString
     *
     * @return string
     */
    @Override
    public String toString()
    {
        return title;
    }
}