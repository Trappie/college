import javax.swing.*;

/**
 * the table to play blackJack, which is the core of the game
 * @author Di Wu
 */
public class Table {
    // the shoe of the game
    private RandIndexQueue<Card> shoe;
    // the discardPile of the game
    private RandIndexQueue<Card> discardPile;

    // control the UI view to show information in interaction version of Blackjack
    private ViewController controller;

    // is the game still playing, used in interaction version
    private boolean inGame;
    // the log to show the detail of every round, if the detailFlag is true
    public static String log = "";

    // how many decks used in the game
    int decks;
    // if the quantity of cards in the shoe lower than the number, then need reshuffle
    int shuffleLine;

    // all players in this array
    Player[] players;
    // the only dealer of the game
    Dealer dealer;

    public Table(int decks, Player[] players, Dealer dealer) {
        this.decks = decks;
        this.players = players;
        this.dealer = dealer;
        this.shuffleLine = decks * 52 / 4;

        // init the shoe with given number of decks
        this.shoe = new RandIndexQueue<>(52 * decks);
        for(int i=0; i<decks; i++) {
            for (Card.Suits s : Card.Suits.values()) {
                for (Card.Ranks r : Card.Ranks.values()) {
                    shoe.offer(new Card(s, r));
                }
            }
        }
        shoe.shuffle();

        this.discardPile = new RandIndexQueue<>(3 * shuffleLine);
        // tell every Participant they are playing on this table
        for (Participant p : players) {
            p.setTable(this);
        }
        dealer.setTable(this);
    }
    /**
     * Participant will draw a card with this method
     * @return a card from the shoe
     */
    public void draw(RandIndexQueue<Card> hand, int quantity) {
        Table.move(shoe, hand, quantity);
    }

    /**
     * for participant to discard all their cards into discard pile after a round.
     */
    public void discard() {
        for (Participant p : players) {
            move(p.hand, discardPile, 0);
        }
        move(dealer.hand, discardPile, 0);
    }

    /**
     * called at the end of each round, check whether need reshuffle the shoe
     * print log while reshuffle
     * @param roundID the count of round for reshuffle log output
     */
    private void needShuffle(int roundID) {
        if(shoe.size() < shuffleLine) {
            Table.move(discardPile, shoe, 0);
            shoe.shuffle();
            System.out.println("reshuffle the shoe in round " + roundID);
        }
    }


    /**
     * play a round of the game
     * @param roundID the count of the round for further output
     * @param detailFlag whether to show the detail about the round
     * @return 1 if dealer wins, -1 if player wins, 0 is push
     */
    public int easyRound(int roundID, boolean detailFlag) {
        inGame = true;
        int result = 0;
        initRound();
        updateViews();// update the views after the init of hands
        int playerValue = players[0].play();
        updateViews();// update the views after the play of player
        inGame = false;
        int dealerValue = dealer.play();
        updateViews();// update the views after the play of dealer
        // determine the result of the round
        if (dealerValue > playerValue) {
            result = 1;
        } else if (dealerValue == playerValue) {
            result = 0;
        } else {
            result = -1;
        }

        // show details of the game if the flag is true
        if (detailFlag) {
            // show detail of game;
            System.out.println("Round " + roundID + " beginning");
            System.out.println(Table.log);
            System.out.print("Result: ");
            switch(result) {
                case -1:
                    if (playerValue == 22) {
                        System.out.println("Player wins Blackjack!");
                    } else {
                        System.out.println("Player wins!");
                    }
                    break;
                case 0:
                    System.out.println("Push!");
                    break;
                case 1:
                    if (dealerValue == 22) {
                        System.out.println("Dealer wins Blackjack!");
                    } else {
                        System.out.println("Dealer wins!");
                    }
                    break;
            }
            System.out.println("");
        }

        discard();
        needShuffle(roundID);
        return result;
    }

    /**
     * to deal every player and the dealer 2 cards alternatively
     */
    public void initRound() {
        // clean the log for last round
        Table.log = "";
        for(Participant p : players) {
            p.draw();
        }
        dealer.draw();
        for(Participant p : players) {
            p.draw();
            Table.log += p.getName() + " " + p + "\n";
        }
        dealer.draw();
        Table.log += dealer.getName() + " " + dealer;
    }
    /**
     *
     * @param from the source of the cards to move
     * @param to the destination of the cards to move
     * @param quantityToMove if 0, means move every card in "from" to "to", or move only one card
     */
    static void move(RandIndexQueue<Card> from, RandIndexQueue<Card> to, int quantityToMove) {
        if (quantityToMove == 0) {
            while(from.size() != 0) {
                to.offer(from.poll());
            }
        } else {
            if(quantityToMove <= from.size()) { //if there are enough cards to move
                for(int i=0; i<quantityToMove; i++) {
                    to.offer(from.poll());
                }
            } else {
                throw new IllegalArgumentException("there are not enough cards here");
            }
        }
    }

    /**
     * indicator for whether the player is playing in the game, used in interaction version of the game
     * @return true if the player is still playing
     */
    public boolean isInGame() {
        return inGame;
    }

    /**
     * to init the view controller in the table to update information to UI
     * @param player the Label to show player's hand
     * @param dealer the label to show dealer's hand
     */
    public void initViewController(JLabel player, JLabel dealer) {
        if (this.controller == null) {
            this.controller = new ViewController(player, dealer);
        }
    }

    /**
     * to update the labels to show hands of player and dealer
     */
    public void updateViews() {
        if (this.controller != null) {
            this.controller.update();
        }
    }

    /**
     * private class to handle the view update from table to UI Views
     */
    private class ViewController {
        private JLabel playerHand;
        private JLabel dealerHand;

        private ViewController (JLabel player, JLabel dealer) {
            this.playerHand = player;
            this.dealerHand = dealer;
        }

        private void update () {
            playerHand.setText(players[0].showInLabel());
            dealerHand.setText(dealer.showInLabel());
        }
    }



}
