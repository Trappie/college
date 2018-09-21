// CS 0445 Spring 2018
// Read this class and its comments very carefully to make sure you implement
// the class properly.  Note the items that are required and that cannot be
// altered!  Generally speaking you will implement your MyStringBuilder using
// a singly linked list of nodes.  See more comments below on the specific
// requirements for the class.


// For more details on the general functionality of most of these methods,
// see the specifications of the similar method in the StringBuilder class.  
public class MyStringBuilder
{
	// These are the only three instance variables you are allowed to have.
	// See details of CNode class below.  In other words, you MAY NOT add
	// any additional instance variables to this class.  However, you may
	// use any method variables that you need within individual methods.
	// But remember that you may NOT use any variables of any other
	// linked list class or of the predefined StringBuilder or 
	// or StringBuffer class in any place in your code.  You may only use the
	// String class where it is an argument or return type in a method.
	private CNode firstC;	// reference to front of list.  This reference is necessary
							// to keep track of the list
	private CNode lastC; 	// reference to last node of list.  This reference is
							// necessary to improve the efficiency of the append()
							// method
	private int length;  	// number of characters in the list

	// You may also add any additional private methods that you need to
	// help with your implementation of the public methods.

	// Create a new MyStringBuilder initialized with the chars in String s
	public MyStringBuilder(String s)
	{
		if (s == null || s.length() == 0) // Special case for empty String
		{					 			  // or null reference
			firstC = null;
			lastC = null;
			length = 0;
		}
		else
		{
			// Create front node to get started
			firstC = new CNode(s.charAt(0));
			length = 1;
			CNode currNode = firstC;
			// Create remaining nodes, copying from String.  Note
			// how each new node is simply added to the end of the
			// previous one.  Trace this to see what is going on.
			for (int i = 1; i < s.length(); i++)
			{
				CNode newNode = new CNode(s.charAt(i));
				currNode.next = newNode;
				currNode = newNode;
				length++;
			}
			lastC = currNode;
		}
	}

	// Create a new MyStringBuilder initialized with the chars in array s
	public MyStringBuilder(char [] s)
	{
	    if (s == null || s.length == 0) {
	    	firstC = null;
	    	lastC = null;
	    	length = 0;
		} else {
	    	firstC = new CNode(s[0]);
	    	length = 1;
	    	CNode currNode = firstC;

	    	for (int i=1; i<s.length; i++) {
	    		CNode newNode = new CNode (s[i]);
	    		currNode.next = newNode;
	    		currNode = newNode;
	    		length++;
			}
			lastC = currNode;
		}
	}

	// Create a new empty MyStringBuilder
	public MyStringBuilder()
	{
		firstC = null;
		lastC = null;
		length = 0;
	}

	// Append MyStringBuilder b to the end of the current MyStringBuilder, and
	// return the current MyStringBuilder.  Be careful for special cases!
	public MyStringBuilder append(MyStringBuilder b)
	{ 
	    // return this.append(b.toString());
	    // I thought I couldn't access private properties in b directly at first, but it turned out I could
	    if (b != null && b.length != 0) {
    		CNode currB = b.firstC;
	    	for (int i=0; i<b.length; i++){
	    		this.lastC.next = new CNode(currB.data);
	    		this.lastC = this.lastC.next;
	    		currB = currB.next;
	    	}
	    	this.length += b.length;
	    }
	    return this;
	}


	// Append String s to the end of the current MyStringBuilder, and return
	// the current MyStringBuilder.  Be careful for special cases!
	public MyStringBuilder append(String s)
	{
	    if (s != null && s.length() != 0) { // if s is a valid string
            CNode currNode;
	        if (this.length == 0) { // if this string builder is empty
	            firstC = new CNode(s.charAt(0));
	            currNode = firstC;
            } else { // if the string builder is not empty
	            lastC.next = new CNode(s.charAt(0));
	            currNode = lastC.next;
            }
            length++;
            for (int i=1; i<s.length(); i++) {
	            currNode.next = new CNode(s.charAt(i));
	            currNode = currNode.next;
	            length++;
            }
            lastC = currNode;
        }
        return this;
	}

