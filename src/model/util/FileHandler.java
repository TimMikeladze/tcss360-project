
package model.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Handles conversion of files and gets file extensions.
 */
public class FileHandler {
    
    /**
     * Converts a file to a String.
     * 
     * @param f The file to convert
     * @return A String representation of the given file.
     * @throws IOException 
     */
	
	
    public static String convertFileToBytes(File f) throws IOException {
        FileInputStream fis = null;
        byte[] byteArray = new byte[(int) f.length()];
        
        // convert file into array of bytes
        fis = new FileInputStream(f);
        fis.read(byteArray);
        fis.close();
            
        
        String result = new String(byteArray);
        
        return result;
    }
    
    /**
     * Converts a String to a file.
     * 
     * @param bArray The String to convert.
     * @param extension The file extension.
     * @return A file created from the given String.
     * @throws IOException 
     */
   
    public static File convertBytesToFile(String bArray, String extension) throws IOException {
    	
    	File f = File.createTempFile("temp", "." + extension);
		byte[] bytes = bArray.getBytes();
		
		/*
		System.out.println("LOLTEST:" + bArray);
		String testing = new String(bytes);
		System.out.println("LOLTEST:" + testing);
		*/
		
		FileOutputStream fos = new FileOutputStream(f);
		fos.write(bytes);
		fos.close();
    	
    	return f;
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
