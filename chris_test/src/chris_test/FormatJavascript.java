package chris_test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/******************************************************************************
 *  edit javascript so that it can inserted into a java servlet 
 * parameter - one parameter, the input file name - at this time /temp/moveBar.js
 * @author ctilton3
 * 
 ******************************************************************************/

public class FormatJavascript {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		if (args.length==0){
		    System.out.println("Error- FormatJavascript requires one input parameter - name of file with javascript.");
		    System.exit(1); 
		}
		
		String fileName=args[0];
		 
		 List<String> records = new ArrayList<String>();
		  try
		  {
		    BufferedReader reader = new BufferedReader(new FileReader(fileName));
		    String line;
		    while ((line = reader.readLine()) != null)
		    {
		    //	String subChar = "\"";
		    	line = line.replaceAll("\"", "\\\\\"");
		    	line = "    +  \"" + line + "\\n\"";
		    	records.add(line);
		    }
		    
		    reader.close();
		 
		  }
		  catch (Exception e)
		  {
		    System.err.format("Exception occurred trying to read '%s'.", fileName);
		    e.printStackTrace();
		  }	
		  
		  BufferedWriter out = null;
		  try  
		  {
		      FileWriter fstream = new FileWriter("/temp/formatted_js.txt", false); //true tells to append data.
		      out = new BufferedWriter(fstream);
		      for (String s : records){
		        System.out.print(s + " ");
		        System.out.println();
		      
		        out.write("\n"+s);
		      }
		  }
		  catch (IOException e)
		  {
		      System.err.println("Error: " + e.getMessage());
		  }
		  finally
		  {
		      if(out != null) {
		    	  try{
		          out.close();
		    	  }
		    	  catch(IOException e)
		    	  {
		    		  System.err.println("Error cannot close file: " + e.getMessage());
		    	  }
		      }
		  } 
		  
		  
		 
	}	// *** end main ***

}		// *** FormatJavascript ***
