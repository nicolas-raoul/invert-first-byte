import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class InvertFirstByte {

    private static final String EXTENSION = ".invByte1";

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: InvertFirstByte <filename>");
            return;
        }

        File file = new File(args[0]);
        if (!file.exists()) {
            System.out.println("Error: File does not exist.");
            return;
        }

        invertFirstByte(file);
        renameFile(file);
    }

    private static void invertFirstByte(File file) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            byte firstByte = raf.readByte();
            raf.seek(0);
            raf.writeByte(~firstByte); // Invert the byte using bitwise NOT
        }
    }

    private static void renameFile(File file) {
        String filename = file.getName();
        if (filename.endsWith(EXTENSION)) {
            filename = filename.substring(0, filename.length() - EXTENSION.length());
        } else {
            filename += EXTENSION;
        }
        file.renameTo(new File(file.getParent(), filename));
    }
}
