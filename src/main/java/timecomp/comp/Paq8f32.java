package timecomp.comp;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Paq8f32 extends Compressor {

    public Paq8f32() throws Exception {
        super("/compressors/paq8f/paq8f.exe");
        if (Optional.ofNullable(comp).isPresent()) isLoad = true;
    }
    @Override
    public Path fcompress(File toCompress) throws IllegalArgumentException {

        try {
            workWithExe(comp.toString() + " " + toCompress.getAbsolutePath(),true );
            Stream<Path> pat = Files.walk(Paths.get("src/main/resources/temp/"));
            List<Path> paths = pat.collect(Collectors.toList());

            for (Path f : paths){
                if (Arrays.asList(f.getFileName().toString().split("\\.")).contains("paq8f")) return f;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }


}
