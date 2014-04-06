package com.android.dialer.dialpad;

import com.android.dialer.util.HanziToPinyin;
////////////////////////////////////////////////
import android.util.Log;

public class ChinaSmartDialMap implements SmartDialMap {

   

    private static final char[] LATIN_LETTERS_TO_DIGITS = {
        '2', '2', '2', // A,B,C -> 2
        '3', '3', '3', // D,E,F -> 3
        '4', '4', '4', // G,H,I -> 4
        '5', '5', '5', // J,K,L -> 5
        '6', '6', '6', // M,N,O -> 6
        '7', '7', '7', '7', // P,Q,R,S -> 7
        '8', '8', '8', // T,U,V -> 8
        '9', '9', '9', '9' // W,X,Y,Z -> 9
    };

    @Override
    public boolean isValidDialpadAlphabeticChar(char ch) {
        return (ch >= 'a' && ch <= 'z');
    }

    @Override
    public boolean isValidDialpadNumericChar(char ch) {
        return (ch >= '0' && ch <= '9');
    }

    @Override
    public boolean isValidDialpadCharacter(char ch) {
        return (isValidDialpadAlphabeticChar(ch) || isValidDialpadNumericChar(ch));
    }

    /*
     * The switch statement in this function was generated using the python code:
     * from unidecode import unidecode
     * for i in range(192, 564):
     *     char = unichr(i)
     *     decoded = unidecode(char)
     *     # Unicode characters that decompose into multiple characters i.e.
     *     #  into ss are not supported for now
     *     if (len(decoded) == 1 and decoded.isalpha()):
     *         print "case '" + char + "': return '" + unidecode(char) +  "';"
     *
     * This gives us a way to map characters containing accents/diacritics to their
     * alphabetic equivalents. The unidecode library can be found at:
     * http://pypi.python.org/pypi/Unidecode/0.04.1
     *
     * Also remaps all upper case latin characters to their lower case equivalents.
     */
    @Override
    public char normalizeCharacter(char ch) {
        String pinyinBuff = null;
        pinyinBuff = HanziToPinyin.getPinYin(String.valueOf(ch));
        //Log.i("T9T","HanziToPinyin =" + pinyin);
        if (pinyinBuff == null) {
            return ch;
        }
        return pinyinBuff.toCharArray()[0];
    }                                     

    @Override
    public byte getDialpadIndex(char ch) {
        // Log.i("T9T","getDialpadIndex = " + ch);
        if (ch >= '0' && ch <= '9') {
            // Log.i("T9T","getDialpadIndex Nreturn  = " + (ch - '0'));
            return (byte) (ch - '0');
        } else if (ch >= 'a' && ch <= 'z') {
            // Log.i("T9T","getDialpadIndex Creturn  = " + (LATIN_LETTERS_TO_DIGITS[ch - 'a'] - '0'));
            return (byte) (LATIN_LETTERS_TO_DIGITS[ch - 'a'] - '0');
        } else {
            return -1;
        }
    }

    @Override
    public char getDialpadNumericCharacter(char ch) {
        if (ch >= 'a' && ch <= 'z') {
            return LATIN_LETTERS_TO_DIGITS[ch - 'a'];
        }
        return ch;
    }

}
