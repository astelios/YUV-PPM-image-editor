package YUV-PPM-image-processor;

/**
 *
 * @author Stelios
 */
public class RGBPixel {
    // Normalisation of RGB values from (0,...,255) to (-128,...,127)
    private byte red, green, blue;
    
    public RGBPixel(short red, short green, short blue) {
        // Byte.MIN_VALUE == -128
        this.red = (byte) (red + Byte.MIN_VALUE);
        this.green = (byte) (green + Byte.MIN_VALUE);
        this.blue = (byte) (blue + Byte.MIN_VALUE);
    }
    
    public RGBPixel(RGBPixel pixel) {
        red = (byte) (pixel.getRed() + Byte.MIN_VALUE);
        green = (byte) (pixel.getGreen() + Byte.MIN_VALUE);
        blue = (byte) (pixel.getBlue() + Byte.MIN_VALUE);
    }
    
    public RGBPixel(YUVPixel pixel) {
        int C = pixel.getY() - 16;
        int D = pixel.getU() - 128;
        int E = pixel.getV() - 128;
        
        red = (byte) (clip((298*C + 409*E + 128)>>8) + Byte.MIN_VALUE);
        green = (byte) (clip((298*C - 100*D - 208*E + 128)>>8) + Byte.MIN_VALUE);
        blue = (byte) (clip((298*C + 516*D + 128)>>8) + Byte.MIN_VALUE);
    }
    
    private static short clip(int value) {
        if(value<0) {
            return(0);
        }
        if(value>255) {
            return(255);
        }
        return((short) value);
    }
    
    public short getRed() {
        // Subtracting Byte.MIN_VALUE denormalizes the RGB values (+128)
        return (short) (red - Byte.MIN_VALUE);
    }

    public short getGreen() {
        return (short) (green - Byte.MIN_VALUE);
    }

    public short getBlue() {
        return (short) (blue - Byte.MIN_VALUE);
    }
    
    public void setRed(short red) {
        this.red = (byte) (red + Byte.MIN_VALUE);
    }

    public void setGreen(short green) {
        this.green = (byte) (green + Byte.MIN_VALUE);
    }

    public void setBlue(short blue) {
        this.blue = (byte) (blue + Byte.MIN_VALUE);
    }
    
    public void setAll(short value) {
        this.red = (byte) (value + Byte.MIN_VALUE);
        this.green = (byte) (value + Byte.MIN_VALUE);
        this.blue = (byte) (value + Byte.MIN_VALUE);
    }
    
    public int getRGB() {
        int r = this.red<<2*8 - Byte.MIN_VALUE;
        int g = this.green<<8 - Byte.MIN_VALUE;
        int b = this.blue - Byte.MIN_VALUE;
        
        return(r + g + b);
    }
    
    public void setRGB(int value) {
        this.red = (byte) ((value&0xFF0000)>>16 + Byte.MIN_VALUE);
        this.green = (byte) ((value&0xFF00)>>8 + Byte.MIN_VALUE);
        this.blue = (byte) (value&0xFF + Byte.MIN_VALUE);
    }
    
    public final void setRGB(short red, short green, short blue) {
        this.red = (byte) (red + Byte.MIN_VALUE);
        this.green = (byte) (green + Byte.MIN_VALUE);
        this.blue = (byte) (blue + Byte.MIN_VALUE);
    }
    
    @Override
    public String toString() {
        return(String.format("%d %d %d", red - Byte.MIN_VALUE, green - Byte.MIN_VALUE, blue - Byte.MIN_VALUE));
    }
    
}
