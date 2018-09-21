/**
* testing class to demonstrate the efficiency of the MyStringBuilder class
* including append, delete, and insert
*/
public class Assig2B {
    static StringBuilder stringBuilder;
    static MyStringBuilder myBuilder;
    static String string;
    static int n;


    public static void main (String[] args) {
        test(Integer.parseInt(args[0]));
    }

	public static void test (int n) {
		// n = Integer.parseInt(args[0]);
        Assig2B.n = n;
		stringBuilder = new StringBuilder();
		myBuilder = new MyStringBuilder();
		string = "";
        System.out.println("\nthe number for test is: " + n);
        testStringBuilder();
        testMyStringBuilder();
        testString();
	}

    public static void testStringBuilder() {
        long startTime, endTime, appendingTime, deletingTime, insertingTime;
        int insertLocation;
        StringBuilder stringBuilder = new StringBuilder();
        startTime = System.nanoTime();
        for (int i=0; i<n; i++) {
            stringBuilder.append('A');
        }
        endTime = System.nanoTime();
        appendingTime = endTime - startTime;

        startTime = System.nanoTime();
        for (int i=0; i<n; i++) {
            stringBuilder.delete(0,1);
        }
        endTime = System.nanoTime();
        deletingTime = endTime - startTime;

        startTime = System.nanoTime();
        for (int i=0; i<n; i++) {
            insertLocation = i / 2;
            stringBuilder.insert(insertLocation, 'A');
        }
        endTime = System.nanoTime();
        insertingTime = endTime - startTime;

        showResult("StringBuilder", appendingTime, deletingTime, insertingTime);
    }

    public static void testMyStringBuilder() {
        long startTime, endTime, appendingTime, deletingTime, insertingTime;
        int insertLocation;
        MyStringBuilder stringBuilder = new MyStringBuilder();
        startTime = System.nanoTime();
        for (int i=0; i<n; i++) {
            stringBuilder.append('A');
        }
        endTime = System.nanoTime();
        appendingTime = endTime - startTime;

        startTime = System.nanoTime();
        for (int i=0; i<n; i++) {
            stringBuilder.delete(0,1);
        }
        endTime = System.nanoTime();
        deletingTime = endTime - startTime;

        startTime = System.nanoTime();
        for (int i=0; i<n; i++) {
            insertLocation = i / 2;
            stringBuilder.insert(insertLocation, 'A');
        }
        endTime = System.nanoTime();
        insertingTime = endTime - startTime;

        showResult("MyStringBuilder", appendingTime, deletingTime, insertingTime);
    }

    public static void testString() {
        long startTime, endTime, appendingTime, deletingTime, insertingTime;
        int insertLocation;
        String string = "";
        startTime = System.nanoTime();
        for (int i=0; i<n; i++) {
            string += 'A';
        }
        endTime = System.nanoTime();
        appendingTime = endTime - startTime;

        startTime = System.nanoTime();
        for (int i=0; i<n; i++) {
            string = string.substring(1);
        }
        endTime = System.nanoTime();
        deletingTime = endTime - startTime;

        startTime = System.nanoTime();
        String firstHalf;
        String secondHalf;
        for (int i=0; i<n; i++) {
            // insertLocation = i/2;
            firstHalf = string.substring(0, i / 2 );
            secondHalf = string.substring(i / 2, string.length());
            // string = string.substring(0, string.length() / 2) + 'A' + string.substring(string.length() / 2, string.length());
            // don't know why, but the last line can be 50% slower than the following line.
            string = firstHalf + 'A' + secondHalf;
        }
        endTime = System.nanoTime();
        insertingTime = endTime - startTime;

        showResult("String", appendingTime, deletingTime, insertingTime);
    }


    public static void showResult(String name, long appendingTime, long deletingTime, long insertingTime){
        System.out.println("For " + name + ": ");
        System.out.println("  Appending total: " + appendingTime);
        System.out.println("  Appending average: " + appendingTime * 1.0 / n);
        System.out.println("  Deleting total: " + deletingTime);
        System.out.println("  Deleting average: " + deletingTime * 1.0 / n);
        System.out.println("  Inserting total: " + insertingTime);
        System.out.println("  Inserting average: " + insertingTime * 1.0 / n);
    }

}
