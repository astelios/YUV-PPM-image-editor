package YUV-PPM-image-processor;
import java.io.*;
/**
 *
 * @author Stelios
 * 
 * @file YUV image Histogram balancing class
 * 
 * histogramDistribution: distribution of pixel's luminocity values (Y value)
 * suggestion: new Y value suggestion for each old Y pixel value
 */
public class Histogram {
    public static final int MAX_LUMINOCITY = 236;
    short[] suggestion = new short[MAX_LUMINOCITY];
    int[] histogramDistribution = new int[MAX_LUMINOCITY];
    int pixelNum;
    
    public Histogram(YUVImage img) {
        int i, j;
        
        for(i=0; i<MAX_LUMINOCITY; i++) {
            histogramDistribution[i] = 0;
        }
        
        for(i=0; i<img.height; i++) {
            for(j=0; j<img.width; j++) {
                histogramDistribution[img.getPixel(i, j).getY()]++;
            }
        }
        
        pixelNum = img.height*img.width;
    }
    
    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder();
        int i, j, thousands, hundreds, tens;
        
        for(i=0; i<MAX_LUMINOCITY; i++) {
            temp.append(String.format("\n%3d.(%4d)\t", i, histogramDistribution[i]));
            
            for(j=0, thousands=histogramDistribution[i]/1000; j<thousands; j++) {
                temp.append('#');
            }
            for(j=0, hundreds=(histogramDistribution[i]/100 - thousands*10); j<hundreds; j++) {
                temp.append('$');
            }
            for(j=0, tens=(histogramDistribution[i]/10 - thousands*100 - hundreds*10); j<tens; j++) {
                temp.append('@');
            }
            for(j=0; j<histogramDistribution[i] - thousands*1000 - hundreds*100 - tens*10; j++) {
                temp.append('*');
            }
        }
        temp.append('\n');
        
        return(temp.toString());
    }
    
    public void toFile(File file) throws IOException {
        if(file.exists()) {
            file.delete();
        }
        
        file.createNewFile();
        
        try(FileWriter writer = new FileWriter(file)) {
            writer.write(this.toString());
        }
    }
    
    public void equalize() {
        int i;
        double[] probability = new double[MAX_LUMINOCITY];
        int[] newHistogramDistribution = new int[MAX_LUMINOCITY];
        
        // Calculate probability distribution
        for(i=0; i<MAX_LUMINOCITY; i++) {
            probability[i] = new Double(histogramDistribution[i])/new Double(pixelNum);
        }
        // Calculate cumulative probability distribution
        for(i=1; i<MAX_LUMINOCITY; i++) {
            probability[i] += probability[i-1];
        }
        // Calculate transmutation suggestion
        for(i=0; i<MAX_LUMINOCITY; i++) {
            suggestion[i] = new Double(probability[i]*new Double(MAX_LUMINOCITY-1)).shortValue(); 
        }
        // Transmutate histogram distribution
        for(i=0; i<MAX_LUMINOCITY; i++) {
            newHistogramDistribution[i] = 0;
        }
        for(i=0; i<MAX_LUMINOCITY; i++) {
            newHistogramDistribution[suggestion[i]] += histogramDistribution[i];
        }
        
        histogramDistribution = newHistogramDistribution; 
    }
    
    public short getEqualizedLuminocity(int luminocity) {
        return(suggestion[luminocity]);
    }
}
