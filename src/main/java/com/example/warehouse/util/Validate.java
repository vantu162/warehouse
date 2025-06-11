package com.example.warehouse.util;

import java.util.regex.Pattern;

public class Validate {
//    Pattern.compile()	Biên dịch chuỗi regex thành một đối tượng Pattern
//    matcher()	Tạo một đối tượng Matcher để kiểm tra chuỗi đầu vào
//    matches()	Kiểm tra toàn bộ chuỗi có khớp với biểu thức chính quy hay không

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    private static final Pattern STRONG_PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{5,20}$");

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10,11}$");

    private static final Pattern NUMBER_PATTERN = Pattern.compile("^\\d+$");

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isStrongPassword(String password) {
        return password != null && STRONG_PASSWORD_PATTERN.matcher(password).matches();
    }

    public static boolean isValidUsername(String username) {
        return username != null && USERNAME_PATTERN.matcher(username).matches();
    }

    public static boolean isValidStr(String str) {
        return str != null && USERNAME_PATTERN.matcher(str).matches();
    }

    public static boolean isValidNumber(long num) {
        return num > 0;
    }

}
