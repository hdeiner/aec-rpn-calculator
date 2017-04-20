package test.com.siq.rpnCalculator;

import com.siq.rpnCalculator.RpnCalculator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class RpnCalculatorTest {

    @Test
    public void canUseTokensToGuideCalculations() throws Exception {
        // ((1+2)*(7-3))/6
        RpnCalculator rpnCalculator = new RpnCalculator("1 2 + 7 3 - * 6 /");
        assertThat(Double.parseDouble(rpnCalculator.calculate().toString()),is(2.0));
    }

    @Test
    public void checkThatDinnerBillWorks() throws Exception {
        RpnCalculator rpnCalculator = new RpnCalculator("21.56 21.56 18 % * +");
        assertThat(Double.parseDouble(rpnCalculator.calculate().toString()),is(25.4408));
    }

    @Rule
    public final ExpectedException rpnException = ExpectedException.none();

    @Test
    public void ensureMonadicOperatorIsProvidedWithAnOperand() throws Exception {
        rpnException.expect(Exception.class);
        rpnException.expectMessage( is("Monadic operator of \"" + "%" + "\" encountered, but there is no operand on the stack"));
        RpnCalculator rpnCalculator = new RpnCalculator("%");
        rpnCalculator.calculate();
    }


    @Test
    public void ensureDyadicOperatorIsProvidedWithAnOperand() throws Exception {
        rpnException.expect(Exception.class);
        rpnException.expectMessage( is("Dyadic operator of \"" + "+" + "\" encountered, but there is no operand on the stack"));
        RpnCalculator rpnCalculator = new RpnCalculator("+");
        rpnCalculator.calculate();
    }

    @Test
    public void ensureDyadicOperatorIsProvidedWithTwoOperands() throws Exception {
        rpnException.expect(Exception.class);
        rpnException.expectMessage( is("Dyadic operator of \"" + "+" + "\" encountered, but there is only one operand on the stack"));
        RpnCalculator rpnCalculator = new RpnCalculator("1 +");
        rpnCalculator.calculate();
    }

    @Test
    public void ensureAllTokensUsed() throws Exception {
        rpnException.expect(Exception.class);
        rpnException.expectMessage( is("unused tokens on calculator stack"));
        RpnCalculator rpnCalculator = new RpnCalculator("1 2 + 7 3 -");
        rpnCalculator.calculate();
    }

    @Test
    public void checkSwapOperator() throws Exception {
        RpnCalculator rpnCalculator = new RpnCalculator("7 3 SWAP -");
        assertThat(Double.parseDouble(rpnCalculator.calculate().toString()),is(-4.0));
    }

    @Test
    public void checkDropOperator() throws Exception {
        RpnCalculator rpnCalculator = new RpnCalculator("1 2 3 DROP 4 + +");
        assertThat(Double.parseDouble(rpnCalculator.calculate().toString()),is(7.0));
    }

    @Test
    public void checkClearOperator() throws Exception {
        RpnCalculator rpnCalculator = new RpnCalculator("1 2 3 CLEAR 4 4 +");
        assertThat(Double.parseDouble(rpnCalculator.calculate().toString()),is(8.0));
    }

    @Test
    public void checkRollOperator() throws Exception {
//      1 2 4 8 ROLL / * + is 2 4 8 1 / * + which is 8/1*4+2
        RpnCalculator rpnCalculator = new RpnCalculator("1 2 4 8 ROLL / * +");
        assertThat(Double.parseDouble(rpnCalculator.calculate().toString()),is(34.0));
    }


    @Test
    public void checkSwapOnStackWithZeroEntries() throws Exception {
        rpnException.expect(Exception.class);
        rpnException.expectMessage( is("can't SWAP with less than two items on the calculator stack"));
        RpnCalculator rpnCalculator = new RpnCalculator("SWAP");
        rpnCalculator.calculate();
    }

    @Test
    public void checkSwapOnStackWithOneEntry() throws Exception {
        rpnException.expect(Exception.class);
        rpnException.expectMessage( is("can't SWAP with less than two items on the calculator stack"));
        RpnCalculator rpnCalculator = new RpnCalculator("1 SWAP");
        rpnCalculator.calculate();
    }

    @Test
    public void checkDropOnStackWithZeroEntries() throws Exception {
        rpnException.expect(Exception.class);
        rpnException.expectMessage( is("can't DROP with an empty calculator stack"));
        RpnCalculator rpnCalculator = new RpnCalculator("DROP");
        rpnCalculator.calculate();
    }

    @Test
    public void checkRollOnStackWithZeroEntries() throws Exception {
        rpnException.expect(Exception.class);
        rpnException.expectMessage( is("can't ROLL with an empty calculator stack"));
        RpnCalculator rpnCalculator = new RpnCalculator("ROLL");
        rpnCalculator.calculate();
    }

}