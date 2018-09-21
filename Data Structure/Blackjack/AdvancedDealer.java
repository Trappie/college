/**
 * advanced version of dealer, which have more complex strategy.
 * 1. need to hit until get hard 17 or higher(17 with Ace counted as 11 is not allowed)
 * 2. meet rule 1 and if dealer value >= player value, stands
 * 3. meet rule 1 and if dealer value < player value, keep hitting
 * @author Di Wu
 */
public class AdvancedDealer extends Dealer{

    public AdvancedDealer(String name) {
        super(name);
    }

    @Override
    public int play() {
        while (calculateMinimumValue() < 17) {
            // need to get at least 17
            Table.log += "\nDealer: still < 17, need to hit";
            hit();
            Table.log += "\n" + this;
        }
        while (calculate(false) < table.players[0].getValue() && calculate(false) != -1) {
            // if value is still less than player, and not bust yet, dealer should hit
            Table.log += "\nDealer: still losing, need to hit";
            hit();
            Table.log += "\n" + this;
        }
        logResult(calculate(false));
        return calculate(false);
    }
}
