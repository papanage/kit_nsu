package numberTheory.fasty;

import numberTheory.FiniteField;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Optional;



public class FiniteFieldTest {

    @Test
    void exp() {
        FiniteField fin = new FastyFinite();
        Assert.assertEquals(Optional.of(4L), fin.exp(3, 100, 7));
        Assert.assertEquals(Optional.of(1L), fin.exp(2, 10, 1023));

    }


}