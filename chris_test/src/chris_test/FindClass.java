package chris_test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FindClass {
	
	// *** count of results to display ***
	private static int MAXIMUMCNT = 12;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 if (args.length == 0) {
		        System.out.println("Error - Usage: java FindClass <class name>");
		        return;
		 }
		 
		 String inStr = (args[0]);
		
		System.out.println("FindClass execution begins for class: " + inStr);
		
		File currentDir = new File("/temp").getAbsoluteFile();
		List<FileData> fileDataList = new ArrayList<FileData>();
		
			File[] files = currentDir.listFiles();
			
			if(files.length < 1){
				System.err.println("No files to check in directory: " + currentDir.getName());
				System.exit(1);
			}
			
			
			for (File file : files) {
				if (! file.isDirectory()) {			
					if(file.getName().matches(".*[.][Jj][Aa][Rr]$")){
					
						try (ZipFile zipFile = new ZipFile(file)) {
							Enumeration<?> zipEntries = zipFile.entries();
							while (zipEntries.hasMoreElements()) {
								String fileName = ((ZipEntry) zipEntries.nextElement()).getName();
								
								if(fileName.matches(".*[.][Cc][Ll][Aa][Ss][Ss]$") ){
									
									File tmpfile = new File(fileName);
									InexactStringMatcher ISM = new InexactStringMatcher();
									double score = ISM.matchTargetScore(inStr,tmpfile.getName().replaceAll("[.][Cc][Ll][Aa][Ss][Ss]$", "") );
								    FileData tmpFD = new FileData((int) score,tmpfile.getAbsolutePath(),file.getAbsolutePath());
								    fileDataList.add(tmpFD);
								}
							}
						}catch(Exception e){
							System.err.println("Unable to determine jar file names. Error: " + e.getMessage());
						}
					}
				}
			}
			
			// *** sort and output top 10 results in ascending order based on ***
			
			Collections.sort(
					fileDataList,
	                (fd1, fd2) -> fd1.getMatchScore()
	                        - fd2.getMatchScore());

			int ctr = 0;
			int maxctr = MAXIMUMCNT;
			
			for (FileData temp:fileDataList){
				System.out.println(temp);
				ctr++;
				
				if (temp.getMatchScore() == 0){
					maxctr += 1;
				}
				if(ctr >maxctr){
					break;
				}
			}
			
			System.out.println("FindClass execution ends.");
		 
	}	/*** end main ***/

	
	
	
}	    /*** end class FindClass ***/
