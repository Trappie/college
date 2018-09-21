public class TestGetChars {
	public static void main (String[] args) {
		MyStringBuilder builder = new MyStringBuilder();
		// fill the stingbuilder with chars whose ASCII code from 0 to 99
		for (int i=0; i<100; i++) {
			builder.append((char) i);	
		}
		// a char array with length of 20
		char[] charArray = new char[20];
		// fill the char array with '_'
		for (int i=0; i< charArray.length; i++) {
			charArray[i] = '_';
		}

		// testing
		System.out.println("Content of the MyStringBuilder is: ");
		System.out.println(builder);
		System.out.println("\nContent of the char array is: ");
		System.out.println(charArray);
		System.out.println("\nGet the chars from index 48 to 58, put them in the char array at index of 1");
		builder.getChars(48, 58, charArray, 1);
		System.out.println("\nNow the char array is: ");
		System.out.println(charArray);
	}
}