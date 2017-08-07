package webcrawler;

import java.util.ArrayList;
import java.util.List;

import chris_test.StringUtil;

/*
 * Represents one RSS message
 */
public class FeedMessage {

    String title;
    String description;
    String link;
    String author;
    String guid;
    int sportScore = 0;
    String teamLevel = null;
    List<String> keyWords = new ArrayList<String>();
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLevel() {
        return teamLevel;
    }

    public void setLevel(String teamLevel) {
        this.teamLevel = teamLevel;
    }
    
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGuid() {
        return guid;
    }
    
    public void setSportScore(){
    	
    	
    	
    	
    	SportScore ss = new SportScore(getTitle(),getDescription());
    	sportScore = ss.getScore();	
    	keyWords = ss.getSportKeyWords();
    	
    	TeamKey tk = new TeamKey();
    	 List<String> teamkeyWords = new ArrayList<String>();
    	 teamkeyWords = tk.generateTeamKeys(getTitle() + getDescription());
    	 
    	 for (String tmp :teamkeyWords ){
    		 if( ! keyWords.contains(tmp) ){
    			 keyWords.add(tmp);
    		 }
    	 }
    	 if(tk.hasTeamLevel()){
    	   teamLevel = tk.getTeamLevel();
    	 }
    	//   keyWords.addAll(tk.generateTeamKeys(getTitle() + getDescription()));
    }
    
    public int getSportScore(){	
    	return sportScore;	
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
    
    public String toHtml(){
    	String html = "<li><a href='" + link +  "'>" + title + "</a><!-- score is: " + sportScore + " -->" ;
    	if (! keyWords.isEmpty()){
    		html += "<span> ";
    	  	for (String temp :keyWords ){
    	  		html += temp + " ";
    	  	}
    	  	if (! StringUtil.isEmpty(teamLevel)){
    	  		html += " " + teamLevel ;
    	  	}
    	  	html += "</span></li>";
    	}
    	return html;
    }

    @Override
    public String toString() {
        return "FeedMessage [title=" + title + ", description=" + description
                + ", link=" + link + ", author=" + author + ", guid=" + guid
                + " Score= " + sportScore + "]";
    }

}   // *** end class FeedMessage ***
