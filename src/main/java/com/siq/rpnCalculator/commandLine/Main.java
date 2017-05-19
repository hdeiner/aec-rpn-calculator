package com.siq.rpnCalculator.commandLine;

import com.siq.rpnCalculator.RpnCalculator;
import com.siq.rpnCalculator.TokenStream;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Agile Engineering RPN Calculator");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = "xxx";
        while (input.length() > 0) {
            System.out.print("> ");
            input = reader.readLine().trim();
            if (input.length() > 0) {
                RpnCalculator rpnCalculator = new RpnCalculator();
                rpnCalculator.setTokenStream(new TokenStream(input));
                System.out.println(rpnCalculator.calculate().toString());
            }
        }
    }
}
