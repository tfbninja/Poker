package poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Tim Barber
 */

/*
 * Suits: ♥,♦,♣,♠
 */
public class PokerRunner {

    private static Deck mDeck = new Deck("Standard"); // Init main deck
    private static Deck bPile = new Deck("Burn"); // Init burn pile
    private static ArrayList<Player> players;
    private static Pot mainPot = new Pot(0, "main");
    private static ArrayList<Pot> pots = new ArrayList<>();

    private static int startingMoney = 2500;
    private static int littleBet = 5;
    private static int bigBet = 10;
    private static Deck communityCards = new Deck("burn");

    private static int currentBet = bigBet;
    private static int roundStage = 1;
    /*
     * roundStage is just a number representing at what the point the game has
     * progressed to. The possible stages are as follows:
     * 1: pre-flop, action is to player right of big blind
     * 2: post-flop, action is to little blind, or equiv.
     * 3: post-turn, action is the same as post-flop
     * 4: post-river action is the same as post-flop
     */
    private static Player lastToBet;
    private static boolean resolved = false;

    private static Scanner keyboard = new Scanner(System.in); // create input obj
    private static Random random = new Random();

    private static String[] validActions = new String[5];
    private static ArrayList<Player> winners = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("\nTexas Hold 'Em Runner\nCreated by Tim Barber, October 2018\n");

        setupGame(); // prompt user for number of players, and get the player's names

        giveMoney(players); // give each player in the list this.startingMoney

        chooseButtons(); // set dealer, little, and big blinds

