package chris_test;

import java.util.ArrayList;
import java.util.List;

public class IndexInt {
    /*** test harness to iterate thru list of strings and edit each item
     *        so that they line on the index of digits in the airframe
     * @param args
     */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
        List<String> airFrameList = new ArrayList<String>();
        
        airFrameList.add("F015A");
        airFrameList.add("F015B");
        airFrameList.add("F015C???");
        airFrameList.add("F015D?,?,?,?");
        airFrameList.add("F015E?");
        airFrameList.add("ZZ015E");
        airFrameList.add("GGG015");
        int maxSpace = 5;
        for (String temp : airFrameList) {
        	 
        	   int pos = temp.replaceFirst("^(\\D+).*$", "$1").length(); 
        	   StringBuilder output = new StringBuilder(temp);
        	   
        	   for(int i=1; i< (maxSpace - pos); i++){
        		   // *** prepend the space ***
        		   output.insert(0,"&nbsp;");
        	   }
        	   
               // *** Test to get count of occurrence of a character in a string (one liner) ***  
               int count = output.toString().length() - output.toString().replace("?", "").length();
        
        	   System.out.println("Question mark count is: " + count);
        	   System.out.println(output.toString());
        	   
        }	 /*** end for loop ***/    
		
	}        /*** end main ***/
		
}            /*** end class ***/
