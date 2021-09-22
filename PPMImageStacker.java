package ce326.hw2;
import java.io.*;
import java.util.*;
/**
 *
 * @author Stelios
 * 
 * PPM image stacker
 * 
 * Stacks multiple photo takes of the same theme, 
 * in order to reduce the noise produced by 
 * overexposure of the camera lens to light.
 */
public class PPMImageStacker {
    PPMImage[] images;
    PPMImage stackedImage;

    public PPMImageStacker(File file) throws FileNotFoundException, UnsupportedFileFormatException {
        File[] files;
        int i;
        
        if(!file.exists()) {
            throw new FileNotFoundException(String.format("[ERROR] Directory %s does not exist!", file.getName()));
        }
        
        if(!file.isDirectory()) {
            throw new FileNotFoundException(String.format("[ERROR] %s is not a directory!", file.getName()));
        }
        
        files = file.listFiles();
        images = new PPMImage[files.length];
        for(i=0; i<files.length; i++) {
            images[i] = new PPMImage(files[i]);
        }
    }
    
    public void stack() {
        RGBImage stackedImageRGB;
        int i, j, k, width, height;
        int r, g, b;
        
        width = images[0].getWidth();
        height = images[0].getHeight();
        stackedImageRGB = new RGBImage(width, height, RGBImage.MAX_COLORDEPTH);
        // Perform stacking
        for(i=0; i<height; i++) {
            for(j=0; j<width; j++) {
                // Average value of pixels from all images
                for(k=0, r=0, g=0, b=0; k<images.length; k++) {
                    r = r + images[k].getPixel(i, j).getRed();
                    g = g + images[k].getPixel(i, j).getGreen();
                    b = b + images[k].getPixel(i, j).getBlue();
                }
                r = r/images.length;
                g = g/images.length;
                b = b/images.length;
                
                stackedImageRGB.setPixel(i, j, new RGBPixel((short)r, (short)g, (short)b));
            }
        }
        stackedImage = new PPMImage(stackedImageRGB);
    }
    
    public PPMImage getStackedImage() {
        return(stackedImage);
    }
}
