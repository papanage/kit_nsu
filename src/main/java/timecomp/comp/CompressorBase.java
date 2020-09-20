package timecomp.comp;

import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.FutureTask;

public interface CompressorBase {
    Path compress(File toCompress) throws IllegalArgumentException;
}
