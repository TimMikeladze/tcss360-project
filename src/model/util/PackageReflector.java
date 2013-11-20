
package model.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * Package Reflector does TODO
 * 
 * @author Tim Mikeladze
 * @version 11-17-2013
 */
public class PackageReflector {
    
    /**
     * TODO 
     * 
     * @param packageName TODO
     * @return TODO
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static List<Class<?>> getClasses(String packageName) throws ClassNotFoundException,
            IOException {
        return getClasses(packageName, new String[] {});
    }
    
    /**
     * TODO
     * 
     * @param packageName TODO
     * @param ignore TODO
     * @return TODO
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static List<Class<?>> getClasses(String packageName, String[] ignore)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName, ignore));
        }
        return classes;
    }
    
    /**
     * TODO
     * 
     * @param directory TODO
     * @param packageName TODO
     * @param ignore TODO
     * @return TODO
     * @throws ClassNotFoundException
     */
    private static List<Class<?>> findClasses(File directory, String packageName,
            String[] ignore) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName(), ignore));
            }
            else if (file.getName().endsWith(".class")) {
                String name = file.getName().replace(".class", "");
                if (!Arrays.asList(ignore).contains(name)) {
                    classes.add(Class.forName(packageName + '.'
                            + file.getName().substring(0, file.getName().length() - 6)));
                }
            }
        }
        return classes;
    }
}