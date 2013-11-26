
package view.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CustomTable<T> extends TableView<T> {
    
    private String[] columnNames;
    private String[] variableNames;
    private static int[] columnWidths = { 30, 25, 10, 10, 25 };
    private ObservableList<T> data;
    
    public CustomTable(final String[] columnNames, final String[] variableNames, final int[] columnWidths) {
        this.columnNames = columnNames;
        this.variableNames = variableNames;
        CustomTable.columnWidths = columnWidths;
        data = FXCollections.observableArrayList();
        create();
    }
    
    public CustomTable(final String[] columnNames, final String[] variableNames) {
        this(columnNames, variableNames, columnWidths);
    }
    
    private void create() {
        TableColumn<T, String> column;
        for (int i = 0; i < columnNames.length; i++) {
            column = new TableColumn<T, String>(columnNames[i]);
            // column.setMinWidth(columnWidths[i]);
            column.prefWidthProperty()
                  .bind(widthProperty().divide(100 / columnWidths[i]));
            
            column.setCellValueFactory(new PropertyValueFactory<T, String>(variableNames[i]));
            
            getColumns().add(column);
        }
    }
    
    public void add(final T t) {
        data.add(t);
        setItems(data);
    }
}
