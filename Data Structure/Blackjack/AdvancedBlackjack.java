/**
 * the advanced version of Blackjack, 90% code here are identical with Blackjack.java.
 * the only difference is when creating player and dealer, I create instances of AdvancedPlayer and AdvancedDealer
 * which have different behaviors while playing, comparing to the normal Player and Dealer.
 * @author Di Wu
 */
public class AdvancedBlackjack {
    public static void main(String[] args) {

        int rounds = 10;
        int decks = 4;

        // make sure the arguments input are valid.
        try {
            rounds = Integer.parseInt(args[0]);
            decks = Integer.parseInt(args[1]);
            if(rounds > 0 && decks > 0) {
                System.out.println("Starting Blackjack with " + rounds + " rounds and " + decks + " decks in the shoe\n");
            } else {
                rounds = 10;
                decks = 4;
                System.out.println("Invalid arguments, starting Blackjack with default arguments: " + rounds + " rounds and " + decks + "decks in the shoe\n");
            }
        } catch (Exception e) {
            System.out.println("Invalid arguments, starting Blackjack with default arguments: " + rounds + " rounds and " + decks + "decks in the shoe\n");
        }

        boolean detailFlag = false;
        // if rounds number is less than or equal to 10, then show detail of every round
        if(rounds <= 10) {
            detailFlag = true;
        }
        int playerWins = 0;
        int dealerWins = 0;
        int pushes = 0;

        // still only one player on the table
        Player[] players = new Player[1];
        // the player here is an AdvancedPlayer
        players[0] = new AdvancedPlayer("Player");

        // there is only dealer on the table, and the dealer is an AdvancedDealer
        Dealer dealer = new AdvancedDealer("Dealer");
        // the table to play blackjack on
        Table table = new Table(decks, players, dealer);


        System.out.println("Starting Blackjack with " + rounds + " rounds and " + decks + " decks in the shoe\n");
        int result;
        for (int i=0; i<rounds; i++) {
            result = table.easyRound(i, detailFlag);
            switch(result) {
                case -1:
                    playerWins++;
                    break;
                case 0:
                    pushes++;
                    break;
                case 1:
                    dealerWins++;
                    break;
            }
        }

        System.out.println("\nAfter " + rounds + " rounds, here are the results: \n" +
                "   Dealer Wins: " + dealerWins + "\n" +
                "   Player Wins: " + playerWins + "\n" +
                "   Pushes: " + pushes);

        System.out.println("\nIn this advanced version of Blackjack: \n" +
                "1. Dealer will only reveal one card at the beginning of round. \n" +
                "2. And they need to get at least HARD 17, which count Ace as 1\n" +
                "3. Player choose their strategy from a cheat sheet, according to the value in hand and the face up card of the dealer\n" +
                "4. Dealer will hit until win or bust even after 17, if the player has higher value");

    }
}
