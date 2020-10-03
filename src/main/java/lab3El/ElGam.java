package lab3El;

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

public class ElGam {
    long p, g, ca, cb, da, db, k , m;
    public static void main(String[] args) throws Exception {
        Path toProp = Paths.get("src/main/resources/conf3lab");
        ElGam elGam = new ElGam(toProp, new FastyFinite());
    }

    public ElGam(Path toProp, FiniteField fin){
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
        g = Long.parseLong(properties.getProperty("g"));

        System.out.println("Get parameter p: " + p);
        System.out.println("Get parameter g: " + g);

    }

    private void initX(){
        Random random = new Random(Instant.now().hashCode());
        ca = (abs(random.nextInt()) % (p - 1)) + 1;
        System.out.println("Generate secret for Alice ca: " + ca);
        cb = (abs(random.nextInt()) % (p - 1)) + 1;
        System.out.println("Generate secret for Bob cb: " + cb);

        k = (abs(random.nextInt()) % (p - 2)) + 2;
        System.out.println("Generate k: " + k);

        m = abs(random.nextInt()) % p;
        System.out.println("Generate message: " + m);
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
