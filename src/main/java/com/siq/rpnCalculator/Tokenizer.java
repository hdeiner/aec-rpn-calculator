package com.siq.rpnCalculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {

    private String inputString = "";
    private Pattern nextToken;
    private Matcher tokenMatcher;

    public Tokenizer(String input) {
        inputString = input;
        nextToken = Pattern.compile("\\s*(\\S+)\\s*");
        tokenMatcher = nextToken.matcher(inputString);
    }

    public boolean isEmpty() {
        return !inputString.matches("^.*\\S.*$");
    }

    public boolean hasMoreTokens() {
        return inputString.matches("^.*\\S.*$");
    }

    public String getNextToken() {
        if (tokenMatcher.find()) {
            String token = tokenMatcher.group(1);
            inputString = inputString.substring(tokenMatcher.end());
            tokenMatcher = nextToken.matcher(inputString);
            return token;
        }
        else {
            return "";
        }
    }
}
