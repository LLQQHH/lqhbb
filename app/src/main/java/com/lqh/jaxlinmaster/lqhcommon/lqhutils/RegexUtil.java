package com.lqh.jaxlinmaster.lqhcommon.lqhutils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则工具类
 */
public class RegexUtil {
    public static float EPSILON_E6= (float) Math.pow(10,-6);

    /**
     * 是否是手机号
     * @param mobileNumber 1 然后3-9开头,最后9位数字就可以了
     * @return true，false
     */
    public static boolean isPhoneNumber(String mobileNumber){
        String telRegex = "[1][3456789]\\d{9}";
        if (StringUtil.isEmpty(mobileNumber)) {
            return false;
        }
        return mobileNumber.matches(telRegex);
    }

    /**
     * @param phoneNum
     * @return 返回去掉空格后的数字
     */
    public static String formatPhoneNum(String phoneNum) {
        String regex = "(\\+86)|[^0-9]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNum);
        return matcher.replaceAll("");
    }


    /*** 验证固定电话号码
     * @return 如果是符合格式的字符串, 返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean isFixedLineNumber(String str) {
        String regex = "^0(10|2[0-5789]-|\\d{3})-?\\d{7,8}$";
        return Pattern.matches(regex, str);
    }

    //^(?=^.{3,255}$)(http(s)?:\\/\\/)?(www\\.)?[a-zA-Z0-9][-a-zA-Z0-9]" +
    //                "{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+(:\\d+)*(\\/\\w+\\.\\w+)*$
    public static boolean isWebUrl(String url){
        String regex = "[a-zA-z]+://[^\\s]*";
        Pattern pattern = Pattern.compile(regex);
        if (pattern.matcher(url).matches()) {
            return true;
        } else {
            return false;
        }
    }
    //密码 6到12位的字母大小写及数字下划线,而且不能为纯数字
    //^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$ 这个也可以判断 ，https://www.cnblogs.com/echolife/p/10509783.html
    public static boolean isPassword(String password) {
        Pattern p = Pattern.compile("^(?!^\\d*$)([0-9a-zA-Z_]{6,12}$)");
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /**
     * @param input 填写
     * @return 返回数字
     */
    public static String getDigit(String input){
        //匹配非数字
        Pattern pattern=Pattern.compile("[^0-9]");
        //替换非数字
        Matcher matcher = pattern.matcher(input);
        //这样返回的就是纯数字
        return matcher.replaceAll("");
    }
    /**
     * @param input 填写
     * @return 返回非数字
     */
    public static String removeDigit(String input){
        //匹配数字
        Pattern pattern=Pattern.compile("[\\d]");
        //替换数字
        Matcher matcher = pattern.matcher(input);
        //这样返回的就是非数字
        return matcher.replaceAll("");
    }

    /**是否为数字
     * @param str
     * @return
     */
    public static boolean isNumber(String str){
        if(StringUtil.isEmpty(str)){
            return  false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");

        return pattern.matcher(str).matches();

    }
    /**
     * 判断一个字符串是否是数字组成
     * @param s
     * @return true是数字，可以是小数
     */
    public static boolean judgeTextIsDigit(String s){
        if (StringUtil.isEmpty(s)){
            return false;
        }
        int pointCount = 0;
        for (int i = 0; i < s.length(); i++) {
            if (i == 0){
                if (46 == s.charAt(i) || !Character.isDigit(s.charAt(i))){
                    return false;//第一个或最后一个位置是“.”返回false
                } else if (s.length() == 1){
                    return true;
                }
            } else if (i == s.length() - 1){
                if (46 == s.charAt(i) || !Character.isDigit(s.charAt(i))){
                    return false;//第一个或最后一个位置是“.”返回false
                }
                return true;
            } else {
                if (!Character.isDigit(s.charAt(i)) && (46 != s.charAt(i))){
                    return false;
                } else if (46 == s.charAt(i)){
                    pointCount ++;
                    if (pointCount > 1){//仅可以存在一个小数点
                        return false;
                    }
                }
            }
        }
        return false;
    }
    /**是否为数字或者小数
     * @param str 注意0.这样的格式不算,即使Float.value(0.)不会报错
     * @return
     */
    public static boolean isFloat(String str) {
        if(StringUtil.isEmpty(str)){
            return  false;
        }
        Pattern pattern = Pattern.compile("((-+)?+[1-9]+[0-9]*|-?+0)(\\.[\\d]+)?");
        return pattern.matcher(str).matches();
    }
    //验证银行卡号
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }
    //从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    /**
     * 匹配16进制颜色值
     * @param color
     * @return
     */
    public static boolean is16Color(String color){
        if (StringUtil.isEmpty(color)){
            return false;
        }
        String colorRegex = "^#[0-9a-fA-F]{6}$";
        return color.matches(colorRegex);
    }


    public static void main(String[] args) {

//        System.out.println(isFloat("500"));
//        System.out.println(isFloat("50.0"));
//        System.out.println(isFloat("1"));
//        System.out.println(isFloat("-0.5"));
//        System.out.println(isFloat("0."));
//        System.out.println(isFloat(".5"));
//        System.out.println(isFloat("-1.5"));
    }


}
