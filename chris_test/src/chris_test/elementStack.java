package chris_test;

import java.util.Stack;

/***********************************************************************
 * @author UTILTJC
 * Class: elementStack
 * Comments: This should be called by the documentHandler.  An elementStack
 * object should be created within the startDocument part of the Handler.
 * Then the top of the stack can be changed as the various elements are placed on
 * the stack at the start element and then removed at the end element.
 * Called by: Numerous States
 * Project: Marhub - la5067
 * Creation Date: Jan 30, 2004
 * Modifications:
 * 
 * This document is the property of Lexis-NEXIS and its contents are 
 * proprietary to Lexis-NEXIS.  Reproduction in any form by anyone of 
 * the material contained herein without the permission of Lexis-NEXIS 
 * is prohibited.  Finders are asked to return this document to 
 * Lexis-NEXIS, 9473 Springboro Pike, Miamisburg, Ohio 45342.
 ************************************************************************/
public class elementStack extends Stack {

/****************************************************************
 * empty stack of all elements. This should be called from the 
 * startDocument portion of the sax parser. 
 * @author UTILTJC
 * *************************************************************/
final public void flushStack(){
  if (! empty()){
    clear();	
  }
}

/********************************************************************
 * Add a new element to the Stack.  Input is a string with the 
 * element name:  pushElement("name");
 * This should be called by the startElement:
 * ex: elestack.pushElement(localName);
 * @author UTILTJC
 * @param String newEl  where newEl is the element name.
 * ******************************************************************/
final public void pushElement(String newEl){
   if (newEl.length() > 0){
     push(newEl);
   }
   else{
   	 System.err.println("\nWarning: elemenStack::pushElement was sent a null string" +
   	                    "\n         this is not a valid element name so this will not be" +
   	                    "\n         added to the stack. This may cause erroneous results.  Processing continues.");
   	  
   }	
}   // *** end of method pushElement ***

/**********************************************************************
 * This will pop the topmost element from the stack. 
 * This should be called from the endElement.
 * ex: elestack.popElement();
 * @author UTILTJC
 * @param none
 * ********************************************************************/
final public void popElement(){
  pop();
}

/*****************************************************************
 * This will return a string with the top element from the stack.
 * It returns a string peek_buf that contains the name of the 
 * element at the top of the stack.
 * @author UTILTJC
 * @param none
 * @return String peek_buf which holds the name of the top element
 * ***************************************************************/
final public String getCurElement() {
  String peek_buf = "";
  if (! empty()){
     peek_buf = peek().toString();
  }	
  return peek_buf;	
}

/*********************************************************************
 * This will check the stack to see if it contains a certain element.
 * input is a String elName; returns is boolean if it does not match
 * on first attempt, it will try to match again in both upper and
 * lower case.   sample call:  boolean hasName = hasElement("name")
 * @author UTILTJC
 * @param String elName - name of element to search for.
 * @return boolean found_it  true or false depending upon if element
 * is in stack or not.
 * 
 * *******************************************************************/
final public boolean hasElement(String elName) {
   boolean found_it = contains(elName);
   // *** if we did not find it then try again in both upper and lower case. ***
   if (! found_it){
   	  found_it = contains(elName.toLowerCase());
   	  if (! found_it){
   	  	found_it = contains(elName.toUpperCase());   
   	  }
   }
   return  found_it;
}


/**********************************************************************
 * Return the name of the next element behind the current element on
 * the stack.  Inputs none. Returns String parentBuf.  
 * The Previous element may or may not be a parent of the current 
 * element.  So this should be considered when using this method
 * String lastElement = getPrevElem();
 * @author UTILTJC
 * @param none
 * @return String parentBuf  - name of previous element.
 * ********************************************************************/
final public String getPrevElem(){
  String parentBuf = "";
  
  // *** size of stack must be greater than 1 for a previous element to exist ***
  if (size() > 1 ) {
  	 int parNum = size() - 2;  // *** subtract 2 since stack starts with an element at 0.
     parentBuf=elementAt(parNum).toString();
  }	
  else{
  	System.err.println("\nWarning: elementStack::getParent - the stack size is currently " + size() +
  	                   "\n         So it is not possible to return the name of a parent. " +
  	                   "\n         the method will return an empty string, processing continues.");
  }
  return parentBuf;	
}


/************************************************************************
 * Return size of the current stack of elements. Could be useful if a user
 * wanted to see how many open elements they had.  
 * returns an integer - int sSize      int stackSize = getStackSize();
 * @author UTILTJC
 * @param none
 * @return int sSize size of current stack of elements.
 * **********************************************************************/
final public int getStackSize(){
    int sSize=size();
    return sSize;
   	
}

}   // *** end of class elementStack ***
