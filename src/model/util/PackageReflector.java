
package model.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * Gets all classes in a package using reflection
 *
 * @author Tim Mikeladze
 * @version 11-17-2013
 */
public class PackageReflector {

    /**
     * Gets all classes in a package
     *
     * @param packageName package name
     * @return List of classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static List<Class<?>> getClasses(final String packageName) throws ClassNotFoundException,
            IOException {
        return getClasses(packageName, new String[] {});
    }

    /**
     * Get classes in a package
     *
     * @param packageName the package name
     * @param ignore classes to ignore
     * @return list of classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static List<Class<?>> getClasses(final String packageName, final String[] ignore)
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
     * Find classes in a package
     *
     * @param directory the file directory
     * @param packageName package name
     * @param ignore classes to ignore
     * @return list of found classes
     * @throws ClassNotFoundException
     */
    private static List<Class<?>> findClasses(final File directory, final String packageName,
            final String[] ignore) throws ClassNotFoundException {
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