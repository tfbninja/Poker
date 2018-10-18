package poker;

import java.util.Random;

/**
 *
 * @author Tim Barber
 */
public class Player {

    private int money;
    private Hand hand;
    private String name;
    Random random;
    private boolean isDealer;
    private boolean isLittle;
    private boolean isBig;

    private boolean isAllIn;

    private boolean debugMode = false;

    public Player() {
        this.random = new Random(0);
        this.isAllIn = false;
        this.money = 0;
        this.hand = new Hand();
        this.name = "Player " + String.valueOf(Math.abs(random.nextInt()));
        this.isDealer = false;
        this.isLittle = false;
        this.isBig = false;
        this.isAllIn = false;

        if (debugMode) {
            System.out.println("Player instantiated with no vars, Name: " + this.name + ", Money: " + this.money + ", Cards: " + this.hand + "");
        }
    }

    public Player(String name, int money, Hand cards) {
        this.isAllIn = false;
        this.name = name;
        this.money = money;
        this.hand = cards;

        this.isDealer = false;
        this.isLittle = false;
        this.isBig = false;

        this.random = new Random(0);

        if (debugMode) {
            System.out.println("Player instantiated with vars, Name: " + this.name + ", Money: " + this.money + ", Cards: " + this.hand + "");
        }
    }

    public void setMoney(int amt) {
        this.money = amt;
    }

    public int getMoney() {
        return this.money;
    }

    public Hand getCards() {
        return this.hand;
    }

    public void setCards(Hand cards) {
        this.hand = cards;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public boolean getDealer() {
        return this.isDealer;
    }

    public boolean getLittle() {
        return this.isLittle;
    }

    public boolean getBig() {
        return this.isBig;
    }

    public void makeDealer(boolean dealer) {
        this.isDealer = dealer;
    }

    public void makeLittle(boolean little) {
        this.isLittle = little;
    }

    public void makeBig(boolean big) {
        this.isBig = big;
    }

    public String goAllIn() {
        if (isAllIn) {
            return "Invalid, already all in.";
        }
        this.isAllIn = true;
        return "Successfull";
    }

    @Override
    public String toString() {
        return this.name + ": Money: " + this.money + ", Cards: " + this.hand
                + ", is all in: " + this.isAllIn + ", is dealer: " + this.isDealer
                + ", is little blind: " + this.isLittle + ", is big blind: "
                + this.isBig + ".";
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
