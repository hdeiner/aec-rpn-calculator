package test.com.siq.rpnCalculator;

import com.siq.rpnCalculator.Token;
import com.siq.rpnCalculator.Tokenizer;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TokenTest {

    @Test
    public void canDistinguishBetweenOperatorsAndOperands() {
        Tokenizer tokenizer = new Tokenizer("1 2 +");
        Token token = new Token(tokenizer.getNextToken());
        assertThat(token.isOperand(), is(true));
        token = new Token(tokenizer.getNextToken());
        assertThat(token.isOperand(), is(true));
        token = new Token(tokenizer.getNextToken());
        assertThat(token.isOperator(), is(true));
    }

    @Test
    public void canRecognizeFloatingPointNumbers() {
        Tokenizer tokenizer = new Tokenizer("1.00");
        Token token = new Token(tokenizer.getNextToken());
        assertThat(token.isFloat(), is(true));
    }

    @Test
    public void rejectsIllFormedFloatingPointNumbers() {
        Tokenizer tokenizer = new Tokenizer("1.00.");
        Token token = new Token(tokenizer.getNextToken());
        assertThat(token.isFloat(), is(false));
        tokenizer = new Tokenizer(".");
        token = new Token(tokenizer.getNextToken());
        assertThat(token.isFloat(), is(false));
    }

    @Test
    public void trivialDisplayString() {
        assertThat(new Token("3").toString(), is("3"));
    }

    @Test
    public void testMonadic() {
        assertThat(new Token("%").isMonadic(), is(true));
    }

    @Test
    public void testDyadic() {
        assertThat(new Token("+").isDyadic(), is(true));
        assertThat(new Token("-").isDyadic(), is(true));
        assertThat(new Token("*").isDyadic(), is(true));
        assertThat(new Token("/").isDyadic(), is(true));
    }

    @Test
    public void testOperandInteger() {
        assertThat(new Token("12345").isInteger(), is(true));
    }

    @Test
    public void testOperandFloat() {
        assertThat(new Token("123.45").isFloat(), is(true));
    }

    @Test
    public void testStackOperators() {
        assertThat(new Token("SWAP").isStackOperator(), is(true));
        assertThat(new Token("dRoP").isStackOperator(), is(true));
        assertThat(new Token("clear").isStackOperator(), is(true));
        assertThat(new Token("ROLL").isStackOperator(), is(true));
    }
}
