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


public class RSSStAXParser {
    static final String ITEM = "item";
    static final String TITLE = "title";

    URL url;

    public RSSStAXParser(String feedUrl) {
        try {
            this.url = new URL(feedUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }


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
                            // event = eventReader.nextEvent();
                            // StartElement startElement = event.asStartElement();
                            // String qName = startElement.getName().getLocalPart();
                            // if(qName.equalsIgnoreCase("title")){
                            //     Title = getCharacterData(event, eventReader);
                            // }

                            break;
                        case TITLE:
                            Title = getCharacterData(event, eventReader);
                            break;
                    }
                } else if (event.isEndElement()) {
                    if (event.asEndElement().getName().getLocalPart() == (ITEM)) {
                        RSSMessage rssMsg = new RSSMessage();
                        rssMsg.setTitle(Title);
                        rssFeed.getList().add(rssMsg);
                        // event = eventReader.nextEvent();
                        continue;
                    }
                }
            }


        } catch (XMLStreamException e) {

        }
        return rssFeed;
    }


    private InputStream read() {
        try {
            return url.openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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






