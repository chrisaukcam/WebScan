package webcrawler;

//import java.net.*;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.SyndFeedOutput;
import com.sun.syndication.io.XmlReader;
import chris_test.StringUtil;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
/*
 * Note: needs JDOM 1.* on path to run properly with Rome.
 */
public class TestURL {

   public static void main(String [] args) throws IOException, FeedException {
	// Feed header
	   SyndFeed feed = new SyndFeedImpl();
	   URL url = null;
	   try {
           url = new URL("http://dailyadvocate.com/feed");
       //    url.addRequestProperty("User-Agent", 
        //		   "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)"); 
        //   url
       } catch (MalformedURLException e) {
           throw new RuntimeException(e);
       } 
	   /* 
	   BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
       String sourceCode = "";
       String line;
	   */
	   
	   
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
	   
	   
	  
	   feed.setFeedType("rss_2.0");
	   feed.setTitle("Sample Feed");
	 //  feed.setLink("http://example.com/");
	   feed.setLink("http://dailyadvocate.com/feed");
	   
	   // Feed entries
	   @SuppressWarnings("unchecked")
	List<SyndEntry> entries = feed.getEntries();
       if (entries == null) {
           entries = new ArrayList<SyndEntry>();
       }
       
	//   List<SyndEntry> entries = new ArrayList<SyndEntry>();
	   feed.setEntries(entries);
	   SyndEntry entry = new SyndEntryImpl();
	   entry.setTitle("Entry #1");
	//   entry.setLink("http://example.com/post/1");
	   
	   /*
	      SyndFeedInput input = new SyndFeedInput();
          SyndFeed feed = input.build(new XmlReader(feedUrl));
          System.out.println(feed);
	    */
	   
	   entry.setLink("http://dailyadvocate.com/feed/");
	   SyndContent description = new SyndContentImpl();
	   description.setType("text/plain");
	   description.setValue("There is text in here.");
	   entry.setDescription(description);
	   entries.add(entry);
	 //  System.out.println("Feed to String: " + feed.toString());
	   // Write the feed to XML
	   StringWriter writer = new StringWriter();
	   new SyndFeedOutput().output(feed, writer);
	   
	   System.out.println(writer.toString());
   }
}