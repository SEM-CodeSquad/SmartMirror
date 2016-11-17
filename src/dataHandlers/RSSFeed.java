package dataHandlers;

import java.util.LinkedList;

/**
 * Created by Geoffrey on 2016/11/4.
 */

public class RSSFeed {


    /*
     * Stores an RSSFeed's Title
     */

    final LinkedList<RSSMessage> entries = new LinkedList<>();
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
