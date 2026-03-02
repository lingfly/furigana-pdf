package com.lingfly.furigana_latex;

import java.io.*;
import java.util.regex.Pattern;

/**
 * latex 转 txt
 */
public class Latex2txt {
    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/ojisama/chapter1.tex");
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = new byte[fileInputStream.available()];
        fileInputStream.read(bytes);
        String latex = new String(bytes);
        Pattern pattern = Pattern.compile("\\\\ruby\\{(.*?)\\}\\{.*?\\}");
        String txt = pattern.matcher(latex).replaceAll("$1");
        PrintStream printStream = new PrintStream("src/main/resources/out.txt");
        printStream.print(txt);
    }
}
