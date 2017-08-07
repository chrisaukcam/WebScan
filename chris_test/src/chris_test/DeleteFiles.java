package chris_test;

import java.io.IOException;
import java.io.InputStream;
//import java.util.ArrayList;
import java.util.Enumeration;
//import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.nio.file.DirectoryStream;
//import java.nio.file.FileAlreadyExistsException;
//import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class DeleteFiles {
	
	static int ctr = 0; 
	
	public static void main(String[] args) {
		
		Properties delprop = new Properties();
		ClassLoader delloader = Thread.currentThread().getContextClassLoader();  
		InputStream delstream = delloader.getResourceAsStream("delete_file.properties");
		
		try {
			delprop.load(delstream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		/*** Loop through all of the directories and delete according to settings from the property file ***/
		Enumeration<?> emq = delprop.propertyNames();
		
		while (emq.hasMoreElements()) {
		      String key = (String) emq.nextElement();
		      String days = delprop.getProperty(key).trim();
		     
		      long cut = LocalDateTime.now().minusDays(Integer.parseInt(days)).toEpochSecond(ZoneOffset.UTC);
		      Path path = Paths.get(key.trim());
		      
		      try {
		    	  // *** make sure that path exists on system ***
		    	  if (Files.exists(path)) {
		    		  Files.list(path)
				          .filter(n -> {
				              try {
				                  return Files.getLastModifiedTime(n).to(TimeUnit.SECONDS) < cut;
				              } catch (IOException ex) {
				                  //handle exception
				            	  System.err.println("DeleteFiles error: " + ex.getMessage());
				            	  return false;
				              }
				          })
				          .forEach(n -> {
				              try {
				            	  try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(n.toString()))) {
				                      for (Path tmppath : directoryStream) {
				                          System.out.println("Deleting files from " + tmppath.toString());
				                      }
				                  } catch (IOException ex) {
				                	  System.err.println("DeleteFiles error: " + ex.getMessage());
				                  }
				            	  
				                  System.out.println("Deleting " + Files.size(n) +  " -- " + n.toString());	 
				                  Files.delete(n);
				                  fileCounter(1);
				                  
				              } catch (IOException ex) {
				            	  System.err.println("DeleteFiles error: Cannot delete - " + ex.getMessage());
				                  //handle exception
				              }
				          });
		    	}
		    	else
		    	{
		    		System.err.println("DeleteFiles warning path " + path.toString() + " does not exist or cannot be accessed.");
		    	}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		          
		}

		if (getfileCounter() > 0 ){
			System.out.println("DeleteFiles total number of files deleted: " + getfileCounter() );
		}
		
	}   /*** end method main ***/

	// increment counter
	static void fileCounter(int inNum)
	{
	   ctr += inNum; 
	}
	
	// get count
	static int getfileCounter()
	{
	   return ctr;
	}
	
}	/*** end class DeleteFiles ***/
