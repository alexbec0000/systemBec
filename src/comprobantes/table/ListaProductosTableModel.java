package comprobantes.table;
 
import javax.swing.table.AbstractTableModel;

public class ListaProductosTableModel
  extends AbstractTableModel
{
  Object[] columnNames = { "Código principal", "Código auxiliar", "Descripción", "P. Unitario", "" };
  String[][] data = { { "0", "0", "descripcion", "0.00", "0.00", "" } };
  Object[][] filas = (Object[][])null;
  
  public ListaProductosTableModel() {}
  
  public ListaProductosTableModel(Object[][] fila)
  {
    this.filas = fila;
  }
  
  public String getColumnName(int col)
  {
    return (String)this.columnNames[col];
  }
  
  public boolean isCellEditable(int row, int col)
  {
    boolean resp = false;
    if (col == 4) {
      resp = true;
    }
    return resp;
  }
  
  public int getRowCount()
  {
    return this.filas.length;
  }
  
  public int getColumnCount()
  {
    return this.columnNames.length;
  }
  
  public Object getValueAt(int rowIndex, int columnIndex)
  {
    return this.filas[rowIndex][columnIndex];
  }
}
