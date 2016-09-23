package com.kaalivandi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Nandha on 9/23/2016.
 * A custom uitility string class
 */

public class StringValidator {
    public static boolean  CheckUserName(String mUserName){
        if (mUserName.length()<5){
            return false;

        }

        Pattern pattern = Pattern.compile("/^[A-z]+$/");
        Matcher matcher = pattern.matcher(mUserName);
        return matcher.matches();


    }

    public static boolean checkPassword(String mUserPassword){
        if (mUserPassword.length()< 6){
            return false;
        }
        return !mUserPassword.isEmpty();

    }


    public static boolean checkeEMail(String eMailEnterred) {
        Pattern pattern = Pattern.compile("^.+@.+\\..+$");
        Matcher matcher = pattern.matcher(eMailEnterred);
        return matcher.matches();
    }

    public static boolean checkPhoneNumber(String phone) {
        String regexStr = "^[0-9]{10}$";
        return phone.matches(regexStr);
    }
}
