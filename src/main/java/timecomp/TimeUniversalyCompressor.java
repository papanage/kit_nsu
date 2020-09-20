package timecomp;

import timecomp.comp.*;
import timecomp.fcm.FileCompressManager;

import java.io.File;
import java.util.Vector;

public class TimeUniversalyCompressor {
    public static void main(String[] args) throws Exception{
        FileCompressManager fileCompressManager = new FileCompressManager(new File("C:\\Users\\Vasya\\Documents\\zip\\cano.txt"));
        Vector<Compressor> compressor = new Vector<>();
        compressor.add(new Paq8f32());
      //  compressor.add(new Durilca());
        compressor.add(new Zip7());
        compressor.add(new NanoZip());
        Vector<Double> per = new Vector<>();
        per.add(0.01);
        per.add(0.02);
        per.add(1.0);
        Vector<Integer> places = new Vector<>();
        places.add(3);
        places.add(1);
        places.add(1);

        fileCompressManager.start(compressor, per, places);
        //Integer x = 9;


    }
}
