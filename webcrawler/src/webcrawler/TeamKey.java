package webcrawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chris_test.InexactStringMatcher;
import chris_test.StringUtil;

public class TeamKey {
  
	private String inFileName = "C:\\eclipse\\jee-neon\\workspace\\webcrawler\\src\\webcrawler\\resources\\team.txt";
	private String teamLevel = "";
	private List<TeamData> teamDataList = new ArrayList<TeamData>();
	
	private Map<String,String> teamLvlMap = new HashMap<String, String>(){
		/**
		 * required serial version ID.
		 */
		private static final long serialVersionUID = -6671627981316065899L;

	    {
	       put("YOUTH","Youth");
	       put("HS","High School");
	       put("COL","College");
	       put("PRO","Pro");
	       }};
	
	
	/**
	 * constructor
	 */
	TeamKey(){
		readTeamFile();
	}
	
	
	/**
	 * Allow calling class to define the input file name for team data.
	 */
	public void readTeamFile(String infile){
		if(! StringUtil.isEmpty(infile)){
			inFileName = infile;
		}
		readTeamFile();
	}
	/**
	 * Open the file that contains team data and read it to build an array of team data objects. 
	 */
	public void readTeamFile(){
		
        File readFile = new File(inFileName);
		
		if(readFile.exists() && !readFile.isDirectory() && readFile.canRead()) { 
			try(BufferedReader br = new BufferedReader(new FileReader(inFileName))) {
			   
			    String line = br.readLine();

			    while (! StringUtil.isEmpty(line)) {
			    	
			    	String[] array = line.split(","); 
			    	
			    	// *** get list of nicknames ***
			    	List<String> tmpList = new ArrayList<String>();
			    	if( ! StringUtil.isEmpty(array[3])){
			    		String[] tmparray = array[3].split("::");
			    		for(String tmp : tmparray){
			    			tmpList.add(tmp);
			    		}
			    	}
			    	
			    	if (array.length == 6){
			    	  teamDataList.add(new TeamData(array[0],array[1],array[2],tmpList,array[4],array[5]));
			    	}
			    	else{
			    		System.err.println("TeamKey::readTeamFile() input array is wrong size.");
			    		if (array.length > 0){
			    			System.err.println("TeamKey::readTeamFile() length is: " + array.length + " text is:" + array[0]);
			    		}
			    	}
			    	//teamWordList.add(line);
			        line = br.readLine();
			    }
			    
			} catch (FileNotFoundException e) {
			    System.err.println("TeamKey::readTeamFile File not found: " + inFileName + " " + e.getMessage());
			} catch (IOException e) {
				System.err.println("TeamKey::readTeamFile File access problem: " + inFileName + " " + e.getMessage());
			}
		}
		else{
			System.err.println("TeamKey::readTeamFile file " + inFileName + " does not exist, is a directory, or cannot be accessed.");
			
		}
	}          // *** readSportWords() ***
	
	
	/**
	 *  Scan through story text and generate a list of matched keywords
	 * @param storyText = text from title or description of story.
	 * @return   List of team keywords that are a match
	 */
	public List<String> generateTeamKeys(String storyText){
		
         InexactStringMatcher stringMatch = new InexactStringMatcher();
         List<String> teamKeyList = new ArrayList<String>();
         teamLevel = "";
         // *** check each team object to see if there is a match ***
		 for (TeamData tempTeam : teamDataList) {
			
			   List<String> tmpkeyList = new ArrayList<String>(); 	
			   tmpkeyList = tempTeam.getMasterList();
			   
			   if(tmpkeyList.isEmpty()){
				  System.err.println("TeamKey::generateTeamKeys - NO keys list for team: "+ tempTeam.getMascot() + " " + tempTeam.getCity()); 
			   }
			   
			   for(String temp : tmpkeyList){
				  
			     if( storyText.contains(temp)){
			    	 if (!teamKeyList.contains(temp) ){
					   teamKeyList.add(temp);
					   teamLevel = teamLvlMap.get(tempTeam.getLevel().toString());
			    	 }
					 // test
					 System.out.println("TeamKey CONTAINS "  + temp);
			     }
			     else{
				   double matchScore =  stringMatch.matchTargetScore(temp, storyText.toLowerCase())  ;
				   // test
				 //  System.out.println("TeamKey " + temp + " match score is: " + matchScore);
				   if (matchScore < 12){
					   if (!teamKeyList.contains(temp) ){
					     teamKeyList.add(temp);
					     teamLevel = teamLvlMap.get(tempTeam.getLevel().toString());
					   }
					   // test
					 //  System.out.println(" Strong match on word " + temp );
				   }
			     }
			   }
		 }
		return teamKeyList;
	}   // *** generateTeamKeys ***
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> teamKeyList = new ArrayList<String>();
		TeamKey tk = new TeamKey();
		
		teamKeyList = tk.generateTeamKeys("This is a Sports Story about the Twin Valley South Panthers in West Alexandria");
		System.out.println("Strong Match keys");
		for (String tmp:teamKeyList){
			System.out.println("key: " + tmp );
		}
		System.out.println("Level: " + tk.getTeamLevel());
		
		List<String> teamKeyList2 = new ArrayList<String>();
		teamKeyList2 = tk.generateTeamKeys("This is a Sports Story about the Twin Valley Panther in West Alex");
		System.out.println("Weak Match keys");
		for (String tmp:teamKeyList2){
			System.out.println("key: " + tmp );
		}
		System.out.println("Level: " + tk.getTeamLevel());
		
		List<String> teamKeyList3 = new ArrayList<String>();
		teamKeyList3 = tk.generateTeamKeys("This is a story about sewing napkins and table cloth patterns");
		System.out.println("No Match keys");
		for (String tmp:teamKeyList3){
			System.out.println("key: " + tmp );
		}
		System.out.println("Level: " + tk.getTeamLevel());
	}   // *** end of main ***


	/**
	 * @return the inFileName
	 */
	public String getInFileName() {
		return inFileName;
	}


	/**
	 * @param inFileName the inFileName to set
	 */
	public void setInFileName(String inFileName) {
		this.inFileName = inFileName;
	}


	/**
	 * @return the teamLevel
	 */
	public String getTeamLevel() {
		return teamLevel;
	}

	
	/*** Did we capture a team level? ***/
	public boolean hasTeamLevel(){
		boolean retVal = false;
		if(!StringUtil.isEmpty(teamLevel)){
			retVal = true;
		}
		return retVal;
	}

	/**
	 * @param teamLevel the teamLevel to set
	 */
	public void setTeamLevel(String teamLevel) {
		this.teamLevel = teamLevel;
	}

}
