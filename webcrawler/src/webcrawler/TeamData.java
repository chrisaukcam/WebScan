package webcrawler;

import java.util.ArrayList;
import java.util.List;
import chris_test.StringUtil;
public class TeamData {

	String school = null;
	String mascot = null;
	String city   = null;
	List<String> nickname = new ArrayList<String>();
	List<String> masterList = new ArrayList<String>();
	public enum Level { YOUTH,HS,COL,PRO };
	Level level;
	String league = null;
	
	
	
	public TeamData(String school, String mascot, String city, List<String> nickname, Level level, String league) {
		super();
		this.school = school;
		this.mascot = mascot;
		this.city = city;
		this.nickname = nickname;
		this.level = level;
		this.league = league;
			
	}
	
	
	public TeamData(String school, String mascot, String city, List<String> nickname, String levelstr, String league) {
		super();
		this.school = school;
		this.mascot = mascot;
		this.city = city;
		this.nickname = nickname;
		// *** if level is not set then default to HS ***
		if(! StringUtil.isEmpty(levelstr)){
		  this.level = Level.valueOf(levelstr.toUpperCase());
		}
		else{
			this.level = Level.HS;
		}
		this.league = league;
			
	}
	
	
	
	/**
	 * @return the school
	 */
	public String getSchool() {
		return school;
	}
	/**
	 * @param school the school to set
	 */
	public void setSchool(String school) {
		this.school = school;
	}
	/**
	 * @return the mascot
	 */
	public String getMascot() {
		return mascot;
	}
	/**
	 * @param mascot the mascot to set
	 */
	public void setMascot(String mascot) {
		this.mascot = mascot;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the nickname
	 */
	public List<String> getNickname() {
		return nickname;
	}
	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(List<String> nickname) {
		this.nickname = nickname;
	}
	/**
	 * @return the level
	 */
	public Level getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(Level level) {
		this.level = level;
	}
	/**
	 * @return the league
	 */
	public String getLeague() {
		return league;
	}
	/**
	 * @param league the league to set
	 */
	public void setLeague(String league) {
		this.league = league;
	}
	
	// List of team key words for this team
	public List<String> getMasterList(){
		
		if( !StringUtil.isEmpty(school)  &&  !masterList.contains(school)){
			masterList.add(school);
		}
		if( !StringUtil.isEmpty(mascot)  &&  !masterList.contains(mascot)){
			masterList.add(mascot);
		}
		if( !StringUtil.isEmpty(city)  &&  !masterList.contains(city)){
			masterList.add(city);
		}
		if( !StringUtil.isEmpty(league)  &&  !masterList.contains(league)){
			masterList.add(league);
		}
		//if( !StringUtil.isEmpty(level.toString())  &&  !masterList.contains(level.toString())){
	//		masterList.add(level.toString());
	//	}
		if(! nickname.isEmpty() ){
			for(String temp:nickname){
				if ( !masterList.contains(temp )){
					masterList.add(temp);
				}
			}
		}
		
		
		return masterList;
	}
	
}   // *** end of class TeamData ***
