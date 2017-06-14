package com.siq.rpnCalculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenStream {

    private String inputString = "";
    private Pattern nextToken;
    private Matcher tokenMatcher;

    public TokenStream(String input) {
        inputString = input;
        nextToken = Pattern.compile("\\s*(\\S+)\\s*");
        tokenMatcher = nextToken.matcher(inputString);
    }

    public boolean hasMoreTokens() {
        return inputString.matches("^.*\\S.*$");
    }

    public Token getNextToken() {
        if (tokenMatcher.find()) {
            String nextMatch = tokenMatcher.group(1);
            inputString = inputString.substring(tokenMatcher.end());
            tokenMatcher = nextToken.matcher(inputString);
            return new Token(nextMatch);
        }
        else return null;
    }
}
