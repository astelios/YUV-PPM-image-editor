package YUV-PPM-image-processor;
import java.io.*;
import java.util.*;
/**
 *
 * @author Stelios
 * 
 * @file Plain PPM format image
 * 
 * - Starts with the magic number "P3"
 * - integer corresponding to width
 * - integer corresponding to height
 * - integer corresponding to color depth
 * 
 * Afterwards, triplets of numbers corresponding to the RGB values of the pixels,
 * by order of rows first (all pixels in first row, all in second, ...)
 */
public class PPMImage extends RGBImage{

    public PPMImage(File file) throws UnsupportedFileFormatException, FileNotFoundException {
        int i, j;
        short r, g, b;
        
        try(Scanner in = new Scanner(new FileInputStream(file))) {
            // either empty or non ppm file
            if(!in.hasNext() || !in.next().equals("P3")) {
                throw new UnsupportedFileFormatException();
            }
            
            width = in.nextInt();
            height = in.nextInt();
            colorDepth = in.nextInt();
            
            img = new RGBPixel[height][width];
            
            for(i=0; i<height; i++) {
                for(j=0; j<width; j++) {
                    r = in.nextShort();
                    g = in.nextShort();
                    b = in.nextShort();
                    
                    img[i][j] = new RGBPixel(r, g, b);
                }
            }
        }
    }
    
    public PPMImage(RGBImage img) {
        super(img);
    }
    
    public PPMImage(YUVImage img) {
        super(img);
    }
        
    // Returns the corresponding ppm image file contents inside a string
    @Override
    public String toString() {
        int i, j;
        
        StringBuilder temp = new StringBuilder(String.format("P3\n%d %d %d\n", width, height, colorDepth));
        
        for(i=0; i<height; i++) {
            for(j=0; j<width; j++) {
                temp.append(img[i][j].toString());
                temp.append('\n');
            }
        }
        
        return(temp.toString());
    }
    
    // Creates new file containing this ppm image (in path pointed by file parameter)
    public void toFile(java.io.File file) {
        int i, j;
        
        if(file.exists()) {
            file.delete();
        }
        
        try {
            file.createNewFile();
        }
        catch(IOException ex) {
        }
        
        try(FileWriter writer = new FileWriter(file);) {
            writer.write(this.toString());
        }
        catch(IOException ex) {
        }
    }
}
