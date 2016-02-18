package comprobantes.table;

import comprobantes.entidades.ImpuestoValor;
import comprobantes.modelo.ImpuestoRetencion;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class RetencionTableModel
  extends AbstractTableModel
{
  DefaultTableModel t;
  private ArrayList<Object[]> data;
  private List<ImpuestoRetencion> impuestos;
  private String[] columnNames = { "Cod. Reten.", "Código", "Descripción", "Base Imponible", "Porcentaje", "Total", "Documento", "Fecha", "Tipo", "Acción" };
  
  public RetencionTableModel()
  {
    this.data = new ArrayList();
    this.impuestos = new ArrayList();
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
  
  public Class getColumnClass(int c)
  {
    return getValueAt(0, c).getClass();
  }
  
  public boolean isCellEditable(int row, int col)
  {
    if (col == 9) {
      return true;
    }
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
  
  public void deleteRow(int fila)
  {
    if (fila == -1) {
      fila = 0;
    }
    Object[] item = (Object[])this.data.get(fila);
    this.data.remove(item);
    
    fireTableRowsDeleted(fila, fila);
  }
  
  public void addRow(ImpuestoValor impuesto, BigDecimal baseImponible, BigDecimal porcentaje, BigDecimal valorTotal, String doc, String fechaEmision, String tipoDoc)
  {
    int filas = this.data.size();
    
    Object[] item = { impuesto.getCodigo(), impuesto.getCodigoImpuesto(), impuesto.getDescripcion(), baseImponible, porcentaje, valorTotal, doc, fechaEmision, tipoDoc, "" };
    this.data.add(item);
    
    fireTableRowsInserted(filas, this.data.size());
  }
  
  public List<ImpuestoRetencion> getImpuestos()
  {
    return this.impuestos;
  }
  
  public void setImpuestos(List<ImpuestoRetencion> impuestos)
  {
    this.impuestos = impuestos;
  }
}
