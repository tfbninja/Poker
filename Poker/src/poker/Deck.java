package poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Tim Barber
 */

/*
 * Suits: ♥,♦,♣,♠
 */
public class Deck {

    private Random random = new Random();
    private String deckType;
    private ArrayList<String> cardList = new ArrayList<>(52);
    //first card in list is on the top
    private int perfectionOffset;
    /*
     * Perfection offset mimics humanness by adding some randomness to otherwise
     * perfectly mathematical shuffles. An random integer between (inclusive)
     * negative(perfOffset) and (inclusive) positive(perfOffset) is added to
     * packet sizes of shuffles and cuts to accomplish this. While you cannot
     * remove this, you can set it to zero to always keep shuffles
     * mathematically perfect. It is always initialized to zero, but can be set
     * with setPerfectionOffset()
     */

    private boolean debugMode;

    public Deck() {
        this.debugMode = false;
        deckType = "null";
        perfectionOffset = 0;
    }

    public Deck(String type) {
        this.debugMode = false;
        setType(type);
        perfectionOffset = 0;
    }

    public Deck(String[] cards) {
        this.debugMode = false;
        setCards(cards);
        perfectionOffset = 0;
    }

    public Deck(ArrayList<Card> cards) {
        this.debugMode = false;
        this.setTypeCards(cards);
    }

    public Deck(String type, int perfectionOffsetAmt) {
        this.debugMode = false;
        setType(type);
        setPerfectionOffset(perfectionOffsetAmt);
    }

    public Deck(String[] cards, int perfectionOffsetAmt) {
        this.debugMode = false;
        setCards(cards);
        setPerfectionOffset(perfectionOffsetAmt);
    }

    public void setCards(String[] cards) {
        cardList.addAll(Arrays.asList(cards));
    }

    public void setType(String type) {
        type = type.toLowerCase();
        if (type.equals("poker") || type.equals("standard") || type.equals("texas hold 'em")) {
            autoPopulate();
        } else if (type.equals("burn") || type.equals("discard")) {
            this.cardList = new ArrayList<>();
        }
    }

    public int getSize() {
        return cardList.size();
    }

    public void clear() {
        cardList.clear();
    }

    public void setCard(int numFromTop, String card) {
        cardList.set(numFromTop, card);
    }

    public void setCard(int numFromTop, Card card) {
        cardList.set(numFromTop, card.toString());
    }

    public void setCards(ArrayList<String> cards) {
        cardList = cards;
    }

    public void setTypeCards(ArrayList<Card> cards) {
        int i = 0;
        for (Card card : cards) {
            setCard(i, card);
            i++;
        }
    }

    public String get(int cardNumFromTop) {
        return cardList.get(cardNumFromTop);
    }

    public void setPerfectionOffset(int amt) {
        if (debugMode) {
            System.out.println("---Method setPerfectionOffset(amt >" + amt + "<)---");
        }
        this.perfectionOffset = amt;
        if (debugMode) {
            System.out.println("New perfection offset: " + this.perfectionOffset + "\n---Method setPerfectionOffset() finished---\n");
        }
    }

    public int getPerfectionOffset() {
        return this.perfectionOffset;
    }

    public void setDebugMode(boolean mode) {
        debugMode = mode;
    }

    public String testDuplicates() {
        int bInt = 0;
        int xInt = 0;
        if (debugMode) {
            System.out.println(cardList); // debug
        }
        for (String b : cardList) {
            for (String x : cardList) {
                if (b.equals(x) && xInt != bInt) {
                    return "Failed, duplicate1: " + b + ", position " + xInt + ", duplicate2: " + x + ", position " + bInt;
                }
                xInt++;
            }
            bInt++;
            xInt = 0;
        }
        return "Passed";
    }

    public void addCardsBottom(String[] cards) {
        for (String card : cards) {
            cardList.add(card);
        }
    }

    public void addCardsTop(String[] cards) {
        int i = 0;
        for (String card : cards) {
            cardList.add(i, card);
            i++;
        }
    }

    public void addCards(int pos, String[] cards) {
        int i = pos;
        for (String card : cards) {
            cardList.add(i, card);
            i++;
        }
    }

    public void addCardTop(String card) {
        cardList.add(0, card);
    }

    public void addCardBottom(String card) {
        cardList.add(card);
    }

