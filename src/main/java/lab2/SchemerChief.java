package lab2;

import utility.numberTheory.Algorithms;
import utility.numberTheory.FiniteField;
import utility.numberTheory.fasty.FastyFinite;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Properties;
import java.util.Random;
import java.util.Vector;

import static java.lang.Math.abs;

public class SchemerChief {
    Random random = new Random(Instant.now().hashCode());
    Path toProp = Paths.get("src/main/resources/conf2lab");
    long p, ca, cb, da, db;
    long x, y, z, u, w;

    public static void main(String[] args) throws Exception {
        FastyFinite fastyFinite = new FastyFinite();
        SchemerChief chief = new SchemerChief();
        chief.start();
        //System.out.println(fastyFinite.euclidExternal(100, 35));
    }

    private void start() throws Exception{
        FastyFinite fastyFinite = new FastyFinite();
        getProp(toProp);
        ca = initSecret();
        da = (Algorithms.euclidExternal(ca, p - 1).get(2) + (p - 1)) % (p - 1);
        cb = initSecret();
        db = (Algorithms.euclidExternal(cb, p - 1).get(2) + (p - 1)) % (p - 1);
        System.out.println("ca = " + ca);
        System.out.println("cb = " + cb);
        System.out.println("da = " + da);
        System.out.println("db = " + db);

        assert(ca*da % (p - 1) == 1);
        assert(cb*db % (p - 1) == 1);
        x = abs(random.nextLong()) % p;
        System.out.println("message = " + x);

        y = fastyFinite.exp(x, ca, p).get();
        System.out.println("y = " + y);

        z = fastyFinite.exp(y, cb, p).get();
        System.out.println("z = " + z);

        u = fastyFinite.exp(z, da, p).get();
        System.out.println("u = " + u);

        w = fastyFinite.exp(u, db, p).get();
        System.out.println("w = " + w);

        assert (w == x);


    }

    private void getProp(Path toProp) throws IOException {
        FileInputStream propFile = new FileInputStream(toProp.toFile());
        Properties properties = new Properties();
        properties.load(propFile);

        p = Long.parseLong(properties.getProperty("p"));

        System.out.println("Get parameter p: " + p);

    }

    private Long initSecret(){
        long temp = abs(random.nextLong()) % p;
        long gcd;
        do {
            temp = (temp + 1) % p;
            gcd = Algorithms.euclidExternal(p - 1, temp).get(0);
        }
        while (gcd != 1 && temp != 1);
        return temp;
    }
}
