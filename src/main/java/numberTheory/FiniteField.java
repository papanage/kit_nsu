package numberTheory;

import java.util.Optional;

public interface FiniteField {

    /*
 @return non negative value of number in finite field
 p - character of field
 x - number
  */
    default long fit(long x, long p){
        return (x + p) % p;
    }
    Optional<Long> exp(long base, long square, long p);
}
