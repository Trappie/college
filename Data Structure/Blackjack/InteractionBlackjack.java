import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * interaction version of blackjack, in which player can play with AdvancedDealer
 * please enjoy
 * @author Di Wu
 */
public class InteractionBlackjack {
    public static void main(String args[]){

        // default number of decks is 8
        int decks = 8;
        HumanPlayer player = new HumanPlayer("Player");
        Dealer dealer = new AdvancedDealer("Dealer");
        Table table = new Table(decks, new HumanPlayer[]{player}, dealer);


        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainP = new JPanel();
        JPanel dealerP = new JPanel();
        JPanel playerP = new JPanel();
        JPanel resultP = new JPanel();
        JPanel buttonP = new JPanel();

        JButton hitB = new JButton("Hit");
        JButton standB = new JButton("Stand");
        JLabel playerHand = new JLabel("player hand ");
        JLabel dealerHand = new JLabel("dealer hand");
        JLabel resultLabel = new JLabel("");

        window.add(mainP);
        mainP.setLayout(new GridLayout(4,1));
        mainP.add(dealerP);
        dealerP.add(new JLabel("Dealer: "));
        dealerP.add(dealerHand);
        mainP.add(playerP);
        playerP.add(new JLabel("Player: "));
        playerP.add(playerHand);
        table.initViewController(playerHand, dealerHand);
        mainP.add(resultP);
        resultP.add(resultLabel);


        mainP.add(buttonP);
        buttonP.add(hitB);
        hitB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("button hit pressed " + player);
                if(table.isInGame()) {
                    player.hit();
                    table.updateViews();
                }
            }
        });
        buttonP.add(standB);
        standB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("button stand pressed" + player);
                if (table.isInGame()) {
                    player.stand();
                    table.updateViews();
                }
            }
        });


//        window.pack();
        window.setBounds(200, 200, 400, 300);
        window.setVisible(true);

        int result;
        int reply;
        String message = "";
        do {
            resultLabel.setText("");
            result = table.easyRound(0, true);
            switch (result) {
                case 1:
                    message = "You Lose!";
                    break;
                case 0:
                    message = "Push!";
                    break;
                case -1:
                    message = "You Win!";
            }
            resultLabel.setText(message);
            reply = JOptionPane.showConfirmDialog(null, message + "\nPlay Again? ", "Blackjack", JOptionPane.YES_NO_OPTION);
        } while (reply == JOptionPane.YES_OPTION);
        System.exit(0);
    }


}
