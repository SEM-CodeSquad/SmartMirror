package dataHandlers;

/*
 * Store the information within the RSS message
 * in this case, the title of the news
 */
class RSSMessage {

    private String title;

    /**
     * item title getter
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * item title setter
     *
     * @param title
     */
    void setTitle(String title) {
        this.title = title;
    }

    /**
     * toString
     *
     * @return string
     */

    @Override
    public String toString() {
        return title;
    }
}