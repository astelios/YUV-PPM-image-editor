package YUV-PPM-image-processor;

/**
 *
 * @author Stelios
 * 
 * Custom exception for cases where user imports non ppm/yuv files 
 */
public class UnsupportedFileFormatException extends java.lang.Exception{
    String msg = "Uninitialized";
    static final long serialVersionUID = -4567891456L;
    
    public UnsupportedFileFormatException() {
    }
    
    public UnsupportedFileFormatException(String msg) {
        this.msg = msg;
    }
    
    @Override
    public String toString() {
        return("[UnsupportedFileFormatException] " + msg);  
    }
}
