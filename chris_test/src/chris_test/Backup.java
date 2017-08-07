package chris_test;


import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.PatternSyntaxException;

public class Backup {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String fileName = null;
		Path backupPath = Paths.get("/temp/backup");
		
		if (args.length > 0 ){
			fileName = args[0];
		}
		else{
			System.err.println("Backup: One argument - file name is needed.");
			System.exit(1);
		}
		 
		File copyFile = new File(fileName);
		
		if(copyFile.exists() && !copyFile.isDirectory() && copyFile.canRead()) { 
			
			/*** Make sure that the backup directory exists, if not create it ***/
			
			if ( ! Files.exists(backupPath)) {
				try{
					Files.createDirectory(backupPath);
				}catch(IOException ioe){
					System.err.println("Backup: cannot create directory " + ioe.getMessage());
				}
			}
			
			String timeStamp = new SimpleDateFormat("YYMMdd_HHmmss").format(Calendar.getInstance().getTime());
			String[] splitArray = null;
			
			try {
			    splitArray = copyFile.getName().split("\\.");
			} catch (PatternSyntaxException ex) {
				System.err.println("Backup: Unable to build backup file name for file: " +  copyFile.getName());
			}
			
			File backupFile = new File ( splitArray[0] + "_" + timeStamp + "." + splitArray[1] ) ;
			Path destinationPath = FileSystems.getDefault().getPath(backupPath.toString(), backupFile.toString());
						
			/*** now do the actual copy ***/
			try {			
			    Files.copy(copyFile.toPath(), destinationPath,StandardCopyOption.REPLACE_EXISTING );
			} catch(FileAlreadyExistsException e) {
			    //destination file already exists
				System.err.println("Backup: File " + destinationPath.toString() + " exists cannot copy.");
			} catch (IOException e) {
			    //something else went wrong
			    e.printStackTrace();
			}	
			
		}
		else{
			System.err.println("Backup: file " + fileName + " does not exist, is a directory, or cannot be accessed. Execution halts.");
			System.exit(2);
		}
	
		/*
		ProcessBuilder pb=new ProcessBuilder(command);
		pb.redirectErrorStream(true);
		Process process=pb.start();
		BufferedReader inStreamReader = new BufferedReader(
		    new InputStreamReader(process.getInputStream())); 

		while(inStreamReader.readLine() != null){
		    //do something with commandline output.
		}  
		*/            
		
	}   /*** end of main ***/

}	/*** end of Backup ***/
