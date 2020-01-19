package com.winderinfo.wechat.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtils {

    /**
     * 获取拼音
     */
    public static String getPingYin(String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        char[] input = inputString.trim().toCharArray();
        StringBuilder output = new StringBuilder();

        try {
            for (char curChar : input) {
                if (Character.toString(curChar).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(curChar, format);
                    output.append(temp[0]);
                } else {
                    output.append(Character.toString(curChar));
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    /**
     * 获取第一个字的拼音首字母
     */
    public static String getFirstSpell(String chinese) {
        StringBuilder pinYinStringBuilder = new StringBuilder();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        for (char curChar : arr) {
            if (curChar > 128) {
                try {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(curChar, defaultFormat);
                    if (temp != null) {
                        pinYinStringBuilder.append(temp[0].charAt(0));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinYinStringBuilder.append(curChar);
            }
        }
        return pinYinStringBuilder.toString().replaceAll("\\W", "").trim();
    }

    /**
     * 获取第一个字的拼音首字母
     */
    public static String getFirstLetter(String chinese) {
        //StringBuilder pinYinStringBuilder = new StringBuilder();

        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        char[] input = chinese.trim().toCharArray();// 把字符串转化成字符数组
        String output = "";

        try {
            for (int i = 0; i < input.length; i++) {
                // \\u4E00是unicode编码，判断是不是中文
                if (java.lang.Character.toString(input[i]).matches(
                        "[\\u4E00-\\u9FA5]+")) {
                    // 将汉语拼音的全拼存到temp数组
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(
                            input[i], defaultFormat);
                    // 取拼音的第一个读音
                    output += temp[0];
                }
                // 大写字母转化成小写字母
                else if (input[i] > 'A' && input[i] < 'Z') {
                    output += java.lang.Character.toString(input[i]);
                    output = output.toLowerCase();
                }
                output += java.lang.Character.toString(input[i]);
            }
        } catch (Exception e) {

        }

        String outPut = "";
        String substring = output.substring(0, 1);


        if (substring.equalsIgnoreCase("a")) {
            outPut = "A";
        } else if (substring.equalsIgnoreCase("b")) {
            outPut = "B";
        } else if (substring.equalsIgnoreCase("c")) {
            outPut = "C";
        } else if (substring.equalsIgnoreCase("d")) {
            outPut = "D";
        } else if (substring.equalsIgnoreCase("e")) {
            outPut = "E";
        } else if (substring.equalsIgnoreCase("f")) {
            outPut = "F";
        } else if (substring.equalsIgnoreCase("g")) {
            outPut = "G";
        } else if (substring.equalsIgnoreCase("h")) {
            outPut = "H";
        } else if (substring.equalsIgnoreCase("i")) {
            outPut = "I";
        } else if (substring.equalsIgnoreCase("j")) {
            outPut = "J";
        } else if (substring.equalsIgnoreCase("k")) {
            outPut = "K";
        } else if (substring.equalsIgnoreCase("l")) {
            outPut = "L";
        } else if (substring.equalsIgnoreCase("m")) {
            outPut = "M";
        } else if (substring.equalsIgnoreCase("n")) {
            outPut = "N";
        } else if (substring.equalsIgnoreCase("o")) {
            outPut = "O";
        } else if (substring.equalsIgnoreCase("p")) {
            outPut = "P";
        } else if (substring.equalsIgnoreCase("q")) {
            outPut = "Q";
        } else if (substring.equalsIgnoreCase("r")) {
            outPut = "R";
        } else if (substring.equalsIgnoreCase("s")) {
            outPut = "S";
        } else if (substring.equalsIgnoreCase("t")) {
            outPut = "T";
        } else if (substring.equalsIgnoreCase("u")) {
            outPut = "U";
        } else if (substring.equalsIgnoreCase("v")) {
            outPut = "V";
        } else if (substring.equalsIgnoreCase("w")) {
            outPut = "W";
        } else if (substring.equalsIgnoreCase("x")) {
            outPut = "X";
        } else if (substring.equalsIgnoreCase("y")) {
            outPut = "Y";
        } else if (substring.equalsIgnoreCase("z")) {
            outPut = "Z";
        } else {
            outPut = "#";
        }


        return outPut;
    }
}
