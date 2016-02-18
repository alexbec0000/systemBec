package comprobantes.table;
 
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class ResultadosTableModel
  extends AbstractTableModel
{
  private ArrayList<Object[]> data;
  private String[] columnNames = { "Nro.", "Archivo XML", "Tipo Comprobante", "Resultado", "Motivo" };
  
  public ResultadosTableModel()
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
  
  public Object[] getRow(int row)
  {
    return (Object[])this.data.get(row);
  }
  
  public Class<?> getColumnClass(int column)
  {
    return getValueAt(0, column).getClass();
  }
  
  public boolean isCellEditable(int row, int col)
  {
    return false;
  }
  
  public void setValueAt(Object value, int row, int col)
  {
    if ((this.data.size() == 1) && (row == 1)) {
      row--;
    }
    ((Object[])this.data.get(row))[col] = value;
    fireTableCellUpdated(row, col);
  }
  
  public void addRow(Integer num, String archivo, String tipo, String resultado, String motivo)
  {
    if (motivo == null) {
      motivo = "";
    }
    Object[] obj = { num, archivo, tipo, resultado, motivo };
    
    this.data.add(obj);
    int numRows = this.data.size();
    fireTableRowsInserted(numRows, numRows + 1);
  }
}
