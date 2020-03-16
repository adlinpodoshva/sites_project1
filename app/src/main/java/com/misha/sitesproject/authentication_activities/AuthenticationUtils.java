package com.misha.sitesproject.authentication_activities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthenticationUtils {
    public static final int MIN_PASSWORD_LENGTH = 7;

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^.+@.+\\..+$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPhone(String phone) {
        return phone.length() >= 8 && phone.length() <= 11;
    }
}
