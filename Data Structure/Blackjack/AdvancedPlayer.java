import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * advanced player strategy learned from online resources,
 * rules based on:
 * 1. assume that dealer has one card face down
 * 2. no splitting
 * 3. no double
 * 4. dealer must get 17 or higher
 * 5. notice that this strategy works when Blackjack win 1.5, but it's not actually reflected in this game
 * according to value in hand and the card dealer have, calculate the best strategy.
 * store the strategy char in another file which will be read when first object of player being created.
 * @author Di Wu
 */
public class AdvancedPlayer extends Player {
    private static final String WITHACE_PATH = "withAce";
    private static final String WITHOUTACE_PATH = "withoutAce";// delete "src/" when zipping to make it right
    private static final char[][] withAce = new char[8][10];
    private static final char[][] withoutAce = new char[12][10];

    public AdvancedPlayer(String name) {
        super(name);
        if (withAce[0][0] != 'H') {
            AdvancedPlayer.createCheatSheet();
        }
    }

    @Override
    public int play() {
        int value = calculate(false);
        Table.log += ("\nDealer's face up card is " + table.dealer.getFaceUpCard().value2());
        while(hitAble()) {
            hit();
        }
        value = calculate(false);
        logResult(value);
        return value;
    }

    /**
     * get strategy according to hand and the face up card of the dealer
     * @return should the player hit or not(stand)
     */
    private boolean hitAble() {
        int row, column = table.dealer.getFaceUpCard().value2() - 1;// the value of the face up card of the dealer
        int value = calculateMinimumValue();
        if (hasAce() && value <= 9 && value > 2) {
            // quiry the result from with ace form, row depend on minimumValue -1 (value except Ace)
            row = calculateMinimumValue() - 3;
            Table.log += ("\nplayer: I have ace, and my other card has value of " + (calculateMinimumValue() - 1) + "so the strategy should be " + withAce[row][column] );
            return withAce[row][column] == 'H';
        } else {
            if (value < 7) {
                return true;
            } else if (value > 18) {
                return false;
            } else {
                row = value - 7;
                Table.log += ("\nplayer: NO ACE, hand value: " + (calculateMinimumValue()) + " so the strategy should be " + withoutAce[row][column] );
                return withoutAce[row][column] == 'H';
            }
        }
    }

    /**
     * to tell whether there is an Ace in the hand
     * @return true if there is an Ace in hand
     */
    private boolean hasAce() {
        for (int i=0; i<hand.size(); i++) {
            if (hand.get(i).value2() == 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * to load the cheat sheet from another text file.
     */
    private static void createCheatSheet() {
        try {
            Scanner scanner = new Scanner(new File(WITHACE_PATH));
            for(int i=0; i<8; i++) {
                withAce[i] = scanner.nextLine().toCharArray();
            }
            scanner = new Scanner(new File(WITHOUTACE_PATH));
            for (int i=0; i<12; i++) {
                withoutAce[i] = scanner.nextLine().toCharArray();
            }
        } catch (FileNotFoundException e) {
            System.out.println("cheat sheet creating failed, exit now");
            System.exit(1);
        }

    }
}
