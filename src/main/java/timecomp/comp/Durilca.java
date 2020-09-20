package timecomp.comp;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Durilca extends Compressor {

    public Durilca() throws Exception{
        super("/compressors/durilca/DURILCA.exe");
        if (Optional.ofNullable(comp).isPresent()) isLoad = true;
    }


    @Override
    protected Path fcompress(File toCompress) throws IllegalArgumentException {

        try {
            workWithEternal(toCompress);
            //System.out.println("end");
            Stream<Path> pat = Files.walk(Paths.get("src/main/resources/temp/"));
            List<Path> paths = pat.collect(Collectors.toList());

            for (Path f : paths){
                // System.out.println(f);
                if (Arrays.asList(f.getFileName().toString().split("\\.")).contains("dur")) return f;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    private void workWithEternal(File toCompress) throws IOException, InterruptedException {

      //  toCompress =  workWithFile(toCompress);
        Process p = Runtime.getRuntime().exec(comp.toString()+ " e " + toCompress.getAbsolutePath());
        System.out.println(comp.toString()+ " e " + toCompress.getAbsolutePath());


        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(p.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(p.getErrorStream()));


        StringBuilder response = new StringBuilder();
        StringBuilder errorStr = new StringBuilder();

        boolean alreadyWaited = false;

        while (p.isAlive()) {
            if(alreadyWaited) {

                String temp;

                while ((temp = stdInput.readLine()) != null) {

                    response.append(temp);
                }


                String errTemp;
                while ((errTemp = stdError.readLine()) != null) {

                    errorStr.append(errTemp);
                }
            }

            System.out.println(errorStr);
            System.out.println(response);
            Thread.sleep(1000);
            alreadyWaited = true;
        }
        stdError.close();
        stdInput.close();
    }

    private File workWithFile(File toCompress) throws IOException {
        File file = new File(Paths.get(comp.getParent()) + "\\" + toCompress.getName() );
        //file.createNewFile();
        //System.out.println(file);
        Files.copy(Paths.get(toCompress.getPath()), Paths.get(comp.getParent()+ "\\" + toCompress.getName()) );
        return file;

    }
}
