package comprobantes.table;
 
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class DatosAdicionalesTableModel
  extends AbstractTableModel
{
  int numeroRegistros = 0;
  String[] columnNames = { "Nombre", "Descripción", "Acción" };
  private ArrayList<Object[]> data;
  
  public DatosAdicionalesTableModel()
  {
    this.data = new ArrayList();
  }
  
  public int getColumnCount()
  {
    return this.columnNames.length;
  }
  
  public int getRowCount()
  {
    return this.data.size();
  }
  
  public String getColumnName(int col)
  {
    return this.columnNames[col];
  }
  
  public Object getValueAt(int row, int col)
  {
    return ((Object[])this.data.get(row))[col];
  }
  
  public Class getColumnClass(int col)
  {
    return getValueAt(0, col).getClass();
  }
  
  public boolean isCellEditable(int row, int col)
  {
    return true;
  }
  
  public void setValueAt(Object value, int row, int col)
  {
    if ((this.data.size() == 1) && (row == 1)) {
      row--;
    }
    if (value != null)
    {
      if ((col == 1) && (col == 0))
      {
        String descripcion = (String)value;
        ((Object[])this.data.get(row))[col] = descripcion.substring(0, descripcion.length() > 100 ? 100 : descripcion.length());
      }
      else
      {
        ((Object[])this.data.get(row))[col] = value;
      }
      super.fireTableCellUpdated(row, col);
    }
  }
  
  public void addRow(String nombre, String descripcion)
  {
    if (getRowCount() < 5)
    {
      Object[] obj = { nombre, descripcion, "" };
      
      this.data.add(obj);
      int numRows = this.data.size();
      fireTableRowsInserted(numRows, numRows + 1);
    }
    else
    {
      JOptionPane.showMessageDialog(new JFrame(), "So puede ingresar hasta cinco detalles adicionales", "ERROR", 0);
    }
  }
  
  public void deleteRow(int fila)
  {
    if (fila == -1) {
      fila = 0;
    }
    Object[] pr = (Object[])this.data.get(fila);
    this.data.remove(pr);
    fireTableRowsDeleted(fila, fila);
  }
  
  public void deleteAllRows()
  {
    this.data.clear();
    super.fireTableDataChanged();
  }
}
