package chris_test;

/*****************************************************************************************
 * @author UTILTJC
 *
 *  Since the program is designed to output visf, a segment class was built.
 *  The data will be held and manipulated in this class until it is output. 
 *  Then the clear command will be used to empty the segment data so that next
 *  document can be built from scratch. Also some minor modifications to the segment data 
 *  can be done via the changeBuf method. I view this class as sort of a hybrid of the
 *  Stringbuffer and String classes - bringing in methods from both classes into a single
 *  class so that the best of both classes can be used seamlessly.
 * 
 * called by StateFactory
 * 
 * 
 *****************************************************************************************/


final public class SegBuf {
    
/* *** Buffers to hold data until ready for output.      ***
 * If there is no manipulation of the data then          ***
 * simply hold the contents until ready to output        ***
 * This uses both String and StringBuffer classes        *** 
 * and is a handy way to manipulate and hold the data    ***
 */
  public int buf_size = 1024;
  public SegBuf SegmentBuf;
  public StringBuffer DataBuf = new StringBuffer(buf_size);
  private StringBuffer SegPrefix = new StringBuffer(100).append("");
  
  // *** Return contents of buffer in String format ***
  final public String getBuf() {
 	  if (SegPrefix.length()>0) {
 	  	StringBuffer tmpgetbuf = new StringBuffer(this.DataBuf.toString());
 	    StringBuffer getbuffer = new StringBuffer(SegPrefix.toString());
 	    getbuffer.append(DataBuf);
 	    
 	    return getbuffer.toString();
 	    
 	  }  
 	  else{
  	    return this.DataBuf.toString();
 	  }
  }
   
    /**************************************************************
     *  Return contents of buffer in StringBuffer format 
     *  if there is a prefix - send it as well. 
     * ************************************************************/
  final public StringBuffer getStrBuf() {
  	  if (SegPrefix.length()>0) {
 	    return SegPrefix.append(DataBuf);	
 	  }  
 	  else {
  	  return DataBuf;
 	  }
  }
    
  /**********************************************************
   * Add data to front of buffer
   * @param String DataBuf data to added to front of buffer
   * ********************************************************/   
   public void prependBuf(String DataBuf){
   	  if(DataBuf.length() > 0){
   	    this.DataBuf.insert(0,DataBuf);
   	  }
   } 
    
   /**********************************************************
   * Add data to front of buffer
   * @param String DataBuf data to added to front of buffer
   * ********************************************************/   
   public void prependBuf(StringBuffer DataBuf){
   	  if(DataBuf.length() > 0){
   	    this.DataBuf.insert(0,DataBuf);
   	  }
   }  
    
    
    
  /*************************************** 
   * 
   * Add StringBuffer data to buffer *** 
   * *************************************/
  public void appendBuf( StringBuffer DataBuf) {
  	
     if (  DataBuf != null)  {
  	   this.DataBuf.append(DataBuf);
     }
    
  }
  
  
  /******************************* 
   * Add String data to buffer 
   * 
   ********************************/
  public void appendBuf(String aBuf) {
  	
     if (  aBuf != null && ! aBuf.equalsIgnoreCase(""))  {
  	   DataBuf.append(aBuf);
     }
  	  
  }
  
  // *** Add data from two strings to buffer *** 
  public void appendBuf(String first_string, String second_string ) {
  	
     if ( first_string != null && ! first_string.equalsIgnoreCase(""))  {
  	    DataBuf.append(first_string);
     }
     if ( second_string != null && ! second_string.equalsIgnoreCase(""))  {
  	    DataBuf.append(second_string);
     }
     	  
  }
 
  // *** does buffer have data? ***
 final public boolean hasBuf() {
       if ( DataBuf.length() > 0)  {
       	 return true;
       	 }
       	 
       else { return false;}    	
  }   // *** end of hasBuf ***

  //*** length of buffer ***
  final public int length(){
    return DataBuf.length();	
  }


