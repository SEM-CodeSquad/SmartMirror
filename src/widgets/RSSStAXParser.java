package widgets;

/**
 * Created by Geoffrey on 2016/11/3.
 */


import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.*;

/*
 *
 *
 */
public class RSSStAXParser {
    static final String ITEM = "item";
    static final String TITLE = "title";

    URL url;


    /**
     * constructor
     *
     * @param feedUrl
     */
    public RSSStAXParser(String feedUrl) {
        try {
            this.url = new URL(feedUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Parse the XML file from given URL and store the Source of the RSS Feed as well as the news title into the linked list
     *
     *
     * @return rssFeed
     */

    public RSSFeed RSSParser() {

        RSSFeed rssFeed = null;

        try {
            boolean isFeedHeader = true;
            String Title = "";

            XMLInputFactory factory = XMLInputFactory.newInstance();

            InputStream RSS = read();
            XMLEventReader eventReader = factory.createXMLEventReader(RSS);

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    String localPart = event.asStartElement().getName()
                            .getLocalPart();

                    switch (localPart) {


                        case ITEM:

                            if (isFeedHeader) {
                                isFeedHeader = false;
                                rssFeed = new RSSFeed(Title);
                            }
                            event = eventReader.nextEvent();
                            StartElement startElement1 = event.asStartElement();
                            String qName1 = startElement1.getName().getLocalPart();
                            if(qName1.equalsIgnoreCase("title")){
                                Title = getCharacterData(event, eventReader);
                            }

                            break;

                        case TITLE:

                            Title = getCharacterData(event, eventReader);
                            break;

                    }
                }


                else if (event.isEndElement()) {

                    if (event.asEndElement().getName().getLocalPart() == (ITEM)) {
                        RSSMessage rssMsg = new RSSMessage();
                        rssMsg.setTitle(Title);
                        rssFeed.getList().add(rssMsg);
                        event = eventReader.nextEvent();
                        continue;

                    }

                }
            }


        } catch (XMLStreamException e) {

        }
        return rssFeed;
    }

    /**
     * connect to the url and read the RSS feed
     *
     * @return an InputStream for reading from that connection (url.openStream())
     */

    private InputStream read() {
        try {
            return url.openStream();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     *
     * @param event
     * @param eventReader
     * @return result
     * @throws XMLStreamException
     */
    private String getCharacterData(XMLEvent event, XMLEventReader eventReader)
            throws XMLStreamException {
        String result = "";
        event = eventReader.nextEvent();
        if (event instanceof Characters) {
            result = event.asCharacters().getData();
        }
        return result;
    }

}






