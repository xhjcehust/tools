package com.github;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

/**
 * @author xiaohengjin <xiaohengjin@kuaishou.com>
 * Created on 2020-07-18
 */
public class Entry {

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
