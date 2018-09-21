/**
 * class represent dealer.
 * @author Di Wu
 */
public class Dealer extends Participant {
    public Dealer(String name) {
        super(name);
    }

    @Override
    public int play() {
        if(table.players[0].getValue() != -1) {
            // normal version strategy, if player not busted, play normally(super.play())
            return super.play();
        } else {
            // if player busted, win automatically
            int value = calculate(false);
            if (value == 21) {
                value = 22;
            }
            logResult(value);
            return value;

        }
    }

    /**
     * show the face up card of the initial two cards, (used in the advanced version)
     * @return
     */
    public Card getFaceUpCard() {
        return hand.get(0);
    }

    @Override
    public String showInLabel() {
        if(hand.size() == 2) {
            return "face up: " + hand.peek().value2();
        } else {
            return super.showInLabel();
        }
    }
}
