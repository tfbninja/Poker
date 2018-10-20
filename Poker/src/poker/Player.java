package poker;

import java.util.Random;

/**
 *
 * @author Tim Barber
 */
public class Player {

    private int money;
    private int transientBet;
    private Hand hand;
    private String name;
    private Random random;
    private boolean isDealer;
    private boolean isLittle;
    private boolean isBig;
    private boolean isActive;

    private boolean isAllIn;

    private boolean debugMode = false;

    public Player() {
        this.random = new Random();
        this.isAllIn = false;
        this.money = 0;
        this.transientBet = 0;
        this.isActive = true;
        this.hand = new Hand();
        String tempNum = "";
        for (int i = 0; i < 6; i++) {
            tempNum += String.valueOf(Math.abs(random.nextInt()) % 10);
        }
        this.name = "Player_" + tempNum;
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
        this.name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        this.money = money;
        this.transientBet = 0;
        this.isActive = true;
        this.hand = cards;

        this.isDealer = false;
        this.isLittle = false;
        this.isBig = false;

        this.random = new Random(0);

        if (debugMode) {
            System.out.println("Player instantiated with vars, Name: " + this.name + ", Money: " + this.money + ", Cards: " + this.hand + "");
        }
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    public void setTransientBet(int amt) {
        this.transientBet = amt;
    }

    public int getTransientBet() {
        return this.transientBet;
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
        if (name.length() > 1) {
            this.name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        } else {
            this.name = name.toUpperCase();
        }
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
        return "<" + this.name + ": Money: " + this.money + ", Cards: " + this.hand
                + ", is all in: " + this.isAllIn + ", is dealer: " + this.isDealer
                + ", is little blind: " + this.isLittle + ", is big blind: "
                + this.isBig + ">\n";
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
