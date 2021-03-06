/*
    This program makes a copy of a file.  The source file and the name of the
    copy are specified as command line arguments.  For example:
    
                java CopyFile source.dat copy.dat
                
    This command will fail if a file named copy.dat already exists.  To
    force the command to succede, add the -f command line option:
    
                java CopyFile -f source.dat copy.dat
    
    Either command will fail if the source file does not exist.
*/
package chris_test;

import java.io.*;

public class CopyFile {

 public static InputStream source;  // Stream for reading from the source file.
 public static OutputStream copy;   // Stream for writing the copy.
 
   public static void main(String[] args) {
      
      String sourceName;   // Name of the source file, specified on the command line.
      String copyName;     // Name of the copy, specified on the command line.
     
      
      boolean force;  // This is set to true if the "-f" option is specified.
      int byteCount;  // The number of bytes copied from the source file.
      
      /* Get file names from the command line and check for the presense
         of the -f option.  If the command line is not one of the two
         possible legal forms, print an error message and end this program. */
   
      if (args.length == 3 && args[0].equalsIgnoreCase("-f")) {
         sourceName = args[1];
         copyName = args[2];
         force = true;
      }
      else if (args.length == 2) {
         sourceName = args[0];
         copyName = args[1];
         force = false;
      }
      else {
         System.out.println("Usage:  java CopyFile <source-file> <copy-name>");
         System.out.println("    or  java CopyFile -f <source-file> <copy-name>");
         return;
      }
      
      /* Create the input stream.  If an error occurs, end the program. */
      
      try {
         source = new FileInputStream(sourceName);
      }
      catch (FileNotFoundException e) {
         System.out.println("Can't find file \"" + sourceName + "\".");
         return;
      }
      
      /* If the output file alrady exists and the -f option was not specified,
         print an error message and end the program. */
   
      File file = new File(copyName);
      if (file.exists() && force == false) {
          System.out.println("Output file exists.  Use the -f option to replace it.");
          closeFileStream();
          return;  
      }
      
      /* Create the output stream.  If an error occurs, end the program. */

      try {
         copy = new FileOutputStream(copyName);
      }
      catch (IOException e) {
         System.out.println("Can't open output file \"" + copyName + "\".");
         closeFileStream();
         return;
      }
      
      /* Copy one byte at a time from the input stream to the out put stream,
         ending when the read() method returns -1 (which is the signal that
         the end of the stream has been reached.  If any error occurs, print
         an error message.  Also print a message if the file has bee copied
         successfully.  */
      
      byteCount = 0;
      
      try {
         while (true) {
            int data = source.read();
            if (data < 0)
               break;
            copy.write(data);
            byteCount++;
         }
         source.close();
         copy.close();
         System.out.println("Successfully copied " + byteCount + " bytes.");
      }
      catch (Exception e) {
         System.out.println("Error occured while copying.  "
                                   + byteCount + " bytes copied.");
         System.out.println(e.toString());
      }
      
   }  // end main()
   
   public static void closeFileStream()
   {
	   try{
		   source.close();
		   copy.close();
	   }catch(IOException e){
		   System.out.println("Error closing file.");
	   }
   }
   
   
} // end class CopyFile

