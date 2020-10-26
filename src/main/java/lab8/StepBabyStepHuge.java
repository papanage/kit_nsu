package lab8;

import lab5.Rsa;
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
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import static java.lang.Math.abs;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;

public class StepBabyStepHuge  {
    long p, a, x, h, m;
    FiniteField fin;
    Random random = new Random(Instant.now().hashCode());

    public static void main(String[] args) throws Exception {
        Path toProp = Paths.get("src/main/resources/lab8.properties");
        StepBabyStepHuge stepBabyStepHuge = new StepBabyStepHuge(toProp, new SlowFinite());
        stepBabyStepHuge.start();
    }

    public StepBabyStepHuge(Path toProp, FiniteField fin){
        try {
            this.fin = fin;
            getProp(toProp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() throws Exception {
        System.out.println("find = " + getLog());
    }

    private void getProp(Path toProp) throws IOException {
        FileInputStream propFile = new FileInputStream(toProp.toFile());
        Properties properties = new Properties();
        properties.load(propFile);

        p = Long.parseLong(properties.getProperty("p"));
        a = Long.parseLong(properties.getProperty("a"));

        h = Long.parseLong(properties.getProperty("h"));


        System.out.println("Need find x that  "  + a + "^x= " + h);


    }

    private Long getLog() throws Exception {

        m = (long) sqrt(p) + 1;
        System.out.println("M choose = " + m);
        HashMap<Long, Long> i_vector = new HashMap<>();
        for (long i = 0; i <= m; i++) {
            long temp = fin.exp(a, i * m, p).orElseThrow(Exception::new);
            System.out.println("table " + i + " " + temp);
            if (i_vector.containsKey(temp)) {
                continue;
            }
            i_vector.put(fin.exp(a, i * m, p).orElseThrow(Exception::new), i);
        }
        for (Map.Entry<Long, Long> entry : i_vector.entrySet()) {
            System.out.println(entry.getKey() + " " +entry.getValue());
        }
        for (long i = 0; i < m; i++) {
            System.out.println("j = " + h * fin.exp(a, i, p).orElseThrow(Exception::new) % p);
            Long k = i_vector.get(h * fin.exp(a, i, p).orElseThrow(Exception::new) % p);
            if (k != null) {
                return m * k - i;
            }

        }
        return null;
    }

}
