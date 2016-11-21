package dataHandlers;

import java.util.LinkedList;


public class RSSFeed {


    /*
     * Stores an RSSFeed's Title
     */

    private final LinkedList<RSSMessage> entries = new LinkedList<>();
    private final String title;

    RSSFeed(String title) {
        this.title = title;
    }

    /**
     * Title getter
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * List Getter
     *
     * @return entries
     */

    public LinkedList getList() {
        return entries;
    }

    /**
     * toString
     *
     * @return string
     */
    @Override
    public String toString() {
        return "News from: [ " + title + " ]";
    }

}