  // *** empty buffer of all contents ***
  final public void clear() {
		if (DataBuf.length() > 0){
		  DataBuf.delete(0, DataBuf.length());
		  // DataBuf = new StringBuffer();
		}
	}
	
  // *** Change a string in the data buffer to another value using a regular expression to generate the match ***
  // *** Example:  changeBuf("ABC","") would change "ABCDEF" to "DEF"                                           ***
 final public void changeBuf(final String cb_regex,String new_string){
  // *** Let's verify that we have data before attempting to modify it. ***
  if(DataBuf.length() > 1){	
  	// *** verify that we have valid inputs ***
  	if (new_string == null) {
  	  new_string = "";	
  	}
  	
    if ( cb_regex != null && ! cb_regex.equalsIgnoreCase("")){
       String tmpchangeBuf = DataBuf.toString().replaceAll(cb_regex,new_string);
       DataBuf.delete(0, DataBuf.length());
  	   DataBuf.append(tmpchangeBuf);
  	  }
    }
  }
  
  // *** empty current contents of DataBuf and assign new value *** 
  // *** input is a String                                      ***
  
 final public void setBuf(String sb_Buf) {
    
     DataBuf.delete(0, DataBuf.length());  // *** empty buffer ***
     //DataBuf= new StringBuffer("");
     if (sb_Buf != null && ! sb_Buf.equalsIgnoreCase("")){  
  	   DataBuf.append(sb_Buf);
     } 	
   
     
  }
  
  
 // *** empty current contents of DataBuf and assign new value *** 
 // *** input is a StringBuffer                                *** 
 final public void setBuf(final StringBuffer sb_Buf) {
    
     DataBuf.delete(0, DataBuf.length());  // *** empty buffer ***
     if (sb_Buf != null && sb_Buf.length() >0){  
  	   DataBuf.append(sb_Buf);
     }
     	
  }
   
	
	
 // *** constructor to build class with StringBuffer initial value ***
  public SegBuf (final StringBuffer SegmentBuf) {
  	
       if ( SegmentBuf != null && ! SegmentBuf.equals("")) {	
  	     DataBuf.append(SegmentBuf);
       }
  }	
	
// *** Constructor to resize the buffer                                       ***
// *** use this constructor if the default buffer size is too high or too low ***
public SegBuf (final int buf_size){
	// *** verify that the buffer is being set to 1 or more ***
	if (buf_size > 0) {
	  this.buf_size = buf_size;	
	}
	
}


  // *** constructor to build class with initial value of String ***
  public SegBuf (final String SegmentBuf) {
  	
       if ( SegmentBuf != null && ! SegmentBuf.equalsIgnoreCase("")) {	
  	     DataBuf.append(SegmentBuf);
       }
  }
 
 // ************************************************ 
 // *** constructor to build class with prefix.  ***
 // *** in this case the prefix will usually be  ***
 // *** a visf segment ($10:) in the future this ***
 // *** could be a beginning tag and a suffix    ***
 // *** added to create html or sgml output      ***
 // ************************************************
  public SegBuf (final String segPre, final String SegmentBuf) {
  	 if (segPre != null && ! segPre.equalsIgnoreCase("")) {
  	   this.SegPrefix.append(segPre);  	
  	 }
  	 if ( SegmentBuf != null && ! SegmentBuf.equalsIgnoreCase("")) {	
  	     this.DataBuf.append(SegmentBuf);
       }
  }
  
   // *** constructor to build class with prefix and initial buffer size***
  public SegBuf (final String segPre, final String SegmentBuf , final int buf_size) {
  	 if (segPre != null && ! segPre.equalsIgnoreCase("")) {
  	   this.SegPrefix.append(segPre);  	
  	 }
  	 if ( SegmentBuf != null && ! SegmentBuf.equalsIgnoreCase("")) {	
  	     this.DataBuf.append(SegmentBuf);
     }
     // *** verify that the buffer is being set to 1 or more ***
	if (buf_size > 0) {
	  this.buf_size = buf_size;	
	}
  }  
  
  // *** constructor to build empty class ***
  public SegBuf () {
  		
  }

}
