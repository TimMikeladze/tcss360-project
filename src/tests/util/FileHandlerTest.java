package tests.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import model.util.FileHandler;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the FileHandler class from model.util
 * 
 * @author Mohammad Juma
 * @version December 5, 2013
 */
public class FileHandlerTest {
	
	/**
	 * Byte Array
	 */
	private byte[] bArray;
	
	/**
	 * File extension
	 */
	private String extension;
	
	/**
	 * File.
	 */
	private File testFile;
	
	/**
	 * Sets up the tests.
	 */
	@Before
	public void setup() {
		testFile = new File("tests/paper.txt");
		bArray = "HelloWorld".getBytes();
		extension = "txt";
	}
	
	/**
	 * Tests convertBytesToFile method.
	 */
    @Test
    public void testConvertBytesToFile() {
    	File f = null;
        try {
			f = FileHandler.convertBytesToFile(new String(bArray), extension);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        assertTrue("Converting bytes to file", f != null);
    }
}
