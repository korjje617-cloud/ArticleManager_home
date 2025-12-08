package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** 현재 날짜 및 시각 가져오기(str) **/
public class Util {
    public static String getNowStr() {
        LocalDateTime now = LocalDateTime.now();
        String formatNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return formatNow;
    }
}