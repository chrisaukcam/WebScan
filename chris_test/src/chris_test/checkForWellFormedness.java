package chris_test;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
/*****************************************************
 * methods to Verify that an xml doc is well formed  with sax
 * @param File - name of xml file to be parsed.
 *****************************************************/

final public class checkForWellFormedness {
	
public void checkFile (File file)
{

   SAXParser saxParser = null;
   DefaultHandler dh = null;
   
   // init parser
   try {
      SAXParserFactory spfactory = SAXParserFactory.newInstance();
      saxParser = spfactory.newSAXParser();
      dh = new DefaultHandler();
   }
   catch(Exception e) {
      System.out.println("Cannot initialize SAX parser.");
      e.printStackTrace();
   }
   // parse the XML document using SAX parser
   try {
      saxParser.parse(file,dh); // SAXException, IOException
   }
   catch(SAXException se) { // (*)
      // only invoked in case of fatalError()
      // what if error() occur? Is the XML document well-formed?
      System.out.println("Document is not well-formed.");
      se.printStackTrace(); 
   }
   catch(IOException ioe) {
      System.out.println("Cannot read file.");
      ioe.printStackTrace();
   }
}

}
