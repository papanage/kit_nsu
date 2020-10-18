package lab4;

import lab3El.ElGam;
import utility.numberTheory.Algorithms;
import utility.numberTheory.FiniteField;
import utility.numberTheory.fasty.FastyFinite;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Properties;
import java.util.Random;

import static java.lang.Math.abs;

public class MentalPoker {
    long p, g, ca, cb, da, db, k , m;
    Random random = new Random(Instant.now().hashCode());
    public static void main(String[] args) throws Exception {
        Path toProp = Paths.get("src/main/resources/conf4lab");
        MentalPoker mentalPoker = new MentalPoker(toProp, new FastyFinite());
    }

    enum Cards{
        ALPHA,
        BETTA,
        GAMMA
    }
    public MentalPoker(Path toProp, FiniteField fin){
        try{
            getProp(toProp);
            initX();
            computeOtherParam(fin);
        }
        catch (IOException e){
            e.printStackTrace();
        }



    }
    private void getProp(Path toProp) throws IOException {
        FileInputStream propFile = new FileInputStream(toProp.toFile());
        Properties properties = new Properties();
        properties.load(propFile);

        p = Long.parseLong(properties.getProperty("p"));

        System.out.println("Get parameter p: " + p);

    }

    private void initX(){
        ca = 3;
        da = (Algorithms.euclidExternal(ca, p - 1).get(2) + (p - 1)) % (p - 1);
        cb = 3;
        db = (Algorithms.euclidExternal(cb, p - 1).get(2) + (p - 1)) % (p - 1);
        System.out.println("compute  secret y for Alice ca: " + ca);
        System.out.println("compute non secret y for Alice cb: " + cb);
        System.out.println("compute non secret y for Alice da: " + da);
        System.out.println("compute non secret y for Alice db: " + db);

        assert(ca*da % (p - 1) == 1);
        assert(cb*db % (p - 1) == 1);
        long r1 = (abs(random.nextInt()) % (p - 2)) + 2;
        long r2 = (abs(random.nextInt()) % (p - 2)) + 2;
        long r3 = (abs(random.nextInt()) % (p - 2)) + 2;

        if (r1 % 4 != 0){
            r1 /= 4;
        }
        

    }

    private void computeOtherParam(FiniteField fin){
        da = fin.exp(g, ca, p).orElseThrow(RuntimeException::new);
        System.out.println("compute non secret y for Alice da: " + da);

        db = fin.exp(g, cb, p).orElseThrow(RuntimeException::new);
        System.out.println("compute non secret y for Bob db: " + db);

        long r = fin.exp(g, k, p).orElseThrow(RuntimeException::new);

        System.out.println("compute r: " + r);
        long e = (m*fin.exp(db, k, p).orElseThrow(RuntimeException::new)) % p;

        System.out.println("compute e: " + e);

        long m2 = (e*fin.exp(r, p - 1 - cb, p).orElseThrow(RuntimeException::new)) % p;
        System.out.println("Check m2 = m: " + m + "=" + m2);

    }


}