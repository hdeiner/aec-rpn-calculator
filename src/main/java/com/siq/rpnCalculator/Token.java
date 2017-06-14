package com.siq.rpnCalculator;

public class Token {

    private String input;

    public enum OPERATOR { ILLEGAL, PLUS, MINUS, MULTIPLY, DIVIDE, PERCENT, SWAP, DROP, CLEAR, ROLL}
    private OPERATOR operatorType;

    public enum OPERAND { ILLEGAL, INTEGER, FLOAT}
    private OPERAND operandType;

    public Token(String input) {
        this.input = input;
        determineOperandType();
        determineOperatorType();
    }

    private void determineOperandType() {
        operandType = OPERAND.ILLEGAL;
        if (input.matches("^[0-9]+$")) { operandType = OPERAND.INTEGER;}
        if (input.matches("^[0-9]*[\\.][0-9]+$")) { operandType = OPERAND.FLOAT;}
    }

    private void determineOperatorType() {
        operatorType = OPERATOR.ILLEGAL;
        if (input.matches("^\\+$")) { operatorType = OPERATOR.PLUS; }
        if (input.matches("^\\-$")) { operatorType = OPERATOR.MINUS; }
        if (input.matches("^\\*$")) { operatorType = OPERATOR.MULTIPLY; }
        if (input.matches("^\\/$")) { operatorType = OPERATOR.DIVIDE; }
        if (input.matches("^\\%$")) { operatorType = OPERATOR.PERCENT; }
        if (input.matches("^[Ss][Ww][Aa][Pp]$")) { operatorType = OPERATOR.SWAP; }
        if (input.matches("^[Dd][Rr][Oo][Pp]$")) { operatorType = OPERATOR.DROP; }
        if (input.matches("^[Cc][Ll][Ee][Aa][Rr]$")) { operatorType = OPERATOR.CLEAR; }
        if (input.matches("^[Rr][Oo][Ll][Ll]$")) { operatorType = OPERATOR.ROLL; }
    }

    public String toString() {
        return input;
    }

    public boolean isOperator() { return (operatorType != OPERATOR.ILLEGAL);}

    public boolean isMonadic() {
        return (operatorType == OPERATOR.PERCENT);
    }

    public boolean isDyadic() { return (operatorType == OPERATOR.PLUS) || (operatorType == OPERATOR.MINUS) || (operatorType == OPERATOR.MULTIPLY) || (operatorType == OPERATOR.DIVIDE); }

    public boolean isStackOperator() { return (operatorType == OPERATOR.SWAP) || (operatorType == OPERATOR.DROP) || (operatorType == OPERATOR.CLEAR) || (operatorType == OPERATOR.ROLL); }

    public boolean isOperand() { return (operandType != OPERAND.ILLEGAL);}

    public boolean isInteger() {
        return (operandType == OPERAND.INTEGER);
    }

    public boolean isFloat() {
        return (operandType == OPERAND.FLOAT);
    }

    OPERATOR getOperatorType() {
        return operatorType;
    }

}
