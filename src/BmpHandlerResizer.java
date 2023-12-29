import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class BmpHandlerResizer {

    private final BmpHandlerCore core;
    private final HeaderLector headerInfo;
    private final String fileName;
    private final int height;
    private final int width;

    public BmpHandlerResizer(String file) {
        this.core = new BmpHandlerCore(file);
        this.headerInfo = new HeaderLector(new File(file));
        this.height = this.headerInfo.getHeight();
        this.width = this.headerInfo.getWidth();
        this.fileName = this.core.getFileName(file);
    }

    public void resize() {
        flatImage();
        thinImage();
    }

    private void flatImage() {
        try {
            FileOutputStream flatImage = new FileOutputStream(this.core.getFileName(this.fileName)+"-flat.bmp");
            flatImage.write(flatBMP());
            flatImage.write(this.core.getPixels());
            flatImage.close();
        } catch (IOException ioe) { 
            ioe.printStackTrace();
        }
    }
    
    public void thinImage() {
        try {
            FileOutputStream thinImage = new FileOutputStream(this.core.getFileName(this.fileName)+"-thin.bmp");
            thinImage.write(thinBMP());
            thinImage.write(this.core.getPixels());
            thinImage.close();
        } catch (IOException ioe) { 
            ioe.printStackTrace();
        }
    }

    private byte[] thinBMP() {
        byte[] newHeader = Arrays.copyOf(this.headerInfo.getHeader(), 54);
        // if we thin then our width reduces
        int newWidth = this.width / 2;
        // width = ((header[21] & 0xFF) << 24) | ((header[20] & 0xFF) << 16) | ((header[19] & 0xFF) << 8) | (header[18] & 0xFF);

        // I have to update the values where I found the header in order to resize the image
        newHeader[21] = (byte) ((newWidth >> 24) & 0xFF);
        newHeader[20] = (byte) ((newWidth >> 16) & 0xFF);
        newHeader[19] = (byte) ((newWidth >> 8) & 0xFF);
        newHeader[18] = (byte) (newWidth & 0xFF);

        int newFileSize = calculateNewFileSize("t");
        return getBytes(newHeader, newFileSize);
    }

    private byte[] getBytes(byte[] newHeader, int newFileSize) {
        newHeader[2] = (byte) (newFileSize & 0xFF);
        newHeader[3] = (byte) ((newFileSize >> 8) & 0xFF);
        newHeader[4] = (byte) ((newFileSize >> 16) & 0xFF);
        newHeader[5] = (byte) ((newFileSize >> 24) & 0xFF);

        return newHeader;
    }

    private byte[] flatBMP() {
        byte[] newHeader = Arrays.copyOf(this.headerInfo.getHeader(), 54);
        // if we flatten then our height reduces
        int newHeight = this.height / 2;
    
        // I have to update the values where I found the header in order to resize the image
        newHeader[25] = (byte) ((newHeight >> 24) & 0xFF);
        newHeader[24] = (byte) ((newHeight >> 16) & 0xFF);
        newHeader[23] = (byte) ((newHeight >> 8) & 0xFF);
        newHeader[22] = (byte) (newHeight & 0xFF);
    
        int newFileSize = calculateNewFileSize("f");
        return getBytes(newHeader, newFileSize);
    }
    
    private int calculateNewFileSize(String action) {
        int newFileSize = 0;
        if (action.equals("t")) {
            newFileSize = 54 + ((this.width / 2) * this.height);
        } else if (action.equals("f")) {
            newFileSize = 54 + (this.width * (this.height / 2));
        }
        return newFileSize;
     }
}