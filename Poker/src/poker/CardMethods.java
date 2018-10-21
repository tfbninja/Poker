package poker;

/**
 *
 * @author Tim Barber
 */

/*
 * Suits: {"♥", "♦", "♣", "♠"};
 */
public final class CardMethods {

    public static final Card ACEOFHEARTS = new Card("A", "♥");
    public static final Card TWOOFHEARTS = new Card("2", "♥");
    public static final Card THREEOFHEARTS = new Card("3", "♥");
    public static final Card FOUROFHEARTS = new Card("4", "♥");
    public static final Card FIVEOFHEARTS = new Card("5", "♥");
    public static final Card SIXOFHEARTS = new Card("6", "♥");
    public static final Card SEVENOFHEARTS = new Card("7", "♥");
    public static final Card EIGHTOFHEARTS = new Card("8", "♥");
    public static final Card NINEOFHEARTS = new Card("9", "♥");
    public static final Card TENOFHEARTS = new Card("T", "♥");
    public static final Card JACKOFHEARTS = new Card("J", "♥");
    public static final Card QUEENOFHEARTS = new Card("Q", "♥");
    public static final Card KINGOFHEARTS = new Card("K", "♥");
    public static final Card ACEOFDIAMONDS = new Card("A", "♦");
    public static final Card TWOOFDIAMONDS = new Card("2", "♦");
    public static final Card THREEOFDIAMONDS = new Card("3", "♦");
    public static final Card FOUROFDIAMONDS = new Card("4", "♦");
    public static final Card FIVEOFDIAMONDS = new Card("5", "♦");
    public static final Card SIXOFDIAMONDS = new Card("6", "♦");
    public static final Card SEVENOFDIAMONDS = new Card("7", "♦");
    public static final Card EIGHTOFDIAMONDS = new Card("8", "♦");
    public static final Card NINEOFDIAMONDS = new Card("9", "♦");
    public static final Card TENOFDIAMONDS = new Card("T", "♦");
    public static final Card JACKOFDIAMONDS = new Card("J", "♦");
    public static final Card QUEENOFDIAMONDS = new Card("Q", "♦");
    public static final Card KINGOFDIAMONDS = new Card("K", "♦");
    public static final Card ACEOFCLUBS = new Card("A", "♣");
    public static final Card TWOOFCLUBS = new Card("2", "♣");
    public static final Card THREEOFCLUBS = new Card("3", "♣");
    public static final Card FOUROFCLUBS = new Card("4", "♣");
    public static final Card FIVEOFCLUBS = new Card("5", "♣");
    public static final Card SIXOFCLUBS = new Card("6", "♣");
    public static final Card SEVENOFCLUBS = new Card("7", "♣");
    public static final Card EIGHTOFCLUBS = new Card("8", "♣");
    public static final Card NINEOFCLUBS = new Card("9", "♣");
    public static final Card TENOFCLUBS = new Card("T", "♣");
    public static final Card JACKOFCLUBS = new Card("J", "♣");
    public static final Card QUEENOFCLUBS = new Card("Q", "♣");
    public static final Card KINGOFCLUBS = new Card("K", "♣");
    public static final Card ACEOFSPADES = new Card("A", "♠");
    public static final Card TWOOFSPADES = new Card("2", "♠");
    public static final Card THREEOFSPADES = new Card("3", "♠");
    public static final Card FOUROFSPADES = new Card("4", "♠");
    public static final Card FIVEOFSPADES = new Card("5", "♠");
    public static final Card SIXOFSPADES = new Card("6", "♠");
    public static final Card SEVENOFSPADES = new Card("7", "♠");
    public static final Card EIGHTOFSPADES = new Card("8", "♠");
    public static final Card NINEOFSPADES = new Card("9", "♠");
    public static final Card TENOFSPADES = new Card("T", "♠");
    public static final Card JACKOFSPADES = new Card("J", "♠");
    public static final Card QUEENOFSPADES = new Card("Q", "♠");
    public static final Card KINGOFSPADES = new Card("K", "♠");

    public static final String[] RANKS = {"a", "2", "3", "4", "5", "6", "7", "8", "9", "t", "j", "q", "k"}; // all the possible ranks
    public static final String[] SUITS = {"♥", "♦", "♣", "♠"}; // all the possible suits

    public static boolean isRoyalFlush(Deck deck) { // deck should be size 7
        /*
         * I wrote a python program to generate this for me, and honestly it
         * took more time to write it than it would have taken me to just write
         * it out =)
         */
        return (deck.contains(TENOFHEARTS) // if deck has t♥-a♥ then true
                && deck.contains(JACKOFHEARTS)
                && deck.contains(QUEENOFHEARTS)
                && deck.contains(KINGOFHEARTS)
                && deck.contains(ACEOFHEARTS))
                || (deck.contains(TENOFDIAMONDS) // or if deck has t♦-a♦ then true
                && deck.contains(JACKOFDIAMONDS)
                && deck.contains(QUEENOFDIAMONDS)
                && deck.contains(KINGOFDIAMONDS)
                && deck.contains(ACEOFDIAMONDS))
                || (deck.contains(TENOFCLUBS) // or if deck has t♣-a♣ then true
                && deck.contains(JACKOFCLUBS)
                && deck.contains(QUEENOFCLUBS)
                && deck.contains(KINGOFCLUBS)
                && deck.contains(ACEOFCLUBS))
                || (deck.contains(TENOFSPADES) // or if deck has t♠-a♠ then true
                && deck.contains(JACKOFSPADES)
                && deck.contains(QUEENOFSPADES)
                && deck.contains(KINGOFSPADES)
                && deck.contains(ACEOFSPADES));
    }