	// Append char array c to the end of the current MyStringBuilder, and
	// return the current MyStringBuilder.  Be careful for special cases!
    public MyStringBuilder append(char [] s)
    {
        if (s != null && s.length != 0) { // if s is a valid string
            CNode currNode;
            if (this.length == 0) { // if this string builder is empty
                firstC = new CNode(s[0]);
                currNode = firstC;
            } else { // if the string builder is not empty
                lastC.next = new CNode(s[0]);
                currNode = lastC.next;
            }
            length++;
            for (int i=1; i<s.length; i++) {
                currNode.next = new CNode(s[i]);
                currNode = currNode.next;
                length++;
            }
            lastC = currNode;
        }
        return this;
    }

	// Append char c to the end of the current MyStringBuilder, and
	// return the current MyStringBuilder.  Be careful for special cases!
	public MyStringBuilder append(char c)
	{
		if(this.length == 0) { // if the string builder is empty
			firstC = new CNode(c);
			lastC = firstC;
		} else { // is the string builder is not empty
			lastC.next = new CNode(c);
			lastC = lastC.next;
		}
		length++;
		return this;
	}

	// Return the character at location "index" in the current MyStringBuilder.
	// If index is invalid, throw an IndexOutOfBoundsException.
	public char charAt(int index)
	{
	    if (index < 0 || index >= length) {
	    	throw new IndexOutOfBoundsException();
		} else {
            return nodeAt(index).data;
		}
	}

	private CNode nodeAt(int index) {
	    assert index < 0 || index >= length;
	    CNode curr = firstC;
	    for (int i=0; i<index; i++) {
	        curr = curr.next;
        }
        return curr;
    }


	// Delete the characters from index "start" to index "end" - 1 in the
	// current MyStringBuilder, and return the current MyStringBuilder.
	// If "start" is invalid or "end" <= "start" do nothing (just return the
	// MyStringBuilder as is).  If "end" is past the end of the MyStringBuilder, 
	// only remove up until the end of the MyStringBuilder. Be careful for 
	// special cases!
	public MyStringBuilder delete(int start, int end)
	{
        if (end > length) { // let the end be valid first
            end = length;
        }
	    if (start >= 0 && start < length && end > start) {
            // now the start and end are both valid
            if (start == 0) { // if delete from the beginning
                firstC = nodeAt(end-1).next;
                length -= (end - start);
                if (length == 0) { // if delete to the end
                    lastC = null;
                }
            } else { // not delete from the beginning

	            CNode beforeStart = nodeAt(start - 1); // find the node before start
	            CNode curr = beforeStart;
	            for(int i = start; i<end; i++) {
	            	curr = curr.next;
	            }
	            beforeStart.next = curr.next;
	            if (end == length) { // if delete to the end
	            	// lastC = tmp;
	            	lastC = beforeStart;
	            }
	            length -= (end - start);
	        }
	    }
	    return this;
	}

	// Delete the character at location "index" from the current
	// MyStringBuilder and return the current MyStringBuilder.  If "index" is
	// invalid, do nothing (just return the MyStringBuilder as is).
	// Be careful for special cases!
	public MyStringBuilder deleteCharAt(int index)
	{
		if (index >= 0 && index < length) {
	        // valid index
            if (index == 0) {
                firstC = firstC.next;
                length--;
                if (length == 0) { // if deleting the last item
                    lastC = null;
                }
            } else {
                CNode tmp = nodeAt(index - 1);
                tmp.next = tmp.next.next;
                if (index == length - 1) { // is deleting the last item
                    lastC = tmp;
                }
                length--;
            }
        }
        return this;
	}

