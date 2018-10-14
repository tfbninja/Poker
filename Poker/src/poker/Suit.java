package poker;

/**
 *
 * @author Tim Barber
 */

/*
 * Suits: ♥,♦,♣,♠
 */
public class Suit {
    private String suitString;

    public Suit(){
        suitString = "";
    }

    public Suit(String suit){
        suitString = suit.toLowerCase();
    }

    public String getSuit(){
        return suitString;
    }

    public void setSuit(String suit){
        suitString = suit.toLowerCase();
    }

    public String intToSuit(int suitnum){
        switch(suitnum){
            case 0:
                return "hearts";
            case 1:
                return "diamonds";
            case 2:
                return "clubs";
            case 3:
                return "spades";
            default:
                return "invalid";
        }
    }
    
    public char unicodeChar(String suit){
        suit = suit.toLowerCase();
        switch(suit){
            case "hearts":
                return '♥';
            case "diamonds":
                return '♦';
            case "clubs":
                return '♣';
            case "spades":
                return '♠';
            default:
                return '-';
        }
    }

    @Override
    public String toString(){
        return String.valueOf(unicodeChar(suitString));
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
