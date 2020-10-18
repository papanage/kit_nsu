package lab5;

import lab3El.ElGam;
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

public class Rsa  {
    long pa, qa, pb, qb,ca, cb, da, db, m;
    FiniteField fin;
    Random random = new Random(Instant.now().hashCode());

    public static void main(String[] args) throws Exception {
        Path toProp = Paths.get("src/main/resources/conf5lab.properties");
        Rsa rsa = new Rsa(toProp, new SlowFinite());
        rsa.start();
    }

    public Rsa(Path toProp, FiniteField fin){
        try {
            this.fin = fin;
            getProp(toProp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        initX();
    }

    public void start() {
        computeOtherParam(fin);
    }

    private void getProp(Path toProp) throws IOException {
        FileInputStream propFile = new FileInputStream(toProp.toFile());
        Properties properties = new Properties();
        properties.load(propFile);

        pa = Long.parseLong(properties.getProperty("pa"));
        qa = Long.parseLong(properties.getProperty("qa"));

        pb = Long.parseLong(properties.getProperty("pb"));
        qb = Long.parseLong(properties.getProperty("qb"));


        System.out.println("Get parameter na: " + pa*qb);
        System.out.println("Get parameter nb: " + pb*qb);


    }

    private void initX() {

        ca = Algorithms.getPer((qa-1)*(pa-1));
        System.out.println("Generate secret for Alice ca: " + ca);
        cb = Algorithms.getPer((qb-1)*(pb-1));
        System.out.println("Generate secret for Bob cb: " + cb);

        m = abs(random.nextInt()) % qa*pa;
         System.out.println("Generate message: " + m);
    }

    private void computeOtherParam(FiniteField fin){
        long phia = (qa-1)*(pa-1);
        da = (Algorithms.euclidExternal(ca, phia).get(2) + phia) % (phia);
      //  System.out.println("opa " + da*ca % phia);
       // System.out.println(Algorithms.euclidExternal(ca, phia - 1).get(2));
        System.out.println("compute non secret y for Alice da: " + da);

        long phib = (qb-1)*(pb-1);
        db = (Algorithms.euclidExternal(cb, phib).get(2) + phib) % (phib);
       //System.out.println("opa " + db*cb % phib);
        System.out.println("compute non secret y for Bob db: " + db);

        long e = fin.exp(m, db, qb*pb).orElseThrow(RuntimeException::new);
        System.out.println("compute e: " + e);
        long m2 = fin.exp(e, cb, qb*pb).orElseThrow(RuntimeException::new);

        System.out.println("compute m: " + m2);


    }

}
