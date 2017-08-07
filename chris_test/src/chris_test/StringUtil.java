package chris_test;

import java.util.LinkedList;
import java.util.List;

public class StringUtil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// test
		String testThis = "Hello";
		System.out.println(padRight(testThis,40) + "<< Pad Right");
		System.out.println("Pad Left >>" + padLeft(testThis,40));
		testThis = padLeft(testThis,40);
		System.out.println("String has " + countOccurrences(testThis," ") + " Spaces.");
		testThis = "Hello";
		System.out.println("Right Pad Char output is: " + padRightChar(testThis,10,'*'));
		System.out.println("Left Pad Char output is: " + padLeftChar(testThis,20,'+'));
		
		System.out.println("XML Test: " + cleanXML("<test>This is a test <tag>Now there are 5 spaces     </tag>" +
		                   "<? Processing instruction /?>" +
				           "             " + "<tag>Spaces in front of this tag.          </tag>\n" +
				           "</test>"
				          ));
		
	}
	
	// *** right pad with spaces ***
	public static String padRight(String s, int n) {
		 
		if(isEmpty(s) ){
			return s; 
		}
		
	    return String.format("%1$-" + n + "s", s);  
	}

	// *** left pad with spaces ***
	public static String padLeft(String s, int n) {
		
		if(isEmpty(s) ){
			return s; 
		}
		
	    return String.format("%1$" + n + "s", s);  
	}
	
	// System.out.println(String.format("%10s", "howto").replace(' ', '*'));
	// *** right pad with character ***
	public static String padRightChar (String s, int n, char c){
		
		if(isEmpty(s) || n < 1){
			return s;
		}
		
		return String.format("%" + n + "s", s).replace(' ', c);
	}
	
	 
	 public static String padLeftChar  (String s, int n, char c){
		 
		if(isEmpty(s) || n < 1){
				return s;
		}
		
	    return String.format("%-" + n + "s", s).replace(' ', c);
	 }
	
	// *** give count of number of occurrences string occurs within string ***
    public static int countOccurrences(String targetString, String searchString)
    {
  
        int count = 0;

        if ((targetString == null) || (targetString.length() == 0))
        {
            return 0;
        }

        if ((searchString == null) || (searchString.length() == 0))
        {
            return 0;
        }
       
        count = targetString.length() - targetString.replace(searchString, "").length();
  
        return count;
    }

    // *** is string empty ***
    public static boolean isEmpty(String inValue)
    {
        return (inValue == null) || (inValue.length() == 0);
    }
    
    // *** given a delimiter, convert a string to a list ***
    @SuppressWarnings("unchecked")
	public static List<String> toList(String inStringList, String inDelimiter)
    {
        int delta = 0;
        int initialPosition = 0;
        int finalPosition = 0;
        // element not used 
      //  String element = null;

        @SuppressWarnings("rawtypes")
		LinkedList<String> outList = new LinkedList();

        if ((inStringList == null)
         || (inStringList.length() == 0))
        {
            return outList;
        }

        if ((inDelimiter == null)
                    || (inDelimiter.length() == 0))
        {
            outList.add(inStringList);
            return outList;
        }

        delta = inDelimiter.length();

        finalPosition = inStringList.indexOf(inDelimiter);
        
        while (finalPosition != -1)
        {
         //   element = inStringList.substring(initialPosition, finalPosition);
            outList.add(inStringList.substring(initialPosition, finalPosition));
            initialPosition = finalPosition + delta;
            finalPosition = inStringList.indexOf(inDelimiter, initialPosition);
        }

        if (finalPosition < inStringList.length())
        {
            outList.add(inStringList.substring(initialPosition, inStringList.length()));
        }

        return outList;
        
    }   // *** end toList ***

    // *** encode characters that may be in a string *** 
    // *** make string xml compliant                 ***
    public static String escapeSimpleXML(String inputString)
    {
        String outputString = inputString;
        
        if (outputString == null || outputString.length()==0)
        {
            return inputString;
        }
        
        outputString = outputString.replace("&","&amp;");
        outputString = outputString.replace("<","&lt;");
        outputString = outputString.replace(">","&gt;");
        outputString = outputString.replace("\"","&quot;");
        outputString = outputString.replace("'","&apos;");
        
        return outputString;    
        
    }   // *** end escapeSimpleXML ***
    
    /***  Clean xml so that it can parsed - get rid of newlines and extra spaces ***/
    public static String cleanXML(String inputStr){
    	String retVal  = "";
    	
    	if(isEmpty(inputStr)){
    	  return inputStr;
    	}
    	
    	retVal = inputStr.trim();
    	
    	retVal = retVal.replaceAll("[\\t\\n\\r]+", "");
    	retVal = retVal.replaceAll(">\\s+<","><");
    	retVal = retVal.replaceAll("\\s+"," ");
    //	retVal = retVal.replaceAll("<\\\?.+\?>", "");
    	
    	return retVal;
    }
    
    
    
}    // *** end class StringUtil ***
