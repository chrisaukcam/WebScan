package chris_test;
//import java.io.File;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
//import java.io.Writer;
//import java.io.FileOutputStream;
//import java.io.OutputStreamWriter;
//import java.io.OutputStream;

/***************************************************************************************************************
* 
* 
* The idea behind this is to build a group of files with a similiar naming scheme, but with a different 
*  trailing number to tell the files apart.  ex: ln_xml1.xml ln_xml2.xml or l00030.xml1 l00030.xml2 etc.                
*  The prefix and suffix are modifiable. A user can change the prefix and suffix ("prefix"."suffix") to    
*  suit their needs  ex: alr4th1.sgml alr4th2.sgml or l00030.xml1 l00030.xml2 etc.                                        *  <p>There is a constructor that will build this with a new prefix or a new prefix and suffix
*  if the defaults (ln_xml.xml) need to be changed by the user.
*  examples of creating a new incrementingFileWriter using the various constructors:
*  <p>incrementingFileWriter ifw = new incrementingFileWriter();               use default suffix and prefix
*  incrementingFileWriter ifw = new incrementingFileWriter("l00030");       files now are named l00030.xml[0-9]
*  incrementingFileWriter ifw = new incrementingFileWriter("l00030","sgml");      
*                               files now are named l00030.sgml[0-9]+
*  <p> One thing to watch is to always be sure and call the close method.  When the close is not called java
*  has a habit of deleting the output...
*  <p> Modification: May 3, 2005  added logic to allow the file name to be output without the ending number.
*      So a new constructor was added and a boolean value doFileNum that can be set to allow this option.
*      example incrementingFileWriter ifw = new incrementingFileWriter("l00030","sgml",false)  files are named l00030.sgml
* @author  J. Chris Tilton
* @param   optional - String prefix where prefix is first part of file name (l00030) String suffix last part of
           file name (xml or sgml for example).
*****************************************************************************************************************/
             
final public class incrementingFileWriter {
    
    private int fileNum = 0;
    public String filePrefix = "ln_xml";
    public String fileSuffix = "xml";
    public String docFilename = "" ;
    public final String encoding = "utf-8"; 
    private int oldFileNum = -1;           // *** set to negative number so that this can't be set to the same value twice ***
    final boolean append = true;          // *** append to output file, don't overwrite ***
    final boolean doFileNum = true;       // *** should the file number be appended to the file name or not? ***
    public PrintWriter out;               // *** used for all writing to output file(s) ***

    /****************************************************************************************                
     * purpose: increment file number by one  ln.xml1 ln.xml2,ln.xml3  etc.
     * @author J. Chris Tilton
     * @param none 
     *****************************************************************************************/ 
    public void incFileNum () {
        fileNum++;
    } 
  
    /****************************************************************************************
     * purpose: increment file number by any positive amount ln.xml1 ln.xml5, etc.
     * @author J. Chris Tilton
     * @param int num Where num is the amount which to increment the file number
     *                 If you want to increment by more than one, this is the method to call.  
     *****************************************************************************************/ 
    public void incFileNum (int num) {
        if (num > 0){
          fileNum+=num;
        }
    } 

    /***************************************************
     * return the value of the file number we are on ***
     * @author J. Chris Tilton
     * @param none 
     ***************************************************/
    public int getFileNum(){
        return fileNum;  
    }
     
    /*************************************************************************************************************
    * allow user to set value of file num to any positive value.  This will come in handy 
    * if the user has to go back and reuse a file number, then the value can be reset to any positive value. 
    * @author J. Chris Tilton
    * @param Int num   Where num is the value to append to the suffix of the file name ex. num=1 creates ln.xml1
    **************************************************************************************************************/
    public void setFileNum(int num) {
	if (num >= 0){
	  fileNum=num;
       	}
    	else {
    		
	  System.err.println("\nERROR incrementingFileWriter::setFileNum cannot set value of file number to value" +
	                     "\n of num: " + num + ".  The file number value will remain " + fileNum); 
    		
    	}	
    }

    /*********************************************************************************
     * This is where the data is actually output. If the file name has changed a new
     * file is opened.  If the filename has not changed then simply append to the 
     * the existing file.
     * @author J. Chris Tilton
     * @param String outBuf where outBuf is the string to be written to the file.
     **********************************************************************************/
    public void writeToFile (String outBuf){
        // *** make sure that some value is being output ***
       
        if ( outBuf.length() > 0) {   
            
            if (fileNum != oldFileNum ) {
                // *** We need to close the old file and open a new file ***
                // *** Check to see if this is the first open or not     ***
                // *** If this is not -1 then we have built at least one ***
                // *** file.                                             ***
                if (oldFileNum != -1) {
                    
                    out.close();  // *** need to close the old file before opening a new one. 
                     
                  }
                openOutputFile(fileNum);
                oldFileNum = fileNum;
            } 
           
            out.print(outBuf);
             
        }
        
    }    // ***  writeToFile ***

