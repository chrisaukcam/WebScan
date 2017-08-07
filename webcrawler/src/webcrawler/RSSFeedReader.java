package webcrawler;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

//import de.vogella.rss.model.Feed;
//import de.vogella.rss.model.FeedMessage;

public class RSSFeedReader {
    static final String TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String CHANNEL = "channel";
    static final String LANGUAGE = "language";
    static final String COPYRIGHT = "copyright";
    static final String LINK = "link";
    static final String AUTHOR = "author";
    static final String ITEM = "item";
    static final String PUB_DATE = "pubDate";
    static final String GUID = "guid";

    URL url = null;

    public RSSFeedReader(String feedUrl) {
        try {
        	
            this.url = new URL(feedUrl);
            // test
         //   System.out.println("URL is " + url.toString());
         //   System.out.println("default port is " + url.getDefaultPort());
        } catch (MalformedURLException e) {
        	System.err.println("RSSFeedRead constructor died trying to create new URL.");
            throw new RuntimeException(e);
        }
    }

    public Feed readFeed() {
        Feed feed = null;
        try {
            boolean isFeedHeader = true;
            // Set header values intial to the empty string
            String description = "";
            String title = "";
            String link = "";
            String language = "";
            String copyright = "";
            String author = "";
            String pubdate = "";
            String guid = "";

            // First create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            InputStream in = read();
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    String localPart = event.asStartElement().getName().getLocalPart();
                    switch (localPart) {
                    case ITEM:
                        if (isFeedHeader) {
                            isFeedHeader = false;
                            feed = new Feed(title, link, description, language,copyright, pubdate);
                        }
                        event = eventReader.nextEvent();
                        break;
                    case TITLE:
                        title = getCharacterData(event, eventReader);
                        break;
                    case DESCRIPTION:
                        description = getCharacterData(event, eventReader);
                        break;
                    case LINK:
                        link = getCharacterData(event, eventReader);
                        break;
                    case GUID:
                        guid = getCharacterData(event, eventReader);
                        break;
                    case LANGUAGE:
                        language = getCharacterData(event, eventReader);
                        break;
                    case AUTHOR:
                        author = getCharacterData(event, eventReader);
                        break;
                    case PUB_DATE:
                        pubdate = getCharacterData(event, eventReader);
                        break;
                    case COPYRIGHT:
                        copyright = getCharacterData(event, eventReader);
                        break;
                    }
                } else if (event.isEndElement()) {
                    if (event.asEndElement().getName().getLocalPart() == (ITEM)) {
                        FeedMessage message = new FeedMessage();
                        message.setAuthor(author);
                        message.setDescription(description);
                        message.setGuid(guid);
                        message.setLink(link);
                        message.setTitle(title);
                        message.setSportScore();
                        feed.getMessages().add(message);
                        event = eventReader.nextEvent();
                        continue;
                    }
                }
            }
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
        return feed;
    }    // *** end readfeed ***

    private String getCharacterData(XMLEvent event, XMLEventReader eventReader)
            throws XMLStreamException {
        String result = "";
        event = eventReader.nextEvent();
        if (event instanceof Characters) {
            result = event.asCharacters().getData();
        }
        return result;
    }
    
    /*
    private setFeed(){
    	try{
	        // url = new URL(address);
		   URLConnection uc = url.openConnection();
		   // *** fool server to think we are actually using a browser ***
		   uc.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		  
	        SyndFeedInput input = new SyndFeedInput();
	        feed = input.build(new XmlReader(uc));
	       // ok = true;
	    } catch (Exception exc){
	        exc.printStackTrace();
	    }
	   
    }
*/
    private InputStream read() {
        try {
        //	URLConnection uc = null;
        	//URLConnection uc = new URLConnection();
        	// *** give a longer timeout in case of slow connection 1000 = 1 second ***
        	
        	
        	URLConnection uc = url.openConnection();
        	uc.setConnectTimeout(1000);
  		    // *** fool server to think we are actually using a browser ***
  		    uc.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
  		    return uc.getInputStream();
           //   return url.openStream();
            
           } catch (IOException e) {
        	  System.err.println("RSSFeedReader::read failure. " + e.getMessage());
              throw new RuntimeException(e);
           }
        
    }
}   //  *** end class RSSFeedReader ***
