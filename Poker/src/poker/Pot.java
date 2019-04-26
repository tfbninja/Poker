package poker;

import java.util.Random;

/**
 *
 * @author Tim Barber
 */
public class Pot { // haha..                                                    ok shut up i know i have no life i get it

    private int amount;
    private int baseValue;
    private String name;
    private Random random = new Random();

    public Pot() { // that's usually called dispensary right? Ok I swear I'm done
        baseValue = 0;
        amount = 0;
        String tempNum = "";
        for (int i = 0; i < 6; i++) {
            tempNum += String.valueOf(Math.abs(random.nextInt()) % 10);
        }
        name = "POT_" + tempNum; // hey quick question why do numbers seem uppercase when they're surrounded by uppercase letters but lowercase when they aren't? Is that just me? Let me know in the comments below and don't forget to SMAAAASH that like button, subscribe, and hit the little bell for notifications so you don't miss a single video. Thanks for coming to my TED talk, peace out.                 (I added these spaces so you don't accidentally scroll to the next line while holding down the right arrow key trying to read this horrendously long comment)
    }

    public Pot(int amt) {
        this.baseValue = amt;
        this.amount = amt;
        String tempNum = "";
        for (int i = 0; i < 6; i++) {
            tempNum += String.valueOf(Math.abs(random.nextInt()) % 10);
        }
        name = "Pot_" + tempNum;
    }

    public Pot(int amt, String name) { // no background checks or anything needed here
        this.baseValue = amt;
        this.amount = amt;
        this.name = name;
    }

    public int getBaseValue() {
        return this.baseValue;
    }

    public void setBaseValue(int amt) {
        this.baseValue = amt;
    }

    public int getAmount() {
        return this.amount;
    }

    public String getName() {
        return this.name;
    }

    public void setAmount(int amt) {
        this.amount = amt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addMoney(int amt) {
        this.amount += amt;
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