	// Find and return the index within the current MyStringBuilder where
	// String str first matches a sequence of characters within the current
	// MyStringBuilder.  If str does not match any sequence of characters
	// within the current MyStringBuilder, return -1.  Think carefully about
	// what you need to do for this method before implementing it.
	public int indexOf(String str)
	{
	    if (str == null || str.length() == 0) {
	        return -1; // if the string passed in is null or empty, return -1
        } else {
	        CNode startNode = firstC;
	        CNode currNode;
	        boolean match;
	        for (int i=0; i<=(length-str.length()); i++) { // only this many chances to get match.
	            currNode = startNode; // at the beginning of every try, let the inner point equal the outer pointer.
                match = true;
	            for (int j=0; j<str.length(); j++) {
	                if (currNode.data != str.charAt(j)) {
	                    match = false;
	                    break;
                    } else {
	                    currNode = currNode.next;
                    }
                }
                if (match) {
	                return i;
                } else {
	                startNode = startNode.next; // move to the next start point to check
                }
            }
            return -1; // if the loop end without return, then there is no such match
        }
	}

	// Insert String str into the current MyStringBuilder starting at index
	// "offset" and return the current MyStringBuilder.  if "offset" == 
	// length, this is the same as append.  If "offset" is invalid
	// do nothing.
	public MyStringBuilder insert(int offset, String str)
	{
	    if (offset == length) {
	        return append(str); // which will handle the case where this builder is empty
        }
	    if (offset >= 0 && offset < length) {// could be the first but not the last
	        if (str != null && str.length() != 0) { // if the string is valid to insert
                CNode tmpHead = new CNode(str.charAt(0));
                CNode curr = tmpHead;
                for (int i=1; i<str.length(); i++) {
                    curr.next = new CNode(str.charAt(i));
                    curr = curr.next;
                }
	            if (offset == 0) {
	                // insert from the beginning
                    curr.next = firstC;
                    firstC = tmpHead;
                } else { // insert in the middle of the list
                    CNode positionNode = nodeAt(offset - 1);
                    curr.next = positionNode.next;
                    positionNode.next = tmpHead;
                }
                length += str.length();
            }
        }
        return this;
	}

	// Insert character c into the current MyStringBuilder at index
	// "offset" and return the current MyStringBuilder.  If "offset" ==
	// length, this is the same as append.  If "offset" is invalid, 
	// do nothing.
	public MyStringBuilder insert(int offset, char c)
	{
		if (offset < 0 || offset > length) {
			return this;
		} else if (offset == 0) { // it works even when the string builder is empty
			CNode newNode = new CNode(c);
			newNode.next = firstC;
			firstC = newNode;
			length++;
			return this;
		} else if (offset == length) {
			return append(c);
		} else { // offset < length && offset > 0
			CNode beforeLocation = nodeAt(offset - 1);
			CNode newNode = new CNode(c);
			newNode.next = beforeLocation.next;
			beforeLocation.next = newNode;
			length++;
			return this;
		}
	}

	// Insert char array c into the current MyStringBuilder starting at index
	// index "offset" and return the current MyStringBuilder.  If "offset" is
	// invalid, do nothing.
	public MyStringBuilder insert(int offset, char [] c)
	{
	    if (offset == length) {
	        return append(c);
        }
	    if (offset >= 0 && offset < length) {// could be the first but not the last
	        if (c != null && c.length != 0) { // if the string is valid to insert
                CNode tmpHead = new CNode(c[0]);
                CNode curr = tmpHead;
                for (int i=1; i<c.length; i++) {
                    curr.next = new CNode(c[i]);
                    curr = curr.next;
                }
	            if (offset == 0) {
	                // insert from the beginning
                    curr.next = firstC;
                    firstC = tmpHead;
                } else { // insert in the middle of the list
                    CNode positionNode = nodeAt(offset - 1);
                    curr.next = positionNode.next;
                    positionNode.next = tmpHead;
                }
                length += c.length;
            }
        }
        return this;
	}

	// Return the length of the current MyStringBuilder
	public int length()
	{
	    return length;
	}

