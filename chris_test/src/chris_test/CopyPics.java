package chris_test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import systemCheck.SSHUtil;
import systemCheck.LogHandler;


/*************************************************************************************
 * Copy pictures from one server to another.  Keep the 10 most recent directories.
 * @author ctilton3
 *
 *************************************************************************************/
import java.util.Properties;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class CopyPics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean DEBUG = true;
		LogHandler logHandle = new LogHandler();
       
		/*** Load properties file data ***/
		Properties prop = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();  
		InputStream propstream = loader.getResourceAsStream("copy_pic.properties");
		try {
			prop.load(propstream);
		 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			LogHandler.setup();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String newsDir = prop.getProperty("root_dir");
		
		File files[] = dirListByAscendingDate (new File(newsDir));
		
	//	File picdir[];
		List<File> picdir = new ArrayList<File>();
	
		/*** Get subdirectories from the root picture directory ***/
		for (File file : files) {
		 //    picdir[] = dirListByAscendingDate (new File(newsDir + "/" +  file.getName()));
			 File tmpfile = new File( newsDir + "/" +  file.getName());
			 
			 File childfiles[] = dirListByAscendingDate (tmpfile);
			
			 for (File tmp : childfiles){
				 picdir.add(tmp) ;
			 }
		    
		//	for (File tmpdir :  picdir  ){
		//		System.out.println("Picture directory: " + tmpdir.getName());
		//	}
			 // test
		    System.out.println(file.getName() + " " +
		                       new Date(file.lastModified())
		                       );
		}
		
	//   File dir[] = sortFilesByDate(picdir.toArray(T[]));
		//File testdir[] = picdir.<File>toArray()
	   
	//   Arrays.sort(files, (a, b) -> Long.compare(a.lastModified(), b.lastModified()));
	   
		picdir.sort((a, b) -> Long.compare(a.lastModified(), b.lastModified()) );
		
	//  Arrays.sort(picdir.toArray(), (a, b) -> Long.compare(((File) a).lastModified(), ((File) b).lastModified()));
	   
	  // *** Get list of directories from the target server ***
	  SSHUtil sshTool  = new SSHUtil(prop.getProperty("target_server"),prop.getProperty("user_id"), prop.getProperty("pass_word"),logHandle);
		
	  String picDirCheck = sshTool.runOnce("ls -d " + prop.getProperty("target_dir") + "/*"  );
		
		// test 
	 // System.out.println("Value of directory command is: " + picDirCheck;
	  List<String> myList = new ArrayList<String>(Arrays.asList(picDirCheck.replace(prop.getProperty("target_dir")+"/","").split(";;")));	
		//picDirCheck.replace(prop.getProperty("target_dir"),"");
	   
	  int ctr =0 ;
	  for (File tmpdir : picdir) {
		  
		  // test 
		//  System.out.println("Final List next path: " + tmpdir.getAbsolutePath() + " Date:" 
        //                     + new Date(tmpdir.lastModified())
        //                    );
		 	 
		  /********************************************************************************* 
		   * If the target directory does not have a matching directory underneath of it
		   *   then we need to copy the directory and contents over.
		   *********************************************************************************/
		   if ( ! myList.contains(tmpdir.getName())){
			   System.out.println("Ready top copy directory contents: " + tmpdir.getName());
			   
			   String createDirResult = sshTool.runOnce("mkdir " + prop.getProperty("target_dir") + "/" + tmpdir.getName() );
			   
			   if (createDirResult != null){ 
				   System.out.println("Create directory results are: " + createDirResult);  
			   }
			   
			  if (! ftpFiles(prop.getProperty("target_server"),
					    prop.getProperty("target_dir"),
					    prop.getProperty("user_id"),
					    prop.getProperty("pass_word"),
					    tmpdir 
					    )
			      ){
				  logHandle.logger.severe( "CopyPics - failed to copy files to target server." + tmpdir.getName());
				  System.err.println("CopyPics - failed to copy files to target server." + tmpdir.getName());
			  }
			ctr++;
		   }
		  /*** we are only interested in the last 10 directories ***/	
		  if(ctr == 10){
			  break;
		  }
     }		/***  for (File tmpdir : picdir)  ***/
		   
	  // test
	  if (DEBUG){
		  System.out.println("Number of directories found on target server: " + myList.size());
		  // test
		  if (myList.size() > 0){
			  for (String tmpStr :myList){
				  System.out.println("Target directory: " + tmpStr); 
			  }
		  } 
	  }	
		
	}   /*** end main ***/

	
	// @SuppressWarnings("unchecked")
	  public static File[] dirListByAscendingDate(File folder) {
	    if (!folder.isDirectory()) {
	      return null;
	    }
	    File files[] = folder.listFiles();
	    Arrays.sort( files, new Comparator<Object>()
	    {
	      public int compare(final Object o1, final Object o2) {
	        return new Long(((File)o1).lastModified()).compareTo
	             (new Long(((File) o2).lastModified()));
	      }
	    }); 
	    return files;
	  }  
	  
	//  @SuppressWarnings("unchecked")
	  public static File[] dirListByDescendingDate(File folder) {
	    if (!folder.isDirectory()) {
	      return null;
	    }
	    File files[] = folder.listFiles();
	    Arrays.sort( files, new Comparator<Object>()
	    {
	      public int compare(final Object o1, final Object o2) {
	        return new Long(((File)o2).lastModified()).compareTo
	             (new Long(((File) o1).lastModified()));
	      }
	    }); 
	    return files;
	  }  
	
	  
//	@SuppressWarnings("unchecked")
	public static File[] sortFilesByDate(File infiles[]) {
		 Arrays.sort( infiles, new Comparator<Object>()
		    {
		      public int compare(final Object o1, final Object o2) {
		        return new Long(((File)o1).lastModified()).compareTo
		             (new Long(((File) o2).lastModified()));
		      }
		    }); 
		    return infiles;
	 }
	
	/*** send all files in a given directory ***/
	public static boolean ftpFiles (String targetServer,String targetDir, String userId,String passWord, File srcDir){
		boolean success = false;
		FileInputStream fis;
		try {			   
			FTPClient client = new FTPClient();
			client.connect(targetServer);
			
			if(FTPReply.isPositiveCompletion(client.getReplyCode())) {
				if(client.login(userId, passWord)) {	
					for ( File ftpfile : srcDir.listFiles() ){
						fis = new FileInputStream(ftpfile.getAbsolutePath());
						String targetFile = targetDir + "/" + srcDir.getName() + "/" +  ftpfile.getName();
						try {
							if(client.storeFile(targetFile, fis)) {
								// *** test ***
								// System.out.println("File " + ftpfile.getName() +  "  uploaded!");
								success = true;
							}
							else{
								success = false;
								System.err.println("CopyPics::ftpFiles could not copy file " + ftpfile.getAbsolutePath() +
										           " to server " + targetServer + " file " + targetFile );
							}
						}
						catch(Exception e){
							System.err.println("CopyPics Unable to copy file " + ftpfile.getName() + 
									           " to " + targetServer + 
		    			                       " " + targetFile + " " + e.getMessage() );
						}	
					}    /*** end for loop                                             ***/
				}		 /*** if(client.login(userId, passWord))                       ***/
			 }			 /*** if(FTPReply.isPositiveCompletion(client.getReplyCode())) ***/
			}catch(Exception se ){
				System.err.println("CopyPics unable to transfer files: " + se.toString()  );
			}
		return success;
	}  /*** end ftpFiles ***/
	
	
}		/*** end class CopyPics ***/