    public void autoPopulate() {
        for (int suitnum = 0; suitnum < 4; suitnum++) {

            for (int ranknum = 1; ranknum <= 13; ranknum++) {

                Rank tempRank = new Rank();
                Suit tempSuit = new Suit();
                String rankStr = tempRank.intToRank(ranknum);
                String suitStr = tempSuit.intToSuit(suitnum);
                Card tempCard = new Card(rankStr, suitStr);
                this.cardList.add(tempCard.toString());
            }
        }
    }

    public String[] takeCardsTop(int amt) {
        String[] cards = new String[amt];
        for (int i = 0; i < amt; i++) {
            cards[i] = cardList.get(0);
            this.cardList.remove(0);
        }
        return cards;
    }

    public Hand[] deal(int numHands) {
        //Init hand list
        Hand[] hands = new Hand[numHands];

        //fill with empty hands
        for (int i = 0; i < numHands; i++) {
            Hand tempHand = new Hand();
            hands[i] = tempHand;
        }

        //init hands to actual cards
        for (int i = 0; i < numHands; i++) {
            hands[i].setCard(1, this.dealCardTop());
        }
        for (int i = 0; i < numHands; i++) {
            hands[i].setCard(0, this.dealCardTop());
        }

        return hands;
    }

    public String dealCardTop() {
        return cardList.remove(0);
    }

    public String dealCard(int indexFromTop) {
        return cardList.remove(indexFromTop);
    }

    public ArrayList<String> cut(int numCards) {
        //cuts off top

        if (debugMode) {
            System.out.println("--- Method cut(numCards >" + numCards + "<)---"); //debug
        }
        numCards += imperfection(); //add randomness
        if (debugMode) {
            System.out.println("Humanized number of cards to cut: " + numCards);
        }
        if (numCards < 0) {
            numCards = Math.abs(numCards); //can't cut negative cards
            if (debugMode) {
                System.out.println("Detected non-positive value, changed to " + numCards);
            }
        }
        ArrayList<String> cutCards = new ArrayList<String>();
        for (int i = 0; i < numCards; i++) {
            cutCards.add(cardList.get(0));
            cardList.remove(0);
            cardList.add(cutCards.get(i));
        }
        if (debugMode) {
            System.out.println("Cutting finished. \nNew Deck: >" + cardList + "<\n---Method cut() finished---\n");
        }
        return cutCards;
    }

    public String[] arrayListToStringList(ArrayList<String> arraylist) {
        int i = 0;
        String[] temp = new String[arraylist.size()];
        for (String str : arraylist) {
            temp[i] = str;
            i++;
        }
        return temp;
    }

    public ArrayList<String> stringListToArrayList(String[] stringlist) {
        ArrayList<String> temp = new ArrayList<>();
        temp.addAll(Arrays.asList(stringlist));
        return temp;
    }

