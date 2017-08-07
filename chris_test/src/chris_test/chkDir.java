package chris_test;


import java.io.*;
import java.util.*;



public class chkDir {


public static void main(String[] args) {


   // *** filter for house files ***
   FilenameFilter houseFilter = new FilenameFilter() {
        public boolean accept(File dir, String name) {
            return (name.matches(".*[Hh][Oo][Uu][Ss][Ee].*"));
        }
   };



 if (args.length == 0) {
        System.out.println("Usage: java chkDir <directory>");
        return;
     }

 // *** verify that the home directory exits ***
    File homeDirectory = new File(args[0]);

    if ( ! homeDirectory.exists() ) {
        System.out.println("Specified directory does not exist." + args[0]);
        return;
     }
     if (! homeDirectory.isDirectory() ) {
        System.out.println("The specified file is not a directory." + args[0] );
        return;
     }

     
     File systemDIR = new File(args[0] + "/" + args[1]);

      
     if ( ! systemDIR.exists() ) {
        System.out.println("system directory does not exist.");
        return;
     }
     if (! systemDIR.isDirectory() ) {
        System.out.println("The system directory is not a directory." +args[0] + "/" + args[1] );
        return;
     }

     // *** Now that we have confirmed that the directories are valid, lets find if subdirectories exist. ***

     File childDir[] = systemDIR.listFiles();
 
     if ( childDir.length > 0 ) {
         for ( int i=0; i < childDir.length; i++ ) {

           if ( childDir[i].isDirectory() )
             {
		 System.out.println("Found directory " + childDir[i].getName());
                 File inFile[] = childDir[i].listFiles(houseFilter);

                 if (inFile.length > 0) {
                   for ( int ctr=0; ctr < inFile.length;ctr++){
		     System.out.println("  Found file " +  inFile[ctr].getName());  
                   }
                 }
                 else {
		     System.out.println("Warning: no input files found for " +  childDir[i].getName());
                 }
                 
             }
	   else{
	       System.out.println("Warning: " +  childDir[i].getName() + " is not a directory.");
           }

         }
     }
     else {
	 System.out.println("There are no Roll Call votes to process under the " + systemDIR.getName() + "directory");
     }

}    // *** end of main ***


}    // *** end of class ***
