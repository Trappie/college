/**
 * basic class for people participate in the game
 * it's the super class for both player and dealer
 * @author Di Wu
 */
public class Participant {
    // name of the participant
    protected String name;
    // the hand of cards in the game
    protected RandIndexQueue<Card> hand;
    // the table on which to play
    protected Table table;


    public Participant(String name) {
        this.name = name;
        this.hand = new RandIndexQueue<>(4);
    }

    /**
     * draw a card from the shoe
     */
    public void draw() {
        table.draw(hand, 1);
    }

    /**
     * different from draw for its output
     */
    public void hit () {
        table.draw(hand, 1);
        Table.log += "\n" + name + " hits: " + hand.get(hand.size()-1);
    }


    /**
     * play with simple strategy, same for player and dealer
     * stand until 17
     * @return 22 if blackjack, -1 if busted, real value other situation
     */
    public int play() {
        int value = calculate(false);
        if (value == 21) {
            value = 22;
        } else {
            while(value < 17 && value > -1) {
                hit();
                value = calculate(false);
            }
        }
        logResult(value);
        return value;
    }

    /**
     * call at the end of the play method, which log the result of the hand accordingly.BLACKJACK, BUSTS or STANDS
     * @param compareValue the compare value of the hand, which has 22 for blackjack and -1 for busted hand
     */
    protected void logResult(int compareValue) {
        String tmp = "";
        if (compareValue == 22) {
            tmp = name + " BLACKJACK: " + this;
        } else if (compareValue == -1) {
            tmp = name + " BUSTS: " + this;
        } else {
            tmp = name + " STANDS: " + this;
        }
        Table.log += "\n" + tmp;
    }

    /**
     * call before playing, one should be at a table to play.
     * @param table the table to play on
     */
    public void setTable(Table table) {
        this.table = table;
    }

    /**
     * notice that this method won't return value 22 if it's blackjack
     * calculate the value of the hand, the output depend on the flag
     * if the flag is true, then return the true value of the hand, highest value for non busted hand or lowest value for busted hand
     * if the flag is false, then return the comparable value of the hand, in which busted hand considered as -1
     * @param trueValue flag to control whether the true value or comparing value of the hand should be returned
     * @return the true value of the hand if true, and the comparing value of the hand if false
     */
    protected int calculate(boolean trueValue) {
        // to return the biggest unbusted number, if there is no such value, return -1
        // the smallest value of the hand, plus 10 could get the biggest value of the hand
        int minimumValue = calculateMinimumValue();
        boolean hasAce = false;
        for (int i=0; i<hand.size(); i++) {
            if(hand.get(i).value2() == 1) {
                hasAce = true;
            }
        }
        if (minimumValue > 21) {
            if(trueValue){
                return minimumValue;
            } else {
                return -1;// the comparing value of busted hand is -1
            }
        } else if (hasAce && minimumValue <= 11) {
            return minimumValue + 10;
        } else {
            return minimumValue;
        }

    }

    /**
     * calculate and return the smallest value of the hand, treat all Ace as 1 point
     * @return
     */
    protected int calculateMinimumValue() {
        int minimumValue = 0;
        for (int i=0; i<hand.size(); i++) {
            minimumValue += hand.get(i).value2();
        }
        return minimumValue;
    }


    /**
     * return the name of the participant
     * @return the name of the participant
     */
    public String getName () {
        return name;
    }

    @Override
    public String toString() {
        return hand + ": " + calculate(true);
    }

    /**
     * this method is only called when need hand to be show on the label of UI
     * used in interaction version of Blackjack
     * @return the String fit to show on the Label
     */
    public String showInLabel() {
        String result = "";
        for (int i=0; i<hand.size(); i++) {
            result += hand.get(i).value2() + " ";
        }
        result += ", total value: " + calculate(true);
        return result;

    }
}

