package poker;

import java.util.ArrayList;

/**
 *
 * @author Tim Barber
 */
public class Hand extends Deck {

    private String card1;
    private String card2;
    private String playerName;

    private boolean debugMode = false;

    public Hand() {
        this.card1 = "";
        this.card2 = "";
        this.playerName = "";
    }

    public Hand(ArrayList<String> cards) {
        this.card1 = cards.get(0);
        this.card2 = cards.get(1);
        this.playerName = "";
    }

    public Hand(Deck cards) {
        this.card1 = cards.get(0);
        this.card2 = cards.get(1);
        this.playerName = "";
    }

    public Hand(Card card1, Card card2) {
        this.card1 = card1.toString();
        this.card2 = card2.toString();
        this.playerName = "";
    }

    public Hand(String card1, String card2) {
        this.card1 = card1;
        this.card2 = card2;
        this.playerName = "";
    }

    public void setPlayerName(String name) {
        this.playerName = name;
    }

    public void setCards(ArrayList<String> cards) {
        this.card1 = cards.get(0);
        this.card2 = cards.get(1);
    }

    public void setCards(Card card1, Card card2) {
        this.card1 = card1.toString();
        this.card2 = card2.toString();
    }

    public void setCards(String card1, String card2) {
        this.card1 = card1;
        this.card2 = card2;
    }

    public void setCards(Deck cards) {
        this.card1 = cards.get(0);
        this.card2 = cards.get(1);
    }

    public void setCard(int which, String value) {
        if (which == 1) {
            this.card1 = value;
        } else {
            this.card2 = value;
        }
    }

    public String toSimpleString() {
        return this.card1 + this.card2;
    }

    public String[] toStringList() {
        String[] temp = new String[2];
        temp[0] = this.card1;
        temp[1] = this.card2;
        return temp;
    }

    @Override
    public void setDebugMode(boolean mode) {
        this.debugMode = mode;
    }

    @Override
    public String toString() {
        if (debugMode) {
            System.out.println("---Method toString()---");
            System.out.println(this.playerName + " hand: " + this.card1 + ", " + this.card2 + "\n---End of Method toString()---");
        }
        return "[" + this.playerName + "'s hand: " + this.card1 + ", " + this.card2 + "]";

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
