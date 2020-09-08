package fasty;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FiniteField {

    public static int fit(int x, int p){
        return (x + p) % p;
    }

    public static Optional<Integer> exp(int base, int square, int p){
        String bitsForm = Stream
                .of(square)
                .map(Integer::toBinaryString)
                .collect(Collectors.joining());
        int a = base;
        int res = 1;
        for (int i = bitsForm.length() - 1; i >= 0; i--){
            res *= nativeExp(a, Integer.parseInt(String.valueOf(bitsForm.charAt(i))), p);
            a = nativeExp(a, 2, p);
        }
        System.out.println(bitsForm);
        return Optional.of(fit(res, p));
    }

    public static int nativeExp(int base, int square, int p){
        int res = 1;
        while (square-- != 0) {
            res = res*base % p;
        }
        return fit(res, p);
    }
}
