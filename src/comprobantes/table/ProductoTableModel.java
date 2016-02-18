package comprobantes.table;
 
import comprobantes.entidades.Producto;
import comprobantes.sql.ProductoSQL;
import comprobantes.util.ValidadorCampos;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class ProductoTableModel
  extends AbstractTableModel
{
  public static final int DECIMALES_IVA_PRESUNTIVO = 2;
  private ArrayList<Object[]> data;
  private List<Producto> productos;
  private String[] columnNames = { "Número", "Cantidad", "Código Principal", "Código Auxiliar", "Descripción", "Acción" };
  
  public ProductoTableModel()
  {
    this.data = new ArrayList();
    this.productos = new ArrayList();
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
    if ((col == 1) || (col == 5)) {
      return true;
    }
    return false;
  }
  
  public void setValueAt(Object value, int row, int col)
  {
    if (this.data.isEmpty()) {
      return;
    }
    if (this.data.size() == row) {
      row--;
    }
    Producto prod = null;
    if (col == 1)
    {
      try
      {
        String codigo = (String)getValueAt(row, 2);
        prod = (Producto)new ProductoSQL().obtenerProducto(codigo, null, null).get(0);
        Producto indexEncontrado = null;
        for (Producto p : this.productos)
        {
          String codLista = p.getCodigoPrincipal();
          if (codLista.equals(prod.getCodigoPrincipal())) {
            indexEncontrado = p;
          }
        }
        this.productos.remove(indexEncontrado);
      }
      catch (Exception ex)
      {
        Logger.getLogger(ProductoTableModel.class.getName()).log(Level.SEVERE, null, ex);
      }
      Double newCantidad = null;
      try
      {
        newCantidad = Double.valueOf(Double.parseDouble(value.toString()));
      }
      catch (NumberFormatException ex)
      {
        JOptionPane.showMessageDialog(new JFrame(), "Valor no permitido", "Se ha producido un error ", 0);
        
        return;
      }
      if (!ValidadorCampos.validarDecimales(value.toString(), 2).booleanValue())
      {
        JOptionPane.showMessageDialog(new JFrame(), "El número máximo de decimales permitido es: ".concat(String.valueOf(2)), "Se ha producido un error ", 0);
        return;
      }
      if (newCantidad.doubleValue() <= 0.0D)
      {
        JOptionPane.showMessageDialog(new JFrame(), "Valor no permitido", "ERROR", 0);
      }
      else
      {
        ((Object[])this.data.get(row))[col] = String.valueOf(value);
        prod.setCantidad(newCantidad);
        this.productos.add(prod);
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
      Producto prod = null;
      String codigo = (String)getValueAt(fila, 2);
      prod = (Producto)new ProductoSQL().obtenerProducto(codigo, null, null).get(0);
      Producto indexEncontrado = null;
      for (Producto p : this.productos)
      {
        String codLista = p.getCodigoPrincipal();
        if (codLista.equals(prod.getCodigoPrincipal()))
        {
          indexEncontrado = p;
          break;
        }
      }
      this.productos.remove(indexEncontrado);
    }
    catch (Exception ex)
    {
      Logger.getLogger(FacturaModel.class.getName()).log(Level.SEVERE, null, ex);
    }
    this.data.remove(item);
    this.data.size();
    fireTableRowsDeleted(fila, fila);
    super.fireTableDataChanged();
  }
  
  public void addRow(int numeroItems, Producto producto)
  {
    int filas = this.data.size();
    String cantidad = "1";
    
    Object[] item = { Integer.valueOf(numeroItems), cantidad, producto.getCodigoPrincipal(), producto.getCodigoAuxiliar(), producto.getNombre(), "" };
    this.data.add(item);
    producto.setCantidad(Double.valueOf(Double.parseDouble(cantidad)));
    this.productos.add(producto);
    
    fireTableRowsInserted(filas, this.data.size());
  }
  
  public List<Producto> getProductos()
  {
    return this.productos;
  }
  
  public void setProductos(List<Producto> productos)
  {
    this.productos = productos;
  }
}
