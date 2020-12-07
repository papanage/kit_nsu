package lab11;

public class FonNeimon {

    private long startNumber = 1234;

    public long nextNumber() {
        long sq = startNumber*startNumber;
        String s = String.valueOf(sq);
        if (s.length() % 2 != 0) {
            s = "0" + s;
        }
        int shift = (s.length() - 4)/2;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = shift; i < shift + 4; i++) {
            stringBuilder.append(s.charAt(i));
        }
        String res = stringBuilder.toString();
        int r = Integer.parseInt(res);
        startNumber = r;
        return r;
    }

    public static void main(String[] args) {
        FonNeimon generator  = new FonNeimon();
        System.out.println(generator.nextNumber());
        System.out.println(generator.nextNumber());
        System.out.println(generator.nextNumber());
        System.out.println(generator.nextNumber());
        System.out.println(generator.nextNumber());
        System.out.println(generator.nextNumber());
        System.out.println(generator.nextNumber());
        System.out.println(generator.nextNumber());
    }
}
