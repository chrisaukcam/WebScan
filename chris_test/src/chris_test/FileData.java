package chris_test;


/***********************************************************************************
 * Contain for data about files.  Original use is with the FindClass application.
 * @author ctilton3
 *
 ***********************************************************************************/
public class FileData {

	
	int matchScore = 0;
	String fileName = "";
	String jarFileName = "";
	
	
	public FileData(int matchScore, String fileName, String jarFileName) {
		super();
		this.matchScore = matchScore;
		this.fileName = fileName;
		this.jarFileName = jarFileName;
	}
	
	public int getMatchScore() {
		return matchScore;
	}
	
	public void setMatchScore(int matchScore) {
		this.matchScore = matchScore;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getJarFileName() {
		return jarFileName;
	}
	
	public void setJarFileName(String jarFileName) {
		this.jarFileName = jarFileName;
	}
		
	
	@Override
    public String toString() {
        return "FileData: class - name=" + this.fileName + " \n    jar file="
                + this.jarFileName + ":: Score= " + this.matchScore + "";
    }
	
	
}   // *** end class FileData ***