        round();
    }

    public static void setupGame() {
        // set valid actions
        validActions[0] = "fold";
        validActions[1] = "raise";
        validActions[2] = "bet";
        validActions[3] = "check";
        validActions[4] = "call";

        // set size of players list
        int numPlayers = 0;
        while (numPlayers < 2 || numPlayers > 10) {
            System.out.print("To begin, please enter the number of players: ");
            numPlayers = keyboard.nextInt();
        }
        players = new ArrayList<>(numPlayers);
        players.clear(); // probably not necessary...
        for (int a = 0; a < numPlayers; a++) {
            players.add(new Player());
        }

        // get names of all the players
        System.out.println("Please enter in the first name of each player.\n");
        int index = 1;
        for (Player player : players) {
            System.out.print("Player " + index + ": ");
            player.setName(keyboard.next());
            index++;
        }
    }

    public static void giveMoney(ArrayList<Player> players) {
        players.forEach((player) -> {
            player.setMoney(startingMoney);
        });
    }

    public static void offerCut(Player playerToCut, Deck deck) {
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
        deck.cut(cutAmt);
    }

    public static void shuffleDeck(Deck deckToShuffle) {
        int shuffles = random.nextInt(3) + 1;
        int cuts = random.nextInt(5) + 1;
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

    public static void dealHands(Deck deck, ArrayList<Player> players) {

        //Make a hand list as large as the number of players
        Hand[] hands = deck.deal(players.size());

        // Give each player their hand
        int upIndex = 0; // ascending index
        for (int index = players.size() - 1; index >= 0; index--) {
            hands[upIndex].setPlayerName(players.get(index).getName());
            players.get(index).setCards(hands[upIndex]);
            upIndex++;
        }

    }

    public static void chooseButtons() {
        int index = random.nextInt(players.size());
        players.get(index).makeDealer(true);
        Player[] temp = new Player[players.size()];
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
        if (players.size() > 2) {
            temp[(temp.length - 1) % temp.length].makeLittle(true);
            temp[(temp.length - 2) % temp.length].makeBig(true);
        } else { // if only two players, dealer is little and other is big
            temp[0].makeLittle(true);
            temp[1].makeBig(true);
        }
        ArrayList<Player> tempArrayList = new ArrayList<>(); // next lines 'convert' Player[] temp to an ArrayList<Player>
        tempArrayList.addAll(Arrays.asList(temp));
        players = tempArrayList; // make the roster sorted, dealer first, little last, big second last
    }

    public static void shiftTheButtons() {
        if (players.size() > 2) {
            players.get(0).makeDealer(false);
            players.get(players.size() - 1).makeDealer(true);
            players.get(players.size() - 1).makeLittle(false);
            players.get(players.size() - 2).makeLittle(true);
            players.get(players.size() - 2).makeBig(false);
            players.get(players.size() - 3).makeBig(true);
            Collections.rotate(players, 1);
        } else {
            players.get(0).makeDealer(false);
            players.get(0).makeBig(true);
            players.get(1).makeDealer(true);
            players.get(1).makeBig(false);
            players.get(1).makeLittle(false);

            Collections.reverse(players);
        }
    }

    public static void upTheBlinds() {
        littleBet *= 2;
        bigBet *= 2;
    }

    public static void bettingRound() {

        while (!resolved) {
            for (Player player : getActionList()) {
                /*
                 * This cycles turns over the action list, not the player
                 * roster, because the roster always has the dealer at the
                 * beginning but the action order depends on what stage of
                 * betting the game has progressed to.
                 */

                if (player.getIsActive()) { // we're not dealing with folded players

                    System.out.println("\n"); // aesthetic is key

                    boolean turnIsOver = false;

                    while (!turnIsOver) { // try to perform action

                        if (!communityCards.isEmpty()) {
                            System.out.println("Community cards: " + communityCards);
                        }
                        System.out.println("Current bet: $" + currentBet);
                        if (currentBet > 0) {
                            System.out.println("VALID ACTIONS: view, fold, raise, and call.");
                        } else {
                            System.out.println("VALID ACTIONS: view, fold, bet, and check.");
                        }
                        System.out.print(player.getName() + ", the action is to you, what is your action?: ");
                        String action = keyboard.next().toLowerCase();
                        switch (action) {
                            case "view":
                                System.out.println("Press enter to display your cards, and press enter again to hide them...");
                                keyboard.nextLine();
                                System.out.println(player.getName() + "'s cards: " + player.getCards() + "\nPress enter to hide cards");
                                keyboard.nextLine();
                                ghettoClear();
                                break;
                            case "fold":
                                mainPot.addMoney(player.getTransientBet());
                                player.setActive(false);
                                turnIsOver = true;
                                System.out.println("Successfully folded, sacrificing $" + player.getTransientBet() + " in the process.");
                                if (currentBet == 0) {
                                    System.out.println("You could have stayed in for free...");
                                }
                                if (getActivePlayers() == 1) {
                                    showdown();
                                }
                                break;
                            case "raise":

                                lastToBet = player;
                                int proposedRaise = 0; // init the var to hold the raise

                                while (proposedRaise < bigBet) { // as long is the raise is not legitimate, keep asking for it
                                    System.out.print("What do you want to raise to?: ");
                                    proposedRaise = keyboard.nextInt(); // set to input

                                    if (proposedRaise > player.getMoney()) { // if they tried to raise more than they have
                                        System.out.println("You don't have " + proposedRaise + ", you have " + player.getMoney() + ". You are all-in.");
                                        player.goAllIn(); // they automatically are put all in
                                        currentBet = player.getMoney();

                                    } else if (proposedRaise < (currentBet + bigBet)) { // if they tried to raise less than the min bet
                                        System.out.println("That is too small, the minimum bet at this time is " + bigBet); // restart the loop

                                    } else { // it must have been a legitimate raise amt
                                        player.setTransientBet(proposedRaise); // update the player
                                        currentBet = proposedRaise; // update the running bet amt
                                        System.out.println("Raise to $" + proposedRaise + " was successful.");
                                        turnIsOver = true;
                                        break;
                                    }
                                }
                                if (turnIsOver) {
                                    break;
                                }

                            case "bet":

                                lastToBet = player;
                                boolean stringBet = false;
                                if (currentBet > 0) {
                                    System.out.println("REEEE NO STRING BETS " + player.getName() + "!!!\nString bets are automatically calls");
                                    stringBet = true;
                                }
                                if (!stringBet) {
                                    int proposedBet = 0; // init the var to hold the bet

                                    while (proposedBet < bigBet) { // as long as the bet is not legitimate, keep asking for it

                                        System.out.print("What amount would you like to bet?: ");
                                        proposedBet = keyboard.nextInt(); // set to input

                                        if (proposedBet > player.getMoney()) { // if they tried to bet more than they have,
                                            player.goAllIn(); // they automatically are all in

                                        } else if (proposedBet < bigBet) { // if they tried to bet less than the minimum bet
                                            System.out.println("That is too small, the minimum raise at this time is $" + (bigBet + currentBet)); // tell them and restart the loop

                                        } else { // it must have been a legitimate bet
                                            player.setTransientBet(proposedBet); // update the player
                                            currentBet = proposedBet; // update the running bet amt
                                            System.out.println("$" + proposedBet + " bet was successful.");
                                        }
                                    }
                                    turnIsOver = true;
                                    break;
                                }
                            case "call": // notice this is right after bet, this is to make string bets easier to handle

                                if (player.getMoney() < currentBet) { // the player cannot properly call, therefore
                                    player.goAllIn(); // they have to go all in
                                    // or they have just the right amount of money, with the same net effect
                                } else if (player.getMoney() == currentBet) { // they have just enough money to call
                                    System.out.println("You have just enough money to call, you are now all-in with $" + player.getMoney());
                                    player.goAllIn();
                                    turnIsOver = true; // done
                                    break; // get out of here

                                } else { // they must have enough money to call
                                    player.setTransientBet(currentBet); // update player
                                    System.out.println("Successfully called $" + currentBet);
                                    turnIsOver = true;
                                    break; // we're done here xD
                                }

                            case "check":
                                if (currentBet > 0) {
                                    System.out.println("You cannot check a bet, you must either call, raise, or fold");
                                } else {
                                    System.out.println("Check successful.");
                                    turnIsOver = true;
                                    break;
                                }

                            default:
                                System.out.println("\nInvalid action \"" + action + "\"\n");
                        }
                    }
                }
            }
            // Now that we've gone through all the players, check if we are resolved
            boolean tempbool = true;
            for (Player player : players) {
                tempbool = tempbool && (player.getTransientBet() == currentBet);
            }
            if (tempbool) {
                resolved = true;
                for (Player player : players) { // add money to the pot
                    mainPot.addMoney(player.getTransientBet() - player.getSidePot()); // but only add the stuff that can go in the main pot
                }
            }

        }
        resolved = false; // reset resolved
        currentBet = 0; // reset minimum bet
    }

    public static void ghettoClear() {
        for (int i = 0; i < 100; i++) {
            System.out.println("\n");
        }
    }

    public static ArrayList<Player> getActionList() {
        ArrayList<Player> actionList = players;
        if (roundStage == 1) { // action goes to first active player to the left of big blind
            Collections.reverse(actionList);
            Collections.rotate(actionList, -2);
        } else { // it is past the flop, action goes to little blind, or next active player to the left of the little if the little folded
            Collections.reverse(actionList);
        }
        return actionList;
    }

    public static void flop() {

        bPile.addCardTop(mDeck.dealCardTop()); // burn a card
        communityCards.addCardBottom(mDeck.dealCardTop()); // update community cards
        communityCards.addCardBottom(mDeck.dealCardTop());
        communityCards.addCardBottom(mDeck.dealCardTop());
        roundStage = 2; // set the stage var to post-flop
    }

    public static void turn() {
        bPile.addCardTop(mDeck.dealCardTop()); // burn a card
        communityCards.addCardBottom(mDeck.dealCardTop()); // update community cards
        roundStage = 3; // set the stage var to post-turn
    }

    public static void river() {
        bPile.addCardTop(mDeck.dealCardTop()); // burn a card
        communityCards.addCardBottom(mDeck.dealCardTop()); // update community cards
        roundStage = 4; // set the stage var to post-river
    }

    public static void declareButtons() {
        System.out.print("Dealer | ");
        for (Player player : players) {
            if (player.getDealer()) {
                System.out.println(player.getName());
            }
        }
        System.out.print("Little | ");
        for (Player player : players) {
            if (player.getLittle()) {
                System.out.println(player.getName());
            }
        }
        System.out.print("Big    |");
        for (Player player : players) {
            if (player.getBig()) {
                System.out.println(player.getName());
            }
        }
    }

    public static void showdown() {

        if (getActivePlayers() > 1) {

            System.out.println("Reveal your cards if you want to win.");

            ArrayList<Player> finalists = new ArrayList<>(); // list of hands that have been revealed

            for (Player player : players) { // ask every player if they want to reveal

                boolean invalidResponse = true; // start by assuming their response is invalid
                while (invalidResponse) {

                    System.out.print("Do you want to reveal your cards? (y/n): "); // prompt
                    String response = keyboard.next().toLowerCase(); // get their input

                    if (response.equals("y") || response.equals("yes") || response.contains("yes") || response.equals("yeet")) { // if yes,
                        finalists.add(player); // add them to the list of finalists
                        invalidResponse = false; // and continue with the next player

                    } else if (response.equals("n") || response.equals("no") || response.contains("no") || response.equals("yeetn't")) { // if not, that constitutes a fold
                        System.out.println("You folded your cards."); // alert
                        invalidResponse = false; // continue with the next player
                        player.setActive(false); // fold

                    } else { // they entered in something else
                        System.out.println("Invalid response \"" + response + "\"");
                        //restart the while loop until they choose
                    }
                }
            }


            /*
         * After figuring out the contesting hands, figure out the best one
         * using the best possible cards from each player's 2 hole cards and the
         * 5 community cards.
             */
            for (Player player : bestHand(finalists)) {
                winners.add(player);
            }
        } else {
            for (Player player1 : players) {
                if (player1.getIsActive()) {
                    winners.add(player1);
                }
            }
        }
        int payout = mainPot.getAmount() / winners.size();
        if (mainPot.getAmount() / (double) winners.size() != payout) { // if not easily divisible
            payout += 1; // just round up
        }
        int numSidePlayers = 0;
        for (Player player : winners) {
            if (player.getSidePot() == 0) {
                player.addMoney(payout); // pay
            } else {
                numSidePlayers++;
            }
        }
        // now pay side players
        int totalSidePot = 0;
        for (Player sideplayer : winners) {
            totalSidePot += sideplayer.getSidePot(); // calculate side pot
        }
        if (numSidePlayers > 0 && totalSidePot % numSidePlayers > 0) {
            int sidepayout = totalSidePot / numSidePlayers;
            sidepayout += 1;
        } else if (numSidePlayers > 0) {
            int sidepayout = totalSidePot / numSidePlayers;
        }

        /*
         * ACTION ITEM
         * only pay equal to side pot
         */
        for (Player sideplayer : winners) {
            if (sideplayer.getSidePot()) {
                sideplayer.addMoney(payout);
            }
        }

    }

    public static Player[] bestHand(ArrayList<Player> contestants) {
        /*
         * Winning hands from best to worst:
         * Royal Flush, t-a suited
         * Straight Flush, 5 consecutive suited
         * Four of a kind, 4 cards equally ranked
         * Full House, 3pair and 2pair
         * Flush, any 5 cards suited
         * Straight, 5 consecutive
         * Three of a kind, 3 cards equally ranked
         * Two pair, 2pair and 2pair
         * One pair, 2pair
         * High card, highest card out of all other hands
         */

        ArrayList<Hand> hands = new ArrayList<>(); // list of hands
        for (Player player : contestants) {
            hands.add(player.getCards()); // fill that list
            hands.get(hands.size() - 1).setPlayerName(player.getName());
        }

        for (int index = 0; index < hands.size() - 1; index++) {
            if (getIntHandRank(hands.get(index)) < getIntHandRank(hands.get(index))) {
                hands.remove(index);
            } else if (getIntHandRank(hands.get(index)) < getIntHandRank(hands.get(index))) {
                hands.remove(index + 1);
            }
        }
        if (hands.size() == 1) { // no ties

            String tempName = hands.get(0).getPlayerName(); // the winning players' name
            ArrayList<String> playerNames = new ArrayList<>(); // list of player names
            for (int i = 0; i < playerNames.size(); i++) { // populate the list
                playerNames.add(players.get(i).getName());
            }

            Player[] tempPlayerList = new Player[1]; // list of size 1 that holds the actual player object that won
            tempPlayerList[0] = players.get(playerNames.indexOf(tempName)); // populate it

            return tempPlayerList; // return it
        } else {
            int oldHandsSize = hands.size() + 1;
            while (oldHandsSize > hands.size()) {
                for (int index = 0; index < hands.size() - 1; index++) {
                    if (getIntHandRank(hands.get(index)) < getIntHandRank(hands.get(index))) {
                        hands.remove(index);
                    } else if (getIntHandRank(hands.get(index)) < getIntHandRank(hands.get(index))) {
                        hands.remove(index + 1);
                    }
                }
            }
        }
        // check again
        if (hands.size() == 1) { // no ties

            String tempName = hands.get(0).getPlayerName(); // the winning players' name
            ArrayList<String> playerNames = new ArrayList<>(); // list of player names
            for (int i = 0; i < playerNames.size(); i++) { // populate the list
                playerNames.add(players.get(i).getName());
            }

            Player[] tempPlayerList = new Player[1]; // list of size 1 that holds the actual player object that won
            tempPlayerList[0] = players.get(playerNames.indexOf(tempName)); // populate it

            return tempPlayerList; // return it
        } else { // there is a tie
            String[] tempNames = new String[hands.size()]; // the winning players' names
            int index = 0;
            for (Player player : players) { // populate it
                tempNames[index] = player.getName();
                index++;
            }

            ArrayList<String> playerNames = new ArrayList<>(); // list of player names
            for (int i = 0; i < playerNames.size(); i++) { // populate the list
                playerNames.add(players.get(i).getName());
            }

            Player[] tempPlayerList = new Player[hands.size()]; // list that holds the actual player objects that tied
            int index1 = 0;
            for (Hand hand : hands) {
                tempPlayerList[index] = players.get(playerNames.indexOf(tempNames[index])); // populate it
                index++;
            }

            return tempPlayerList; // return it
        }
    }

    public static int getIntHandRank(Deck deck) {
        if (CardMethods.isRoyalFlush(deck)) {
            return 10;
        } else if (CardMethods.isStraightFlush(deck)) {
            return 9;
        } else if (CardMethods.isFourOfAKind(deck)) {
            return 8;
        } else if (CardMethods.isFullHouse(deck)) {
            return 7;
        } else if (CardMethods.isFlush(deck)) {
            return 6;
        } else if (CardMethods.isStraight(deck)) {
            return 5;
        } else if (CardMethods.isThreeOfAKind(deck)) {
            return 4;
        } else if (CardMethods.isTwoPair(deck)) {
            return 3;
        } else if (CardMethods.isPair(deck)) {
            return 2;
        } else {
            return 1;
        }
    }

    public static void round() {
        for (Player player : players) {
            player.setActive(true);
        }

        declareButtons();

        shuffleDeck(mDeck); // shuffle the deck

        offerCut(players.get(players.size() - 1), mDeck); // offer cut to player right of dealer

        dealHands(mDeck, players); // deal hands to players

        /*
         * ACTION ITEM
         * Add method for showing players their hands
         * before the betting round begins
         */
        checkPlayerList();

        bettingRound(); // bet

        checkPlayerList();

        flop(); // first 3 community cards

        bettingRound(); // bet

        checkPlayerList();

        turn(); // 4th community card

        bettingRound(); // bet

        checkPlayerList();

        river(); // 5th community card

        bettingRound(); // final bet

        showdown();

    }

    public static void checkPlayerList() {
        if (players.size() == 1) {
            showdown();
        }
    }

    public static void viewCards(Player player) {
        System.out.println(player.getName() + ": Type 'view' and press enter to display your cards, and press enter again to hide them...");
        keyboard.next();
        System.out.println(player.getName() + "'s cards: " + player.getCards() + "\nType 'hide' and then press enter to hide cards");
        keyboard.next();
        ghettoClear();
    }

    public static int getActivePlayers() {
        int numActive = 0;
        for (Player player : players) {
            if (player.getIsActive()) {
                numActive++;
            }
        }
        return numActive;
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
