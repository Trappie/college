// the class is the combination of the logic expression and associated methods
// because it's for extra credit, I use String and StringBuilder classes, and looping for convenient
public class Expression {
	// the original string of the expression
	private MyStringBuilder2 exStrBuilder;
	// the string contains all the possible chars to be match.
	private String chars;
	// the mode of matching, -1 means match only one, 0 means match 0 or more, 1 means match 1 or more
	private int mode;

    // constructor method takes a well-formatted string as argument
	public Expression(String exStr) {
        MyStringBuilder2 builder = new MyStringBuilder2 (exStr);
        // decode the given string to get the proper mode and pattern info to represent the expression
		decodeExpression(builder);
	}

	// given strings, return expressions
    // static method called to get an array of expressions from given strings
	public static Expression[] getExpressions(String[] expressions) {
		Expression[] result = new Expression[expressions.length];
        initExpressionsRec(0, result, expressions);
		return result;
	}

    // act as a loop to get the Expression[] filled
    public static void initExpressionsRec (int index, Expression[] result, String[] expressions) {
        if (index != result.length) {
            result[index] = new Expression(expressions[index]);
            initExpressionsRec(index + 1, result, expressions);
        }
    }

    // return the mode of the current expression
	public int getMode() {
		return mode;
	}

    // to see whether char c is in this expression
	public boolean match(char c) {
        return containsRec(c, chars, 0);
	}

    // act as a loop to see whether the char c is in the string s (after given index)
    private boolean containsRec (char c, String s, int index) {
        if (index == s.length()) {
            return false;
        } else if (c == s.charAt(index)) {
            return true;
        } else {
            return containsRec(c, s, index + 1);
        }
    }

    // to tell whether the given string builder meet the requirement of the expression
    public boolean match(MyStringBuilder2 sb) {
        if (sb == null) {
            return false;
        } else if (sb.length() == 0) {
            if (this.mode == 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }



	// decode the string expression, if not valid, throw exceptions.
	// else, init the class with given information in the string
	private void decodeExpression(MyStringBuilder2 ex) {
		if (ex == null && ex.length() <= 2) {
			throw new IllegalArgumentException("the format of the argument string can't be understand");
		} else {
			char first = ex.charAt(0);
			char last = ex.charAt(ex.length() - 1);
			String toDecode;

			if (first == '[') { // if the first char is [, valid
				if (last == ']') { // the expression only matches one character
					mode = -1; 
					toDecode = ex.substring(1, ex.length() - 1 );
				} else if (last == '+') { 
					if (ex.charAt(ex.length() - 2) != ']') { // not valid
						throw new IllegalArgumentException("the format of the argument string can't be understand");
					} else { // the expression matches one or more chars
						mode = 1;
						toDecode = ex.substring(1, ex.length() - 2);
					}
				} else if (last == '*') { 
					if (ex.charAt(ex.length() - 2) != ']') { // not valid
						throw new IllegalArgumentException("the format of the argument string can't be understand");
					} else { // the expression matches 0 or more chars
						mode = 0;
						toDecode = ex.substring(1, ex.length() - 2);
					}
				} else { // if the last char is not one of the three
					throw new IllegalArgumentException("the format of the argument string can't be understand");
				} 
			} else { // if the first char is not "[", invalid
				throw new IllegalArgumentException("the format of the argument string can't be understand");
			}

			// if not throw any exception yet, it means the input string is valid, so decode starts.
			// this step puts every possible chars into the string "chars"
			this.exStrBuilder = ex;
			getChars(toDecode);
		}
	}

	// this method is called when the input string pattern is valid
	// this method load every possible chars into the string "chars"
	// this method assume the input matching type is valid, like abcd a-z abce-z
	private void getChars(String code) {
		// System.out.println("the code is: " + code);
		MyStringBuilder2 sb = new MyStringBuilder2();
        getCharsRec(code, sb, 0);
		this.chars = sb.toString();
	}

    // act as a loop, to add all possible chars in the code to this.chars
    private void getCharsRec(String code, MyStringBuilder2 sb, int index) {
        if (index != code.length()) {
            char curr = code.charAt(index);
            if (curr != '-') {
                sb.append(curr);
            } else {
                sb.append(getRange(code.charAt(index-1), code.charAt(index+1)));
            }
            getCharsRec(code, sb, index + 1);
        }
    }

	// get all chars in the range from start to end, exclusively
    // as an example, if start char is 'e', and end char is 'h', then this method return a StringBuilder with content: "fg"
    // the char come from (char)intValue of the given start and end
	// the range should always be valid
	private MyStringBuilder2 getRange(char start, char end) {
		MyStringBuilder2 sb = new MyStringBuilder2();
        appendCharRec(start + 1, sb, end);
		return sb; 
	}

    // act as a loop, append every char in the range to the given MyStringBuilder2 object
    private void appendCharRec(int startIndex, MyStringBuilder2 sb, int end) {
        if (startIndex != end) {
            sb.append((char)startIndex);
            appendCharRec(startIndex + 1, sb, end);
        }
    }

}
