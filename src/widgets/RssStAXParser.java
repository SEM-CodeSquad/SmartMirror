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




public class RssStAXParser {
    static final String ITEM = "item";
    static final String TITLE = "title";

    URL url;




    public RSSFeed RssStAXParser(String feedUrl)  {


        RSSFeed rssFeed = null;
        try {
            this.url = new URL(feedUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        boolean bTitle = false;

        try {
            boolean isFeedHeader = true;
            String Title ="";
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
                        rssFeed.getMessages().add(rssMsg);
                        event = eventReader.nextEvent();
                        continue;
                    }
                }
            }


            }catch(XMLStreamException e){
                throw new RuntimeException(e);
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


}






