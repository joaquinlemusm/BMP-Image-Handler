import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

public class HeaderLector {

    private byte[] header;
    private int fileSize;
    private int width;
    private int height;

    public HeaderLector(File bmp) {
        try (FileInputStream rawData = new FileInputStream(bmp)) {
            // lenght 54 = Header (14 bytes) + InfoHeader(40 bytes)
            this.header = rawData.readNBytes(54); // Header and InfoHeader bytes
            fileSize = ((header[5] & 0xFF) << 24) | ((header[4] & 0xFF) << 16) | ((header[3] & 0xFF) << 8) | (header[2] & 0xFF);
            width = ((header[21] & 0xFF) << 24) | ((header[20] & 0xFF) << 16) | ((header[19] & 0xFF) << 8) | (header[18] & 0xFF);
            height = ((header[25] & 0xFF) << 24) | ((header[24] & 0xFF) << 16) | ((header[23] & 0xFF) << 8) | (header[22] & 0xFF);    
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public byte[] getHeader() {
        return this.header;
    }
    
    public int getSize() {
        return this.fileSize;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}