package chris_test;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;


public class QuickLog {

	public static File logfile =new File("C://log/chris.log");
		
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
	public static void append (String entry){
		if(! logfile.exists()){
			try{
		 	   logfile.createNewFile();
			}catch (Exception e){
				System.err.println("QuickLog::append unable to create file " + logfile.getName());
			}
		}
		
		if (entry != null && entry.length()>0){
			
			FileWriter fw;
			try {
				fw = new FileWriter(logfile,true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(entry);
				printOutput(bw,entry,false);
			//	printOutput(bw,this.className,false);
				//Closing BufferedWriter Stream
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("QuickLog::append " + e.getMessage());
			}
		}
		
	}
	
  	    /**
    	 * Helper function to output text.
   	     * @param outputString  string to be written
    	 * @param lineFeed		add a line feed
    	 */
    	private static void printOutput(BufferedWriter bw,   String outputString, boolean lineFeed)
    	{
    	  
   		   try {
			bw.write(outputString);
		 
			//System.out.println(outputString);
    		if(lineFeed)
    		{
    			bw.write(System.getProperty("line.separator"));
    		}
   		   }
    		catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}

	
	
	
}
