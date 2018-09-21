/**
 * represent players controlled by human instead of program
 * used in the interaction version of the game
 * @author Di Wu
 */
public class HumanPlayer extends Player {

    private boolean notStand = false;

    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public int play() {
        // let the player decide to hit or stand
        notStand = true;
        while(true){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!(notStand && notBusted())) {
                break;
            }
        }
        return calculate(false);
    }

    public void stand() {
        notStand = false;
    }


    /**
     * check whether the player's hand has busted
     * @return return true if not busted
     */
    private boolean notBusted() {
        int comparableValue = calculate(false);
        return (comparableValue >= 0 && comparableValue <= 21) ;
    }
}
