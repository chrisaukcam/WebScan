/**
 * 
 */
package webcrawler;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException; 

/**
 * @author 482086
 *
 */
public class ParseIt {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Document doc = null;
		
		SportScore ss = new SportScore();
		
		try {
		  doc = Jsoup.connect("http://www.daytondailynews.com/sports/").get();
		}
		catch (IOException e) {
			System.err.println("ParseIt Caught IOException: " + e.getMessage());
		}
		
		String title = doc.title();
		
		Element link = doc.select("a").first();
		String linkHref = link.attr("href");
		String linkText = link.text(); // "example""

		Elements links = doc.select("a[href]");
		
		System.out.println("Number of Links is: " + links.size());
		
		String text = doc.body().text(); 
	//	System.out.println ("text is: " + text );
		System.out.println(title);
	//	System.out.println(text);
		
		System.out.println(linkHref + " :: " + linkText);
		
	//	String html = "<html><head><title>First parse</title></head>"
	//			  + "<body><p>Parsed HTML into a doc.</p></body></html>";
	//			Document doc = Jsoup.parse(html);
		
		String content = doc.getElementsByTag("a").toString();
		
		System.out.println ("Link Content is: " + content);
		
	}

}
