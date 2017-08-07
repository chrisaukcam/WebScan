package webcrawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import chris_test.Logger1;
public class RssCrawl {
	
	
	public static List<String> urlList = new ArrayList<String>();
	public static List<FeedMessage> allEntries = new ArrayList<FeedMessage>();
	
    public static void main(String[] args) {
    	
    	Logger1 log = new Logger1("/log/rss_crawl.log");
    	log.start();
    	getURLs();
    	
    	if (urlList.isEmpty()){
    		System.err.println("Error Unable to capture URLS.  Processing halts.");
    		System.exit(1);
    	}
    	
    	Feed feed = null;
    	
    	for (String tmpUrl : urlList ){
    		int ctr = 0;
    		
    		System.out.println("Now Checking url: " +tmpUrl );
    		RSSFeedReader parser = new RSSFeedReader(tmpUrl);
    		
    		// *** trap any errors so that we can keep processing if there is poorly formed xml in any one feed ***
    		try{
    			feed = parser.readFeed();
    		}catch( RuntimeException re) {
    			System.err.println("RssCrawl Error problems parsing url: " + tmpUrl + re.getMessage());
    			continue;
    		}catch(Exception e){
    			System.err.println("RssCrawl Error problems parsing url: " + tmpUrl + e.getMessage());
    			continue;
    		}
    		
        // test
     //   System.out.println(feed);
    		for (FeedMessage message : feed.getMessages()) {
    			ctr++;
    			if (message.getSportScore() > 4){
    				allEntries.add(message);
        	  // test
          //    System.out.println(message);
              log.writeln(message.toString());
    			}
    		}
    		
    		log.writeln("Message count is: " + ctr );
    	} 
    	
    	if (! allEntries.isEmpty()){
    		Collections.sort(allEntries, (p1, p2) -> p2.getSportScore() - p1.getSportScore());
    		for (FeedMessage message :allEntries){
    			System.out.println(message.toHtml());
    		}
    	}
    	else{
    		System.out.println("Warning no sports stories found.");
    	}
    	log.stop();
    }   // *** end main           ***
    
    /*** use the default file name ***/
    private static void  getURLs(){ 
    	getURLs("C:\\eclipse\\jee-neon\\workspace\\webcrawler\\src\\webcrawler\\resources\\rss_crawl_urls.txt");
    }
    
   public static void  getURLs(String inFileName){
	   File readFile = new File(inFileName);
		
		if(readFile.exists() && !readFile.isDirectory() && readFile.canRead()) { 
			try(BufferedReader br = new BufferedReader(new FileReader(inFileName))) {
			   
			    String line = br.readLine();

			    while (line != null) {
			    	urlList.add(line);
			        line = br.readLine();
			    }
			    
			} catch (FileNotFoundException e) {
			    System.err.println("RssCrawl::getURLS File not found: " + inFileName + " " +	e.getMessage());
			} catch (IOException e) {
				System.err.println("RssCrawl::getURLS File access problem: " + inFileName + " " +	e.getMessage());
			}
		}
		else{
			System.err.println("RssCrawl::getURLS file " + inFileName + " does not exist, is a directory, or cannot be accessed.");
		}
   }
      
    
}       // *** end class RssCrawl ***
