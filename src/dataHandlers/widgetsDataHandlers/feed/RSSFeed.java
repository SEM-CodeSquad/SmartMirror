package dataHandlers.widgetsDataHandlers.feed;

import java.util.LinkedList;


class RSSFeed {
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

    LinkedList getList() {
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
