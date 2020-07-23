package com.github;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @author xiaohengjin <xiaohengjin@kuaishou.com>
 * Created on 2020-07-18
 */
public class Time2Str {
    private static final Map<Pattern, String> patternFormatMap = new LinkedHashMap<>();

    static {
        patternFormatMap.put(Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{1,3}"), "yyyy-MM-dd HH:mm:ss.SSS");
        patternFormatMap.put(Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}"), "yyyy-MM-dd HH:mm:ss");
        patternFormatMap.put(Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}"), "yyyy-MM-dd HH:mm");
        patternFormatMap.put(Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}"), "yyyy-MM-dd HH");
        patternFormatMap.put(Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}"), "yyyy-MM-dd");
        patternFormatMap.put(Pattern.compile("\\d{4}/\\d{1,2}/\\d{1,2}"), "yyyy/MM/dd");
        patternFormatMap.put(Pattern.compile("\\d{4}\\.\\d{1,2}\\.\\d{1,2}"), "yyyy.MM.dd");
        patternFormatMap.put(Pattern.compile("\\d{4}\\d{1,2}\\d{1,2}\\d{1,2}\\d{1,2}\\d{1,2}"), "yyyyMMddHHmmss");
        patternFormatMap.put(Pattern.compile("\\d{4}\\d{1,2}\\d{1,2}\\d{1,2}\\d{1,2}"), "yyyyMMddHHmm");
        patternFormatMap.put(Pattern.compile("\\d{4}\\d{1,2}\\d{1,2}\\d{1,2}"), "yyyyMMddHH");
        patternFormatMap.put(Pattern.compile("\\d{4}\\d{1,2}\\d{1,2}"), "yyyyMMdd");

    }

    public static String process(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        str = str.trim();
        for (Map.Entry<Pattern, String> entry: patternFormatMap.entrySet()) {
            Matcher matcher = entry.getKey().matcher(str);
            if (matcher.find()) {
                String date = matcher.group();
                long timestamp = 0;
                try {
                    timestamp = convertTimeStr2Long(date, entry.getValue());
                } catch (ParseException ignored) {
                }
                return String.valueOf(timestamp);
            }
        }

        str = str.replace(",", "");
        if (StringUtils.isNumeric(str)) {
            return sdf.format(Long.parseLong(str));
        }

        return "unrecognized time str";
    }

    public static long convertTimeStr2Long(String str, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date;
        date = sdf.parse(str);
        return date.getTime();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            if (!scanner.hasNextLine()) {
                return;
            }
            String str = scanner.nextLine();
            if (str == null) {
                return;
            }
            if (StringUtils.isBlank(str)) {
                continue;
            }
            String output = Time2Str.process(str);
            System.out.println(output);
        }
    }
}