    public static boolean isStraightFlush(Deck deck) { // deck should be size 7
        return isStraight(deck) && isFlush(deck); // easiest of them all lmao
    }

    public static boolean isFourOfAKind(Deck deck) { // deck should be size 7
        String tempDeck = deck.toString(); // string representation of the deck
        // e.g. "2♦ t♠ k♣ 2♣ 2♥ 2♠ a♦"

        for (String rank : RANKS) {

            // for every possible rank, if there are 4 occurences of it, we have four of a kind
            if (countOccurences(tempDeck, rank) == 4) { // not >= because you can't have 5 of a kind
                return true;
            }
        }
        return false; // if there is not 4 same-ranked cards, return false
    }

    public static boolean isFullHouse(Deck deck) { // deck should have 7 cards
        // a full house is 3 cards of one rank, and 2 cards of another rank
        // e.g. k♥ j♦ j♣ k♠ k♦ a♣ 5♥ is kings full of jacks. 3 kings + 2 jacks

        String tempDeck = deck.toString(); // String representation of the deck
        // e.g. "k♥ j♦ j♣ k♠ k♦ a♣ 5♥"

        for (int indexA = 1; indexA <= 13; indexA++) {

            // since index is a number, we need to convert it to a rank
            String converted = intToRank(indexA);

            // count the number of chars in the string we made
            int countA = countOccurences(tempDeck, converted);

            /*
             * AHH!! Nested for loop madness!
             * No no, it's ok, it's really not that confusing.
             * It's just that for every suit that might be a 2 or three of a
             * kind, we need to also check all of the OTHER suits.
             * (OTHER suits is key hence indexB is going to have to skip
             * indexA's value, to avoid always returning true if there is ever
             * three of a kind.)
             */
            for (int indexB = 1; indexB <= 13; indexB++) {

                if (indexB == indexA) { // indexB can't be checking indexA's rank
                    indexB++; // otherwise if there is three of a kind, it will say its a full house no matter what
                }
                // since index is a number, we need to convert it to a rank
                String convertedB = intToRank(indexB);

                // count the number of chars in the string we made
                int countB = countOccurences(tempDeck, convertedB);

                if ((countA >= 3 && countB >= 2) || (countA >= 2 && countB >= 3)) {
                    // if one of the counts is >=3 and the other is >=2,
                    return true; // we have a full house
                }
            }
        }

        return false; // if we never found 3 and 2 of a kind, return false

    }

    public static boolean isFlush(Deck deck) { // deck should be size 7

        String tempDeck = deck.toString(); // string representation of the deck,
        // e.g. "2♦ t♠ 6♣ k♣ j♥ 2♠ 4♦"

        String[] suits = {"♥", "♦", "♣", "♠"}; // string[] to loop through

        for (String tempChar : suits) { // for every suit,

            // count the number of chars in the string we made
            int count = countOccurences(tempDeck, tempChar);

            //System.out.println(count); // debug only
            if (count >= 5) { // if 5 or more cards are the same suit,
                return true; // it's a flush
            }
        }
        return false; // if there are not 5 same-suit cards, there is not a flush
    }

    public static boolean isStraight(Deck deck) { // deck should be size 7

        for (int tempRank = 1; tempRank <= 10; tempRank++) {
            boolean tempbool = true;
            for (int adder = 0; adder < 5; adder++) {
                int newnum = tempRank + adder;
                String replacement = intToRank(newnum);

                tempbool = tempbool && deck.contains(replacement);
            }
            if (tempbool == true) {
                return tempbool;
            }
        }
        return false;
    }

    public static boolean isThreeOfAKind(Deck deck) { // deck should be of size 7
        /*
         * Ah yes, the subjective question of whether or not to return true if
         * there is more than three of a kind. Purists might say no, because
         * it's not THREE of a kind, its four of a kind, but, as a realist
         * (roasted), I am going to return true if there is more than three of a
         * kind, because my Poker engine is going to check hands highest to
         * lowest, thus eliminating the problem of mistaking a four of a kind
         * hand for a three of a kind hand.
         */

        String tempDeck = deck.toString();
        for (String rank : RANKS) { // for every possible rank,
            if (countOccurences(tempDeck, rank) >= 3) { // Mad? See above you filthy purist. xD
                return true; // if there are 3 OR MORE of that rank, return true
            }
        }

        return false; // if we didn't find three of a kind, return false

    }

    public static int countOccurences(String str, char chr) {
        String chrStr = String.valueOf(chr); // turn chr into a string to allow the algorithm to work

        return str.length() - str.replace(chrStr, "").length();
        // extremely concise and simple algorithm I found on stackoverflow, at
        // https://stackoverflow.com/a/8910767

    }

    public static int countOccurences(String haystack, String needle) {
        return haystack.length() - haystack.replace(needle, "").length();
        // extremely concise and simple algorithm I found on stackoverflow, at
        // https://stackoverflow.com/a/8910767
    }

    public static String intToRank(int old) {
        String converted = "";
        switch (old) {
            case 1:
                converted = "a";
                break;
            case 10:
                converted = "t";
                break;
            case 11:
                converted = "j";
                break;
            case 12:
                converted = "q";
                break;
            case 13:
                converted = "k";
                break;
            case 14:
                converted = "a";
            default: // if its not 1,10,11,12,13, or 14, we just keep it as it is
                converted = String.valueOf(old);
        }
        return converted;
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
