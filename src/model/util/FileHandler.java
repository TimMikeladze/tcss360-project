
package model.util;

import java.io.File;
import java.io.FileInputStream;

/**
 * Handles conversion of files to byte arrays and gets file extensions.
 */
public class FileHandler {
    
    /**
     * Converts a file to byte array.
     * 
     * @param f The file to convert
     * @return A byte array representation of the given file.
     */
    public static byte[] convertFileToBytes(File f) {
        FileInputStream fis = null;
        byte[] byteArray = new byte[(int) f.length()];
        
        try {
            // convert file into array of bytes
            fis = new FileInputStream(f);
            fis.read(byteArray);
            fis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return byteArray;
    }
    
    /**
     * Finds and returns the file extension of the given file.
     * 
     * @param f The file to find the extension for.
     * @return The file extension of the given file.
     */
    public static String getFileExtension(File f) {
        String filePath = f.getAbsolutePath();
        int dot = filePath.lastIndexOf('.');
        return filePath.substring(dot + 1);
    }
}
