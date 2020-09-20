package timecomp.comp;

import java.io.File;

public class CompressFile {
    public File file;
    public CompressorBase compressorBase;

    public CompressFile(File file, CompressorBase compressorBase) {
        this.file = file;
        this.compressorBase = compressorBase;
    }
}
