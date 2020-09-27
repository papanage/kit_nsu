package utility.numberTheory;

import java.util.Vector;

public class Algorithms {
    /**
     * implementation of extended Euclid algorithm.
     * @param a first number
     * @param b second number
     * @return vector(gcd, c1, c2)
     * a * c1 + b* c2 = gcd
     */
    public static Vector<Long> euclidExternal(long a, long b){
        Vector<Long> line1 = new Vector<>(3);
        Vector<Long> line2 = new Vector<>(3);

        if (a < b){
            long temp = a;
            a = b;
            b = temp;
        }
        line1.add(a);
        line1.add(1L);
        line1.add(0L);

        line2.add(b);
        line2.add(0L);
        line2.add(1L);

        while (line2.get(0) != 0L){
            long div = line1.get(0) / line2.get(0);
            for (int i  = 0; i < 3; i++){
                line1.set(i, line1.get(i) - div*line2.get(i));
            }

            Vector<Long> temp = line1;
            line1 = line2;
            line2 = temp;
        }
        return line1;
    }
}
