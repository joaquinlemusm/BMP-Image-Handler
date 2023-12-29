import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;

public class HeaderLector {

    private byte[] header;
    private int width;
    private int height;

    public HeaderLector(File bmp) {
        try (FileInputStream rawData = new FileInputStream(bmp)) {
            // lenght 54 = Header (14 bytes) + InfoHeader(40 bytes)
            this.header = rawData.readNBytes(54); // Header and InfoHeader bytes
            width = ((header[21] & 0xFF) << 24) | ((header[20] & 0xFF) << 16) | ((header[19] & 0xFF) << 8) | (header[18] & 0xFF);
            height = ((header[25] & 0xFF) << 24) | ((header[24] & 0xFF) << 16) | ((header[23] & 0xFF) << 8) | (header[22] & 0xFF);    
        } catch (IOException fnfe) {
            fnfe.printStackTrace();
        }
    }

    public byte[] getHeader() {
        return this.header;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}