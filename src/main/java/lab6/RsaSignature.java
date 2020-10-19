package lab6;

import lab5.Rsa;
import utility.numberTheory.Algorithms;
import utility.numberTheory.FiniteField;
import utility.numberTheory.fasty.FastyFinite;
import utility.numberTheory.slowly.SlowFinite;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Properties;
import java.util.Random;

import static java.lang.Math.abs;

public class RsaSignature {
    long p, q, c, d, m, n, s, ms;
    FiniteField fin;
    Random random = new Random(Instant.now().hashCode());

    public static void main(String[] args) {
        Path toProp = Paths.get("src/main/resources/conf6lab.properties");
        RsaSignature rsa = new RsaSignature(toProp, new FastyFinite());
        System.out.println("check res: " + check(500, 46514,52891, 3, new FastyFinite()));

    }

    public RsaSignature(Path toProp, FiniteField fin){
        try {
            this.fin = fin;
            getProp(toProp);
            initX();
            computeOtherParam(fin);
            System.out.println("generation res: " + check(m, s, n, d, fin));
        } catch (IOException e) {
            e.printStackTrace();
        }
        initX();
    }

    private void getProp(Path toProp) throws IOException {
        FileInputStream propFile = new FileInputStream(toProp.toFile());
        Properties properties = new Properties();
        properties.load(propFile);

        p = Long.parseLong(properties.getProperty("p"));
        q = Long.parseLong(properties.getProperty("q"));
        n = p*q;
        System.out.println("Get parameter na: " + p*q);
    }

    private void initX() {

        c = Algorithms.getPer((q-1)*(p-1));
        System.out.println("Generate secret for Alice c: " + c);

        long phia = (q-1)*(p-1);
        d = (Algorithms.euclidExternal(c, phia).get(2) + phia) % (phia);
        System.out.println("compute non secret y for Alice d: " + d);


        m = abs(random.nextInt()) % n;
        System.out.println("Generate message: " + m);
    }

    private void computeOtherParam(FiniteField fin){
       ms = Algorithms.hash(m);
       System.out.println("compute ms: " + ms);
       s = fin.exp(ms, c, n).orElseThrow(RuntimeException::new);
       System.out.println("compute s: " + s);
    }

    public static boolean check(long m, long s, long n, long d, FiniteField fin) {
        long e = fin.exp(s, d, n).orElseThrow(RuntimeException::new);
        return  e == Algorithms.hash(m);

    }


}
