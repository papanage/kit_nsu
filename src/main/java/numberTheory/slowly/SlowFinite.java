package numberTheory.slowly;

import numberTheory.FiniteField;

import java.util.Optional;

public class SlowFinite implements FiniteField {

    @Override
    public Optional<Long> exp(long base, long square, long p){
            long res = 1;
            while (square-- != 0) {
                res = res*base % p;
            }
            return Optional.of(fit(res, p));
    }
}
