package lab1Diffi;

import utility.numberTheory.fasty.FastyFinite;
import lab1Diffi.protocols.DiffieHellman;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Task1_DH {
    public static void main(String[] args) {

        Path toProp = Paths.get("src/main/resources/conf1lab");
        DiffieHellman df = new DiffieHellman(toProp, new FastyFinite());
    }
}
