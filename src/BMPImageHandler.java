import java.io.IOException;

public class BMPImageHandler {
    public static void main(String[] args) throws IOException {
        if (args.length > 2 || args.length < 2) {
            System.out.println("Usage: java BMPImageHandler <flag> <file>");
            return;
        }
        
        String flag = args[0];
        String file = args[1];

        BmpHandlerCore basics = new BmpHandlerCore(file);
        BmpHandlerRotator rotate = new BmpHandlerRotator(file);

        switch(flag){
            case "-basics":
                basics.coloredImages();
                break;
            case "-rotate":
                rotate.rotatedImages();
                break;
            case "-resize":
                System.out.println("Not implemented");
                break;
            case "-all":
                System.out.println("Not implemented");
                break;  
            default:
                System.out.println("Invalid flag");
                return;
        }
        System.out.println("Changes over the BMP file were successful.");
    }
} 