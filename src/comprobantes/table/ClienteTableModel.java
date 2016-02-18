package comprobantes.table;
 
import java.io.File;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class ClienteTableModel
  extends AbstractTableModel
{
  private static final int COLUMNAS_ARCHIVOS = 6;
  private File directorio;
  private ArrayList<Object[]> fila = null;
  String[] columnNames = { "Apellidos y Nombres / Razón Social", "Tipo Identificación", "Número Identidicación", "Tipo Cliente", "Actualizar", "Eliminar" };
  boolean[] canEdit = { false, false, false, false, true, true };
  
  public ClienteTableModel(ArrayList<Object[]> fila)
  {
    this.fila = fila;
  }
  
  public void deleteRow(int row)
  {
    if (row == -1) {
      row = 0;
    }
    Object[] pr = (Object[])this.fila.get(row);
    this.fila.remove(pr);
    fireTableRowsDeleted(row, row);
  }
  
  public int getColumnCount()
  {
    return 6;
  }
  
  public int getRowCount()
  {
    return this.fila.size();
  }
  
  public String getColumnName(int col)
  {
    return this.columnNames[col];
  }
  
  public Object getValueAt(int row, int col)
  {
    if ((this.fila.size() == 1) && (row == 1)) {
      row--;
    }
    if (row == -1) {
      row = 0;
    }
    return ((Object[])this.fila.get(row))[col];
  }
  
  public boolean isCellEditable(int rowIndex, int columnIndex)
  {
    return this.canEdit[columnIndex];
  }
  
  public File getDirectorio()
  {
    return this.directorio;
  }
  
  public void setDirectorio(File directorio)
  {
    this.directorio = directorio;
  }
}
