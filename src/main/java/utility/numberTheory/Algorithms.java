package utility.numberTheory;

import java.time.Instant;
import java.util.Random;
import java.util.Vector;

import static java.lang.Math.abs;

public class Algorithms {
    static Random random = new Random(Instant.now().hashCode());


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
            //System.out.println(line2.get(0) + " " + line2.get(1) + " " + line2.get(2));
        }
        return line1;
    }

    public static long getPer(long p) {
        long temp = abs(random.nextLong()) % p;
        long gcd;
        do {
            temp = (temp + 1) % p;
            gcd = Algorithms.euclidExternal(p, temp).get(0);
        }
        while (gcd != 1 && temp != 1);
        return temp;
    }

    public static long hash(long s) {
        return s;
    }
}
