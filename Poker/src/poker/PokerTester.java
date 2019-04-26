package poker;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Tim Barber
 */

/*
 * Suits: ♥,♦,♣,♠
 */
public class PokerTester {

    private static Deck testDeck = new Deck("Standard"); // Init main deck
    private static Player[] players;
    private static Player player1 = new Player(); // Make player 1
    private static Player player2 = new Player(); // Make player 2
    private static Player player3 = new Player(); // Make player 3
    private static Pot mainPot = new Pot(0, "main");
    private static Scanner keyboard = new Scanner(System.in); // create input obj
    private static Random random = new Random();

    private static int startingMoney = 2500;
    private static int minBet = 5;

    public static void main(String[] args) {
        Card[] testcards = {CardMethods.THREEOFCLUBS, CardMethods.THREEOFHEARTS, CardMethods.THREEOFSPADES, CardMethods.QUEENOFCLUBS, CardMethods.KINGOFHEARTS, CardMethods.QUEENOFDIAMONDS, CardMethods.TWOOFSPADES};
        Deck testdeck = new Deck(testcards);
        System.out.println("Full house of " + testdeck + ": " + CardMethods.isFullHouse(testdeck));
    }

    public static void giveMoney(Player[] players) {
        for (Player player : players) {
            player.setMoney(startingMoney);
        }
    }

    public static void offerCut(Player playerToCut) {
        int cutAmt = 0;
        int tries = 0;
        do {
            if (tries < 3) {
                System.out.print(playerToCut.getName() + ", how many cards to cut?: ");
            } else {
                System.out.print("I swear " + playerToCut.getName() + ", quit messing around: ");
            }
            cutAmt = keyboard.nextInt();
            tries++;
        } while (cutAmt < 0 || cutAmt > 52);
        testDeck.cut(cutAmt);
    }

    public static void shuffleDeck(Deck deckToShuffle) {
        int shuffles = random.nextInt(3) + 1;
        int cuts = random.nextInt(5) + 3;
        int overhands = random.nextInt(6) + 1;

        while (shuffles > 0) {
            deckToShuffle.shuffle();
            shuffles--;
        }
        while (cuts > 0) {
            deckToShuffle.cut(random.nextInt(51) + 1);
            cuts--;
        }
        while (overhands > 0) {
            deckToShuffle.overhandShuffle();
            overhands--;
        }
    }

    public static void dealHands(Deck deck, Player[] players) {

        //Make a hand list as large as the number of players
        Hand[] hands = deck.deal(players.length);

        // Give each player their hand
        int index = 0;
        for (Player player : players) {
            hands[index].setPlayerName(player.getName());
            player.setCards(hands[index]);
            index++;
        }

    }

    public static Player[] chooseButtons(Player[] players) {
        int index = random.nextInt(players.length);
        players[index].makeDealer(true);
        Player[] temp = new Player[players.length];
        // Not only does this set the buttons, but it 'rotates' the
        // player list so that the dealer is at the beginning
        int i = 0;
        for (Player player : players) {
            int newIndex = i - index;
            if (newIndex < 0) {
                newIndex += temp.length;
            }
            temp[newIndex] = player;
            i++;
        }
        temp[(temp.length - 1) % temp.length].makeLittle(true);
        temp[(temp.length - 2) % temp.length].makeBig(true);
        return temp;
    }
}
/*
 * The MIT License
 *
 * Copyright (c) 2018 Tim Barber.
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
