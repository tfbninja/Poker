package poker;

/**
 *
 * @author Tim Barber
 */

/*
 * Suits: ♥,♦,♣,♠
 */
public class PokerTester {
    
    private static Deck testDeck = new Deck("Standard"); // Init main deck
    private static Player player1 = new Player(); // Make player 1
    private static Player player2 = new Player(); // Make player 2
    private static Player player3 = new Player(); // Make player 3

    public static void main(String[] args) throws Exception {
        player1.setName("Bob"); // Name player 1
        player2.setName("Joe"); // Name player 2
        player3.setName("Jim"); // Name player 3
        testDeck.setDebugMode(true);

        testDeck.shuffle(); // Shuffle the deck

        Hand player1Hand = new Hand(); // Init hands
        Hand player2Hand = new Hand();
        Hand player3Hand = new Hand();
        player3Hand.setDebugMode(true);
        
        player1Hand.setPlayerName(player1.getName()); //Name the hands to their respective players
        player2Hand.setPlayerName(player2.getName());
        player3Hand.setPlayerName(player3.getName());
        
        System.out.println(testDeck);
        String[] hands = testDeck.deal(3); // Make a string[] with cards in it
        for (String i : hands) {
            System.out.println("yee: " + i);
        }
        /*
         * the list will have cards like so:
         * {Player1 card1, Player1 card2, Player2 card1, Player2 card2...}
         */
        
        player1Hand.setCards(hands[0], hands[1]); //Give the player objects their cards
        player2Hand.setCards(hands[2], hands[3]);
        player3Hand.setCards(hands[4], hands[5]);
        
        System.out.println(player1Hand); // toString() of hands
        System.out.println(player2Hand);
        System.out.println(player3Hand);
        
        System.out.println(testDeck); // toString() of main deck
        System.out.println(testDeck.getSize()); // num cards in main deck
        System.out.println(testDeck.testDuplicates()); // Make sure there are no dupes
    }
    
}
/*
 * The MIT License
 *
 * Copyright 2018 Tim Barber.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
