package comprobantes.table;

import comprobantes.entidades.Producto;
import comprobantes.modelo.DestinatarioProducto;
import comprobantes.doc.guiaRemision.Destinatario;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class DestinatarioTableModel
  extends AbstractTableModel
{
  private ArrayList<Object[]> data;
  private List<DestinatarioProducto> destinatarios;
  private String[] columnNames = { "Número", "Identificación", "Razón Social", "Nro. Comprobante", "Fecha Emisión", "Productos", "Acción" };
  
  public DestinatarioTableModel()
  {
    this.data = new ArrayList();
    this.destinatarios = new ArrayList();
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
    boolean resp = false;
    if ((col == 1) || (col == 6)) {
      resp = true;
    }
    return resp;
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
    
    DestinatarioProducto destinatario = buscarDestinatario((String)item[1]);
    this.destinatarios.remove(destinatario);
    
    fireTableRowsDeleted(fila, fila);
  }
  
  private DestinatarioProducto buscarDestinatario(String codigo)
  {
    DestinatarioProducto encontrado = null;
    for (DestinatarioProducto item : this.destinatarios) {
      if (item.getDestinatario().getIdentificacionDestinatario().equals(codigo)) {
        encontrado = item;
      }
    }
    return encontrado;
  }
  
  public void addRow(int numeroItems, Destinatario destinatario, List<Producto> productos)
  {
    int filas = this.data.size();
    
    Object[] item = { Integer.valueOf(numeroItems), destinatario.getIdentificacionDestinatario(), destinatario.getRazonSocialDestinatario(), destinatario.getNumDocSustento() == null ? "" : destinatario.getNumDocSustento(), destinatario.getFechaEmisionDocSustento() == null ? "" : destinatario.getFechaEmisionDocSustento(), Integer.valueOf(productos.size()), "" };
    
    this.data.add(item);
    this.destinatarios.add(new DestinatarioProducto(destinatario, productos));
    
    fireTableRowsInserted(filas, this.data.size());
  }
  
  public List<DestinatarioProducto> getDestinatarios()
  {
    return this.destinatarios;
  }
  
  public void setDestinatarios(List<DestinatarioProducto> destinatarios)
  {
    this.destinatarios = destinatarios;
  }
}
