// in Assig2B.java, delete is only tested at the front of the structure, delete(0,1)
// this class is used to test delete in the middle

public class ExtraEfficiency {
	static int number;
	static StringBuilder javaStringBuilder;
	static MyStringBuilder myStringBuilder;
	public static void main(String[] args) {
		javaStringBuilder = new StringBuilder();
		myStringBuilder = new MyStringBuilder();
		// init the string builders to delete
		System.out.println("Testing, the number is 160,000, so it might take some time");
		test(160000);
		System.out.println("It's obvious that when N is large enough, StringBuilder is much faster than MyStringBuilder when deleting from the middle");
	}

	// run the test with given round
	public static void test(int count) {
		number = count;
		for (int i=0; i<number; i++) {
			javaStringBuilder.append('a');
			myStringBuilder.append('a');
		} 
		System.out.println("\nfor " + number + " items, the result is :");
		testStringBuilder();
		testMyStringBuilder();
	}

	// test the StringBuilder of Java
	public static void testStringBuilder() {
		long startTime, endTime, totalTime;
		int location;
		startTime = System.nanoTime();
		for (int i=0; i<number; i++) {
			location = (number - i) / 2;
			javaStringBuilder.delete(location, location + 1);
		}
		endTime = System.nanoTime();
		totalTime = endTime - startTime;
		System.out.println("the total time is " + totalTime);
		System.out.println("and the average time for one operation is " + totalTime / (double)number);
	}

	// test the MyStringBuilder of me
	public static void testMyStringBuilder() {
		long startTime, endTime, totalTime;
		int location;
		startTime = System.nanoTime();
		for (int i=0; i<number; i++) {
			location = (number - i) / 2;
			myStringBuilder.delete(location, location + 1);
		}
		endTime = System.nanoTime();
		totalTime = endTime - startTime;
		System.out.println("the total time is " + totalTime);
		System.out.println("and the average time for one operation is " + totalTime / (double)number);
	}
}