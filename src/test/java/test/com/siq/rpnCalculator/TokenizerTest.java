package test.com.siq.rpnCalculator;

import com.siq.rpnCalculator.Tokenizer;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TokenizerTest {

    @Test
    public void emptyTokenizerShouldBeEmpty() {
        Tokenizer tokenizer = new Tokenizer("");
        assertThat(tokenizer.isEmpty(), is(true));
    }

    @Test
    public void nonEmptyTokenizerShouldNotBeEmpty() {
        Tokenizer tokenizer = new Tokenizer("   Hello World!  ");
        assertThat(tokenizer.isEmpty(), is(false));
    }

    @Test
    public void whitespaceDelimitedTokensReturnInSequence() {
        Tokenizer tokenizer = new Tokenizer("  one two   three  ");
        assertThat(tokenizer.hasMoreTokens(), is(true));
        assertThat(tokenizer.getNextToken(), is("one"));
        assertThat(tokenizer.hasMoreTokens(), is(true));
        assertThat(tokenizer.getNextToken(), is("two"));
        assertThat(tokenizer.hasMoreTokens(), is(true));
        assertThat(tokenizer.getNextToken(), is("three"));
        assertThat(tokenizer.hasMoreTokens(), is(false));
        assertThat(tokenizer.getNextToken(), is(""));
    }
}