package chris_test;
  /**
    * InexactStringMatcher class sketch v2.
    * the lower the score, the better match.  Under 100 is decent match.
    * History:
    *   v1. used total of char proximity offsets for score.
    *   v2. uses the total of differences between successive char prox offsets,
    *     means that "aTrackName" will get low score for "zzzzzzzzzzzzaTackNme"
    *     which is desirable.
    *     Also, have realised that if one of the match strings is empty/close
    *     to empty, you may get falsely low scores (e.g. score=0 for
    *                                               empty string)
    *                                               
    *                                               
    *
    * Use freely, enjoy kittens
    *
    * @author Alex Hunsley
    */
 
  public class InexactStringMatcher
  {
       // hack for the moment - char proximity will have this
       // number for case where no char matches at all
       private static int NO_CHAR_MATCH = 65535;
       private static int A_HIGH_SCORE = 1000;
       private static int BEST_SCORE = 0;
       private static int DECENT_SCORE = 100;
       private static int CLOSE_SCORE = 10;
       private static int THIS_SCORE = NO_CHAR_MATCH ;
       
       private static boolean DEBUG = false;
 
       public int getMatchScore(String str1, String str2, String spaceChars) {
         str1 = removeChars(str1, spaceChars).toLowerCase();
         str2 = removeChars(str2, spaceChars).toLowerCase();
         return (int) getMatchScore(str1, str2);
       }
 
       /**
        * returns the given string with all chars in charsToRemove
        * removed from it
        */
       public String removeChars(String str, String charsToRemove)
       {
        for (int charIndex = 0; charIndex < charsToRemove.length(); charIndex ++) {
           char charToRemove = charsToRemove.charAt(charIndex);
 
           StringBuffer buf = new StringBuffer();
 
      int charOccurencePos = -1;
      
      do {
        charOccurencePos = str.indexOf(charToRemove);
        if (charOccurencePos  == 0) {
          buf.append(str.substring(0, charOccurencePos));
          str = str.substring(charOccurencePos + 1,
          str.length());
        }
      } while (charOccurencePos  == 0);
 
      // now put remainder of string in buf
      buf.append(str);
      // set str to be our newly made string with possibly
      // chars removed
      str = buf.toString();
      }
      return str;
    }
 
       /**
        * See how closely two strings match each other
        * by checking for proximity of similar characters.
        * the lower the return value, the closer the match
        */
       public double getMatchScore(String str1, String str2) {
        if (str1 == null || str2 == null) {
          return A_HIGH_SCORE;
        }
        if (str1.equalsIgnoreCase(str2)) {
           return BEST_SCORE;
        }

        double scoreA = getOneWayMatchScore(str1, str2);
        double scoreB = getOneWayMatchScore(str2, str1);
 
        if (DEBUG){
        	System.out.println("InexactStringMatch::getMatchScore___scoreA="+scoreA
        			         + " scoreB="+scoreB);
        }
  // average the two scores
  // (could always take the minimum or maximum instead,
  // the average seem to work)
        
        return  ((scoreA + scoreB) / 2);
      
       }
 
       /**
        * See how closely two strings match each other
        * by checking for proximity of similar characters.
        * the lower the return value, the closer the match
        * We are searching the candidate to try and find a match for the target string
        */
       public double matchTargetScore(String target, String candidate) {
    	double scoreB = 0;
    	double scoreA = 0;
    	
        if (target == null || candidate == null) {
          return A_HIGH_SCORE;
        }
        
        if (target.equalsIgnoreCase(candidate)) {
           return BEST_SCORE;
        }
        
        // score when target string is found in candidate
        if(candidate.contains(target)){
          scoreA= 1;
        }
        else if (candidate.matches("[\\s._-]")){
        	scoreA=scoreSubStrings(candidate,target);
        }
        else{
        	scoreA +=3;
        	scoreA += getOneWayMatchScore(candidate.trim(), target.trim());
        }
        
        // Score when candidate string is found in target
        if(target.contains(candidate)){
        	scoreB = 2;
        }
        else if (target.matches("[._-]")){
        	scoreB=scoreSubStrings(target,candidate);
        }
        else{
        	scoreB +=3;
        	scoreB += getOneWayMatchScore(target.trim(),candidate.trim() );
        }
        
 /*
        if (DEBUG){
        	System.out.println("InexactStringMatch::getMatchScore___scoreA="+scoreA
        			         + " scoreB="+scoreB);
        }
        */
  // average the two scores
  // (could always take the minimum or maximum instead,
  // the average seem to work)
        
      if(scoreB > -1) { 
        return((scoreA + scoreB) / 2);
      //  return THIS_SCORE;
      }
      else{
        return scoreA;
      }
     //   return (int) ((scoreA + scoreB) / 2);
 }       
       
       
       
 /**
  *  getOneWayMatchScore  - one way to calculate the match score 
  */
       public double getOneWayMatchScore(String str1, String str2)
       {
        double totalScore = 0;
        int str1Len = str1.length();
        int str2Len = str2.length();
        int matchCnt = 0;
        // Validate parms - if either is of zero length then there are no characters to match.
        if(str1Len < 1 || str2Len < 1){
        	return NO_CHAR_MATCH;
        }
        
        boolean havePreviousProximity = false;
        int prevProximity = 0;
 
        for (int i = 0; i < str1Len; i++) {
        	char c = str1.charAt(i);
        	int proximity = findCharProximity(str2, i, c);
        	
        	if (proximity != NO_CHAR_MATCH) {
        		if (havePreviousProximity == true) {
        			int proxDifference = proximity - prevProximity;
        			totalScore += Math.abs(proxDifference);
        			if (DEBUG){
        			System.out.println("InexactStringMatch::getOneWayMatchScore char= "+ c
        						    +  " prox= "+ proximity
        						    +  " *** proxDiff= "  +  proxDifference 
        						    +  " Total Score is: " + totalScore);
        			}
        		}
        		else{
        			totalScore += Math.abs(proximity);
        		}
        		prevProximity = proximity;
        		havePreviousProximity = true;
        		matchCnt++;
        	}
        }
        // Make sure we ding a string that is a bad match
        if(totalScore < 3 && matchCnt < 2 ){
        	totalScore += str2Len + str1Len - matchCnt;
        }
        
        if (matchCnt > 0){
        	// *** find the percentage of matching characters and then get the inverse of that by dividing by one ***
        	// *** so that more matches creates a lower number for a low match score.                             ***
        	totalScore += (double)1/((double)matchCnt/(double)str2Len) * 10; 
        }      
        
  return totalScore;
 }
       
   public boolean isCloseScore(){
	   
	   boolean retVal = false;
	   
	   if (THIS_SCORE <= CLOSE_SCORE){
		   retVal = true; 
	   }
	   return retVal;
   }
   
  public boolean isDecentScore(){
	   
	   boolean retVal = false;
	   
	   if (THIS_SCORE <= DECENT_SCORE){
		   retVal = true; 
	   }
	   return retVal;
   }
       
       
 /**
  * findCharProximity  how close in proximity are characters.
  */
  private int findCharProximity(String str, int position, char c) {
  int strlen = str.length();
  int closestCharDist = NO_CHAR_MATCH;
  boolean doRightSearch = true;
  int startPosition = position;
 
  // verify input parms
  if(str == null || str.length() < 1 || position < 0 || ! Character.isDefined(c) ){
	  doRightSearch = false;
	  return closestCharDist;
  }
  
  // may need to fix start position if it's past end
  // of string!
  if (position  >= str.length()) {
      doRightSearch = false;
      startPosition = str.length() - 1;
  }
 
  // work left along the string from the given position,
  // looking for matching char
  // test
  
  if(DEBUG){
	  System.out.println("findCharProximity Input String is: " + str + 
		             	 " length of string is: " + str.length() +
		             	 " position is: " + 
		             	 position + " character is: " + c);
  }
 
  for (int i = startPosition; i  != 0; i--) {
      if (str.charAt(i) == c) {
    	  int thisCharDist = position - i;
    	  if (thisCharDist < closestCharDist || closestCharDist == NO_CHAR_MATCH) {
    		  // we negate the distance, as we're looking
    		  // to the left
    		  closestCharDist = - thisCharDist;
    		  break;
    	  }
      }
  }
 
  if (doRightSearch == true) {
      // work right along the string from the give position,
      // looking for matching char
      for (int i = position; i < strlen; i++) {
        if (str.charAt(i) == c) {
          int thisCharDist = i - position;
          if (thisCharDist < closestCharDist || closestCharDist == NO_CHAR_MATCH) {
    	  	closestCharDist = thisCharDist;
    	  	break;
          }
        }
      }
  }
  
  if (DEBUG){
	 System.out.println(" findCharProximity returns: " + closestCharDist);
  }
  
  return closestCharDist;
 }
 
 public int scoreSubStrings(String target, String candidate){
	 int retVal = -1;
	 
	 String[] splitString = (target.split("[\\s\\.-_]"));
	 
	 for (String string : splitString) {
		 if(candidate.contains(string)){
			 retVal = 2;
		 }
	 }
	 if (retVal > 0){
		 retVal = retVal / 1;
	 }
	 
	 return retVal;
 }
  
  
  
 /**
  *    Main method  test methods
  */
       public static void main(String[] args)
       {
       InexactStringMatcher matcher = new InexactStringMatcher();
       
       System.out.println("score from closely matched items is "
       +matcher
       .getMatchScore("msedcationoflarynhill",
      "the_miseducation_of_lauryn_hill",
      "_ "));
 
       
    String str1 = "ReviewList";
    String str2 = "ReviewList";
    System.out.println("Target Match exact" + matcher.matchTargetScore(str1,str2)) ;  
       
    str2 += "DataStuff" ;
    System.out.println("Target Match close " + matcher.matchTargetScore(str1,str2)) ; 
     
    str2 = "aaabbbcccdddgggggzzzhhh";
    System.out.println("Target Match none " + matcher.matchTargetScore(str1,str2)) ;
    
    str1="msedcationoflarynhill";
    str2="zzzzzzzzzzzzzzzzzzzzzzzthe_miseducation_of_lauryn_hill";
    System.out.println("Target Match some " + matcher.matchTargetScore(str1,str2)) ;
    
    
  System
      .out
      .println("score from another closely matched item is "
       +matcher
       .getMatchScore("msedcationoflarynhill",
      "zzzzzzzzzzzzzzzzzzzzzzzthe_miseducation_of_lauryn_hill",
      "_ "));
      
  System.out.println("score from not so matched item is " +
                      matcher.getMatchScore("msedcationoflarynhill",
                                              "another_track_name_entirely",
                                              "_ "));
 
  
  System
      .out
      .println("score from not so matched item is "
       +matcher
       .getMatchScore("msedcationoflarynhill",
      "",
      "_ "));
  System
      .out
      .println("score from not so matched item is "
       +matcher
       .getMatchScore("",
      "msedcationoflarynhill",
      "_ "));
     
       }
  }
 
 

