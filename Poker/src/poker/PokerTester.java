package poker;

/**
 *
 * @author Tim Barber
 */

/*
 * Suits: ♥,♦,♣,♠
 */
public class PokerTester {

    private static Deck testDeck = new Deck("Standard");
    private static Player testPlayer = new Player();

    public static void main(String[] args) throws Exception {
        //System.out.println(testPlayer);
        testPlayer.setName("Tim");
        //System.out.println(testPlayer);
        Hand Player1 = new Hand(CardList.ACEOFCLUBS, CardList.NINEOFHEARTS);
        System.out.println(Player1);
        //testPlayer.setCards(Player1);
        //System.out.println(testPlayer);

        /*
         * testDeck.setDebugMode(false); testDeck.setPerfectionOffset(4);
         *
         * testDeck.cut(5); verbiage(); testDeck.cut(5); verbiage();
         * testDeck.cut(5); verbiage();
         */
    }

    public static void verbiage(Deck deckus) {
        System.out.println("New deck: " + deckus);
        System.out.println("Duplicates test: " + deckus.testDuplicates());
    }

    public static void verbiage() {
        System.out.println("New deck: " + testDeck);
        System.out.println("Duplicates test: " + testDeck.testDuplicates());
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
