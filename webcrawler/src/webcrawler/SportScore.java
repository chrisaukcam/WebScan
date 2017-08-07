package webcrawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import chris_test.StringUtil;
import chris_test.InexactStringMatcher;

public class SportScore {

	int score =0;
	String titleTxt = null;
	String descTxt = null;
	List<String> sportyWordList = new ArrayList<String>();
	List<String> keyWordList = new ArrayList<String>();
	
	List<String> keySportsWords = new ArrayList<String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = -5189047032690673280L;

	{
	    add("football");
	    add("volleyball");
	    add("cross country");
	    add("tennis");
	    add("basketball");
	    add("wrestle");
	    add("track");
	    add("baseball");
	    add("swim");
	    add("bowl");
	    add("golf");
	}};
	
	String inFileName = "C:\\eclipse\\jee-neon\\workspace\\webcrawler\\src\\webcrawler\\resources\\sportywords.txt" ;
	
	/*** constructors ***/
	SportScore(){
		readSportWords();
	}
	
	
	SportScore (String titleTxt, String descTxt){
		this.titleTxt = titleTxt;
		this.descTxt = descTxt;
		readSportWords();
		// *** If the sports words are found in title then that is a strong indicator of a sports story so double value ***
		score += scanText(this.titleTxt) * 2;
		score += scanText(this.descTxt);
	}
	
	/**
	 * Constructor that takes title, description, and inputfile file name 
	 * @param titleTxt  title of document
	 * @param descTxt   description of document
	 * @param infile    filename of Sportyword file.
	 */
	SportScore (String titleTxt, String descTxt ,String infile){
		this.titleTxt = titleTxt;
		this.descTxt = descTxt;
		this.inFileName = infile;
		readSportWords();
		// *** If the sports words are found in title then that is a strong indicator of a sports story so double value ***
		score += scanText(this.titleTxt) * 2;
		score += scanText(this.descTxt);
	}
	
	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @return the titleTxt
	 */
	public String getTitleTxt() {
		return titleTxt;
	}

	/**
	 * @param titleTxt the titleTxt to set
	 */
	public void setTitleTxt(String titleTxt) {
		this.titleTxt = titleTxt;
	}

	/**
	 * @return the descTxt
	 */
	public String getDescTxt() {
		return descTxt;
	}

	/**
	 * @param descTxt the descTxt to set
	 */
	public void setDescTxt(String descTxt) {
		this.descTxt = descTxt;
	}
	
	public List<String> getSportKeyWords(){
		return keyWordList;
	}

	public int generateSportsScore(String txt){
		int retVal =0;
		
		this.score = scanText(txt );
		
		return retVal;
	}   // *** generateSportsScore ***
	
	
	public void setSportFileName(String inName){
				
		if (!StringUtil.isEmpty(inName)){
			File f = new File(inName);
			if(f.exists() && !f.isDirectory()) { 
			  inFileName = inName;
			}
			else{
				System.err.println("SportScore::setSportFileName input file: " + inName + " does not exist or cannot be accessed." );
			}
		}
		else{
			System.err.println("SportScore::setSportFileName input is null - using default file: " +inFileName);
		}
	}   // *** end setSportFileName ***
	
	/**
	 * Open the file that contains the sporty words and read it to build an arraylist of words. 
	 */
	public void readSportWords(){
		
        File readFile = new File(inFileName);
		
		if(readFile.exists() && !readFile.isDirectory() && readFile.canRead()) { 
			try(BufferedReader br = new BufferedReader(new FileReader(inFileName))) {
			   
			    String line = br.readLine();

			    while (! StringUtil.isEmpty(line)) {
			    	sportyWordList.add(line);
			        line = br.readLine();
			    }
			    
			} catch (FileNotFoundException e) {
			    System.err.println("SportScore::readSportWords File not found: " + inFileName + " " +	e.getMessage());
			} catch (IOException e) {
				System.err.println("SportScore::readSportWords File access problem: " + inFileName + " " +	e.getMessage());
			}
		}
		else{
			System.err.println("SportScore::readSportWords file " + inFileName + " does not exist, is a directory, or cannot be accessed.");
			
		}
	}          // *** readSportWords() ***
	
	
	/*
	 * go through list of sports words and find matches within the text.  
	 */
	public int scanText (String txt){
		int retVal = 0;
		
		if(StringUtil.isEmpty(txt)){
			  return retVal; 
		}
		
		InexactStringMatcher stringMatch = new InexactStringMatcher();
		
		for (String temp : sportyWordList) {
			
			   if(txt.startsWith(temp) || txt.endsWith(temp)){
				   retVal += 7; 
				   // *** if this is a key sports word, it needs to be added to html ***
				   if(keySportsWords.contains(temp.toLowerCase())){
					 retVal += 3;
					 if(! keyWordList.contains(temp.toLowerCase())){
				       keyWordList.add(temp.toLowerCase());
					 }
				   }
			   }
			   else if( txt.contains(temp)){
				   retVal += 5;
				   // *** if this is a key sports word, it needs to be added to html ***
				   if(keySportsWords.contains(temp.toLowerCase())){
					 retVal += 2;
					 if(! keyWordList.contains(temp.toLowerCase())){
				       keyWordList.add(temp.toLowerCase());
					 }
				   }
			   }
			   else{
				 double matchScore =  stringMatch.matchTargetScore(temp.toLowerCase(), txt.toLowerCase())  ;
				 if (matchScore < 5){
					 retVal += 3;
					 // test
					// System.out.println(" Strong match on word " + temp);
				 }
				 else if (matchScore < 10){
					 retVal += 2;
					 // test
				//	 System.out.println(" fuzzy match on word " + temp); 
				 }
				 else if(matchScore < 20){
					 retVal += 1;
					 // test
				//	 System.out.println(" weak match on word " + temp); 
				 }
			  }	     
		}

		return retVal;
	}   // *** end scanText ***
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        SportScore testScore = new SportScore();
		
		testScore.generateSportsScore("This is a sporty football and baseball story with a high score.");
		System.out.println("testScore for sporty text is: " + testScore.getScore());
		
		
		testScore.generateSportsScore("Football has been very very good to me.");
		System.out.println("testScore for sporty text is: " + testScore.getScore());
		
		testScore.generateSportsScore("Lots of daffodils and roses at the flower show today and tomorrow.");
		System.out.println("testScore for NO sporty text is: " + testScore.getScore());
		
		testScore.generateSportsScore("Lots of footballs and basketballs at the sporting show today.");
		System.out.println("testScore for somewhat sporty text is: " + testScore.getScore());
		
	}
	
	
	

}
