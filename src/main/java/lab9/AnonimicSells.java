package lab9;

import lab8.StepBabyStepHuge;
import utility.numberTheory.Algorithms;
import utility.numberTheory.FiniteField;
import utility.numberTheory.slowly.SlowFinite;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class AnonimicSells  {
    long p, q, n, c ,d , h, m, r, n_s, s, s_s, N;
    FiniteField fin;
    Random random = new Random(Instant.now().hashCode());

    public static void main(String[] args) throws Exception {
        Path toProp = Paths.get("src/main/resources/lab9.properties");
        AnonimicSells  anonimicSells= new AnonimicSells(toProp, new SlowFinite());
        anonimicSells.start();
    }

    public AnonimicSells(Path toProp, FiniteField fin){
        try {
            this.fin = fin;
            getProp(toProp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() throws Exception {
        System.out.println("n^c = s* ? = " + getLog());
    }

    private void getProp(Path toProp) throws IOException {
        FileInputStream propFile = new FileInputStream(toProp.toFile());
        Properties properties = new Properties();
        properties.load(propFile);

        p = Long.parseLong(properties.getProperty("p"));
        q = Long.parseLong(properties.getProperty("q"));
        N = p*q;
        System.out.println("Банк парметр N =  P*Q" + N);

        long phi = (q-1)*(p-1);
        c = Algorithms.getPer(phi);
        System.out.println("Банк парметр С - секретный ключ = " + c);

        d= (Algorithms.euclidExternal(c, phi).get(2) + phi) % (phi);
        System.out.println("Банк парметр D - открытый ключ  = " + d);



    }

    private boolean getLog() throws Exception {
        n = abs(random.nextInt()) % N;
        System.out.println("Покупатель: мое n = " +n);
        r =  Algorithms.getPer(N);
        System.out.println("Покупатель: r = " +r);
        n_s = (n * fin.exp(r, d, N).orElseThrow(Exception::new)) % N;

        s = fin.exp(n_s, c, N).orElseThrow(Exception::new);
        System.out.println("Банк: Высылаю <n_s, s> : <" + n_s + ", " + s + ">" );

        long r_per = (Algorithms.euclidExternal(r, N).get(2) + N) % (N);
        s_s = (s *  r_per) % (N);
        System.out.println("s* = " + s_s);
        System.out.println("n^c = " + fin.exp(n, c, N));
        return s_s == fin.exp(n, c, N).orElseThrow(Error::new);
    }

}
