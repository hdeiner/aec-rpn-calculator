package test.com.siq.rpnCalculator;

import com.siq.rpnCalculator.TokenStream;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class TokenStreamTest {

    @Test
    public void nonEmptyTokenizerShouldHaveTokens() {
        TokenStream tokenStream = new TokenStream("   Hello World!  ");
        assertThat(tokenStream.hasMoreTokens(), is(true));
    }

    @Test
    public void whitespaceDelimitedTokensReturnInSequence() {
        TokenStream tokenStream = new TokenStream("  one two   three  ");
        assertThat(tokenStream.hasMoreTokens(), is(true));
        assertThat(tokenStream.getNextToken(), is("one"));
        assertThat(tokenStream.hasMoreTokens(), is(true));
        assertThat(tokenStream.getNextToken(), is("two"));
        assertThat(tokenStream.hasMoreTokens(), is(true));
        assertThat(tokenStream.getNextToken(), is("three"));
        assertThat(tokenStream.hasMoreTokens(), is(false));
        assertThat(tokenStream.getNextToken(), is(""));
    }

    @Test
    public void whitespaceDelimitedTokensReturnInSequenceMockedStateVerification() {
        // Arrange
        TokenStream tokenStream = mock(TokenStream.class);
        // Act
        when(tokenStream.hasMoreTokens()).thenReturn(true,true,true,false);
        when(tokenStream.getNextToken()).thenReturn("one", "two", "three", "");
        // Assert
        assertThat(tokenStream.hasMoreTokens(), is(true));
        assertThat(tokenStream.getNextToken(), is("one"));
        assertThat(tokenStream.hasMoreTokens(), is(true));
        assertThat(tokenStream.getNextToken(), is("two"));
        assertThat(tokenStream.hasMoreTokens(), is(true));
        assertThat(tokenStream.getNextToken(), is("three"));
        assertThat(tokenStream.hasMoreTokens(), is(false));
        assertThat(tokenStream.getNextToken(), is(""));
        // Verify that getNextToken did not make any calls to hasMoreTokens
        verify(tokenStream, times(4)).hasMoreTokens();
    }
}