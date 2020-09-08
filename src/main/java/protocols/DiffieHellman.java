package protocols;

import numberTheory.FiniteField;
import numberTheory.fasty.FastyFinite;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Properties;
import java.util.Random;

import static java.lang.Math.abs;
//import java.nio.file.Path;

public class DiffieHellman {
    long  p, g, xa, xb;
    public DiffieHellman(Path toProp, FiniteField fin){
        try{
            getProp(toProp);
            initX();
            computeOtherParam(fin);
        }
        catch (IOException e){
            e.printStackTrace();
        }



    }

    private void getProp(Path toProp) throws  IOException {
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
        xa = abs(random.nextInt()) % p;
        System.out.println("Generate secret for Alice: " + xa);
        xb = abs(random.nextInt()) % p;
        System.out.println("Generate secret for Bob: " + xb);
    }

    private void computeOtherParam(FiniteField fin){
        long ya = fin.exp(g, xa, p).orElseThrow(RuntimeException::new);
        System.out.println("compute non secret y for Alice: " + ya);

        long yb = fin.exp(g, xb, p).orElseThrow(RuntimeException::new);
        System.out.println("compute non secret y for Bob: " + yb);

        long zab = fin.exp(yb, xa, p).orElseThrow(RuntimeException::new);
        long zba = fin.exp(ya, xb, p).orElseThrow(RuntimeException::new);
        System.out.println("Compute za = zb general key: " + zab + " " + zba);

    }
}
