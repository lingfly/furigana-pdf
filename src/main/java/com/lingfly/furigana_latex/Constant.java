package com.lingfly.furigana_latex;

import java.util.regex.Pattern;

public class Constant {

    public static final String RUBY = "\\ruby";
    public static final String braces = "{%s}";

    public static final Pattern EOF = Pattern.compile("[。？」]$");
}
