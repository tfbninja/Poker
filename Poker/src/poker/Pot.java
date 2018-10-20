package poker;

import java.util.Random;

/**
 *
 * @author Tim Barber
 */
public class Pot {

    private int amount;
    private String name;
    private Random random = new Random();

    public Pot() {
        amount = 0;
        String tempNum = "";
        for (int i = 0; i < 6; i++) {
            tempNum += String.valueOf(Math.abs(random.nextInt()) % 10);
        }
        name = "Pot_" + tempNum;
    }

    public Pot(int amt) {
        this.amount = amt;
        String tempNum = "";
        for (int i = 0; i < 6; i++) {
            tempNum += String.valueOf(Math.abs(random.nextInt()) % 10);
        }
        name = "Pot_" + tempNum;
    }

    public Pot(int amt, String name) {
        this.amount = amt;
        this.name = name;
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

    public void addMoney(int amt){
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
