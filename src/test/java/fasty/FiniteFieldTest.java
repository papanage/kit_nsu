package fasty;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Optional;



public class FiniteFieldTest {

    @Test
    void exp() {
        Assert.assertEquals(Optional.of(4), FiniteField.exp(3, 100, 7));
        Assert.assertEquals(Optional.of(1), FiniteField.exp(2, 10, 1023));

    }


    @Test
    void nativeExp() {
        Assert.assertEquals(FiniteField.nativeExp(2, 2, 3), 1);
        Assert.assertEquals(FiniteField.nativeExp(-1, 3, 3), 2);
        Assert.assertEquals(FiniteField.nativeExp(0, 2, 3), 0);
    }
}