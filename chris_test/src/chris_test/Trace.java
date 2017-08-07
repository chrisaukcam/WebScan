package chris_test;

//import java.io.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import org.apache.xerces.parsers.SAXParser;

/* A simple sax parser - verify that data is xml compliant */

public class Trace extends DefaultHandler {

  int indent;

  void printIndent() {
    for (int i=0; i<indent; i++) System.out.print("-");
  }

  public void startDocument() {
    System.out.println("start document");
  }

  public void endDocument() {
    System.out.println("end document");
  }

  public void startElement(String uri, String localName,
                           String qName, Attributes attributes) {
    printIndent();
    System.out.println("starting element: " + qName);
    indent++;
  }

  public void endElement(String uri, String localName, 
                         String qName) {
    indent--;
    printIndent();
    System.out.println("end element: " + qName);
  }

  public void ignorableWhitespace(char[] ch, int start, int length) {
    printIndent();
    System.out.println("whitespace, length " + length);
  }

  public void processingInstruction(String target, String data) {
    printIndent();
    System.out.println("processing instruction: " + target);
  }

  public void characters(char[] ch, int start, int length){
    printIndent();
    System.out.println("character data, length " + length);
  }

  public static void main(String[] args) {
    Trace t = new Trace();
    SAXParser p = new SAXParser();
    p.setContentHandler(t);
    try { p.parse(args[0]); } 
    catch (Exception e) {e.printStackTrace();}
  }
}
 


 

