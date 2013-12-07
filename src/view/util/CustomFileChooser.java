
package view.util;

import java.io.File;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

/**
 * File chooser that only allows specified file extensions.
 * @author Cathryn Castillo
 * @version 12-01-13
 */
public class CustomFileChooser {
    
    private FileChooser fileChooser;
    
    public CustomFileChooser() {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("DOC files (*.doc)", "*.doc"));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("DOCX files (*.docx)", "*.docx"));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Rich files (*.rtf)", "*.rtf"));
    }
    
    @Override
    public boolean equals(final Object obj) {
        return fileChooser.equals(obj);
    }
    
    public ObservableList<ExtensionFilter> getExtensionFilters() {
        return fileChooser.getExtensionFilters();
    }
    
    public final File getInitialDirectory() {
        return fileChooser.getInitialDirectory();
    }
    
    public final String getInitialFileName() {
        return fileChooser.getInitialFileName();
    }
    
    public final String getTitle() {
        return fileChooser.getTitle();
    }
    
    @Override
    public int hashCode() {
        return fileChooser.hashCode();
    }
    
    public final ObjectProperty<File> initialDirectoryProperty() {
        return fileChooser.initialDirectoryProperty();
    }
    
    public final ObjectProperty<String> initialFileNameProperty() {
        return fileChooser.initialFileNameProperty();
    }
    
    public final void setInitialDirectory(final File arg0) {
        fileChooser.setInitialDirectory(arg0);
    }
    
    public final void setInitialFileName(final String arg0) {
        fileChooser.setInitialFileName(arg0);
    }
    
    public final void setTitle(final String arg0) {
        fileChooser.setTitle(arg0);
    }
    
    public File showOpenDialog(final Window arg0) {
        return fileChooser.showOpenDialog(arg0);
    }
    
    public List<File> showOpenMultipleDialog(final Window arg0) {
        return fileChooser.showOpenMultipleDialog(arg0);
    }
    
    public File showSaveDialog(final Window arg0) {
        return fileChooser.showSaveDialog(arg0);
    }
    
    public final StringProperty titleProperty() {
        return fileChooser.titleProperty();
    }
    
    @Override
    public String toString() {
        return fileChooser.toString();
    }
    
}
