/**
 * player class
 * @author Di Wu
 */
public class Player extends Participant {
    public Player(String name) {
        super(name);
    }

    /**
     * get the compare value of the hand.
     * need this method because in the game, dealer should always know the value in players hand
     * @return the compare value of the hand.
     */
    public int getValue() {
        return calculate(false);
    }
}
