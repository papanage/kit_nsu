package timecomp.comp;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NanoZip extends Compressor {

    public NanoZip() throws Exception {
        super("/compressors/nanozip/nz.exe");
        if (Optional.ofNullable(comp).isPresent()) isLoad = true;
    }
    @Override
    public Path fcompress(File toCompress) throws IllegalArgumentException {

        try {
            workWithExe(comp.toString()+ " a -cO " + toCompress.getAbsolutePath(), false);
            Stream<Path> pat = Files.walk(Paths.get("src/main/resources/temp/"));
            List<Path> paths = pat.collect(Collectors.toList());

            for (Path f : paths){
                if (Arrays.asList(f.getFileName().toString().split("\\.")).contains("nz")) return f;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }


}
