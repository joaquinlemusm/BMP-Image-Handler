import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;

public class BmpHandlerRotator {
    
    private final BmpHandlerCore rotate;
    private final HeaderLector headerInfo;
    private final String fileName;
    private final int height;
    private final int width;
    
    public BmpHandlerRotator(String file) {
        this.rotate = new BmpHandlerCore(file);
        this.headerInfo = new HeaderLector(new File(file));
        this.height = this.headerInfo.getHeight();
        this.width = this.headerInfo.getWidth();
        this.fileName = this.rotate.getFileName(file);
    }

    public void rotatedImages() {
        hRotationImage();
        vRotationImage();
    }

    private byte[][] convertToMatrix(byte[] array) {
        byte[][] array2D = new byte[this.height][3*this.width];
        int k = 0;
        for (int y = 0; y < array2D.length; y++) {
            for (int x = 0; x < array2D[y].length; x++) {
                array2D[y][x] = array[k];
                k++;
            }
            if (k == array.length-1) {
                break;
            }
        }
        return array2D;
    }

    private byte[][] hRotation() {
        byte[][] matrix = convertToMatrix(this.rotate.getPixels());
        int k = matrix.length-1;
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                byte tmp = matrix[y][x];
                matrix[y][x] = matrix[k][x];
                matrix[k][x] = tmp;
            }
            k--;
            if (matrix.length/2 == k) {
                break;
            }
        }
        return matrix;
    }

    private byte[][] vRotation() {
        byte[][] matrix = convertToMatrix(this.rotate.getPixels());
        byte[][] copy = copyMatrix(matrix);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0, k = matrix[i].length-1; j < matrix[i].length-1; j+=3, k-=3) {
                matrix[i][j] = copy[i][k-2];
                matrix[i][j+1] = copy[i][k-1];
                matrix[i][j+2] = copy[i][k];
            }
        }
        return matrix;
    }

    private byte[][] rotatedMatrix(String rotation) {
        if (rotation.equals("v")) {
            return vRotation();
        }
        else if (rotation.equals("h")) {
            return hRotation();    
        }
        return new byte[0][0];
    }

    private byte[][] copyMatrix(byte[][] originalMatrix) {
        byte[][] copyOriginal = new byte[originalMatrix.length][originalMatrix[0].length];
        for (int i = 0; i < originalMatrix.length; i++) {
            for (int j = 0; j < originalMatrix[i].length; j++) {
                copyOriginal[i][j] = originalMatrix[i][j];
            }
        }
        return copyOriginal;  
    }

    private byte[] singleArray(String rotation) {
        byte[][] matrix = rotatedMatrix(rotation);
        int arrayLength = matrix.length * matrix[0].length;
        byte [] arrayRotated = new byte[arrayLength];
        int arrayPosition = 0;
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                arrayRotated[arrayPosition] = matrix[y][x];
                arrayPosition++;
            }
            if (arrayPosition == arrayLength) {
                break;
            }
        }
        return arrayRotated;
    }

    private void hRotationImage() {
        try {
            FileOutputStream hRotation = new FileOutputStream(rotate.getFileName(this.fileName)+"-hrotation.bmp");
            hRotation.write(this.headerInfo.getHeader());
            hRotation.write(singleArray("h"));
            hRotation.close();
        } catch (IOException e) { 
            System.out.println("The file already exists.");
        }
    }

    private void vRotationImage() {
        try {
            FileOutputStream vRotation = new FileOutputStream(rotate.getFileName(this.fileName)+"-vrotation.bmp");
            vRotation.write(this.headerInfo.getHeader());
            vRotation.write(singleArray("v"));
            vRotation.close();
        } catch (IOException e) { 
            System.out.println("The file already exists.");
        }
    }
}