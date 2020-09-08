import numberTheory.fasty.FastyFinite;
import numberTheory.slowly.SlowFinite;
import protocols.DiffieHellman;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Task1_DH {
    public static void main(String[] args) {

        Path toProp = Paths.get("src/main/resources/conf");
        System.out.println(toProp.getFileName());
        DiffieHellman df = new DiffieHellman(toProp, new FastyFinite());
    }
}
