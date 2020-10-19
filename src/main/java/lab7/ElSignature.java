package lab7;

import lab6.RsaSignature;
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

import static java.lang.Math.abs;

public class ElSignature {
    long p, g, x, y, k, m, ms, u, s, r;
    FiniteField fin;
    Random random = new Random(Instant.now().hashCode());

    public static void main(String[] args) {
        Path toProp = Paths.get("src/main/resources/lab7.properties");
        ElSignature rsa = new ElSignature(toProp, new FastyFinite());
       System.out.println("check res: " + check(500, 27665,26022, 2, 16196, 31259, new FastyFinite()));

    }

    public ElSignature(Path toProp, FiniteField fin){
        try {
            this.fin = fin;
            getProp(toProp);
            initX();
            computeOtherParam(fin);
            System.out.println("generation res: " + check(m, r, s, g, y, p , fin));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //initX();
    }

    private void getProp(Path toProp) throws IOException {
        FileInputStream propFile = new FileInputStream(toProp.toFile());
        Properties properties = new Properties();
        properties.load(propFile);

        p = Long.parseLong(properties.getProperty("p"));
        g = Long.parseLong(properties.getProperty("g"));
        System.out.println("Get parameter p: " + p);
        System.out.println("Get parameter g: " + g);
    }

    private void initX() {
        x = abs(random.nextInt() % (p-2)) + 1;
        y = fin.exp(g, x, p).orElseThrow(RuntimeException::new);

        System.out.println("Generate secret for Alice x: " + x);
        System.out.println("compute non secret y for Alice y: " + y);


        m = abs(random.nextInt()) % p;
        System.out.println("Generate message: " + m);
    }

    private void computeOtherParam(FiniteField fin){
        ms = Algorithms.hash(m);
        System.out.println("compute ms: " + ms);
        k = Algorithms.getPer(p - 1);
        System.out.println("compute k: " + k);
        r = fin.exp(g, k, p).orElseThrow(RuntimeException::new);

        System.out.println("compute r: " + r);
        u = (ms - x*r % (p-1) + p - 1) % (p-1);
        System.out.println("compute u: " + u);
        long ks = Algorithms.euclidExternal(k, p - 1).get(2) + p - 1 % (p - 1);
        s = (ks * u) % (p - 1);

        System.out.println("compute s: " + s);



    }

    public static boolean check(long m, long r, long s,  long g, long y, long p,  FiniteField fin) {
        long h = Algorithms.hash(m);
        long e1 = fin.exp(y, r, p).orElseThrow(RuntimeException::new);
        long e2 = fin.exp(r, s, p).orElseThrow(RuntimeException::new);
        long e3 = fin.exp(g, h, p).orElseThrow(RuntimeException::new);
        return  e1*e2 % p == e3;

    }


}
