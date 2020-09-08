package numberTheory.fasty;

import numberTheory.FiniteField;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FastyFinite implements FiniteField {


    /*
    * some more effective method of extension nativeExp()
    * @param base - base of exp
    * @param square - square of base
    * @param p - characteristic of finite field
     */
    public Optional<Long> exp(long base, long square, long p){
        String bitsForm = Stream
                .of(square)
                .map(Long::toBinaryString)
                .collect(Collectors.joining());
        long a = base;
        long res = 1;
        for (int i = bitsForm.length() - 1; i >= 0; i--){
            res *= nativeExp(a, Integer.parseInt(String.valueOf(bitsForm.charAt(i))), p) ;
            a = nativeExp(a, 2, p);
            res = fit(res, p);

        }
        return Optional.of(fit(res, p));
    }

    /*
     extension of pow(base, square) on finite fields with characteristic p
     */

    private long nativeExp(long base, long square, long p){
        long res = 1;
        while (square-- != 0) {
            res = res*base % p;
        }
        return fit(res, p);
    }


}
