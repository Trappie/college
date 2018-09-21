// CS 0445 Spring 2018
// Read this class and its comments very carefully to make sure you implement
// the class properly.  The data and public methods in this class are identical
// to those MyStringBuilder, with the exception of the two additional methods
// shown at the end.  You cannot change the data or add any instance
// variables.  However, you may (and will need to) add some private methods.
// No iteration is allowed in this implementation. 

// For more details on the general functionality of most of these methods, 
// see the specifications of the similar method in the StringBuilder class.  
public class MyStringBuilder2
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

	// Create a new MyStringBuilder2 initialized with the chars in String s
	public MyStringBuilder2(String s)
	{
		if (s != null && s.length() != 0) {
			CNode newNode = new CNode(s.charAt(0));
			firstC = newNode;
			lastC = newNode;
			length++;
			lastC = addStringRec(s, 1, lastC); // an issue, if the string is only 1 on length
		} else {
            length = 0;
            firstC = null;
            lastC = null;
        }
	}

	// add a string from given index after the given node last
	// notice that the next item after last will lost after this operation
	// the return value is the last node after adding
	private CNode addStringRec(String s, int index, CNode last) {
		assert index >= 0 && index <= s.length() && last != null;
		if (index != s.length()) {
			CNode newNode = new CNode(s.charAt(index));	
			last.next = newNode;
			length++;
			return addStringRec(s, index + 1, newNode);
		} else {
			return last;
		}
	}

	// Create a new MyStringBuilder2 initialized with the chars in array s
	public MyStringBuilder2(char [] s)
	{
		if (s != null && s.length != 0) {
			CNode newNode = new CNode(s[0]);
			firstC = newNode;
			lastC = newNode;
			length++;
			lastC = addCharsRec(s, 1, lastC);
		} else {
            length = 0;
            firstC = null;
            lastC = null;
        }
	}

	// add a char[] from given index after the given node last
	// notice that the next item after last will lost after this operation
	// the return value is the last node after adding
	private CNode addCharsRec(char[] chars, int index, CNode last) {
		assert index >= 0 && index <= chars.length && last != null;
		if (index != chars.length) {
			CNode newNode = new CNode(chars[index]);
			last.next = newNode;
			length++;
			return addCharsRec(chars, index + 1, newNode);
		} else {
			return last;
		}
	}

	// Create a new empty MyStringBuilder2
	public MyStringBuilder2()
	{
		firstC = null;
		lastC = null;
		length = 0;
	}

	// Append MyStringBuilder2 b to the end of the current MyStringBuilder2, and
	// return the current MyStringBuilder2.  Be careful for special cases!
	public MyStringBuilder2 append(MyStringBuilder2 b)
	{
		if (b != null && b.length != 0) {
			if (length == 0) { // if current object is empty
				firstC = new CNode(b.firstC.data); // init the first node
				lastC = firstC;
				length++;
				lastC = addList(b.firstC.next, lastC); // call recursive method to add other nodes, and then set the last node
			} else {
				lastC = addList(b.firstC, lastC);
			}
		}
		return this;
	}

	// add the given cnode and after the cnode after the node last, notice that the original last.next will lost
    // similar to the other two private methods, addCharsRec and addStringRec
	private CNode addList(CNode firstNodeOfList, CNode last) {
		assert last != null;
		if (firstNodeOfList != null) {
			CNode newNode = new CNode(firstNodeOfList.data);
			last.next = newNode;
			length++;
			return addList(firstNodeOfList.next, newNode);
		} else {
			return last;
		}
	}


	// Append String s to the end of the current MyStringBuilder2, and return
	// the current MyStringBuilder2.  Be careful for special cases!
	public MyStringBuilder2 append(String s)
	{
		if (s != null && s.length() != 0) {
			if (length == 0) {
				firstC = new CNode(s.charAt(0));
				lastC = firstC;
				length++;
				lastC = addStringRec(s, 1, lastC);
			} else {
				lastC = addStringRec(s, 0, lastC);
			}
		}
		return this;
	}

	// Append char array c to the end of the current MyStringBuilder2, and
	// return the current MyStringBuilder2.  Be careful for special cases!
	public MyStringBuilder2 append(char [] c)
	{
		if (c != null && c.length != 0) {
			if (length == 0) {
				firstC = new CNode(c[0]);
				lastC = firstC;
				length++;
				lastC = addCharsRec(c, 1, lastC);
			} else {
				lastC = addCharsRec(c, 0, lastC);
			}
		}
		return this;
	}

	// Append char c to the end of the current MyStringBuilder2, and
	// return the current MyStringBuilder2.  Be careful for special cases!
	public MyStringBuilder2 append(char c)
	{
		if (length == 0) {
			firstC = new CNode(c);
			lastC = firstC;
			length++;
		} else {
			lastC.next = new CNode(c);
			lastC = lastC.next;
			length++;
		}
		return this;
	}

	// Return the character at location "index" in the current MyStringBuilder2.
	// If index is invalid, throw an IndexOutOfBoundsException.
	public char charAt(int index)
	{
		if (index >= 0 && index < length) {
			return nodeAt(0, index, firstC).data;
		} else {
			throw new IndexOutOfBoundsException();
		}
	}

	// find the index in the given index, currIndex and curr are the starting point of recursion
	private CNode nodeAt(int currIndex, int index, CNode curr) {
		assert curr != null && currIndex >= 0 && currIndex < length && index >= 0 && index < length;
		if (currIndex == index) {
			return curr;
		} else {
			return nodeAt(currIndex + 1, index, curr.next);	
		}
	}

	// Delete the characters from index "start" to index "end" - 1 in the
	// current MyStringBuilder2, and return the current MyStringBuilder2.
	// If "start" is invalid or "end" <= "start" do nothing (just return the
	// MyStringBuilder2 as is).  If "end" is past the end of the MyStringBuilder2, 
	// only remove up until the end of the MyStringBuilder2. Be careful for 
	// special cases!
	public MyStringBuilder2 delete(int start, int end)
	{
		if (end > length) {
			end = length;
		}
		if (start >= 0 && start < length && end > start) {
			CNode startNode;
			CNode endNode;
			if (start == 0) {
				startNode = firstC;
				endNode = nodeAt(0, end - 1, firstC);
				firstC = endNode.next;
			} else {
				startNode = nodeAt(0, start - 1, firstC);
				endNode = nodeAt(start - 1, end - 1, startNode); // notice that this doesn't contain unnecessary traversals
				startNode.next = endNode.next;
			}
			length -= (end - start);
			if (end == length) {
				lastC = startNode;
			}
		}
		return this;
	}

	// Delete the character at location "index" from the current
	// MyStringBuilder2 and return the current MyStringBuilder2.  If "index" is
	// invalid, do nothing (just return the MyStringBuilder2 as is).
	// Be careful for special cases!
	public MyStringBuilder2 deleteCharAt(int index)
	{
		if (index >= 0 && index < length) {
			if (index == 0) {
				firstC = firstC.next;
				length--;
				if (length == 0) {
					lastC = null;
				}
			} else {
				CNode prev = nodeAt(0, index - 1, firstC);
				prev.next = prev.next.next;
				length--;
				if (index == length - 1) {
					lastC = prev;
				}
			}
		}
		return this;
	}

	// Find and return the index within the current MyStringBuilder2 where
	// String str first matches a sequence of characters within the current
	// MyStringBuilder2.  If str does not match any sequence of characters
	// within the current MyStringBuilder2, return -1.  Think carefully about
	// what you need to do for this method before implementing it.
	public int indexOf(String str)
	{
		if (str == null || str.length() == 0) {
			return -1;
		} else {
			return matchStringIndex(0, firstC, str);
		}
	}

	// try to match the string from given index and node, the index and node should be associate
	private int matchStringIndex(int index, CNode node, String str) {
		if (matchSubstring(node, str, 0)) { // base case 1: found match
			return index;
		} else if (node == null) { // base case 2: unfound 
			return -1;
		} else { // recursive case: try in the following sequence
			return matchStringIndex(index + 1, node.next, str);
		}
	}

	// try to match the substring from given point
	private boolean matchSubstring(CNode startPoint, String str, int index) {
		if (index == str.length()) { // base case 1: find match
			return true;
		} else if (startPoint == null) { // base case 2: unfound till the end of string builder
			return false;
		} else {
			if (startPoint.data == str.charAt(index)) {
				return matchSubstring(startPoint.next, str, index + 1);
			} else {
				return false;
			}
		}
	}

	// Insert String str into the current MyStringBuilder2 starting at index
	// "offset" and return the current MyStringBuilder2.  if "offset" == 
	// length, this is the same as append.  If "offset" is invalid
	// do nothing.
	public MyStringBuilder2 insert(int offset, String str)
	{
		if (str != null && str.length() != 0) { // only operate when string is valid
			if (offset == length) { 
				append(str);
			} else if (offset == 0) {
				CNode newNode = new CNode(str.charAt(0));
				addStringRec(str, 1, newNode).next = firstC;
				firstC = newNode;
				length++;
			} else if (offset > 0 && offset < length) {
				CNode prev = nodeAt(0, offset - 1, firstC);
				CNode after = prev.next;
				addStringRec(str, 0, prev).next = after;
			}
		}
		return this;
	}


	// Insert character c into the current MyStringBuilder2 at index
	// "offset" and return the current MyStringBuilder2.  If "offset" ==
	// length, this is the same as append.  If "offset" is invalid, 
	// do nothing.
	public MyStringBuilder2 insert(int offset, char c)
	{
		if (offset == length) {
			append(c);
		} else if (offset == 0) {
			CNode newNode = new CNode(c);
			newNode.next = firstC;
			firstC = newNode;
			length++;
		} else if (offset > 0 && offset < length) {
			CNode prev = nodeAt(0, offset - 1, firstC);
			CNode newNode = new CNode(c);
			newNode.next = prev.next;
			prev.next = newNode;
			length++;
		}
		return this;
	}

	// Insert char array c into the current MyStringBuilder2 starting at index
	// index "offset" and return the current MyStringBuilder2.  If "offset" is
	// invalid, do nothing.
	public MyStringBuilder2 insert(int offset, char [] c)
	{
		if (c != null && c.length != 0) { // only operate when string is valid
			if (offset == length) { 
				append(c);
			} else if (offset == 0) {
				CNode newNode = new CNode(c[0]);
				addCharsRec(c, 1, newNode).next = firstC;
				firstC = newNode;
				length++;
			} else if (offset > 0 && offset < length) {
				CNode prev = nodeAt(0, offset - 1, firstC);
				CNode after = prev.next;
				addCharsRec(c, 0, prev).next = after;
			}
		}
		return this;
	}

    // Return the length of the current MyStringBuilder2
	public int length()
	{
		return length;
	}

	// Delete the substring from "start" to "end" - 1 in the current
	// MyStringBuilder2, then insert String "str" into the current
	// MyStringBuilder2 starting at index "start", then return the current
	// MyStringBuilder2.  If "start" is invalid or "end" <= "start", do nothing.
	// If "end" is past the end of the MyStringBuilder2, only delete until the
	// end of the MyStringBuilder2, then insert.  This method should be done
	// as efficiently as possible.  In particular, you may NOT simply call
	// the delete() method followed by the insert() method, since that will
	// require an extra traversal of the linked list.
	public MyStringBuilder2 replace(int start, int end, String str)
	{
		if (str != null && str.length() != 0) { // if the string is valid
			if (end > length) {
				end = length;
			}
			if (start >= 0 && start < end) { // if both indexes are valid
				CNode listHead = new CNode(str.charAt(0));
				length++;
				CNode listEnd = addStringRec(str, 1, listHead); // build the new list of node, and now list length added into total
				if (start == 0) {
					listEnd.next = nodeAt(0, end - 1, firstC).next;
					firstC = listHead;
					length = length - (end - start);
				} else {
					CNode prev = nodeAt(0, start - 1, firstC);
					listEnd.next = nodeAt(start - 1, end - 1, prev).next;
					prev.next = listHead;
					length = length - (end - start);
				}
				if (end == length) {
					lastC = listEnd;
				}
			}
		}
		return this;
	}

	// Reverse the characters in the current MyStringBuilder2 and then
	// return the current MyStringBuilder2.
	public MyStringBuilder2 reverse()
	{
		if (length != 0) {
			CNode tmpEnd = reverseRec(firstC);
			firstC = lastC;
			lastC = tmpEnd;
			lastC.next = null;
		}
		return this;
	}

    // reverse from the given node to the end
	private CNode reverseRec(CNode subHead) {
		if (subHead.next == null) { // base case: if current node is the last node of the list
			return subHead;
		} else { // recursive case: if current node is not the last node
			reverseRec(subHead.next).next = subHead; // reverse the sublist start from the next node and add current node at the end of the list
			return subHead;	 // return the node at the end of the list
		}
	}
	
	// Return as a String the substring of characters from index "start" to
	// index "end" - 1 within the current MyStringBuilder2
	public String substring(int start, int end)
	{
		if (start >= 0 && end <= length && start < end) {
			char[] charArray = new char[end - start];
			CNode startNode = nodeAt(0, start, firstC);
			fillArrayRec(charArray, 0, startNode); 
			return new String(charArray);
		} else {
			return null;
		}
	}

    // act like looping
    // add char in the char array at the given index to the given node
	private void fillArrayRec(char[] charArray, int index, CNode startNode) {
		if (index != charArray.length) {
			charArray[index] = startNode.data;
			fillArrayRec(charArray, index + 1, startNode.next);
		}

		
	}

	// Return the entire contents of the current MyStringBuilder2 as a String
	public String toString()
	{
		char[] charArray = new char[length];
		fillArrayRec(charArray, 0, firstC);
		return new String(charArray);
	}

	// Find and return the index within the current MyStringBuilder2 where
	// String str LAST matches a sequence of characters within the current
	// MyStringBuilder2.  If str does not match any sequence of characters
	// within the current MyStringBuilder2, return -1.  Think carefully about
	// what you need to do for this method before implementing it.  For some
	// help with this see the Assignment 3 specifications.
	public int lastIndexOf(String str)
	{
        if (str == null || str.length() == 0) {
            return -1;
        } else {
            return findLastIndex(0, firstC, str);
        }
	}

    // find the last match of substring recursively, from a given node and associated index.
	private int findLastIndex(int currIndex, CNode currNode, String str) {
		if (currNode == null || length - str.length() < currIndex){ // if already hit the end of the list or it's not possible to find it because the remaining is not long enough.
			return -1;
		} else {
			int indexReturned = findLastIndex(currIndex + 1, currNode.next, str); // try to find match in the sublist from the next node
			if (indexReturned != -1) { // if find match from after the current node, return the index got as the result
				return indexReturned;
			} else if (matchSubstring(currNode, str, 0)) { // if not find any match from after the current node, but current node works, return the index of current node
				return currIndex;
			} else { // if there is no match from current node till the end, return -1 as false
				return -1;
			}
		}
	}
	
	// Find and return an array of MyStringBuilder2, where each MyStringBuilder2
	// in the return array corresponds to a part of the match of the array of
	// patterns in the argument.  If the overall match does not succeed, return
	// null.  For much more detail on the requirements of this method, see the
	// Assignment 3 specifications.
	public MyStringBuilder2 [] regMatch(String [] pats)
	{
		MyStringBuilder2[] result = new MyStringBuilder2[pats.length]; // result array of MyStringBuilder2 for further use
		initStringBuilderArrayRec(0, result); // create the array for the result, every builder will be empty at first
		return regMatchFromNode(firstC, result, pats); // try from the first node
	}

	// act as a loop to allocate new objects to the string builder array.
	private void initStringBuilderArrayRec(int index, MyStringBuilder2[] array) {
		if (index != array.length) {
			array[index] = new MyStringBuilder2();	
			initStringBuilderArrayRec(index + 1, array);
		}
	}

	// match the given patterns from the given node, act like a loop
	private MyStringBuilder2[] regMatchFromNode(CNode startNode, MyStringBuilder2[] result, String[] pats) {
		if (startNode == null) {
			return null;
		} else if (matchPatternRec(startNode, result, pats, 0)) { // if the match succeed here
			return result;
		} else {
			return regMatchFromNode(startNode.next, result, pats);
		}
	}

    // logic of the regular expression match
	private boolean matchPatternRec(CNode currNode, MyStringBuilder2[] buffer, String[] pats, int currPatIndex) {
		if (currNode == null) { // base case: if the curr node is null
			if (buffer[buffer.length - 1].length != 0) { // if the last MyStringBuilder is not empty, then match succeed
				return true;
			} else { // if the last MyStringBuilder is empty, then the match failed
				return false;
			}
		} else { 
			buffer[currPatIndex].append(currNode.data); // add the data into buffer first
			if (containRec(currNode.data, pats[currPatIndex], 0)) { // recursive case: if curr match the given pattern
				if (matchPatternRec(currNode.next, buffer, pats, currPatIndex)) { // branch 1: next node succeed for current pattern too
					return true;
				} else if (currPatIndex == pats.length - 1) { // branch 2: next node not succeed, but current pattern is already the last one, succeed
					return true;
				} else if (matchPatternRec(currNode.next, buffer, pats, currPatIndex + 1)) { // branch 3: next node not succeed current pattern but succeed in next pattern
					return true;
				} else if (buffer[currPatIndex].length != 0) { // if my next failed, but this node is not the only match of current patter
					if (currPatIndex == pats.length - 1) { // if the current is the last pattern
						return true;
					} else { // if not last pattern, need to try the next pattern
						buffer[currPatIndex].deleteLast();
						return matchPatternRec(currNode, buffer, pats, currPatIndex + 1);
					} 
				} else {
					buffer[currPatIndex].deleteLast();
					return false;					
				}
			} else { // base case: if current node doesn't match given pattern, return false
				buffer[currPatIndex].deleteLast();
				return false;
			}
		}
	}

	private boolean containRec(char c, String s, int index) {
		if (index == s.length()) {
			return false;
		} else if (c == s.charAt(index)){
			return true;
		} else {
			return containRec(c, s, index + 1);
		}
	}

	private void deleteLast() {
		if (length != 0) {
			deleteCharAt(length - 1);
		}
	}

	// [abc] means match one character from a, b or c
	// [abc]+ means match one or more character from a, b, or c
	// [abc]* means match zero or more character from a, b, or c
	// [a-c] means [abc] and so on
	// if possible [abcx-z] == [a-cx-z] means a,b,c,x,y,z
    // assume the input pattern strings are always correct
	public MyStringBuilder2[] regMatchAdvanced(String[] pats) {
		Expression[] expressions = Expression.getExpressions(pats);
		MyStringBuilder2[] result = new MyStringBuilder2[expressions.length];
		initStringBuilderArrayRec(0, result);
		result = loopNode(firstC, expressions, result);
		return result;
	}

	// try the given node, if succeed, return result, if not, try next, until the end of the list
    // it's the recursive version of looping from the first node to the end node
	private MyStringBuilder2[] loopNode(CNode startNode, Expression[] expressions, MyStringBuilder2[] result) {
		if (startNode == null) { // if the startNode of the input is null, return null
			return null;
		} else { // if the startNode is not null, try to match it
			if (tryNode(startNode, expressions, result, 0)) {
				return result;
			} else { // if current startNode is not succeed, try next, until the last node of the list
				return loopNode(startNode.next, expressions, result);
			}
		}
	}

    // recursively compare the every results to expressions
    private boolean rightResultRec(int index, Expression[] expressions, MyStringBuilder2[] result) {
        if (index == expressions.length) {
            return true;
        } else if (expressions[index].match(result[index])) {
            return rightResultRec(index + 1, expressions, result);
        } else {
            return false;
        }
    }
	// recursive logic of the regMatchAdvanced
	private boolean tryNode(CNode currNode, Expression[] expressions, MyStringBuilder2[] result, int currPattern) {
		if (currNode == null) { // BASE CASE 1:  reach the end of the list
            return rightResultRec(0, expressions, result);
		}

		// BASE CASE 2: current node doesn't match
		if (!expressions[currPattern].match(currNode.data)) {
			if (currPattern == expressions.length - 1) { // final pattern reached
				if (result[currPattern].length() != 0 || expressions[currPattern].getMode() == 0) {
					return true;
				} else {
					return false;
				}
			} else { // final pattern not reached
				if (expressions[currPattern].getMode() == 0) {
					return tryNode(currNode, expressions, result, currPattern + 1);
				} else {
					return false;
				}
			}
		} else { // RECURSIVE CASE: current node match to current pattern
			result[currPattern].append(currNode.data); // add before trying, because it matches
			if (expressions[currPattern].getMode() == -1) { // if current only match exact 1 char
				if (currPattern == expressions.length - 1) { // if this 1 match is the last pattern
					return true;
				} else if (tryNode(currNode.next, expressions, result, currPattern + 1)) { // if not last pattern, and next node match from next pattern succeed
					return true;
				} else { // if not last pattern, and next node for next pattern failed.
					result[currPattern].deleteLast();
					return false;
				}
			} else { // match current and not only match 1(0 or more or 1 or more)
				if (tryNode(currNode.next, expressions, result, currPattern)) { // if next node works from the current pattern
					return true;
				} else { // if next node not work from the current pattern
					if (tryNode(currNode.next, expressions, result, currPattern + 1)) { // if next node works in next pattern
						return true;
					} else {
						result[currPattern].deleteLast(); // delete current node from already match
                        if (expressions[currPattern].match(result[currPattern])) {// if after deleting, current pattern is still fulfilled, try this node on next pattern
                            return tryNode(currNode, expressions, result, currPattern + 1);
                        } else {
                            return false;
                        }
					}
				}
			}
		} 
	}

	
	// You must use this inner class exactly as specified below.  Note that
	// since it is an inner class, the MyStringBuilder2 class MAY access the
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