    public void overhandShuffle(int seed, int packets) {
        if (debugMode) {
            System.out.println("\n---overhandShuffle(seed >" + seed + "<, packets >" + packets + "<)---");
            System.out.println("Original number of cards (check with new number of cards at the end of the method): " + cardList.size() + "\n");
        }
        boolean goAhead = true; //this only changes if packets is >= cardList.size()
        random.setSeed(seed); //set random seed
        double avgPacketSize = cardList.size() / (double) packets; // average number of cards in each packet, usually a double number
        if (debugMode) {
            System.out.println("\nAverage packet size if the deck has " + cardList.size() + " cards and is cut into " + packets + " packets: " + avgPacketSize + "\n"); // debug
        }
        int counter = 0; // this keeps the number of cards that have been moved so far
        Deck tempDeck = new Deck(); // init of new deck that will replace this one

        if (debugMode) {
            System.out.println("Begin overhand shuffling:");
        }

        if (packets >= cardList.size()) { // if the number of packets to use is >= the actual number of cards, just reverse the deck
            if (debugMode) {
                System.out.println("Number of packets is greater than or equal to deck size, initiation reversion of deck.");
            }
            goAhead = false;
            Collections.reverse(cardList); // reversion
            if (debugMode) {
                System.out.println("Reversion of deck finished. Skipping to end of method.");
            }
        }
        if (goAhead) {
            for (int i = 0; i < packets - 1; i++) { //repeat as many times as there are packets
                if (random.nextFloat() >= avgPacketSize % 1) { // if a random number between 0 and 1 is >= the number after the decimal point of avgPacketSize

                    int packetSize = (int) Math.floor(avgPacketSize); //use the floor of avgPacketSize for this packetsize
                    packetSize += imperfection(); //change by the perfection value (negative or positive)
                    ArrayList<String> tempArray = cut(packetSize); //create a new String list holding packetsize number of cards off the top of the old deck
                    String[] packetCards = arrayListToStringList(tempArray); //since cut() returns an arrayList<String>, convert that to a String[]
                    //I could have done this all in one line, but it's more readable to declare it as one thing, and then convert b/c that's a lot for one line

                    counter += packetSize; //update the counter variable

                    if (debugMode) {
                        System.out.println("Cards cut so far: " + counter + " (this packet size: " + packetSize + ")"); // debug
                        System.out.println("Cards in this packet: " + Arrays.deepToString(packetCards) + "\n"); // debug

                    }

                    tempDeck.addCardsTop(packetCards); //add the cut cards from the old deck to our new deck

                } else {
                    int packetSize = (int) Math.ceil(avgPacketSize); //use the ceiling of avgPacketSize for this packetsize
                    packetSize += imperfection(); //change by the perfection value (negative or positive)
                    ArrayList<String> packetCards = cut(packetSize); //create a new String list holding packetsize number of cards off the top of the old deck
                    String[] templist = arrayListToStringList(packetCards); //since cut() returns an arrayList<String>, convert that to a String[]
                    counter += packetSize; //update the counter variable
                    //I could have done this all in one line, but it's more readable to declare it as one thing, and then convert b/c that's a lot for one line

                    if (debugMode) {
                        System.out.println("Cards cut so far: " + counter + " (this packet size: " + packetSize + ")"); // debug
                        System.out.println("Cards in this packet: " + Arrays.deepToString(templist) + "\n"); // debug
                    }

                    tempDeck.addCardsTop(templist); //add the cut cards from the old deck to our new deck
                }
            }
            if (debugMode) {
                System.out.println("Size of the last packet of cards: " + String.valueOf(cardList.size() - counter)); // debug
            }

            ArrayList<String> packetCards = cut(cardList.size() - counter);
            String[] tempList = arrayListToStringList(packetCards);

            if (debugMode) {
                System.out.println("Cards in this packet: " + Arrays.deepToString(tempList) + "\nEnd overhand shuffling."); // debug
            }

            tempDeck.addCardsTop(tempList);

            if (debugMode) {
                System.out.println("\nLast card in new deck: >" + tempDeck.get(tempDeck.getSize() - 1) + "<\nNew deck: >" + tempDeck + "<"); // debug
            }
        }
        if (goAhead) {
            ArrayList<String> tempArray = tempDeck.toArrayList();
            this.cardList = tempArray;
        }
        if (debugMode) {
            System.out.println("New number of cards (check with old number at beginning of the method): " + cardList.size());
            System.out.println("\n---Method overhandShuffle() finished---");
        }
    }

    public void overhandShuffle() {
        overhandShuffle(0, random.nextInt(12) + 5);
    }

    public void overhandShuffle(int packets) {
        overhandShuffle(0, packets);
    }

    public int imperfection() {
        return imperfection(random.nextInt());
    }

    public int imperfection(int seed) {
        if (debugMode) {
            System.out.println("\n---Method imperfection(seed >" + seed + "<)---");
        }
        random.setSeed(seed); //init random seed
        int randOffset = random.nextInt((this.perfectionOffset * 2) + 1) - this.perfectionOffset; //Between -perfectionOffset and perfectionOffset
        if (debugMode) {
            System.out.println("Original Perf offset: " + this.perfectionOffset + "\nNew perfection offset: " + randOffset + "\n---Method imperfection() finished---\n");
        }
        return randOffset;
    }

    public String toOrganizedString() {
        return cardList.toString();
    }

    public ArrayList<String> toArrayList() {
        return cardList;
    }

    public ArrayList<String> shuffle(int seed) {
        random.setSeed(seed);
        Collections.shuffle(cardList, random);
        return cardList;
    }

    public ArrayList<String> shuffle() {
        int seed = random.nextInt(99999999);
        if (debugMode) {
            System.out.println("---Method shuffle()---\nNo params, shuffling with seed: " + seed);
        }
        return shuffle(seed);
    }

    @Override
    public String toString() {
        if (debugMode) {
            System.out.println("\n---toString()---");
        }
        String builder = "";
        for (String card : cardList) {
            builder += (card + " ");
        }
        if (debugMode) {
            System.out.println("value of builder string: >" + builder + "<");
        }
        builder = builder.trim();
        if (debugMode) {
            System.out.println("trimmed builder string: >" + builder + "<");
        }
        if (debugMode) {
            System.out.println("---Method toString() finished---\n");
        }
        return builder;
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
