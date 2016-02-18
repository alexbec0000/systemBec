package comprobantes.table;

import comprobantes.entidades.ImpuestoValor;
import comprobantes.modelo.SubtotalImpuesto;
import comprobantes.sql.ImpuestoValorSQL;
import comprobantes.util.TipoImpuestoIvaEnum;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

public class NotaDebitoTableModel extends AbstractTableModel
{
  private BigDecimal totalIva = BigDecimal.ZERO;
  private BigDecimal porcentajeIva = BigDecimal.ZERO;
  private List<SubtotalImpuesto> listaIva = new ArrayList();
  private ArrayList<Object[]> data;
  private String[] columnNames = { "Nro.", "Razón de la modificación", "Valor de la modificación", "Acción" };
  
  public NotaDebitoTableModel()
  {
    this.data = new ArrayList();
  }
  
  public NotaDebitoTableModel(Integer numero, String razon, BigDecimal valorModificacion)
  {
    Object[] item = { numero, razon, valorModificacion, "" };
    this.data.add(item);
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
  
  public Class getColumnClass(int c)
  {
    return getValueAt(0, c).getClass();
  }
  
  public boolean isCellEditable(int row, int col)
  {
    if (col == 0) {
      return false;
    }
    return true;
  }
  
  public void setValueAt(Object value, int row, int col)
  {
    if (this.data.size() == row) {
      row--;
    }
    if (col == 2)
    {
      String id = String.valueOf(getValueAt(row, 0));
      BigDecimal valorModificacion = (BigDecimal)value;
      if (valorModificacion.doubleValue() >= 0.0D)
      {
        ((Object[])this.data.get(row))[col] = value;
        try
        {
          calculaImpuestosProducto(valorModificacion, id, true);
        }
        catch (Exception ex)
        {
          Logger.getLogger(NotaDebitoTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      else
      {
        ((Object[])this.data.get(row))[col] = BigDecimal.ZERO;
      }
    }
    else
    {
      ((Object[])this.data.get(row))[col] = value;
    }
    fireTableCellUpdated(row, col);
  }
  
  public void deleteRow(int fila)
  {
    if (fila == -1) {
      fila = 0;
    }
    Object[] item = (Object[])this.data.get(fila);
    try
    {
      String codigo = String.valueOf((Integer)getValueAt(fila, 0));
      eliminaImpuestosProducto(codigo);
    }
    catch (Exception ex)
    {
      Logger.getLogger(NotaDebitoTableModel.class.getName()).log(Level.SEVERE, null, ex);
    }
    this.data.remove(item);
    fireTableRowsDeleted(fila, fila);
  }
  
  public void addRow(Integer numero, String razon, BigDecimal valorModificacion)
    throws SQLException
  {
    int numRows = this.data.size();
    
    Object[] item = { numero, razon, valorModificacion, "" };
    this.data.add(item);
    
    try
    {
      calculaImpuestosProducto( valorModificacion, numero+"", false);
    }
    catch (Exception ex)
    {
      Logger.getLogger(NotaCreditoModel.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    fireTableRowsInserted(numRows, this.data.size());
  }
  
  private void calculaImpuestosProducto(BigDecimal newCantidad, String contribuyente, boolean old)
    throws SQLException, ClassNotFoundException
  {
    ImpuestoValor iv = new ImpuestoValorSQL().obtenerValorPorCodigo(TipoImpuestoIvaEnum.IVA_VENTA_12.getCode());
    if (iv != null) {
      this.porcentajeIva = BigDecimal.valueOf(iv.getPorcentaje().doubleValue() / 100.0D).setScale(2, RoundingMode.HALF_UP);
    }
    BigDecimal baseimponible = newCantidad.setScale(2, RoundingMode.HALF_UP);
    BigDecimal total = baseimponible.multiply(this.porcentajeIva);
    SubtotalImpuesto subTotal = new SubtotalImpuesto(null, null, contribuyente, this.porcentajeIva, baseimponible, total);
    if (old == true) {
      remueveSubtotal(contribuyente, this.listaIva);
    }
    this.listaIva.add(subTotal);
    this.totalIva = calcularTotal(this.listaIva);
  }
  
  private void eliminaImpuestosProducto(String codigo)
    throws SQLException, ClassNotFoundException
  {
    remueveSubtotal(codigo, this.listaIva);
    this.totalIva = calcularTotal(this.listaIva);
  }
  
  private BigDecimal calcularTotal(List<SubtotalImpuesto> lista)
  {
    BigDecimal total = BigDecimal.ZERO;
    for (SubtotalImpuesto s : lista) {
      total = total.add(s.getBaseImponible());
    }
    return total;
  }
  
  private void remueveSubtotal(String codigo, List<SubtotalImpuesto> lista)
    throws SQLException, ClassNotFoundException
  {
    boolean itemEncontrado = false;
    for (int i = 0; i < lista.size(); i++) {
      if ((((SubtotalImpuesto)lista.get(i)).getCodigo().equals(codigo)) && (!itemEncontrado))
      {
        lista.remove(i);
        itemEncontrado = true;
      }
    }
  }
  
  public List<SubtotalImpuesto> getListaIva()
  {
    return this.listaIva;
  }
  
  public void setListaIva(List<SubtotalImpuesto> listaIva)
  {
    this.listaIva = listaIva;
  }
  
  public BigDecimal getTotalIva()
  {
    return this.totalIva;
  }
  
  public void setTotalIva(BigDecimal totalIva)
  {
    this.totalIva = totalIva;
  }
}
