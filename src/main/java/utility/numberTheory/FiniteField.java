package utility.numberTheory;

import java.util.Optional;
import java.util.Vector;

public interface FiniteField {

    /**
     @return non negative value of number in finite field
     @param p - character of field
     @param x - number
      */
    default long fit(long x, long p){
        return (x + p) % p;
    }
    Optional<Long> exp(long base, long square, long p);


}
