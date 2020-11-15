package lab10;

import lombok.Getter;
import lombok.Setter;
import org.apache.maven.surefire.shared.lang3.ArrayUtils;
import sun.security.util.ArrayUtil;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Random;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.toRadians;

/**
 * implementation proof-of-work : hashcash
 */
@Getter
@Setter
public class Hashcash {
    Random random = new Random(Instant.now().hashCode());
    RandomString randomString = new RandomString(21);
    private byte[] s;
    private int k;
    /**
     * hash func
     */
    private MessageDigest digest = MessageDigest.getInstance("MD5");

    public Hashcash() throws NoSuchAlgorithmException {
    }

    public boolean start() {
        init();
        for(byte b: s) {
            System.out.print(b);
        }
        System.out.println();
        k = (abs(random.nextInt()) % 5) + 1;
        System.out.println("Server compute  k: " + k);

        return clientWork();

    }

    private void init() {
        int i = random.nextInt();
        s = ByteBuffer.allocate(4).putInt(i).array();
       // s = randomString.nextString().getBytes();
        System.out.println("Server compute s");
    }

    public boolean clientWork() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            byte[] bytes = ByteBuffer.allocate(4).putInt(i).array();
            byte[] potential = ArrayUtils.addAll(s, bytes);
            byte[] dig = digest.digest(potential);
            for (int j = 0; j < k ; j++) {
                if (dig[j] != 0) {
                    System.out.println("bad " + j + " " + dig.length);
                    break;
                }
                else if (j == k - 1) return true;
            }

        }
        return false;
    }


    public static void main(String[] args) throws NoSuchAlgorithmException {
        Hashcash hashcash = new Hashcash();
        System.out.println(hashcash.start());
    }
}