	// Delete the subcing from "start" to "end" - 1 in the current
	// MyStringBuilder, then insert String "c" into the current
	// MyStringBuilder starting at index "start", then return the current
	// MyStringBuilder.  If "start" is invalid or "end" <= "start", do nothing.
	// If "end" is past the end of the MyStringBuilder, only delete until the
	// end of the MyStringBuilder, then insert.  This method should be done
	// as efficiently as possible.  In particular, you may NOT simply call
	// the delete() method followed by the insert() method, since that will
	// require an extra traversal of the linked list.
	public MyStringBuilder replace(int start, int end, String c)
	{
        if ( start >= 0 && start < length && end > start && c != null) { // notice that c could be empty I think
            if (c.length() == 0) { // empty string
                return delete(start, end);
            } else {
                if (end > length) {
                    end = length;
                }
                CNode tmpHead = new CNode(c.charAt(0));
                CNode curr = tmpHead;
                for (int i=1; i<c.length(); i++) {
                    curr.next = new CNode(c.charAt(i));
                    curr = curr.next;
                }
                curr.next = nodeAt( end - 1 ).next;
                if (curr.next == null) {
                    lastC = curr;
                }
                length += c.length() - (end - start);
                if (start == 0) {
                    firstC = tmpHead;
                } else {
                    CNode startPosition = nodeAt(start - 1);
                    startPosition.next = tmpHead;
                }
            }
        }
        return this;
	}

	// Reverse the characters in the current MyStringBuilder and then
	// return the current MyStringBuilder.
	public MyStringBuilder reverse()
	{
	    if (length >= 2) {
	        // 0 or 1 are self reverse
            lastC = firstC;
            CNode faster = firstC.next;
            CNode tmp = firstC.next;
            while(faster.next != null) {
                faster = faster.next;
                tmp.next = firstC;
                firstC = tmp;
                tmp = faster;
            }
            tmp.next = firstC;
            firstC = tmp;
            lastC.next = null;
        }
        return this;
	}
	
	// Return as a String the substring of characters from index "start" to
	// index "end" - 1 within the current MyStringBuilder
	public String substring(int start, int end)
	{
	    String result = null;
	    if (start >= 0 && start < length && end > start && end <= length) {
	        char[] array = new char[end - start];
	        CNode curr = nodeAt(start);
	        for (int i=0; i<array.length; i++) {
	            array[i] = curr.data;
	            curr = curr.next;
            }
            result = new String(array);
        }
	    return result;
	}

	/**
	 * Characters are copied from this sequence into the destination character array dst. The first character to be copied is at index srcBegin; the last character to be copied is at index srcEnd-1. The total number of characters to be copied is srcEnd-srcBegin. The characters are copied into the subarray of dst starting at index dstBegin and ending at index:
	 * dstbegin + (srcEnd-srcBegin) - 1
	*/
	public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
		if (srcBegin < 0 || srcEnd > length || srcBegin > srcEnd || dstBegin < 0 || dstBegin + srcEnd - srcBegin > dst.length) {
			throw new IndexOutOfBoundsException();
		} else {
			CNode tmp = nodeAt(srcBegin);
			for (int i=0; i<srcEnd - srcBegin; i++) {
				dst[dstBegin + i] = tmp.data;
				tmp = tmp.next;
			}
		}

	}
	// Return the entire contents of the current MyStringBuilder as a String
	public String toString()
	{
	    String result = "";
	    if (length != 0) {
	        char[] array = new char[length];
	        CNode curr = firstC;
	        for (int i=0; i<length; i++) {
                array[i] = curr.data;
	            curr = curr.next;
            }
            result = new String(array);
        }
	    return result;
	}


	// You must use this inner class exactly as specified below.  Note that
	// since it is an inner class, the MyStringBuilder class MAY access the
	// data and next fields directly.
	private class CNode
	{
		private char data;
		private CNode next;

		public CNode(char c)
		{
			data = c;
			next = null;
		}

		public CNode(char c, CNode n)
		{
			data = c;
			next = n;
		}
	}
}

