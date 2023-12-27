import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class BmpHandlerCore {
        
    private byte[] pixels;
    private byte[] redPixels;
    private byte[] bluePixels;
    private byte[] greenPixels;
    private byte[] sepiaPixels;
    private int width;
    private int height;
    private int pixelDataSize;
    private int tr, tb, tg;
    private String fileName;
    private HeaderLector info;

    public BmpHandlerCore(String BMPImage) {
        info = new HeaderLector(new File(BMPImage));
       
        this.width = info.getWidth();
        this.height = info.getHeight();  
        
        // The 24-bit per pixel stores 1 pixel value per 3 bytes
        this.pixelDataSize = 3 * this.height * this.width;

        this.pixels = new byte[this.pixelDataSize];
        this.fileName = BMPImage;

        try (FileInputStream BMP = new FileInputStream(new File(this.fileName))) {
            BMP.skip(54);
            BMP.read(this.pixels);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        this.redPixels = Arrays.copyOf(this.pixels, this.pixelDataSize);
        this.greenPixels = Arrays.copyOf(this.pixels, this.pixelDataSize);
        this.bluePixels = Arrays.copyOf(this.pixels, this.pixelDataSize);
        this.sepiaPixels = Arrays.copyOf(this.pixels, this.pixelDataSize);
    }

    public byte[] getPixels() {
        return this.pixels;
    }

    public String getFileName(String file) {
        String fileName = file;
        String[] imageName = fileName.split("\\.bmp");
        return imageName[0];
    }

    public void redImage() {
        try {
            FileOutputStream red = new FileOutputStream(getFileName(this.fileName) + "-red.bmp");
            red.write(this.info.getHeader());
            red.write(RGBColor("r"));
            red.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void blueImage() {
        try {
            FileOutputStream blue = new FileOutputStream(getFileName(this.fileName) + "-blue.bmp");
            blue.write(this.info.getHeader());
            blue.write(RGBColor("b"));
            blue.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void greenImage() {
        try {
            FileOutputStream green = new FileOutputStream(getFileName(this.fileName) + "-green.bmp");
            green.write(this.info.getHeader());
            green.write(RGBColor("g"));
            green.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void sepiaImage() {
        try {
            FileOutputStream sepia = new FileOutputStream(getFileName(this.fileName) + "-sepia.bmp");
            sepia.write(this.info.getHeader());
            sepia.write(sepiaColor());
            sepia.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void coloredImages() {
        redImage();
        blueImage();
        greenImage();
        sepiaImage();
    }

    private byte[] RGBColor(String color) {
        if (color.equals("b")) {
            for (int i = 0; i < this.pixelDataSize; i+=3) {
                this.bluePixels[i] = this.bluePixels[i];
                this.bluePixels[i+1] = 0;
                this.bluePixels[i+2] = 0;
            }
            return this.bluePixels;
        } else if (color.equals("g")) {
             for (int i = 0; i < this.pixelDataSize; i+=3) {
                this.greenPixels[i] = 0;
                this.greenPixels[i+1] = this.greenPixels[i+1];
                this.greenPixels[i+2] = 0;
            }
            return this.greenPixels;
        } else if (color.equals("r")) {
            for (int i = 0; i < this.pixelDataSize; i+=3) {
                this.redPixels[i] = 0;
                this.redPixels[i+1] = 0;
                this.redPixels[i+2] = this.redPixels[i+2];
            }
            return this.redPixels;
        }
        return this.pixels;
    }

    private byte[] sepiaColor() {
        for (int i = 0; i < pixelDataSize; i+=3) {
            tr = (int)(0.393*(this.sepiaPixels[i+2]& 0xFF) + 0.769*(this.sepiaPixels[i+1]& 0xFF) + 0.189*(this.sepiaPixels[i]& 0xFF));
            tg = (int)(0.349*(this.sepiaPixels[i+2]& 0xFF) + 0.686*(this.sepiaPixels[i+1]& 0xFF) + 0.168*(this.sepiaPixels[i]& 0xFF));
            tb = (int)(0.272*(this.sepiaPixels[i+2]& 0xFF) + 0.534*(this.sepiaPixels[i+1]& 0xFF) + 0.131*(this.sepiaPixels[i]& 0xFF));
            if (tr > 255) tr = 255;
            if (tg > 255) tg = 255;
            if (tb > 255) tb = 255;
            this.sepiaPixels[i] = (byte)tb;
            this.sepiaPixels[i+1] = (byte)tg;
            this.sepiaPixels[i+2] = (byte)tr;
        }
        return this.sepiaPixels;
    } 
}