     /*********************************************************************************
     * @author J. Chris Tilton
     * @param none
     * @return returns a String that is the output file name.
     **********************************************************************************/
    public String getOutFileName(){
        // *** Build the output file name ***
       StringBuffer sb = new StringBuffer(300);        // *** set to 300 as it is unlikely for a file name to be longer than this. ***

       // *** check the doFileNum value. If true output the file number at the end of the file name.
       // *** if false, the file number is not needed for this application.
       if (doFileNum){
         sb.append(filePrefix).append(".").append(fileSuffix).append(fileNum);
       }
       else {
	 sb.append(filePrefix).append(".").append(fileSuffix);
       }
       if (sb.length() > 0){
         return sb.toString();
       }
       else{
           // *** in theory this should never happen - but just in case... ***
         System.err.println("\nERROR incrementingFileWriter::getOutFileName filename value is currently null." +
                            "\n      Values are being reset to defaults.");
         filePrefix = "ln_xml";
         fileSuffix = "xml";
         fileNum = 0;
         return ( filePrefix  + "." + fileSuffix + fileNum); 
       }
    }

   /*********************************************************************************
    * Open the output file for writing.
    * @author J. Chris Tilton
    * @param int fileNum - the number to append to the file suffix ex:  *.xml1
    *********************************************************************************/
   public void openOutputFile( int fileNum){
       docFilename=getOutFileName();
        try {  

           out =  new PrintWriter(new BufferedWriter(new FileWriter(docFilename,append)));
           
   	    } 
   	    catch (java.io.IOException ioe) {
    	          System.err.println(ioe + "\nFATAL ERROR: incrementingFileWriter::DocWriter - unable to open file " + docFilename);
    	          System.exit(1);	
    	          } 
 
   }

 /*********************************************************************************
  * when finished writing the user can close the output stream. 
  * @author J. Chris Tilton
  * @param none
  **********************************************************************************/
  public void closeOutput(){
     
       out.close();	
  
  }


   /*********************************************************************************************
  * constructor to build class with initial value of String file prefix and String file suffix 
  * and a boolean value that determines whether to add the number at the end or not.
  * @author J. Chris Tilton
  * @param String filePrefix,   The file prefix   "prefix".
  * @param String fileSuffix    The file suffix   ."suffix
  * @param boolean dofilenum    do we put out the file number at the end or not?  true = yes; false = no
  *********************************************************************************************/
  public incrementingFileWriter (final String filePrefix, final String fileSuffix, final boolean doFileNum) {
  	
       if ( filePrefix != null && ! filePrefix.equalsIgnoreCase("")) {	
  	     this.filePrefix = filePrefix;
       }
       else {
         System.err.println("\nincrementingFileWriter Error: attempt to build new instance with null filePrefix." +
                            "\n                              Default prefixe value will be used instead.");
       }
       if ( fileSuffix != null && ! fileSuffix.equalsIgnoreCase("")) {	
  	     this.fileSuffix = fileSuffix;
       }
       else{
         System.err.println("\nincrementingFileWriter Error: attempt to build new instance with null fileSuffix." +
                            "\n                              Default suffix value will be used instead.");
       }

  }
 

 /*********************************************************************************************
  * constructor to build class with initial value of String file prefix and String file suffix 
  * @author J. Chris Tilton
  * @param String filePrefix,   The file prefix   "prefix".
  * @param String fileSuffix    The file suffix   ."suffix
  *********************************************************************************************/
  public incrementingFileWriter (final String filePrefix, final String fileSuffix) {
  	
       if ( filePrefix != null && ! filePrefix.equalsIgnoreCase("")) {	
  	     this.filePrefix = filePrefix;
       }
       else {
         System.err.println("\nincrementingFileWriter Error: attempt to build new instance with null filePrefix." +
                            "\n                              Default prefixe value will be used instead.");
       }
       if ( fileSuffix != null && ! fileSuffix.equalsIgnoreCase("")) {	
  	     this.fileSuffix = fileSuffix;
       }
       else{
         System.err.println("\nincrementingFileWriter Error: attempt to build new instance with null fileSuffix." +
                            "\n                              Default suffix value will be used instead.");
       }

  }
 

 /*********************************************************************************************
  * constructor to build class with initial value of String file prefix - obviously this could be the
  * applic number ex: l99999.xml 
  * @author J. Chris Tilton
  * @param String filePrefix  prefix for the filename  "prefix".xml                                                    
   *********************************************************************************************/
  public incrementingFileWriter (final String filePrefix) {
  	
       if ( filePrefix != null && ! filePrefix.equalsIgnoreCase("")) {	
  	     this.filePrefix = filePrefix;
       }
       else {
         System.err.println("\nincrementingFileWriter Error: attempt to build new instance with null filePrefix." +
                            "\n                              Default prefixe value will be used instead.");
       }
  }

 /*********************************************************************************************
  * constructor to build empty class 
  * @author J. Chris Tilton
  * @param none
  *********************************************************************************************/
  public incrementingFileWriter() {
  		
  }

}  // *** end of incrementingFileWriter ***
