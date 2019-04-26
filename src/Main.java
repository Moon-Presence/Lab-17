import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Main {
    // Lab-17 --srcfile=/path/to/srcfile --dstfile=/path/to/dstfile --bufsize=<size>

    public static void main(String[] args) throws Exception {

        String srcFile = "";
        String dstFile = "";
        int bufSize = 0;

        for (int i = 0; i < args.length; ++i) {
            String key = args[i].split("=")[0];
            String value = args[i].split("=")[1];

            if (key.compareTo("--srcfile") == 0) {
                System.out.println("Source: "+(srcFile = value));
            } else if (key.compareTo("--dstfile") == 0) {
                System.out.println("Destination: "+(dstFile = value));
            } else if (key.compareTo("--bufsize") == 0) {
                System.out.println("Buffer size: "+value+" Kb");
                bufSize = Integer.parseInt(value) * 1024;
            }
        }

        FileInputStream fis = new FileInputStream(srcFile);
        FileOutputStream fos = new FileOutputStream(dstFile);

        FileChannel fci = fis.getChannel();
        FileChannel fco = fos.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(bufSize);

        long srcFileSize = fci.size();
        int count = 0;
        int progress = 0;
        while ((count = fci.read(buffer)) != -1) {
            progress += count;
            System.out.println("progress: " + (progress / (float)srcFileSize) * 100);
            buffer.flip();
            fco.write(buffer);
            buffer.clear();
        }

        fco.close();
    }
}