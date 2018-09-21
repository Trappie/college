/**
 * The blackjack main application which get two integer arguments, one for rounds and one for number of decks.
 * if the arguments are not valid, default value will be used, rounds = 10, and decks = 4;
 * @author Di Wu
 */
public class Blackjack {
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

        // the flag to show details of game or not
        boolean detailFlag = false;
        // if rounds number is less than or equal to 10, then show detail of every round
        if(rounds <= 10) {
            detailFlag = true;
        }

        // counting variables, which will be used to show the final result of the game
        int playerWins = 0;
        int dealerWins = 0;
        int pushes = 0;

        // for the easy version of the game, there is only one player on the table
        // at beginning, I wanted to make the game as flexible as possible, so I decide to use an array here
        // but I didn't actually use it in the program
        Player[] players = new Player[1];
        players[0] = new Player("Player");

        // there is only dealer on the table
        Dealer dealer = new Dealer("Dealer");

        // the table to play blackjack on
        Table table = new Table(decks, players, dealer);

        // the result for every round of the game
        int result;
        for (int i=0; i<rounds; i++) {
            result = table.easyRound(i, detailFlag);// every round will return an integer about the result of the game
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

    }


}
