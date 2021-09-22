package YUV-PPM-image-processor;
import java.io.*;
import java.util.*;
/**
 *
 * @author Stelios
 * 
 * @file YUV3 format image 
 * 
 * - starts with magic number "YUV3"
 * - integer width
 * - integer height
 * 
 * triplets of YUV values for each pixel in order of rows
 */
public class YUVImage {
    int width, height;
    YUVPixel[][] img;
    
    public YUVImage(int width, int height) {
        int i, j;
        
        this.width = width;
        this.height = height;
        img = new YUVPixel[height][width];
        
        for(i=0; i<height; i++) {
            for(j=0; j<width; j++) {
                img[i][j] = new YUVPixel((short)16, (short)128, (short)128);
            }
        }
    }
    
    public YUVImage(YUVImage copyImg) {
        int i, j;
        
        width = copyImg.width;
        height = copyImg.height;
        img = new YUVPixel[height][width];
        
        for(i=0; i<height; i++) {
            for(j=0; j<width; j++) {
                img[i][j] = new YUVPixel(copyImg.getPixel(i, j));
            }
        }
    }

    public YUVImage(RGBImage RGBImg) {
        int i, j;
        
        width = RGBImg.getWidth();
        height = RGBImg.getHeight();
        img = new YUVPixel[height][width];
        
        for(i=0; i<height; i++) {
            for(j=0; j<width; j++) {
                img[i][j] = new YUVPixel(RGBImg.getPixel(i, j));
            }
        }
    }
    
    public YUVImage(File file) throws FileNotFoundException, UnsupportedFileFormatException {
        int i, j;
        short Y, U, V;
        
        try(Scanner in = new Scanner(new FileInputStream(file))) {
            if(!in.hasNext() || !in.next().equals("YUV3")) {
                throw new UnsupportedFileFormatException();
            }
            
            width = in.nextInt();
            height = in.nextInt();
            img = new YUVPixel[height][width];
            
            for(i=0; i<height; i++) {
                for(j=0; j<width; j++) {
                    Y = in.nextShort();
                    U = in.nextShort();
                    V = in.nextShort();
                    
                    img[i][j] = new YUVPixel(Y, U, V);
                }
            }
        }
    }
    
    public YUVPixel getPixel(int row, int col) {
        return img[row][col];
    }
    
    @Override
    public String toString() {
        int i, j;
        
        StringBuilder temp = new StringBuilder(String.format("YUV3\n%d %d\n", width, height));
        
        for(i=0; i<height; i++) {
            for(j=0; j<width; j++) {
                temp.append(img[i][j].toString());
                temp.append('\n');
            }
        }
        
        /*for(i=0; i<height-1; i++) {
        temp = temp.append(img[i][0].toString());
        for(j=1; j<width-1; j++) {
        temp = temp.append(String.format(" %s ", img[i][j].toString()));
        }
        
        temp = temp.append(img[i][width-1].toString());
        temp = temp.append('\n');
        }
        temp = temp.append(img[height-1][0].toString());
        for(j=0; j<width-1; j++) {
        temp = temp.append(String.format(" %s ", img[height-1][j].toString()));
        }
        temp = temp.append(img[height-1][width-1].toString());*/
        
        return(temp.toString());
    }
    
    public void toFile(File file) {
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
            /*writer.write(width);
            writer.write(' ');
            writer.write(height);
            writer.write('\n');
            for(i=0; i<height; i++) {
            for(j=0; j<width; j++) {
            writer.write(img[i][j].toString());
            writer.write('\n');
            }
            }*/
        }
        catch(IOException ex) {
        }
    }
    
    public void equalize() {
        int i, j;
        
        Histogram temp = new Histogram(this);
        
        temp.equalize();
        for(i=0; i<height; i++) {
            for(j=0; j<width; j++) {
                img[i][j].setY(temp.getEqualizedLuminocity(img[i][j].getY()));
            }
        }
    }
}
