package com.siq.rpnCalculator;

import java.util.Stack;

public class RpnCalculator {

    private TokenStream tokenStream;
    private Stack<Token> calculatorStack;

    public RpnCalculator() {
        calculatorStack = new Stack<>();
    }

    public void setTokenStream(TokenStream tokenStream) {
        this.tokenStream = tokenStream;
    }

    public Token calculate() throws Exception {
        while (tokenStream.hasMoreTokens()) {
            Token token = new Token(tokenStream.getNextToken());
            calculatorStack.push(token);
            if (token.isMonadic()) { handleMonadicOperator(token); }
            if (token.isDyadic()) { handleDyadicOperator(token); }
            if (token.isStackOperator()) { handleStackOperator(token); }
        }
        if (calculatorStack.size() > 1) { throw new Exception("unused tokens on calculator stack"); }
        return calculatorStack.pop();
    }

    private void handleMonadicOperator(Token token) throws Exception {
        calculatorStack.pop();
        if (calculatorStack.empty()) { throw new Exception("Monadic operator of \"" + token.toString() + "\" encountered, but there is no operand on the stack"); }
        Token operand1 = calculatorStack.pop();
        Token result = new Token(monadicOperator(token, operand1).toString());
        calculatorStack.push(result);
    }

    private void handleDyadicOperator(Token token) throws Exception {
        calculatorStack.pop();
        if (calculatorStack.empty()) { throw new Exception("Dyadic operator of \"" + token.toString() + "\" encountered, but there is no operand on the stack"); }
        Token operand1 = calculatorStack.pop();
        if (calculatorStack.empty()) { throw new Exception("Dyadic operator of \"" + token.toString() + "\" encountered, but there is only one operand on the stack"); }
        Token operand2 = calculatorStack.pop();
        Token result = new Token(dyadicOperator(token, operand1, operand2).toString());
        calculatorStack.push(result);
    }

    private void handleStackOperator(Token token) throws Exception {
        calculatorStack.pop();
        if (token.getOperatorType() == Token.OPERATOR.SWAP) {
            if (calculatorStack.size() < 2) { throw new Exception("can't SWAP with less than two items on the calculator stack"); }
            Token operand1 = calculatorStack.pop();
            Token operand2 = calculatorStack.pop();
            calculatorStack.push(operand1);
            calculatorStack.push(operand2);
        }
        if (token.getOperatorType() == Token.OPERATOR.DROP) {
            if (calculatorStack.empty()) { throw new Exception("can't DROP with an empty calculator stack"); }
            calculatorStack.pop();
        }
        if (token.getOperatorType() == Token.OPERATOR.CLEAR) { calculatorStack.clear(); }
        if (token.getOperatorType() == Token.OPERATOR.ROLL) {
            if (calculatorStack.empty()) { throw new Exception("can't ROLL with an empty calculator stack"); }
            Token oldLast = calculatorStack.get(0);
            calculatorStack.removeElementAt(0);
            calculatorStack.push(oldLast);
        }
    }

    private Token dyadicOperator(Token operator, Token operand1, Token operand2){
        Token result = new Token("");
        Double arg1 = Double.parseDouble(operand1.toString());
        Double arg2 = Double.parseDouble(operand2.toString());
        if (operator.getOperatorType() == Token.OPERATOR.PLUS) { result = new Token(Double.toString(arg2 + arg1)); }
        if (operator.getOperatorType() == Token.OPERATOR.MINUS) { result = new Token(Double.toString(arg2 - arg1)); }
        if (operator.getOperatorType() == Token.OPERATOR.MULTIPLY) { result = new Token(Double.toString(arg2 * arg1)); }
        if (operator.getOperatorType() == Token.OPERATOR.DIVIDE) { result = new Token(Double.toString(arg2 / arg1)); }
        return result;
    }

    private Token monadicOperator(Token operator, Token operand1){
        Token result = new Token("");
        Double arg1 = Double.parseDouble(operand1.toString());
        if (operator.getOperatorType() == Token.OPERATOR.PERCENT) { result = new Token(Double.toString(arg1 * 0.01)); }
        return result;
    }

}
