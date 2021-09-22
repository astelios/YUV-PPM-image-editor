package ce326.hw2;

/**
 *
 * @author Stelios
 */
public class RGBImage implements Image{
    public static final int MAX_COLORDEPTH = 255;
    public int width, height, colorDepth;
    RGBPixel[][] img;
    
    public RGBImage() {
    }
    
    public RGBImage(int width, int height, int colordepth) {
        this.width = width;
        this.height = height;
        this.colorDepth = colordepth;
        img = new RGBPixel[height][width];
    }
    
    public RGBImage(RGBImage copyImg) {
        int i, j;
        
        width = copyImg.getWidth();
        height = copyImg.getHeight();
        colorDepth = copyImg.getColorDepth();
        img = new RGBPixel[height][width];
        
        for(i=0; i<height; i++) {
            for(j=0; j<width; j++) {
                img[i][j] = new RGBPixel(copyImg.getPixel(i, j));
            }
        }
    }
    
    public RGBImage(YUVImage YUVImg) {
        int i, j;
        
        width = YUVImg.width;
        height = YUVImg.height;
        colorDepth = MAX_COLORDEPTH;
        img = new RGBPixel[height][width];
        
        for(i=0; i<height; i++) {
            for(j=0; j<width; j++) {
                img[i][j] = new RGBPixel(YUVImg.getPixel(i, j));
            }
        }
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }

    public int getColorDepth() {
        return colorDepth;
    }
    
    public RGBPixel getPixel(int row, int col) {
        return(img[row][col]);
    }
    
    public void setPixel(int row, int col, RGBPixel pixel) {
        img[row][col] = pixel;
    }
    
    //----Interface----
    
    public void grayscale() {
        short gray;
        int i, j;
        
        for(i=0; i<height; i++) {
            for(j=0; j<width; j++) {
                gray = (short) (img[i][j].getRed()*0.3 + img[i][j].getGreen()*0.59 + img[i][j].getBlue()*0.11);
                
                img[i][j].setAll(gray);
            }
        }
    }
    
    public void doublesize() {
        RGBPixel[][] newImg = new RGBPixel[2*height][2*width];
        int i, j;
        
        for(i=0; i<height; i++) {
            for(j=0; j<width; j++) {
                newImg[2*i][2*j] = new RGBPixel(img[i][j]);
                newImg[2*i + 1][2*j] = new RGBPixel(img[i][j]);
                newImg[2*i][2*j + 1] = new RGBPixel(img[i][j]);
                newImg[2*i + 1][2*j + 1] = new RGBPixel(img[i][j]);
            }
        }
        
        img = newImg;
        height = 2*height;
        width = 2*width;
    }
    
    public void halfsize() {
        RGBPixel[][] newImg = new RGBPixel[height/2][width/2];
        int i, j;
        short r, g, b;
        
        for(i=0; i<height/2; i++) {
            for(j=0; j<width/2; j++) {
                r = (short) ((img[2*i][2*j].getRed() + img[2*i + 1][2*j].getRed() + img[2*i][2*j + 1].getRed() + img[2*i + 1][2*j + 1].getRed())/4);
                g = (short) ((img[2*i][2*j].getGreen() + img[2*i + 1][2*j].getGreen() + img[2*i][2*j + 1].getGreen() + img[2*i + 1][2*j + 1].getGreen())/4);
                b = (short) ((img[2*i][2*j].getBlue() + img[2*i + 1][2*j].getBlue() + img[2*i][2*j + 1].getBlue() + img[2*i + 1][2*j + 1].getBlue())/4);
                
                newImg[i][j] = new RGBPixel(r,g,b);
            }
        }
        
        img = newImg;
        height = height/2;
        width = width/2;
    }
    
    public void rotateClockwise() {
        RGBPixel[][] newImg = new RGBPixel[width][height];
        int i, j, temp;
        
        for(i=0; i<height; i++) {
            for(j=0; j<width; j++) {
                newImg[j][height-1-i] = img[i][j];
            }
        }
        
        img = newImg;
        temp = width;
        width = height;
        height = temp;
    }
}
