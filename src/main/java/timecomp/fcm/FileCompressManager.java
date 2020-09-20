package timecomp.fcm;

import timecomp.comp.CompressFile;
import timecomp.comp.Compressor;
import timecomp.comp.CompressorBase;

import java.io.*;
import java.util.*;

public class FileCompressManager {
    File file;


    public FileCompressManager(File file){
        this.file = file;
    }

    public CompressorBase start(Vector<Compressor> compressor, Vector<Double> per, Vector<Integer> places) throws Exception {

        assert (per.size() == places.size());
        assert  (places.lastElement() == 1);
        assert (per.lastElement() == 1.0);

        return startRounds(compressor, per, places);

    }

    private CompressorBase startRounds(Vector<Compressor> compressor, Vector<Double> per, Vector<Integer> places) throws Exception {
        List<CompressorBase> c = new ArrayList<>(compressor);

        int pl_index = 0;
        double delta = 0;

        for (Double p : per){

            if (p.equals(1.0)) {
                System.out.println("delta = " + delta);
                return c.get(0);
            }

            delta += p * places.get(pl_index);

            Vector<CompressFile> compressFiles = compressOneRound(compressor, p);

            deleteTemp(compressFiles.get(0).file.getParent());
            c.clear();

            for (CompressFile compressFile : compressFiles.subList(0, places.get(pl_index++))){
                System.out.println("hello = " + compressFile.compressorBase);
                c.add(compressFile.compressorBase);
            }

        }

        throw new Exception();
    }

    private Vector<CompressFile> compressOneRound(Vector<Compressor> compressor, Double p){

        File f = getFirstPart(p);
        Vector<CompressFile> compressFiles = new Vector<>();
        for (Compressor co : compressor){
            File file = new File(String.valueOf(co.compress(f)));
            compressFiles.add(new CompressFile(file, co));
        }

        compressFiles.sort((x,y)-> compare(x.file, y.file));

        for (CompressFile cf : compressFiles){
            System.out.println(cf.file.getName() + " " + cf.compressorBase + " " + cf.file.length());
        }
        return compressFiles;
    }
    private void deleteTemp(String dir){
        File par = new File(dir);
        for (File fd : Objects.requireNonNull(par.listFiles())){
            fd.delete();
        }
    }
    public File getFirstPart(double percent){
        if (percent == 1.0) return file;
        File part = new File("src/main/resources/temp/first" + percent + ".txt");
        try {
            if (!part.createNewFile()){
                throw new IllegalArgumentException();
            }

            FileWriter myWriter = new FileWriter(part);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();

            while (line != null) {
                myWriter.write(line +"\n");

                if ((double)part.length() / file.length() > percent){
                    myWriter.close();
                    break;
                }

                //System.out.println(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return part;
    }

    private  static byte compare(File f1, File f2){
        if (f1.length() == f2.length()) return 0;
        if (f1.length() < f2.length()) return -1;
        return 1;
    }
}
