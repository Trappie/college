public class Test {
	public static void main(String[] args) {
        
        System.out.println("***************************************");
        System.out.println("* overall demo about the extra credit *");
        System.out.println("***************************************");
		// the following are matching pattern strings for the method regMatchAdvanced()
		String s1 = "[a-z]"; // 1 from a to z
		String s2 = "[a-z]*"; // 0 or more from a to z
		String s3 = "[a-z]+"; // 1 or more from a to z

		String s4 = "[0-9]"; // 1 from 0 to 9
		String s5 = "[0-9]*"; // 0 or more from 0 to 9
		String s6 = "[0-9]+"; // 1 or more from 0 to 9

		String s7 = "[@]"; // 1 @
		String s8 = "[@]*"; // 0 or more @
		String s9 = "[@]+"; // 1 or more @
		
		MyStringBuilder2 sb1 = new MyStringBuilder2("kkkkkkkkabb0xxxwudixxx@gmail.com0ckkkkkabbc9989diw26@@@@@@@@pitt.edu");
		String[] pattern1 = {s3, s6, s7}; // this pattern looks like : 1 or more letters, 1 or more numbers, and 1 @
        testMatch(sb1, pattern1);

		String[] pattern2 = {s3, s5, s7}; // this pattern looks like: 1 or more letters, 0 or more numbers, and 1 @
        testMatch(sb1, pattern2);

		String[] pattern3 = {s3, s6, s9}; // this pattern looks like: 1 or more letters, 1 or more numbers, and 1 or more @
        testMatch(sb1, pattern3);

        String d = "[B2]+";
        String e = "[2]"; // match exactly one '2'
        String f = "[2]+";
        String[] pats2 = {d, e, f};
        MyStringBuilder2 sb2 = new MyStringBuilder2("BBB222YYYCCC");
        testMatch(sb2, pats2);


        System.out.println("******************************");
        System.out.println("* Test for skipping patterns *");
        System.out.println("******************************");
        String h = "[abc]+";
        String i = "[123]*";
        String j = "[k]*";
        String[] pats3 = {h, i, j};
        MyStringBuilder2 sb3 = new MyStringBuilder2("kkkabbcjjjj");
        testMatch(sb3, pats3);
        sb3 = new MyStringBuilder2("kkkkaac122kkkk");
        testMatch(sb3, pats3);
        String[] pats4 = {i, h, j};
        sb3 = new MyStringBuilder2("hhhhhaabchhhh");
        testMatch(sb3, pats4);
        sb3 = new MyStringBuilder2("hhhh23abchhh");
        testMatch(sb3, pats4);

        // the following are the original test in Assig3.java, I rewrite them with the format of []*+ and test the result, which match the proper output
        System.out.println("**************************************************************");
        System.out.println("* The following patterns come from the Assig3.java, it works *");
        System.out.println("**************************************************************");
        String patA = "[ABC123XYZ]+";
        String patB = "[123]+";
        String patC = "[XYZ]+";
        String [] patterns = { patA, patB, patC };
        MyStringBuilder2 B = new MyStringBuilder2("BBB222YYYCCC");
        testMatch(B, patterns);

        // it's convinient to use these expressions to do regular express match
        String str1 = "[A-Za-z]+";
        String str2 = "[0-9]+";
        String str3 = "[A-Za-z0-9]+";
        String str4 = "[*]+";
        String [] pat1 = {str3};
        String [] pat2 = {str2};
        String [] pat3 = {str1, str2};
        String [] pat4 = {str3, str2, str1};
        String [] pat5 = {str3, str2};
        String [] pat6 = {str3, str2, str4};
        String [] pat7 = {str3, str2, str1, str3, str2};

        MyStringBuilder2 b1 = new MyStringBuilder2("1234HelloThere456Friends---");
        MyStringBuilder2 b2 = new MyStringBuilder2("R2D2IsAVeryWittyDroid");
        MyStringBuilder2 b3 = new MyStringBuilder2("AA22-ABC123abc-ABC123***-BCA321***");

        testMatch(b1, pat1);
        testMatch(b1, pat2);
        testMatch(b1, pat3);
        testMatch(b1, pat4);
        testMatch(b2, pat1);
        testMatch(b2, pat5);
        testMatch(b3, pat6);
        testMatch(b2, pat6);
        testMatch(b1, pat7);

	}
	

	// static method to show the given result of regMatchAdvanced
    // because it's for extra credit, I use iteration for conveniement
	public static void showMatchResult(MyStringBuilder2[] result) {
        if (result == null) {
            System.out.println("No match found!");
        } else {
            for (int i=0; i<result.length; i++) {
                System.out.print(" " + result[i].toString());
            }
            System.out.println("");
        }
	}

    public static void testMatch(MyStringBuilder2 b, String[] patterns) {
        MyStringBuilder2[] result = b.regMatchAdvanced(patterns);
        String pattString = "Looking for pattern: ";
        for (int i=0; i<patterns.length; i++) {
            pattString += patterns[i];
        }
        System.out.println(pattString);
        System.out.println("Looking in string: " + b.toString());
        System.out.print("Match: ");
        showMatchResult(result);
        System.out.println("");
    }
	
}
