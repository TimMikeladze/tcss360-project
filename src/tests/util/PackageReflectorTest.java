package tests.util;

import static org.junit.Assert.*;

import java.io.IOException;

import model.util.PackageReflector;

import org.junit.Test;

/**
 * Tests the PackageReflector class from model.util
 * 
 * @author Jordan Matthews
 * @version December 5, 2013
 */
public class PackageReflectorTest {
    
    /**
     * Tests the getClasses method
     */
    @Test
    public void testGetClasses() {
        try {
            assertTrue("", PackageReflector.getClasses("model") != null);
        } catch (ClassNotFoundException | IOException e) {
            fail("Did not work");
        }
    }
}
